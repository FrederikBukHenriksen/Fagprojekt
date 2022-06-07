package sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
	// Setting up variables
	int[][] sudoku = new int[0][0];
	stackObj[] sudokuStack2 = new stackObj[1000];
	stackObj[] redoStack = new stackObj[1000];
  	int[][] solvedSudoku = new int [0][0];
	public static int k = 0;
	public static int n = 0;
	public static int[] xSums = new int[n*k];
	public static int[] ySums = new int[n*k];
	int moves = 0;
	int redoes = 0;
  	boolean solved = false;
	boolean unique = false;
	boolean change = false;
	boolean isSandwich = false;
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
	public void boardCreater() {
		Path file = null;
		file = findSudokuPath("C:\\");
		if (file == null){
			System.exit(0);
		}
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
					//ex.printStackTrace();
					boardCreater();
					return;
				}
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
							try {
								// If input isn't ".", read the number and insert into array
								sudoku[c][d] = Integer.parseInt(str);
							} catch (NumberFormatException ex) {	
								boardCreater();
								return;
								//ex.printStackTrace();
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
				if(scanner.hasNextLine()){
					isSandwich = true;
					String line = scanner.nextLine();
					Scanner lineScanner = new Scanner(line);
					lineScanner.useDelimiter(":");
					int index = 0;
					while(lineScanner.hasNext()){
						String str = lineScanner.next();
						try {
							// If input isn't ".", read the number and insert into array
							xSums[index] = Integer.parseInt(str);
							index++;
						} catch (NumberFormatException ex) {
							boardCreater();
							return;
						}
					}
					lineScanner.close();
					line = scanner.nextLine();
					lineScanner = new Scanner(line);
					lineScanner.useDelimiter(":");
					index = 0;
					while(lineScanner.hasNext()){
						String str = lineScanner.next();
						try {
							// If input isn't ".", read the number and insert into array
							ySums[index] = Integer.parseInt(str);
							index++;
						} catch (NumberFormatException ex) {
							
							boardCreater();
							return;
						}
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			boardCreater();
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			boardCreater();
			return;
		}
	}
	public SudokuModel(SudokuView view){
		this.view = view;
		boardCreater();
		
	}

	public void solver() {
		if(! (isSandwich || getN()>4 || getN() != getK())){
		ArrayList<ArrayList<ArrayList<Integer>>> prem = preemtiveSets(singleton(markUpCells()));
		change = true;
		while (change == true) {
			prem = preemtiveSets(singleton(prem));
		}
		solvedSudoku = new int[n*k][n*k];
		int[][]sudokuSimpleArray = new int[n*k][n*k];
		for(int l = 0; l< n*k; l++) {
			for(int m = 0; m<n*k; m++) {
				if(prem.get(l).get(m).size() == 1) {
					sudokuSimpleArray[l][m] = prem.get(l).get(m).get(0);
				} else {
					sudokuSimpleArray[l][m] = 0;
				}
			}
		}
		int loopCount = 0;
		if(checkValidity(sudokuSimpleArray, false) && isFilledLoop(sudokuSimpleArray)) {
			for(int l = 0; l< n*k; l++) {
				for(int m = 0; m<n*k; m++) {
					solvedSudoku[l][m]=prem.get(l).get(m).get(0);
				}
			}
			unique = true;
		}
		while(!checkValidity(sudokuSimpleArray, false) || !isFilledLoop(sudokuSimpleArray)) {
			loopCount++;
			prem = loop(prem);
			for(int l = 0; l< n*k; l++) {
				for(int m = 0; m<n*k; m++) {
					if(prem.get(l).get(m).size() == 1) {
						sudokuSimpleArray[l][m] = prem.get(l).get(m).get(0);
					} else {
						sudokuSimpleArray[l][m] = 0;
					}
				}
			}
			if (loopCount>(n*k)*(n*k)) {
				break;
			}
			if (solved == true && unique == false) {
				break;
			}
		}
		
       /*for(int i = 0; i<n*k;i++) {
        	  for(int j = 0; j<n*k;j++) {
        		  System.out.print("[" +solvedSudoku[i][j]+"]");
              	
              }
        	  System.out.println("");
        	
        }*/
        //de-comment below lines for uniqueness and solution
        //System.out.println("It is unique = " + unique);
        //System.out.println(prem);
		} 
	}

	// Method for getting the board
	public int[][] getSudoku() {
		return sudoku;
	}

	public ArrayList<ArrayList<ArrayList<Integer>>> createPreemtiveSets() {
		ArrayList<ArrayList<ArrayList<Integer>>> prem = preemtiveSets(singleton(markUpCells()));
		for (int i = 0; i < 10; i++) {//arbitræt
			prem = preemtiveSets(singleton(prem));
		}
		return prem;
	}

	// Method for setting the entire board

	public void setSudoku(int[][] board) {
		sudoku = board;
	}

	// Method for changing a single cell in the board
	public void setSudokuCell(int x, int y, int value) {
		sudoku[x][y] = value;
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

	// Methods for returning N and K
	public int getN() {
		return n;
	}

	public int getK() {
		return k;
	}

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
			if (sizeOfSet > n*k-1) {
				break;
			}
		}
		return sudokuPre;
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
				if (checkValidity(newSudoku, false)) {

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
						if (checkValidity(newSudokuTemp, false) && !numFound) {
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

	// Method for updating the markUp board, given a set of possible entries and
	// their coordinates
	public ArrayList<ArrayList<ArrayList<Integer>>> updateMarkup(ArrayList<ArrayList<ArrayList<Integer>>> markupBoard,
			ArrayList<Integer> set, ArrayList<Integer> xCoords, ArrayList<Integer> yCoords, int mode) {

		if (mode == 1) {
			for (int i = 0; i < getSudoku().length; i++) {
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
			for (int i = 0; i < getSudoku().length; i++) {
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

	public ArrayList<ArrayList<ArrayList<Integer>>> markUpCells() {

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
				if (getSudoku()[i][j] != 0) {
					markUpBoard.get(i).get(j).add(getSudoku()[i][j]);
				}
			}
		}

		// Find mulige tal
		for (int i = 0; i < SudukoSize; i++) {
			for (int j = 0; j < SudukoSize; j++) {
				if (getSudoku()[i][j] == 0) {

					// KOPIER SUDOKUBOARDET
					int[][] copyOfSudoku = new int[getSudoku().length][];
					for (int p = 0; p < copyOfSudoku.length; ++p) {

						// allocating space for each row of destination array
						copyOfSudoku[p] = new int[getSudoku()[p].length];

						for (int o = 0; o < copyOfSudoku[p].length; ++o) {
							copyOfSudoku[p][o] = getSudoku()[p][o];
						}
					}

					for (int q = 1; q <= n * k; q++) { // Mulige tal som kan indsættes på boarded

						// Indsæt gyldige tal fra 1-9
						copyOfSudoku[i][j] = q;
						if (checkValidity(copyOfSudoku, false)) {
							markUpBoard.get(i).get(j).add(q);
						}
					}
				}
			}
		}
		return markUpBoard;
	}

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
  
	public ArrayList<ArrayList<ArrayList<Integer>>> loop(ArrayList<ArrayList<ArrayList<Integer>>> sudokuLoop) {
		//System.out.println(sudokuLoop);
		int sizeOfArrayLoop = 2; //initializing variables
		int currentLoopX = -1;
		int currentLoopY = -1;
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
		loop://This loop find the first smallest cell (with the least amount of options)
		while(sizeOfArrayLoop<n*k) { 
			for (int i = 0; i<n*k; i++) { 
				for (int j = 0; j<n*k; j++) {
					if (sudokuClone.get(i).get(j).size() == sizeOfArrayLoop) {		
						returner.addAll(sudokuClone.get(i).get(j)); //adds all numbers to a returner variable 
						sudokuClone.get(i).get(j).clear();
						sudokuClone.get(i).get(j).add(returner.get(0)); //adds only the first number to the sudoku clone
						returner.remove(returner.get(0));	 //removes the first element from the returner function 
						change = true;
						currentLoopX = i;
						currentLoopY = j;
						break loop;  //breaks out of loop
						}
				}
			}
			sizeOfArrayLoop++;
		}
		if (currentLoopX == -1) { //if no solution was found
			return sudokuLoop;
		}
		change=true;
		while(change == true) {
			sudokuClone = preemtiveSets(singleton(sudokuClone));  //does preemtivesets and singleton
			//System.out.println(sudokuClone);
			for(int l = 0; l< n*k; l++) {
				for(int m = 0; m<n*k; m++) {
					if(sudokuClone.get(l).get(m).size() == 0) {
						sudokuLoop.get(currentLoopX).get(currentLoopY).clear(); //returns unchanged loop with returner, if sudokuClone finds a cell with no possibilities
						sudokuLoop.get(currentLoopX).get(currentLoopY).addAll(returner);
						return sudokuLoop;
					}
				}
			}
		}
		int[][] sudokuSimpleArray = Converter3D2D(sudokuClone); //create 2d array, to verify
		if(checkValidity(sudokuSimpleArray, false) && isFilledLoop(sudokuSimpleArray)) { //Checks if it is solved. If it is the first time it is solved, it will return like it was not solved. 
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
				change = true;
				return sudokuLoop;
			}
		}
		if(!checkValidity(sudokuSimpleArray, false)){ //checks if invalid, returns original with returner if invalid
				sudokuLoop.get(currentLoopX).get(currentLoopY).clear();
				sudokuLoop.get(currentLoopX).get(currentLoopY).addAll(returner);
				return sudokuLoop;
			}
		
		while(!checkValidity(sudokuSimpleArray, false) || !isFilledLoop(sudokuSimpleArray)) {//this loop runs till the sudoku is solved
			sudokuClone = loop(sudokuClone); //It calls recursive
			sudokuSimpleArray = Converter3D2D(sudokuClone); //Creates simple sudoku array to verify, and then runs all the verification like before
		
			if(checkValidity(sudokuSimpleArray, false) && isFilledLoop(sudokuSimpleArray)){
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
					change = true;
					return sudokuLoop;
				}
			
			}
			if (!checkValidity(sudokuSimpleArray, false)){
				sudokuLoop.get(currentLoopX).get(currentLoopY).clear();
				sudokuLoop.get(currentLoopX).get(currentLoopY).addAll(returner);
				return sudokuLoop;
			
			}
		}			
		
		sudokuLoop.get(currentLoopX).get(currentLoopY).clear();
		sudokuLoop.get(currentLoopX).get(currentLoopY).addAll(returner);
		return sudokuLoop;
	}

	public boolean checkValidity(int[][] sudoku, boolean print) {
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
			if(rowSum != ySums[i] && isSandwich && sandwichFilled && !inSandwich){
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
			if(rowSum != xSums[j] && isSandwich && sandwichFilled && !inSandwich){
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
		for (int i = 0; i < failedCoords.size(); i++) {
			failedCoords.get(i).conflict();
		}
		return valid;
	}
	public int[][] Converter3D2D(ArrayList<ArrayList<ArrayList<Integer>>> sudoku3D){
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

	public boolean getUniqueness(){
		return unique;
	}

	public int[][] getSolvedSudoku(){
		return solvedSudoku;
	}

	public static ArrayList<Cell> getFailedCells() {
		return failedCoords;
	}
}
