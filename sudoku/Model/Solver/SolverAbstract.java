package sudoku.Model.Solver;

import sudoku.Controller.Exceptions.NoSolutionAvailable;

public abstract class SolverAbstract {

    public abstract void solve() throws Exception;

    public abstract boolean isSolved();

    public abstract int[][] getSolvedSudoku() throws Exception;

    public abstract boolean getUniqueness() throws Exception;

}
