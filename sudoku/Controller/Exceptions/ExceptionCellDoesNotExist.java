package sudoku.Controller.Exceptions;

public class ExceptionCellDoesNotExist extends Exception {
	/*
	 * Author: Frederik
	 * Function: Creates exception for Cell doesn't exist
	 */
    public ExceptionCellDoesNotExist() {
        super("Cell does not exist");
    }

    public ExceptionCellDoesNotExist(String text) {
        super(text);
    }
}
