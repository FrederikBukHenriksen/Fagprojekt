package sudoku.Controller.Exceptions;

public class ExceptionNoSolutionAvailable extends Exception {
	/*
	 * Author: Frederik
	 * Function: Exception for no solution is available
	 */
    public ExceptionNoSolutionAvailable() {
        super("No solution is avaliable");
    }

    public ExceptionNoSolutionAvailable(String text) {
        super(text);
    }

}
