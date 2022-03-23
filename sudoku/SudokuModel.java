package sudoku;

import java.util.Scanner;
import java.util.Stack;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;

public class SudokuModel {
	// Setting up variables
	int[][] sudoku = new int[0][0];
	int[][][] sudokuStack = new int[10][sudoku.length][sudoku.length];
	int k = 0;
	int n = 0;
	int moves = 0;
	int change = 0;

	// constructor for the model
	public SudokuModel() {
		File file = new File("E:\\Eclibse\\Sudoku\\src\\Puzzle_4_01.dat");


		Scanner scanner;
		// reading the input
		try {
			scanner = new Scanner(file);
			String setup = scanner.next();
			Scanner setupScanner = new Scanner(setup);
			setupScanner.useDelimiter(";");
			// reading k & n
			while (setupScanner.hasNext()) {
				try {
					String str = setupScanner.next();
					k = Integer.parseInt(str);
					str = setupScanner.next();
					n = Integer.parseInt(str);
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
				}
			}
			setupScanner.close();

			// Creating the board
			sudoku = new int[n * k][n * k];
			// Creating variables for looping through input
			sudokuStack = new int[10][sudoku.length][sudoku.length];
			int c = 0;
			int d = 0;
			scanner.nextLine();
			while (scanner.hasNextLine()) {
				// Reads the next line
				String line = scanner.nextLine();
				Scanner lineScanner = new Scanner(line);
				lineScanner.useDelimiter(";");
				while (lineScanner.hasNext()) {
					// Reads the next input on the line, separated by ";"
					String str = lineScanner.next();
					if (str.equals(".")) {
						// If input is ".", convert to a "0"
						sudoku[c][d] = 0;
						// Go to next entry
						d++;
					} else {
						try {
							// If input isn't ".", read the number and insert into array
							sudoku[c][d] = Integer.parseInt(str);
						} catch (NumberFormatException ex) {
							ex.printStackTrace();
						}
						// Go to next entry
						d++;
					}
				}
				// Go to next line, and start from first entry
				c++;
				d = 0;
				lineScanner.close();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int[][] getSudoku() {
		return sudoku;
	}

	public void setSudoku(int[][] board){
		sudoku = board;
	}

	public void setSudokuCell(int x, int y, int value) {
		sudoku[x][y] = value;
	}

	public boolean isFilled() {
		boolean result = true;
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku.length; j++) {
				if (sudoku[i][j] == 0) {
					result = false;
				}
			}
		}
		return result;
	}
	public int getN() {
		return n;
	}
	public int getK() {
		return k;
	}

	public void pushStack(int[][] newBoard){
		for(int i = 0; i < sudoku.length; i++){
			for(int j = 0; j < sudoku.length; j++){
				sudokuStack[moves][i][j] = sudoku[i][j];
			}
		}
		moves ++;
	}

	public int[][] popStack(){
		int[][] temp = new int[sudoku.length][sudoku.length];
		for(int i = 0; i < sudoku.length; i++){
			for(int j = 0; j < sudoku.length; j++){
				temp[i][j] = sudokuStack[moves][i][j];
			}
		}
		moves --;
		return temp;
	}

	public int[][] peekStack(){
		int[][] temp = new int[sudoku.length][sudoku.length];
		for(int i = 0; i < sudoku.length; i++){
			for(int j = 0; j < sudoku.length; j++){
				temp[i][j] = sudokuStack[moves-1][i][j];
			}
		}
		return temp;
	}
	public void preemtiveSets(ArrayList<ArrayList<ArrayList<Integer>>> sudokuPre) {
		change = 0;
		int sizeOfSet = 2;
		int flag = 0;
		int numberOfRuns = 0;
		while (change == 0) {
			ArrayList<Integer> xcord = new ArrayList<Integer>(); //arraylist to find all elements that matches 
			ArrayList<Integer> ycord = new ArrayList<Integer>();
			ArrayList<Integer> xcordSend = new ArrayList<Integer>(); //arraylist i send. Have required size
			ArrayList<Integer> ycordSend = new ArrayList<Integer>();
			ArrayList<Integer> numbers = new ArrayList<Integer>();  //array of numbers for algorithm
				for (int i = 0; i < k*n; i++) {
					for (int j = 0; j < k*n; j++) {
						if(sudokuPre.get(i).get(j).size() == sizeOfSet) { //checks if given square is an arraylist of the size we are intrested in
							numbers = sudokuPre.get(i).get(j);
							xcord.add(i);
							ycord.add(j);
							int skipX = 0;
							int skipY = 0;
								for (int l = skipX; l< k*n; l++) { 
									for (int m = skipY; m< k*n; m++) {
										if(numbers.containsAll(sudokuPre.get(l).get(m)) && (l!=i && m!=j) && sudokuPre.get(l).get(m).size()>1) { //checks if all of another array is in the main array and is larger than 1
											xcord.add(l); //adds if true
											ycord.add(m);			
											}	
										}
									}	
		
				if (xcord.size() >= sizeOfSet) { //checks if xcord is bigger than sets. We are not intrested in sending sets, if they are not bigger
						for (int l = numberOfRuns; l <n*k; l++) { //goes through all numbers in sudoku
							
							if(Collections.frequency(xcord, l) == sizeOfSet) { //checks if there are more in a given xcord 
								for(int m=0; m < xcord.size();m++) {
									if (xcord.get(m) == l) {
										xcordSend.add(xcord.get(m));
										ycordSend.add(ycord.get(m));
									}
								}
								sudokuPre = updateMarkup(sudokuPre,numbers,xcordSend,ycordSend); 
								xcordSend.removeAll(xcordSend);
								ycordSend.removeAll(ycordSend);
							}
							if(Collections.frequency(ycord, l) == sizeOfSet) { //checks if there are more in a gived ycord
								for(int m=0; m < xcord.size();m++) {
									if (ycord.get(m) == l) {
										xcordSend.add(xcord.get(m));
										ycordSend.add(ycord.get(m));
									}
								}
								sudokuPre = updateMarkup(sudokuPre,numbers,xcordSend,ycordSend);
								xcordSend.removeAll(xcordSend);
								ycordSend.removeAll(ycordSend);
							}
							int[] kArray = new int[k*k]; //checks if there are more in a given box
							for(int m=0; m<xcord.size();m++) {
								kArray[(xcord.get(m)%k+1+(ycord.get(m)%k*k+1))-1] += 1;
							}
							for(int m = 0; m<kArray.length; m++) {
								if (kArray[m] == sizeOfSet) {
									for (int p = 0; p<xcord.size(); p++) {
										if((xcord.get(p)%k+1+(ycord.get(p)%k*k+1))-1 == j) {
											xcordSend.add(xcord.get(p));
											ycordSend.add(ycord.get(p));
										}
									}
									sudokuPre = updateMarkup(sudokuPre,numbers,xcordSend,ycordSend);
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
			if (sizeOfSet > 8) {
				break;
			}
		}
	}
	public ArrayList<ArrayList<ArrayList<Integer>>> updateMarkup(ArrayList<ArrayList<ArrayList<Integer>>> sudokuPre, ArrayList<Integer> numbers, ArrayList<Integer> xcord, ArrayList<Integer> ycord) {
		return sudokuPre;
	}

	public int getStackSize(){
		return moves;
	}
}

