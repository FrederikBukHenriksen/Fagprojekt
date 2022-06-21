package sudoku.Controller.Exceptions;

public class ExceptionCellDoesNotExist extends Exception {

    public ExceptionCellDoesNotExist() {
        super("Cell does not exist");
    }

    public ExceptionCellDoesNotExist(String text) {
        super(text);
    }
}
