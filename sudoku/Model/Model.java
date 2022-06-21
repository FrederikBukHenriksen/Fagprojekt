package sudoku.Model;

import sudoku.Model.Solver.BacktrackAlgorithm;
import sudoku.Model.Solver.CrooksAlgorithm;
import sudoku.Model.Solver.SolverInterface;
import sudoku.Model.Validity.ValidityClassic;
import sudoku.Model.Validity.ValidityInterface;

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

	// Is used by the test:
	public Model() {
	}

	public Model(int[][] sudoku, int n, int k, ValidityInterface validity, SolverInterface solver) {
		this.sudoku = sudoku;
		this.n = n;
		this.k = k;
		this.validity = validity;
		this.solver = solver;
		this.isSandwich = false;
		validity = new ValidityClassic(sudoku, n, k);
		solver = new CrooksAlgorithm(n, k, sudoku, this);
		stack = new Stack(getSudoku());
	}

	public Model(int[][] sudoku, int n, int k, int[] xSums, int[] ySums, ValidityInterface validity,
			SolverInterface solver) {
		this.sudoku = sudoku;
		this.n = n;
		this.k = k;
		this.xSums = xSums;
		this.ySums = ySums;
		this.validity = validity;
		this.solver = solver;
		this.isSandwich = true;
		validity = new ValidityClassic(sudoku, n, k);
		solver = new BacktrackAlgorithm(n, k, xSums, ySums, sudoku, this);
		stack = new Stack(getSudoku());

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
