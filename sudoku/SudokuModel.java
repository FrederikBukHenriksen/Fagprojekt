package sudoku;

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
		File file = new File("sudoku/test.txt");

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
			/*
			 * for (int i = 0; i< n*n; i++) {
			 * String line = scanner.next();
			 * for(int j = 0; j < n*n; j++) {
			 * if(line.charAt(j*2)=='.') {
			 * sudoku[i][j]=0;
			 * } else {
			 * sudoku[i][j]=Character.getNumericValue(line.charAt(j*2));
			 * }
			 * }
			 * }
			 */
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
						// System.out.println("c: " + c);
						// If input is ".", convert to a "0"
						sudoku[c][d] = 0;
						// System.out.println("i: " + d + ", j: " + c);
						// Go to next entry
						d++;
						/*
						 * for(int a = 0; a < sudoku.length; a++){
						 * for(int b = 0; b < sudoku.length; b++){
						 * System.out.print(sudoku[a][b] + " ");
						 * }
						 * System.out.println();
						 * }
						 * System.out.println();
						 */
					} else {
						try {
							// If input isn't ".", read the number and insert into array
							sudoku[c][d] = Integer.parseInt(str);
							/*
							 * System.out.println("i: " + d + ", j: " + c);
							 * for(int a = 0; a < sudoku.length; a++){
							 * for(int b = 0; b < sudoku.length; b++){
							 * System.out.print(sudoku[a][b] + " ");
							 * }
							 * System.out.println();
							 * }
							 * System.out.println();
							 */
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
		System.out.println(x + " " + y);
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

}