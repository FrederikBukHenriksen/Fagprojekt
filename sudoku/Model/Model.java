package sudoku.Model;

import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.JFileChooser;
import sudoku.Model.Solver.BacktrackAlgorithm;
import sudoku.Model.Solver.CrooksAlgorithm;
import sudoku.Model.Solver.SolverInterface;
import sudoku.Model.Validity.ValidityInterface;

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
	public ValidityInterface validity;
	public SolverInterface solver;
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

	public Model() {
	}

	public void runSolver() throws Exception {

		if (!solver.isSolved() || !solver.getUniqueness()) {
			solver.solve();
		}
	}

	// Set functions

	public void setValidity(ValidityInterface validity) {
		this.validity = validity;
	}

	public void setSolver(SolverInterface solver) {
		this.solver = solver;
	}

	public void setStack(Stack stack) {
		this.stack = stack;
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
