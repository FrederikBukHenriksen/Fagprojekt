package sudoku.Model.Solver;

public interface SolverInterface {
	/*
	 * Author: Frederik
	 * Function: Interface, the rest of the code uses to get solver
	 */
    public abstract void solve() throws Exception;

    public abstract boolean isSolved();

    public abstract int[][] getSolvedSudoku() throws Exception;

    public abstract boolean getUniqueness() throws Exception;

}
