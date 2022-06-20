package sudoku.Controller.Exceptions;

public class ExceptionNoCellSelected extends Exception {

    public ExceptionNoCellSelected() {
        super("No cell is selected");
    }

    public ExceptionNoCellSelected(String text) {
        super(text);
    }

}
