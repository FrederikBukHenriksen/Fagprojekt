package sudoku.Model.Solver;


import java.util.ArrayList;

import sudoku.Model.Validity.ValidityInterface;
import sudoku.Model.Validity.ValiditySandwich;
import sudoku.Model.Model;

public class BacktrackAlgorithm implements SolverInterface {
	int[][] sudoku;
	int[][] solvedSudoku = new int [0][0];
	Model model;
	int n;
	int k;
	int[] xSums;
	int[] ySums;
  	boolean solved = false;
	boolean unique = false;
	ValidityInterface validity;
	
	public BacktrackAlgorithm(int n, int k, int[] xSums, int[] ySums, int[][] sudoku,Model model) {
		this.n = n;
		this.k = k;
		this.xSums = xSums;
		this.ySums = ySums;
		this.sudoku = sudoku;
		this.model = model;
		
	}

	protected ArrayList<ArrayList<ArrayList<Integer>>> markUpCells(int[][] sudokuMarkUp) {
		ValidityInterface validity = new ValiditySandwich(sudokuMarkUp, n, k, xSums, ySums);

		ArrayList<ArrayList<ArrayList<Integer>>> markUpBoard = new ArrayList<>();
		for (int j = 0; j < n * k; j++) {
			ArrayList<ArrayList<Integer>> rows = new ArrayList<>();
			for (int i = 0; i < n * k; i++) {
				ArrayList<Integer> markUpsCells = new ArrayList<>();
				rows.add(markUpsCells);
			}
			markUpBoard.add(rows);
		}

		// Overskriv det primitive array's værider til 3D-ArrayList
		int SudukoSize = n * k;
		for (int i = 0; i < SudukoSize; i++) {
			for (int j = 0; j < SudukoSize; j++) {
				if (sudokuMarkUp[i][j] != 0) {
					markUpBoard.get(i).get(j).add(sudokuMarkUp[i][j]);
				}
			}
		}

		// Find mulige tal
		for (int i = 0; i < SudukoSize; i++) {
			for (int j = 0; j < SudukoSize; j++) {
				if (sudokuMarkUp[i][j] == 0) {

					// KOPIER SUDOKUBOARDET
					int[][] copyOfSudoku = new int[sudokuMarkUp.length][];
					for (int p = 0; p < copyOfSudoku.length; ++p) {

						// allocating space for each row of destination array
						copyOfSudoku[p] = new int[sudokuMarkUp[p].length];

						for (int o = 0; o < copyOfSudoku[p].length; ++o) {
							copyOfSudoku[p][o] = sudokuMarkUp[p][o];
						}
					}

					for (int q = 1; q <= n * k; q++) { // Mulige tal som kan indsættes på boarded

						// Indsæt gyldige tal fra 1-9
						copyOfSudoku[i][j] = q;
						if (validity.checkValidity(copyOfSudoku)) {
							markUpBoard.get(i).get(j).add(q);
						}
					}
				}
			}
		}
		//System.out.println(markUpBoard);
		return markUpBoard;
	}

	public void solve() {
		ArrayList<ArrayList<ArrayList<Integer>>> prem = markUpCells(sudoku);
		int loopCount = 0;
		int [][] sudokuSimpleArray = sudoku;
		ValidityInterface validity = new ValiditySandwich(sudoku, n, k, xSums, ySums);
		while (!validity.checkValidity(sudokuSimpleArray) || !model.isFilledLoop(sudokuSimpleArray)) {
			loopCount++;
			prem = loop(prem);
			Converter3D2D(prem);
			if (loopCount > (n * k) * (n * k)) {
				break;
			}
			if (solved == true && unique == false) {
				break;
			}
		}
	}
	
	protected ArrayList<ArrayList<ArrayList<Integer>>> loop(ArrayList<ArrayList<ArrayList<Integer>>> sudokuLoop) {
		//System.out.println(sudokuLoop);
		ValidityInterface validity = new ValiditySandwich(sudoku, n, k, xSums, ySums);
		ArrayList<Integer> returner = new ArrayList<>();	
		ArrayList<ArrayList<ArrayList<Integer>>> sudokuClone = new ArrayList<>(); //creating a sudoku clone
		for (int j = 0; j < n * k; j++) {
			ArrayList<ArrayList<Integer>> rows = new ArrayList<>();
			for (int m = 0; m < n * k; m++) {
				ArrayList<Integer> markUpsCells = new ArrayList<>();
				rows.add(markUpsCells);
			}
			sudokuClone.add(rows);
		}
		for (int i = 0; i < n * k; i++) {
			for (int j = 0; j < n * k; j++) {
				sudokuClone.get(i).get(j).addAll(sudokuLoop.get(i).get(j)); //adding data into sudoku clone
			}
		}
		int currentLoopX = -1;
		int currentLoopY = -1;
		loop://This loop find the first smallest cell (with the least amount of options)
		for (int i = 0; i<n*k; i++) { 
			for (int j = 0; j<n*k; j++) {
				if (sudokuClone.get(i).get(j).size() > 1) {		
					returner.addAll(sudokuClone.get(i).get(j)); //adds all numbers to a returner variable 
					sudokuClone.get(i).get(j).clear();
					sudokuClone.get(i).get(j).add(returner.get(0)); //adds only the first number to the sudoku clone
					returner.remove(returner.get(0));	 //removes the first element from the returner function 
					currentLoopX = i;
					currentLoopY = j;
					break loop;  //breaks out of loop
				}
			}
		}
		if (currentLoopX == -1) { //if no solution was found
			return sudokuLoop;
		}
		int[][] sudokuSimpleArray = Converter3D2D(sudokuClone); //create 2d array, to verify
		if(validity.checkValidity(sudokuSimpleArray) && model.isFilledLoop(sudokuSimpleArray)) { //Checks if it is solved. If it is the first time it is solved, it will return like it was not solved. 
			if (solved == true) {
				unique = false;
				return sudokuClone;
			}
			else {
				solvedSudoku = Converter3D2D(sudokuClone);
				solved = true;
				unique = true;
				sudokuLoop.get(currentLoopX).get(currentLoopY).clear();
				sudokuLoop.get(currentLoopX).get(currentLoopY).addAll(returner);
				return sudokuLoop;
			}
		}
		if(!validity.checkValidity(sudokuSimpleArray)){ //checks if invalid, returns original with returner if invalid
				sudokuLoop.get(currentLoopX).get(currentLoopY).clear();
				sudokuLoop.get(currentLoopX).get(currentLoopY).addAll(returner);
				return sudokuLoop;
			}
		
		while(!validity.checkValidity(sudokuSimpleArray) || !model.isFilledLoop(sudokuSimpleArray)) {//this loop runs till the sudoku is solved
			sudokuClone = loop(sudokuClone); //It calls recursive
			sudokuSimpleArray = Converter3D2D(sudokuClone); //Creates simple sudoku array to verify, and then runs all the verification like before
		
			if (validity.checkValidity(sudokuSimpleArray) && model.isFilledLoop(sudokuSimpleArray)) {
				if (solved == true) {
					unique = false;
					return sudokuClone;
				}
				else {
					for(int l = 0; l< n*k; l++) {
						for(int m = 0; m<n*k; m++) {
							solvedSudoku[l][m] = sudokuClone.get(l).get(m).get(0);
						}
					}
					solved = true;
					unique = true;
					sudokuLoop.get(currentLoopX).get(currentLoopY).clear();
					sudokuLoop.get(currentLoopX).get(currentLoopY).addAll(returner);
					return sudokuLoop;
				}
			
			}
			if (!validity.checkValidity(sudokuSimpleArray)){
				sudokuLoop.get(currentLoopX).get(currentLoopY).clear();
				sudokuLoop.get(currentLoopX).get(currentLoopY).addAll(returner);
				return sudokuLoop;
			
			}
		}			
		
		sudokuLoop.get(currentLoopX).get(currentLoopY).clear();
		sudokuLoop.get(currentLoopX).get(currentLoopY).addAll(returner);
		return sudokuLoop;
	}
	
	public int[][] getSolvedSudoku() {
		return solvedSudoku;
	}


	public boolean getUniqueness() {
		return unique;
	}
	
	protected int[][] Converter3D2D(ArrayList<ArrayList<ArrayList<Integer>>> sudoku3D) {
		int[][] sudoku2D = new int[n*k][n*k];
		for(int l = 0; l< n*k; l++) {
			for(int m = 0; m<n*k; m++) {
				if(sudoku3D.get(l).get(m).size() == 1) {  //only checks our known numbers, not markups
					sudoku2D[l][m] = sudoku3D.get(l).get(m).get(0);
				}
				else {
					sudoku2D[l][m] = 0;
				}
			}
		}
		return sudoku2D;
	}

	@Override
	public boolean isSolved() {
		return solved;
	}
}


