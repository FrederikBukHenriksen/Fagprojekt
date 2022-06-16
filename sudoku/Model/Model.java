package sudoku.Model;


import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import sudoku.Model.Solver.BacktrackAlgorithm;
import sudoku.Model.Solver.CrooksAlgorithm;
import sudoku.Model.Solver.SolverAbstract;
import sudoku.Model.Validity.ValidityExtend;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;

public class Model {
	public CrooksAlgorithm crooks;
	public BacktrackAlgorithm backtrack;
	public boolean isSandwich = false;
	// Setting up variables
	public int[][] sudoku = new int[0][0];
	public static int k = 0;
	public static int n = 0;
	public static int[] xSums = new int[n * k];
	public static int[] ySums = new int[n * k];

	// Containers
	public ValidityExtend validity;
	public SolverAbstract solver;
	public Stack stack;

	// constructor for the model
	public Path findSudokuPath(String s) {// https://stackoverflow.com/questions/51973636/how-to-return-the-file-path-from-the-windows-file-explorer-using-java
		// File file = new
		// File("C:\\Users\\Candytom\\Documents\\GitHub\\sudoku\\Puzzles_1\\Puzzle_3_evil.dat");
		Path file = null;
		JFileChooser jd = s == null ? new JFileChooser() : new JFileChooser(s);
		jd.setDialogTitle("Choose Sudoku you wish to solve");
		int returnVal = jd.showOpenDialog(null);
		/* If user didn't select a file and click ok, return null Path object */
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			return file = jd.getSelectedFile().toPath();
		}
		return null;

	}

	// public void boardCreater()
	// throws FileNotFoundException, IOException, NumberFormatException,
	// NoSuchElementException {
	// Path file = null;
	// file = findSudokuPath("C:\\");
	// // System.out.println(file);
	// if (file == null) {
	// System.exit(0);
	// }
	// Scanner scanner;
	// // reading the input
	// scanner = new Scanner(file);
	// String setup = scanner.next();
	// Scanner setupScanner = new Scanner(setup);
	// setupScanner.useDelimiter(";");
	// // reading k & n
	// while (setupScanner.hasNext()) {
	// String str = setupScanner.next();
	// k = Integer.parseInt(str);
	// str = setupScanner.next();
	// n = Integer.parseInt(str);

	// }
	// setupScanner.close();
	// if (k > n) {
	// System.out.println("Not a valid sudoku-size, k cannot exceed n");
	// } else {// Creating the board
	// sudoku = new int[n * k][n * k];
	// // Creating variables for sandwich Sums
	// xSums = new int[n * k];
	// ySums = new int[n * k];
	// // Creating variables for looping through input
	// int c = 0;
	// int d = 0;
	// scanner.nextLine();
	// for (int j = 0; j < n * k; j++) {
	// // Reads the next line
	// String line = scanner.nextLine();
	// Scanner lineScanner = new Scanner(line);
	// lineScanner.useDelimiter(";");
	// while (lineScanner.hasNext()) {
	// // Reads the next input on the line, separated by ";"
	// String str = lineScanner.next();
	// if (str.equals(".")) {
	// // If input is ".", convert to a "0"
	// sudoku[c][d] = 0;
	// // Go to next entry
	// d++;
	// } else {
	// // If input isn't ".", read the number and insert into array
	// sudoku[c][d] = Integer.parseInt(str);
	// // Go to next entry
	// d++;
	// }
	// }
	// // Go to next line, and start from first entry
	// c++;
	// d = 0;
	// lineScanner.close();
	// }
	// crooks = new CrooksAlgorithm(getN(), getK(), getSudoku(), this);
	// if (scanner.hasNextLine()) {
	// setSandwich(true);
	// String line = scanner.nextLine();
	// Scanner lineScanner = new Scanner(line);
	// lineScanner.useDelimiter(":");
	// int index = 0;
	// while (lineScanner.hasNext()) {
	// String str = lineScanner.next();
	// // If input isn't ".", read the number and insert into array
	// xSums[index] = Integer.parseInt(str);
	// index++;

	// }
	// lineScanner.close();
	// line = scanner.nextLine();
	// lineScanner = new Scanner(line);
	// lineScanner.useDelimiter(":");
	// index = 0;
	// while (lineScanner.hasNext()) {
	// String str = lineScanner.next();
	// // If input isn't ".", read the number and insert into array
	// ySums[index] = Integer.parseInt(str);
	// index++;
	// }
	// backtrack = new BacktrackAlgorithm(getN(), getK(), xSums, ySums, sudoku,
	// this);
	// }
	// }
	// }

	public Model() {
		this.stack = new Stack(sudoku);
	}

	public void runSolver() throws Exception {
		if (!solver.isSolved() || !solver.getUniqueness()) {
			solver.solve();
		}
	}

	// Set functions

	public void setValidity(ValidityExtend validity) {
		this.validity = validity;
	}

	public void setSolver(SolverAbstract solver) {
		this.solver = solver;
	}

	public void setSudoku(int[][] board) {
		sudoku = board;
	}

	public void setSudokuCell(int x, int y, int value) {
		sudoku[x][y] = value;
	}

	// Get functions

	public int getN() {
		return n;
	}

	public int getK() {
		return k;
	}

	public int[][] getSudoku() {
		return sudoku;
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

	public boolean getSandwich() {
		return isSandwich;
	}

	public void setSandwich(boolean sandwich) {
		isSandwich = sandwich;
	}
}
