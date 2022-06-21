package sudoku.Model.Solver;

import sudoku.Controller.Exceptions.ExceptionNoSolutionAvailable;

public interface SolverInterface {

    public abstract void solve() throws Exception;

    public abstract boolean isSolved();

    public abstract int[][] getSolvedSudoku() throws Exception;

    public abstract boolean getUniqueness() throws Exception;

}
