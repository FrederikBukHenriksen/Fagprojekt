package sudoku.Controller.Exceptions;

public class ExceptionNoSolutionAvailable extends Exception {

    public ExceptionNoSolutionAvailable() {
        super("No solution is avaliable");
    }

    public ExceptionNoSolutionAvailable(String text) {
        super(text);
    }

}
