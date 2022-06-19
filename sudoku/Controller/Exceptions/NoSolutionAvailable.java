package sudoku.Controller.Exceptions;

public class NoSolutionAvailable extends Exception {

    public NoSolutionAvailable() {
        super("No solution is avaliable");
    }

    public NoSolutionAvailable(String text) {
        super(text);
    }

}
