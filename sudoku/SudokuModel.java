package sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.io.File;
import java.io.FileNotFoundException;

public class SudokuModel {
	// Setting up variables
	int[][] sudoku = new int[0][0];
	Stack<int[][]> sudokuStack = new Stack<int[][]>();
	int k = 0;
	int n = 0;

	// constructor for the model
	public SudokuModel() {
		File file = new File("sudoku/Puzzles_1/Puzzle_3_01.dat");

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

	public ArrayList<ArrayList<ArrayList<Integer>>> markUpCells() {

		ArrayList<ArrayList<ArrayList<Integer>>> markUpBoard = new ArrayList();
		for (int j = 0; j < 9; j++) {
			ArrayList<ArrayList<Integer>> rows = new ArrayList<>();
			for (int k = 0; k < 9; k++) {
				ArrayList<Integer> markUps = new ArrayList<>();
				rows.add(markUps);
			}
			markUpBoard.add(rows);
		}

		// Overskriv det primitive array til 3D-ArrayList
		int SudukoSize = 9;
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
					for (int q = 1; q <= 9; q++) { // Mulige tal som kan indsættes på boarded

						// Indsæt gyldige tal fra 1.9
						copyOfSudoku[i][j] = q;
						if (checkValidity(copyOfSudoku, getN(), getK())) {
							markUpBoard.get(i).get(j).add(q);
						}
					}
				}
			}
		}
		return markUpBoard;
	}

	public static boolean checkValidity(int[][] sudoku, int n, int k) {
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

}
