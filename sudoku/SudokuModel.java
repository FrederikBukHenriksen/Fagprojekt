package sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

import javax.swing.JFileChooser;

import java.util.Random;

import sudoku.SudokuBoard.Cell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

public class SudokuModel {
	CrooksAlgorithm crooks; 
	// Setting up variables
	int[][] sudoku = new int[0][0];
	int[][] solvedSudoku = new int [0][0];
	stackObj[] sudokuStack2 = new stackObj[1000];
	stackObj[] redoStack = new stackObj[1000]; 	
	public static int k = 0;
	public static int n = 0;
	public static int[] xSums = new int[n*k];
	public static int[] ySums = new int[n*k];
	int moves = 0;
	int redoes = 0;


	static ArrayList<Cell> failedCoords = new ArrayList<Cell>();

	static SudokuView view;

	// constructor for the model
	public Path findSudokuPath(String s) {//https://stackoverflow.com/questions/51973636/how-to-return-the-file-path-from-the-windows-file-explorer-using-java
		//File file = new File("C:\\Users\\Candytom\\Documents\\GitHub\\sudoku\\Puzzles_1\\Puzzle_3_evil.dat");
			Path file = null;
			JFileChooser jd = s == null ? new JFileChooser() : new JFileChooser(s);
			jd.setDialogTitle("Choose Sudoku you wish to solve");
			int returnVal= jd.showOpenDialog(null);
			/* If user didn't select a file and click ok, return null Path object*/
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				return file = jd.getSelectedFile().toPath();
			}
			return null;
		
	}
	
	public void boardCreater() throws FileNotFoundException, IOException, NumberFormatException, NoSuchElementException  {
		Path file = null;
		file = findSudokuPath("C:\\");
		//System.out.println(file);
		if (file == null){
			System.exit(0);
		}
		Scanner scanner;
		// reading the input
			scanner = new Scanner(file);
			String setup = scanner.next();
			Scanner setupScanner = new Scanner(setup);
			setupScanner.useDelimiter(";");
			// reading k & n
			while (setupScanner.hasNext()) {
				String str = setupScanner.next();
				k = Integer.parseInt(str);
				str = setupScanner.next();
				n = Integer.parseInt(str);

			}
			setupScanner.close();
			if (k > n) {
				System.out.println("Not a valid sudoku-size, k cannot exceed n");
			} else {// Creating the board
				sudoku = new int[n * k][n * k];
				// Creating variables for sandwich Sums
				xSums = new int[n*k];
				ySums = new int[n*k];
				// Creating variables for looping through input
				int c = 0;
				int d = 0;
				scanner.nextLine();
				for(int j = 0; j < n*k; j++) {
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
								// If input isn't ".", read the number and insert into array
								sudoku[c][d] = Integer.parseInt(str);
							// Go to next entry
							d++;
						}
					}
					// Go to next line, and start from first entry
					c++;
					d = 0;
					lineScanner.close();
				}
				if(scanner.hasNextLine()){
					crooks.setSandwich(true);
					String line = scanner.nextLine();
					Scanner lineScanner = new Scanner(line);
					lineScanner.useDelimiter(":");
					int index = 0;
					while(lineScanner.hasNext()){
						String str = lineScanner.next();
							// If input isn't ".", read the number and insert into array
						xSums[index] = Integer.parseInt(str);
						index++;

					}
					lineScanner.close();
					line = scanner.nextLine();
					lineScanner = new Scanner(line);
					lineScanner.useDelimiter(":");
					index = 0;
					while(lineScanner.hasNext()){
						String str = lineScanner.next();
						// If input isn't ".", read the number and insert into array
						ySums[index] = Integer.parseInt(str);
							index++;
					}
				}
			}
			crooks = new CrooksAlgorithm(getN(),getK(),getSudoku(),this);
		
	}
	public SudokuModel(SudokuView view){
		this.view = view;
		
	}


	// Methods for returning N and K
	public int getN() {
		return n;
	}

	public int getK() {
		return k;
	}

	
	public int[][] createSudoku() {

		List<Integer> chooseNumberList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		Random random = new Random();

		int[][] newSudoku = new int[n * k][n * k];

		// Generate a board with aprox. 16 randomly places numbers.
		int numberCounter = 0;
		int maxAttempts = 50;

		while (numberCounter < 17) { // 17 i while-loopet vil medføre 17 tal.
			int x = random.nextInt(n * k);
			int y = random.nextInt(n * k);
			int cellNumber = random.nextInt(n * k) + 1;

			if (maxAttempts == 0) {
				newSudoku = new int[n * k][n * k];
				maxAttempts = 50;
			}

			if (newSudoku[x][y] == 0) {
				if (checkValidity(newSudoku, false, false)) {

					newSudoku[x][y] = cellNumber;
					numberCounter++;

				} else {
					maxAttempts--;
				}
			}
		}

		ArrayList<int[][]> listOfBoards = new ArrayList<>();
		listOfBoards.add(newSudoku);

		for (int i = 0; i < listOfBoards.size(); i++) {
			newSudoku = listOfBoards.get(i);

			for (int x = 0; x < (getN() * getK()); x++) {
				for (int y = 0; y < (getN() * getK()); y++) {
					int[][] newSudokuTemp = newSudoku.clone();

					boolean numFound = false;
					for (int num = 0; num < chooseNumberList.size(); num++) {
						newSudokuTemp[x][y] = chooseNumberList.get(num);
						if (checkValidity(newSudokuTemp, false, false) && !numFound) {
							int save[][] = newSudoku.clone();
							save[x][y] = chooseNumberList.get(num);
							listOfBoards.add(save);
							// newSudoku[x][y] = chooseNumberList.get(num);
						}

					}
				}
			}

		}

		// for (int i = 0; i < newSudoku[0].length; i++) {
		// for (int j = 0; j < newSudoku[1].length; j++) {

		// while (true) {
		// int number = chooseNumberList.get(random.nextInt(chooseNumberList.size()));
		// int[] square = getSquare(i, j, newSudoku);
		// int[] peers = getPeers(i, j, newSudoku);
		// boolean cond1 = Arrays.stream(square).anyMatch(n -> n != number);
		// boolean cond2 = Arrays.stream(peers).anyMatch(n -> n != number);

		// if (cond1 && cond2) {
		// newSudoku[i][j] = number;
		// break;
		// }
		// }

		// }
		// }

		return newSudoku;
	}

	public int[] getSquare(int axis0, int axis1, int[][] board) {
		ArrayList<Integer> square = new ArrayList<Integer>();

		// Determent the position of upper left corner of the square
		int squareX = axis0 / n; // 2
		int squareY = axis1 / n; // 1

		// Run through the scare
		for (int i = squareX * n; i < squareX * n + n; i++) {
			for (int j = squareY * n; j < squareY * n + n; j++) {
				square.add(board[i][j]);
			}
		}
		// Convert arraylist to primitive array
		return square.stream().mapToInt(i -> i).toArray();

	}

	public int[] getPeers(int axis0, int axis1, int[][] board) {
		ArrayList<Integer> peers = new ArrayList<Integer>();

		// Run through the cells two axis
		for (int i = 0; i < n * k; i++) {
			peers.add(board[axis0][i]);
		}
		for (int i = 0; i < n * k; i++) {
			peers.add(board[i][axis1]);
		}

		// Remove the duplicated cell itself.
		peers.remove((Integer) board[axis0][axis1]);

		return peers.stream().mapToInt(i -> i).toArray();
	}

	class stackObj {
		int xCoord = 0;
		int yCoord = 0;
		int prevVal = 0;
		int newVal = 0;

		public stackObj(int a, int b, int c, int d) {
			xCoord = a;
			yCoord = b;
			prevVal = c;
			newVal = d;
		}

		public int getX() {
			return xCoord;
		}

		public int getY() {
			return yCoord;
		}

		public int getPrevVal() {
			return prevVal;
		}

		public int getNewVal() {
			return newVal;
		}
	}
	public int[][] getSudoku() {
		return sudoku;
	}

	// Push for new stack
	public void pushStack2(stackObj x) {
		sudokuStack2[moves] = x;
		moves++;
	}

	// Push for redo-stack
	public void pushRedoStack(stackObj x) {
		redoStack[redoes] = x;
		redoes++;
	}



	// new pop method
	public stackObj popStack2() {
		stackObj temp = sudokuStack2[moves - 1];
		sudoku[temp.getX()][temp.getY()] = temp.prevVal;
		moves--;
		return temp;
	}

	// Pop for redo-stack
	public stackObj popRedoStack() {
		stackObj temp = redoStack[redoes - 1];
		sudoku[temp.getX()][temp.getY()] = temp.newVal;
		redoes--;
		return temp;
	}

	public int[][] peekStack() {
		return getSudoku();
	}

	// Method for clearing the redo stack
	public void clearRedoStack() {
		redoStack = new stackObj[1000];
		redoes = 0;
	}

	// Returns the size of the stack
	public int getStackSize() {
		return moves;
	}

	// CreateStackObject method
	public stackObj createStackObj(int x, int y, int oldVal, int newVal) {
		return new stackObj(x, y, oldVal, newVal);
	}

	// Method for returning coords of the last change on the sudokuStack
	public int[] getStackCoords(){
		int[] result = new int[2];
		stackObj temp = sudokuStack2[moves - 1];
		result[0] = temp.getX();
		result[1] = temp.getY();
		return result;
	}

	// Method for returning coords of the last change on the redoStack
	public int[] getRedoStackCoords(){
		int[] result = new int[2];
		stackObj temp = redoStack[redoes - 1];
		result[0] = temp.getX();
		result[1] = temp.getY();
		return result;
	}

	// Method for updating the markUp board, given a set of possible entries and
	// their coordinates
	
	public boolean checkValidity(int[][] sudoku, boolean print, boolean changeColours) {
		failedCoords.clear();
		boolean valid = new Boolean(true);
		// Grid for storing already found values
		// int[][] sortedGrid = new int[sudoku.length+1][sudoku.length+1];
		int[][] sortedGrid = new int[sudoku.length][sudoku.length];
		// for(int i = sortedGrid.length-1; i >= 0; i--){
		for (int i = sortedGrid.length - 1; i >= 0; i--) {
			for (int j = 0; j < sortedGrid.length; j++) {
				sortedGrid[i][j] = 0;
			}
		}

		// Checking rows for duplicates
		for (int i = 0; i < sudoku.length; i++) {
			int rowSum = 0;
			boolean inSandwich = false;
			boolean sandwichFilled = false;
			for (int j = 0; j < sudoku.length; j++) {
				int cur = (sudoku[i][j]);
				if (cur != 0) {
					if (sortedGrid[i][cur - 1] == 0) {
						sortedGrid[i][cur - 1] = 1;
					} else {
						valid = false;
						if (print) {
							// System.out.println("Row: x: " + i + ", j: " + j);
						}
						failedCoords.add(view.getCellFromCoord(i, j));
						for (int o = 0; o < j; o++) {
							if (sudoku[i][o] == cur) {
								if (!(failedCoords.contains(view.getCellFromCoord(i, o)))) {
									failedCoords.add(view.getCellFromCoord(i, o));
								}
							}
						}
					}
					if((cur == 1 || cur == 9) && !inSandwich){
						inSandwich = true;
						sandwichFilled = true;
						continue;
					}
					if((cur == 1 || cur == 9) && inSandwich){
						inSandwich = false;
						continue;
					}
					if(inSandwich){
						rowSum += cur;
					}
				}
				else if (cur == 0 && inSandwich){
					sandwichFilled = false;
				}
			}
			if(rowSum != ySums[i] && crooks.getSandwich() && sandwichFilled && !inSandwich){
				valid = false;
			}
		}

		/*
		 * for(int i = 0; i < sortedGrid.length; i++){
		 * for(int k = 0; k < sortedGrid.length; k++){
		 * System.out.print(sortedGrid[i][k] + " ");
		 * }
		 * System.out.println();
		 * }
		 * System.out.println();
		 */

		// Resetting the sorted grid
		for (int i = sortedGrid.length - 1; i >= 0; i--) {
			for (int j = 0; j < sortedGrid.length; j++) {
				sortedGrid[i][j] = 0;
			}
		}

		// Checking columns for duplicates
		for (int j = 0; j < sudoku.length; j++) {
			int rowSum = 0;
			boolean inSandwich = false;
			boolean sandwichFilled = false;
			for (int i = 0; i < sudoku.length; i++) {
				int cur = (sudoku[i][j]);
				if (cur != 0) {
					if (sortedGrid[j][cur - 1] == 0) {
						sortedGrid[j][cur - 1] = 1;
					} else {
						valid = false;
						if (print) {
							// System.out.println("Row: x: " + i + ", y: " + j);
						}
						failedCoords.add(view.getCellFromCoord(i, j));
						for (int o = 0; o < i; o++) {
							if (sudoku[o][j] == cur) {
								if (!(failedCoords.contains(view.getCellFromCoord(o, j)))) {
									failedCoords.add(view.getCellFromCoord(o, j));
								}
							}
						}
					}
					if((cur == 1 || cur == 9) && !inSandwich){
						inSandwich = true;
						sandwichFilled = true;
						continue;
					}
					if((cur == 1 || cur == 9) && inSandwich){
						inSandwich = false;
						continue;
					}
					if(inSandwich){
						rowSum += cur;
					}
				}
				else if (cur == 0 && inSandwich){
					sandwichFilled = false;
				}
			}
			if(rowSum != xSums[j] && crooks.getSandwich() && sandwichFilled && !inSandwich){
				valid = false;
			}
		}

		// Resetting the sorted grid
		for (int i = sortedGrid.length - 1; i >= 0; i--) {
			for (int j = 0; j < sortedGrid.length; j++) {
				sortedGrid[i][j] = 0;
			}
		}


		for (int l = 0; l < k * k; l++) {

			for (int i = 0; i < n; i++) {

				for (int j = 0; j < n; j++) {// l/k benytter sig af hvordan java runder op. det er n hvor mange felter
												// den skal rygge, og den skal rygge det hver gang l har bev�get sig k
												// felter.
					int cur = sudoku[(i + n * (l / k))][(j + n * l) % (k * n)];
					if (cur != 0) {
						sortedGrid[l][cur - 1] = sortedGrid[l][cur - 1] + 1;
					}
				}
			}
		}
		for (int l = 0; l < k * k; l++) {

			for (int i = 0; i < n; i++) {

				for (int j = 0; j < n; j++) {// l/k benytter sig af hvordan java runder op. det er n hvor mange felter
												// den skal rygge, og den skal rygge det hver gang l har bev�get sig k
												// felter.
					int cur = sudoku[(i + n * (l / k))][(j + n * l) % (k * n)];
					if (cur != 0) {
						if (sortedGrid[l][cur - 1] > 1) {
							valid = false;
							if (print) {
								// System.out.println("Square: j: " +(i + n * (l / k))+ ", i: " + (j + n * l) %
								// (k * n));
							}
							failedCoords.add(view.getCellFromCoord((i + n * (l / k)), (j + n * l) % (k * n)));

						}
					}
				}
			}
		}
		/*
		 * for(int i = 0; i < sortedGrid.length; i++){
		 * for(int k = 0; k < sortedGrid.length; k++){
		 * System.out.print(sortedGrid[i][k] + " ");
		 * }
		 * System.out.println();
		 * }
		 */
		if (print) {
			for (int i = 0; i < failedCoords.size(); i++) {
				// System.out.print(view.getCellCoordinate(failedCoords.get(i))[0] + "," +
				// view.getCellCoordinate(failedCoords.get(i))[1] + " ");
			}
		}
		if(changeColours){
			for (int i = 0; i < failedCoords.size(); i++) {
				failedCoords.get(i).conflict();
			}
		}
		return valid;
	}
	// Method for checking if the entire board is filled
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
	public void setSudoku(int[][] board) {
		sudoku = board;
	}

	// Method for changing a single cell in the board
	public void setSudokuCell(int x, int y, int value) {
		sudoku[x][y] = value;
	}

	public boolean isFilledLoop(int[][] sudoku) {
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

	public void printSudoku(int[][] sudokuBoard) {
		for (int i = 0; i < sudokuBoard.length; i++) {
			for (int k = 0; k < sudokuBoard.length; k++) {
				System.out.print(sudokuBoard[i][k] + " ");
			}
			System.out.println();
		}
	}


	public static ArrayList<Cell> getFailedCells() {
		return failedCoords;
	}
}
