package sudoku.Controller.Exceptions;

public class ExceptionNoCellSelected extends Exception {
	/*
	 * Author: Frederik
	 * Function: Creates exception for Cell not marked
	 */
    public ExceptionNoCellSelected() {
        super("No cell is selected");
    }

    public ExceptionNoCellSelected(String text) {
        super(text);
    }

}
