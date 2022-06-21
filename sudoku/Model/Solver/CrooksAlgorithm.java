package sudoku.Model.Solver;

import java.util.ArrayList;
import java.util.Collections;

import sudoku.Controller.Exceptions.ExceptionNoSolutionAvailable;
import sudoku.Model.Model;
import sudoku.Model.Validity.ValidityClassic;
import sudoku.Model.Validity.ValidityInterface;
import sudoku.Model.Validity.ValiditySandwich;

public class CrooksAlgorithm implements SolverInterface {
	boolean solved = false;
	boolean unique = false;

	boolean change = false;
	int[][] sudoku;
	int[][] solvedSudoku = new int[0][0];
	Model model;
	int n;
	int k;

	public CrooksAlgorithm(int n, int k, int[][] sudoku, Model model) {
		this.n = n;
		this.k = k;
		this.sudoku = sudoku;
		this.model = model;
	}
	
	/*
	 * Author: Christian
	 * Function: Starts the solving progress.
	 * Inputs: void, but uses sudoku, n and k as global variables
	 * Outputs: void, but creates a solved sudoku, that can be got with a get function
	 */
	public void solve() {
		ValidityInterface validity = new ValidityClassic(sudoku, n, k);
		solved = false;
		if (!(model.getSandwich() || n > 4 || n != k)) {
			ArrayList<ArrayList<ArrayList<Integer>>> prem = preemtiveSets(singleton(markUpCells()));
			change = true;
			while (change == true) {
				prem = preemtiveSets(singleton(prem));
			}
			solvedSudoku = new int[n * k][n * k];
			int[][] sudokuSimpleArray = new int[n * k][n * k];
			for (int l = 0; l < n * k; l++) {
				for (int m = 0; m < n * k; m++) {
					if (prem.get(l).get(m).size() == 1) {
						sudokuSimpleArray[l][m] = prem.get(l).get(m).get(0);
					} else {
						sudokuSimpleArray[l][m] = 0;
					}
				}
			}
			int loopCount = 0;
			if (validity.checkValidity(sudokuSimpleArray) && model.isFilledLoop(sudokuSimpleArray)) {
				for (int l = 0; l < n * k; l++) {
					for (int m = 0; m < n * k; m++) {
						solvedSudoku[l][m] = prem.get(l).get(m).get(0);
					}
				}
				unique = true;
				solved = true;
			}
			while (!validity.checkValidity(sudokuSimpleArray) || !model.isFilledLoop(sudokuSimpleArray)) {
				loopCount++;
				prem = loop(prem);
				for (int l = 0; l < n * k; l++) {
					for (int m = 0; m < n * k; m++) {
						if (prem.get(l).get(m).size() == 1) {
							sudokuSimpleArray[l][m] = prem.get(l).get(m).get(0);
						} else {
							sudokuSimpleArray[l][m] = 0;
						}
					}
				}
				if (loopCount > (n * k) * (n * k)) {
					break;
				}
				if (solved == true && unique == false) {
					break;
				}
			}

		}
	}

	// Method for getting the board

	/*
	 * Author: Christian
	 * Function: runs all base functions for as long as there are changes
	 * Inputs: Takes 3d array of sudoku
	 * Outputs: Updated 3d array of sudoku
	 */
	public ArrayList<ArrayList<ArrayList<Integer>>> createPreemtiveSets() {
		ArrayList<ArrayList<ArrayList<Integer>>> prem = preemtiveSets(singleton(markUpCells()));
		while (change) {
			prem = preemtiveSets(singleton(prem));
		}
		return prem;
	}

	// Method for setting the entire board
	/*
	 * Author: Christian
	 * Function: creates preemtive sets..
	 * Inputs: Takes 3d array of sudoku
	 * Outputs: Updated 3d array of sudoku
	 */
	public ArrayList<ArrayList<ArrayList<Integer>>> preemtiveSets(ArrayList<ArrayList<ArrayList<Integer>>> sudokuPre) {
		change = false;
		int sizeOfSet = 2;
		while (change == false) {
			ArrayList<Integer> xcord = new ArrayList<Integer>(); // arraylist to find all elements that matches
			ArrayList<Integer> ycord = new ArrayList<Integer>();
			ArrayList<Integer> xcordSend = new ArrayList<Integer>(); // arraylist i send. Have required size
			ArrayList<Integer> ycordSend = new ArrayList<Integer>();
			ArrayList<Integer> numbers = new ArrayList<Integer>(); // array of numbers for algorithm
			for (int i = 0; i < k * n; i++) {
				for (int j = 0; j < k * n; j++) {
					xcord = new ArrayList<Integer>();
					ycord = new ArrayList<Integer>();
					if (sudokuPre.get(i).get(j).size() == sizeOfSet) { // checks if given square is an arraylist of the
																		// size we are intrested in
						numbers = sudokuPre.get(i).get(j);
						// System.out.println("set: " + numbers);
						xcord.add(i);
						ycord.add(j);
						int skipX = 0;
						int skipY = 0;
						for (int l = skipX; l < k * n; l++) {
							for (int m = skipY; m < k * n; m++) {
								if (numbers.containsAll(sudokuPre.get(l).get(m)) && (l != i || m != j)
										&& sudokuPre.get(l).get(m).size() > 1) { // checks if all of another array is in
																					// the main array and is larger than
																					// 1
									xcord.add(l); // adds if true
									ycord.add(m);
								}
							}
						}

						if (xcord.size() >= sizeOfSet) { // checks if xcord is bigger than sets. We are not intrested in
															// sending sets, if they are not bigger

							for (int l = 0; l < n * k; l++) { // goes through all numbers in sudoku
								if (Collections.frequency(xcord, l) == sizeOfSet) { // checks if there are more in a
																					// given xcord
									for (int m = 0; m < xcord.size(); m++) {
										if (xcord.get(m) == l) {
											xcordSend.add(xcord.get(m));
											ycordSend.add(ycord.get(m));
										}
									}
									// System.out.print(numbers);
									sudokuPre = updateMarkup(sudokuPre, numbers, xcordSend, ycordSend, 2);
									xcordSend.removeAll(xcordSend);
									ycordSend.removeAll(ycordSend);
								}
								if (Collections.frequency(ycord, l) == sizeOfSet) { // checks if there are more in a
																					// gived ycord

									for (int m = 0; m < xcord.size(); m++) {
										if (ycord.get(m) == l) {
											xcordSend.add(xcord.get(m));
											ycordSend.add(ycord.get(m));
										}
									}
									// System.out.println("ycord: " +ycord);
									sudokuPre = updateMarkup(sudokuPre, numbers, xcordSend, ycordSend, 1);
									xcordSend.removeAll(xcordSend);
									ycordSend.removeAll(ycordSend);
								}
								int[] kArray = new int[k * k + 1]; // checks if there are more in a given box
								for (int m = 0; m < xcord.size(); m++) {
									kArray[(ycord.get(m) / k) + (xcord.get(m) / k) * (k) + 1] += 1;
								}
								for (int m = 0; m < kArray.length; m++) {
									if (kArray[m] == sizeOfSet) {
										for (int p = 0; p < xcord.size(); p++) {
											if ((ycord.get(p) / k) + (xcord.get(p) / k) * (k) + 1 == m) {

												xcordSend.add(xcord.get(p));
												ycordSend.add(ycord.get(p));
											}
										}

										sudokuPre = updateMarkup(sudokuPre, numbers, xcordSend, ycordSend, 3);
										xcordSend.removeAll(xcordSend);
										ycordSend.removeAll(ycordSend);
									}
								}
							}
						}
					}
				}
			}
			sizeOfSet++;
			if (sizeOfSet > n * k - 1) {
				break;
			}
		}
		return sudokuPre;
	}

	/*
	 * Author: Rasmus
	 * Function: Updates Markup of sudoku
	 * Inputs: Takes 3d array of sudoku and mode, that explains what the code needs to do.
	 * Outputs: Updated 3d array of sudoku
	 */
	public ArrayList<ArrayList<ArrayList<Integer>>> updateMarkup(ArrayList<ArrayList<ArrayList<Integer>>> markupBoard,
			ArrayList<Integer> set, ArrayList<Integer> xCoords, ArrayList<Integer> yCoords, int mode) {

		if (mode == 1) {
			for (int i = 0; i < sudoku.length; i++) {
				if (!(xCoords.contains(i))) {
					int size = markupBoard.get(i).get(yCoords.get(0)).size();
					markupBoard.get(i).get(yCoords.get(0)).removeAll(set);
					if (!(size == markupBoard.get(i).get(yCoords.get(0)).size())) {
						change = true;
					}
				}
			}
		}

		if (mode == 2) {
			for (int i = 0; i < sudoku.length; i++) {
				if (!(yCoords.contains(i))) {
					int size = markupBoard.get(xCoords.get(0)).get(i).size();
					markupBoard.get(xCoords.get(0)).get(i).removeAll(set);

					if (!(size == markupBoard.get(xCoords.get(0)).get(i).size())) {
						change = true;
					}
				}
			}
		}

		if (mode == 3) {
			boolean delete = true;
			for (int i = (xCoords.get(0) - xCoords.get(0) % n); i < ((xCoords.get(0) - xCoords.get(0) % n) + n); i++) {
				for (int j = (yCoords.get(0) - yCoords.get(0) % n); j < ((yCoords.get(0) - yCoords.get(0) % n)
						+ n); j++) {
					for (int l = 0; l < xCoords.size(); l++) {
						if (((i == xCoords.get(l)) && j == yCoords.get(l))) {
							delete = false;
							break;

						}
					}
					if (delete) {
						int size = markupBoard.get(i).get(j).size();
						markupBoard.get(i).get(j).removeAll(set);

						if (!(size == markupBoard.get(i).get(j).size())) {
							change = true;
						}
					}
					delete = true;
				}
			}
		}
		return markupBoard;
	}
	
	/*
	 * Author: Frederik
	 * Function: Create original markup, is only run once per sudoku
	 * Inputs: Uses global sudoku, and n and k.
	 * Outputs: Creates 3d array list of sudoku with markups
	 */
	public ArrayList<ArrayList<ArrayList<Integer>>> markUpCells() {
		ValidityInterface validity = new ValidityClassic(sudoku, n, k);
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
				if (sudoku[i][j] != 0) {
					markUpBoard.get(i).get(j).add(sudoku[i][j]);
				}
			}
		}

		// Find mulige tal
		for (int i = 0; i < SudukoSize; i++) {
			for (int j = 0; j < SudukoSize; j++) {
				if (sudoku[i][j] == 0) {

					// KOPIER SUDOKUBOARDET
					int[][] copyOfSudoku = new int[sudoku.length][];
					for (int p = 0; p < copyOfSudoku.length; ++p) {

						// allocating space for each row of destination array
						copyOfSudoku[p] = new int[sudoku[p].length];

						for (int o = 0; o < copyOfSudoku[p].length; ++o) {
							copyOfSudoku[p][o] = sudoku[p][o];
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
		return markUpBoard;
	}
	/*
	 * Author: Christian
	 * Function: Creates singleton variables. 
	 * Inputs: 3d array list of sudoku
	 * Outputs: updated 3d array list of sudoku
	 */
	public ArrayList<ArrayList<ArrayList<Integer>>> singleton(ArrayList<ArrayList<ArrayList<Integer>>> sudokuSing) {
		// System.out.println("start: "+sudokuSing);
		for (int i = 0; i < n * k; i++) {
			for (int j = 0; j < n * k; j++) {
				if (sudokuSing.get(i).get(j).size() == 1) {

					for (int l = 0; l < n * k; l++) {
						if (j != l) {
							int size = sudokuSing.get(i).get(l).size();
							sudokuSing.get(i).get(l).remove(sudokuSing.get(i).get(j).get(0));
							if (!(size == sudokuSing.get(i).get(l).size())) {
								change = true;
							}
						}
						if (i != l) {
							int size = sudokuSing.get(l).get(j).size();
							sudokuSing.get(l).get(j).remove(sudokuSing.get(i).get(j).get(0));
							if (!(size == sudokuSing.get(i).get(l).size())) {
								change = true;
							}
						}
						if (i != (i / n) * n + l % n || j != (j / n) * n + l % n) {
							int size = sudokuSing.get((i / n) * n + l % n).get((j / n) * n + l % n).size();
							sudokuSing.get((i / n) * n + l % n).get((j / n) * n + l % n)
									.remove(sudokuSing.get(i).get(j).get(0));
							if (!(size == sudokuSing.get((i / n) * n + l % n).get((j / n) * n + l % n).size())) {
								change = true;
							}
						}

					}

				}
			}
		}
		return sudokuSing;
	}

	/*
	 * Author: Christian
	 * Function: Does Backtracking on crooks algorithm 
	 * Inputs: 3d array list of sudoku
	 * Outputs: updated 3d array list of sudoku
	 */
	public ArrayList<ArrayList<ArrayList<Integer>>> loop(ArrayList<ArrayList<ArrayList<Integer>>> sudokuLoop) {
		// System.out.println(sudokuLoop);
		ValidityInterface validity = new ValidityClassic(sudoku, n, k);
		int sizeOfArrayLoop = 2; // initializing variables
		int currentLoopX = -1;
		int currentLoopY = -1;
		ArrayList<Integer> returner = new ArrayList<>();
		ArrayList<ArrayList<ArrayList<Integer>>> sudokuClone = new ArrayList<>(); // creating a sudoku clone
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
				sudokuClone.get(i).get(j).addAll(sudokuLoop.get(i).get(j)); // adding data into sudoku clone
			}
		}
		loop: // This loop find the first smallest cell (with the least amount of options)
		while (sizeOfArrayLoop < n * k) {
			for (int i = 0; i < n * k; i++) {
				for (int j = 0; j < n * k; j++) {
					if (sudokuClone.get(i).get(j).size() == sizeOfArrayLoop) {
						returner.addAll(sudokuClone.get(i).get(j)); // adds all numbers to a returner variable
						sudokuClone.get(i).get(j).clear();
						sudokuClone.get(i).get(j).add(returner.get(0)); // adds only the first number to the sudoku
																		// clone
						returner.remove(returner.get(0)); // removes the first element from the returner function
						change = true;
						currentLoopX = i;
						currentLoopY = j;
						break loop; // breaks out of loop
					}
				}
			}
			sizeOfArrayLoop++;
		}
		if (currentLoopX == -1) { // if no solution was found
			return sudokuLoop;
		}
		change = true;
		while (change == true) {
			sudokuClone = preemtiveSets(singleton(sudokuClone)); // does preemtivesets and singleton
			// System.out.println(sudokuClone);
			for (int l = 0; l < n * k; l++) {
				for (int m = 0; m < n * k; m++) {
					if (sudokuClone.get(l).get(m).size() == 0) {
						sudokuLoop.get(currentLoopX).get(currentLoopY).clear(); // returns unchanged loop with returner,
																				// if sudokuClone finds a cell with no
																				// possibilities
						sudokuLoop.get(currentLoopX).get(currentLoopY).addAll(returner);
						return sudokuLoop;
					}
				}
			}
		}
		int[][] sudokuSimpleArray = Converter3D2D(sudokuClone); // create 2d array, to verify
		if (validity.checkValidity(sudokuSimpleArray) && model.isFilledLoop(sudokuSimpleArray)) { // Checks
																									// if it
																									// is
																									// solved.
																									// If it
																									// is
																									// the
																									// first
																									// time
																									// it is
																									// solved,
																									// it
																									// will
																									// return
																									// like
																									// it
																									// was
																									// not
																									// solved.
			if (solved == true) {
				unique = false;
				return sudokuClone;
			} else {
				for (int l = 0; l < n * k; l++) {
					for (int m = 0; m < n * k; m++) {
						solvedSudoku[l][m] = sudokuClone.get(l).get(m).get(0);
					}
				}
				solved = true;
				unique = true;
				sudokuLoop.get(currentLoopX).get(currentLoopY).clear();
				sudokuLoop.get(currentLoopX).get(currentLoopY).addAll(returner);
				change = true;
				return sudokuLoop;
			}
		}
		if (!validity.checkValidity(sudokuSimpleArray)) { // checks if invalid, returns original with
															// returner if invalid
			sudokuLoop.get(currentLoopX).get(currentLoopY).clear();
			sudokuLoop.get(currentLoopX).get(currentLoopY).addAll(returner);
			return sudokuLoop;
		}

		while (!validity.checkValidity(sudokuSimpleArray) || !model.isFilledLoop(sudokuSimpleArray)) {// this
																										// loop
																										// runs
																										// till
																										// the
																										// sudoku
																										// is
																										// solved
			sudokuClone = loop(sudokuClone); // It calls recursive
			sudokuSimpleArray = Converter3D2D(sudokuClone); // Creates simple sudoku array to verify, and then runs all
															// the verification like before

			if (validity.checkValidity(sudokuSimpleArray) && model.isFilledLoop(sudokuSimpleArray)) {
				if (solved == true) {
					unique = false;
					return sudokuClone;
				} else {
					for (int l = 0; l < n * k; l++) {
						for (int m = 0; m < n * k; m++) {
							solvedSudoku[l][m] = sudokuClone.get(l).get(m).get(0);
						}
					}
					solved = true;
					unique = true;
					sudokuLoop.get(currentLoopX).get(currentLoopY).clear();
					sudokuLoop.get(currentLoopX).get(currentLoopY).addAll(returner);
					change = true;
					return sudokuLoop;
				}

			}
			if (!validity.checkValidity(sudokuSimpleArray)) {
				sudokuLoop.get(currentLoopX).get(currentLoopY).clear();
				sudokuLoop.get(currentLoopX).get(currentLoopY).addAll(returner);
				return sudokuLoop;

			}
		}

		sudokuLoop.get(currentLoopX).get(currentLoopY).clear();
		sudokuLoop.get(currentLoopX).get(currentLoopY).addAll(returner);
		return sudokuLoop;
	}

	/*
	 * Author: Christian
	 * Function: Converts 3d array list to 2d array
	 * Inputs: 3d array list of sudoku
	 * Outputs: simple 2d array
	 */
	public int[][] Converter3D2D(ArrayList<ArrayList<ArrayList<Integer>>> sudoku3D) {
		int[][] sudoku2D = new int[n * k][n * k];
		for (int l = 0; l < n * k; l++) {
			for (int m = 0; m < n * k; m++) {
				if (sudoku3D.get(l).get(m).size() == 1) { // only checks our known numbers, not markups
					sudoku2D[l][m] = sudoku3D.get(l).get(m).get(0);
				} else {
					sudoku2D[l][m] = 0;
				}
			}
		}
		return sudoku2D;
	}
	/*
	 * Author: Christian, Updates by Frederik
	 * Function: Getter function for uniqueness
	 * Inputs: Takes nothing
	 * Outputs: returns if sudoku is unique
	 */
	public boolean getUniqueness() throws Exception {
		if (!isSolved()) {
			throw new ExceptionNoSolutionAvailable();
		}
		return unique;
	}
	/*
	 * Author: Christian, Updates by Frederik
	 * Function: Getter function for solved sudoku
	 * Inputs: Takes nothing
	 * Outputs: returns solved sudoku
	 */
	public int[][] getSolvedSudoku() throws Exception {
		if (!isSolved()) {
			throw new ExceptionNoSolutionAvailable();
		}
		return solvedSudoku;
	}

	/*
	 * Author: Christian
	 * Function: Getter function for if sudoku is solved
	 * Inputs: Takes nothing
	 * Outputs: returns if sudoku is solved
	 */
	public boolean isSolved() {
		return solved;
	}

}
