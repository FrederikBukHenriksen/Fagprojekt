package sudoku.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import sudoku.Model.Model;
import sudoku.Model.Validity.*;
import sudoku.Model.Solver.*;

class SolverTest {
	public Model model = new Model();

	@Test
	void SudokuSolverFindsCorrectAnswertest() {
		int[][] sudoku = { { 1, 0, 8, 5, 0, 0, 6, 0, 0 }, { 0, 2, 0, 0, 0, 1, 0, 0, 0 }, { 0, 4, 0, 0, 0, 0, 0, 0, 8 },
				{ 8, 0, 5, 0, 0, 3, 0, 0, 9 }, { 0, 0, 4, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 9, 0, 2, 0, 0 },
				{ 3, 0, 9, 0, 0, 5, 0, 0, 2 }, { 0, 0, 0, 6, 0, 0, 0, 7, 0 }, { 0, 1, 0, 0, 0, 0, 0, 0, 0 } };
		int[][] solvedSudoku = { { 1, 7, 8, 5, 3, 2, 6, 9, 4 }, { 9, 2, 6, 8, 4, 1, 5, 3, 7 },
				{ 5, 4, 3, 9, 6, 7, 1, 2, 8 }, { 8, 6, 5, 2, 1, 3, 7, 4, 9 }, { 2, 9, 4, 7, 5, 6, 8, 1, 3 },
				{ 7, 3, 1, 4, 9, 8, 2, 5, 6 }, { 3, 8, 9, 1, 7, 5, 4, 6, 2 }, { 4, 5, 2, 6, 8, 9, 3, 7, 1 },
				{ 6, 1, 7, 3, 2, 4, 9, 8, 5 } };
		int n = 3;
		int k = 3;
		model.setValidity(new ValidityClassic(sudoku, n, k));
		model.setSolver(new CrooksAlgorithm(n, k, sudoku, model));
		try {
			model.solver.solve();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			assertEquals(solvedSudoku, model.solver.getSolvedSudoku());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	void SudokuSolverUniqunessTruetest() {
		int[][] sudoku = { { 1, 0, 8, 5, 0, 0, 6, 0, 0 }, { 0, 2, 0, 0, 0, 1, 0, 0, 0 }, { 0, 4, 0, 0, 0, 0, 0, 0, 8 },
				{ 8, 0, 5, 0, 0, 3, 0, 0, 9 }, { 0, 0, 4, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 9, 0, 2, 0, 0 },
				{ 3, 0, 9, 0, 0, 5, 0, 0, 2 }, { 0, 0, 0, 6, 0, 0, 0, 7, 0 }, { 0, 1, 0, 0, 0, 0, 0, 0, 0 } };
		int[][] solvedSudoku = { { 1, 7, 8, 5, 3, 2, 6, 9, 4 }, { 9, 2, 6, 8, 4, 1, 5, 3, 7 },
				{ 5, 4, 3, 9, 6, 7, 1, 2, 8 }, { 8, 6, 5, 2, 1, 3, 7, 4, 9 }, { 2, 9, 4, 7, 5, 6, 8, 1, 3 },
				{ 7, 3, 1, 4, 9, 8, 2, 5, 6 }, { 3, 8, 9, 1, 7, 5, 4, 6, 2 }, { 4, 5, 2, 6, 8, 9, 3, 7, 1 },
				{ 6, 1, 7, 3, 2, 4, 9, 8, 5 } };
		int n = 3;
		int k = 3;
		model.setValidity(new ValidityClassic(sudoku, n, k));
		model.setSolver(new CrooksAlgorithm(n, k, sudoku, model));
		try {
			model.solver.solve();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			assertEquals(true, model.solver.getUniqueness());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	void SudokuSolverUniqunessFalsetest() {
		int[][] sudoku = { { 1, 0, 0, 0, 0, 0, 6, 0, 0 }, { 0, 2, 0, 0, 0, 1, 0, 0, 0 }, { 0, 4, 0, 0, 0, 0, 0, 0, 8 },
				{ 8, 0, 5, 0, 0, 3, 0, 0, 9 }, { 0, 0, 4, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 9, 0, 2, 0, 0 },
				{ 3, 0, 9, 0, 0, 5, 0, 0, 2 }, { 0, 0, 0, 6, 0, 0, 0, 7, 0 }, { 0, 1, 0, 0, 0, 0, 0, 0, 0 } };
		int[][] solvedSudoku = { { 1, 7, 8, 5, 3, 2, 6, 9, 4 }, { 9, 2, 6, 8, 4, 1, 5, 3, 7 },
				{ 5, 4, 3, 9, 6, 7, 1, 2, 8 }, { 8, 6, 5, 2, 1, 3, 7, 4, 9 }, { 2, 9, 4, 7, 5, 6, 8, 1, 3 },
				{ 7, 3, 1, 4, 9, 8, 2, 5, 6 }, { 3, 8, 9, 1, 7, 5, 4, 6, 2 }, { 4, 5, 2, 6, 8, 9, 3, 7, 1 },
				{ 6, 1, 7, 3, 2, 4, 9, 8, 5 } };
		int n = 3;
		int k = 3;
		model.setValidity(new ValidityClassic(sudoku, n, k));
		model.setSolver(new CrooksAlgorithm(n, k, sudoku, model));
		try {
			model.solver.solve();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			assertEquals(false, model.solver.getUniqueness());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	void SudokuAndSolverAreDifferentTest() {
		int[][] sudoku = { { 0, 0, 7, 5, 0, 0, 0, 0, 3 }, { 0, 0, 8, 0, 0, 0, 0, 0, 0 }, { 0, 4, 0, 0, 0, 0, 8, 0, 7 },
				{ 0, 0, 0, 2, 0, 0, 4, 0, 0 }, { 9, 2, 0, 0, 0, 0, 0, 1, 0 }, { 0, 0, 0, 0, 3, 0, 0, 5, 0 },
				{ 0, 0, 0, 3, 8, 7, 0, 0, 0 }, { 4, 0, 1, 0, 0, 5, 0, 0, 0 }, { 6, 0, 0, 0, 0, 4, 0, 0, 0 } };
		int[][] solvedSudoku = { { 1, 7, 8, 5, 3, 2, 6, 9, 4 }, { 9, 2, 6, 8, 4, 1, 5, 3, 7 },
				{ 5, 4, 3, 9, 6, 7, 1, 2, 8 }, { 8, 6, 5, 2, 1, 3, 7, 4, 9 }, { 2, 9, 4, 7, 5, 6, 8, 1, 3 },
				{ 7, 3, 1, 4, 9, 8, 2, 5, 6 }, { 3, 8, 9, 1, 7, 5, 4, 6, 2 }, { 4, 5, 2, 6, 8, 9, 3, 7, 1 },
				{ 6, 1, 7, 3, 2, 4, 9, 8, 5 } };
		int n = 3;
		int k = 3;
		model.setValidity(new ValidityClassic(sudoku, n, k));
		model.setSolver(new CrooksAlgorithm(n, k, sudoku, model));
		try {
			model.solver.solve();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			assertNotSame(solvedSudoku, model.solver.getSolvedSudoku());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/*
	 * @Test
	 * void SudokuAndSolverAreSandwichTest() {
	 * model = new Model();
	 * int[][] sudoku =
	 * {{0,0,0,0,0,0,0,0,0},{0,0,0,9,8,0,0,0,7},{0,0,0,3,0,0,0,8,0},{0,0,0,0,0,0,0,0
	 * ,0},{0,0,0,5,0,1,0,0,0},{0,0,0,0,0,0,0,0,0},{0,5,0,0,0,3,0,0,0},{7,0,0,0,9,6,
	 * 0,0,0},{0,0,0,0,0,0,0,0,0}};
	 * int[][] solvedSudoku =
	 * {{4,8,5,1,6,7,9,3,2},{1,3,2,9,8,5,4,6,7},{9,6,7,3,4,2,1,8,5},{5,4,6,8,2,9,7,1
	 * ,3},{3,9,8,5,7,1,2,4,6},{2,7,1,6,3,4,5,9,8},{8,5,4,2,1,3,6,7,9},{7,2,3,4,9,6,
	 * 8,5,1},{6,1,9,7,5,8,3,2,4}};
	 * int[] xSums = {0,14,7,0,0,0,4,4,0};
	 * int[] ySums = {13,5,22,7,20,18,16,19,0};
	 * int n = 3;
	 * int k = 3;
	 * model.setValidity(new ValiditySandwich(sudoku, n, k,xSums,ySums));
	 * model.setSolver(new BacktrackAlgorithm(n, k,xSums,ySums, sudoku, model));
	 * try {
	 * model.solver.solve();
	 * } catch (Exception e1) {
	 * e1.printStackTrace();
	 * }
	 * try {
	 * assertEquals(solvedSudoku, model.solver.getSolvedSudoku());
	 * } catch (Exception e) {
	 * // TODO Auto-generated catch block
	 * e.printStackTrace();
	 * }
	 * 
	 * }
	 */
}
