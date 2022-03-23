package sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SudokuModel {
	// Setting up variables
	int[][] sudoku = new int[0][0];
	int[][][] sudokuStack = new int[10][sudoku.length][sudoku.length];
	public static int k = 0;
	public static int n = 0;
	int moves = 0;

	// constructor for the model
	public SudokuModel() {
		File file = new File("sudoku/Puzzles_1/Puzzle_4_01.dat");

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

	public void setSudoku(int[][] board) {
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

<<<<<<< Updated upstream
=======
	public int[][] createSudoku() {

		List<Integer> chooseNumberList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		Random random = new Random();

		int[][] newSudoku = new int[n * k][n * k];

		for (int i = 0; i < newSudoku[0].length; i++) {
			for (int j = 0; j < newSudoku[1].length; j++) {

				while (true) {
					int number = chooseNumberList.get(random.nextInt(chooseNumberList.size()));
					int[] square = getSquare(i, j, newSudoku);
					int[] peers = getPeers(i, j, newSudoku);
					boolean cond1 = Arrays.stream(square).anyMatch(l -> l != number);
					boolean cond2 = Arrays.stream(peers).anyMatch(l -> l != number);

					if (cond1 && cond2) {
						newSudoku[i][j] = number;
						break;
					}
				}

			}
		}

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

		// Convert arraylist to primitive array
		int[] lol = peers.stream().mapToInt(i -> i).toArray();

		return peers.stream().mapToInt(i -> i).toArray();
	}

>>>>>>> Stashed changes
	public void pushStack(int[][] newBoard) {
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku.length; j++) {
				sudokuStack[moves][i][j] = sudoku[i][j];
			}
		}
		moves++;
	}

	public int[][] popStack() {
		int[][] temp = new int[sudoku.length][sudoku.length];
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku.length; j++) {
				temp[i][j] = sudokuStack[moves][i][j];
			}
		}
		moves--;
		return temp;
	}

	public int[][] peekStack() {
		int[][] temp = new int[sudoku.length][sudoku.length];
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku.length; j++) {
				temp[i][j] = sudokuStack[moves - 1][i][j];
			}
		}
		return temp;
	}

	public int getStackSize() {
		return moves;
	}

	public ArrayList<ArrayList<ArrayList<Integer>>> markUpCells() {

		// Initialise an empty 3D-arraylist, mathcing the board's size.
		ArrayList<ArrayList<ArrayList<Integer>>> board = new ArrayList();
		for (int j = 0; j < 9; j++) {
			ArrayList<ArrayList<Integer>> rows = new ArrayList<>();
			for (int k = 0; k < 9; k++) {
				ArrayList<Integer> markUpsCells = new ArrayList<>();
				rows.add(markUpsCells);
			}
			board.add(rows);
		}

		// Overskriv det primitive array's værider til 3D-ArrayList
		int SudukoSize = n * k;
		for (int i = 0; i < SudukoSize; i++) {
			for (int j = 0; j < SudukoSize; j++) {
				if (getSudoku()[i][j] != 0) {
					board.get(i).get(j).add(getSudoku()[i][j]);
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

					for (int q = 1; q <= 9; q++) { // Mulige tal som kan indsættes på boarded

						// Indsæt gyldige tal fra 1-9
						copyOfSudoku[i][j] = q;
						if (checkValidity(copyOfSudoku)) {
							board.get(i).get(j).add(q);
						}
					}
				}
			}
		}
		return board;
	}

	public static boolean checkValidity(int[][] sudoku) {
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
			for (int j = 0; j < sudoku.length; j++) {
				int cur = (sudoku[i][j]);
				if (cur != 0) {
					if (sortedGrid[i][cur - 1] == 0) {
						sortedGrid[i][cur - 1] = 1;
					} else {
						valid = false;
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
		 * System.out.println();
		 */

		// Resetting the sorted grid
		for (int i = sortedGrid.length - 1; i >= 0; i--) {
			for (int j = 0; j < sortedGrid.length; j++) {
				sortedGrid[i][j] = 0;
			}
		}

		// Checking columns for duplicates
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku.length; j++) {
				int cur = (sudoku[j][i]);
				if (cur != 0) {
					if (sortedGrid[i][cur - 1] == 0) {
						sortedGrid[i][cur - 1] = 1;
					} else {
						valid = false;
					}
				}
			}
		}

		// Resetting the sorted grid
		for (int i = sortedGrid.length - 1; i >= 0; i--) {
			for (int j = 0; j < sortedGrid.length; j++) {
				sortedGrid[i][j] = 0;
			}
		}

		// Checking each square
		/*
		 * for (int r = 0; r < Math.sqrt(sudoku.length); r++) {
		 * for (int c = 0; c < Math.sqrt(sudoku.length); c++) {
		 * for (int br = 0; br < Math.sqrt(sudoku.length); br++) {
		 * for (int bc = 0; bc < Math.sqrt(sudoku.length); bc++) {
		 * int cur = sudoku[(c * 3) + bc][(r * 3) + br];
		 * if (cur != 0) {
		 * if (sortedGrid[c + r * 3][cur - 1] == 0) {
		 * sortedGrid[c + r * 3][cur - 1] = 1;
		 * } else {
		 * valid = false;
		 * }
		 * }
		 * }
		 * }
		 * }
		 * }
		 */

		for (int l = 0; l < k * k; l++) {

			for (int i = 0; i < n; i++) {

				for (int j = 0; j < n; j++) {// l/k benytter sig af hvordan java runder op. det er n hvor mange felter
												// den skal rygge, og den skal rygge det hver gang l har bev�get sig k
												// felter.
					int cur = sudoku[(i + n * (l / k))][(j + n * l) % (k * n)];
					if (cur != 0) {
						if (sortedGrid[l][cur - 1] == 0) {
							sortedGrid[l][cur - 1] = 1;
						} else {
							valid = false;
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

		return valid;
	}

	public void printSudoku(int[][] sudokuBoard) {
		for (int i = 0; i < sudokuBoard.length; i++) {
			for (int k = 0; k < sudokuBoard.length; k++) {
				System.out.print(sudokuBoard[i][k] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

}
