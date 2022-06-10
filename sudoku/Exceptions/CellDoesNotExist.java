package sudoku.Exceptions;

public class CellDoesNotExist extends Exception {

    public CellDoesNotExist() {
        super("Cell does not exist");
    }

    public CellDoesNotExist(String text) {
        super(text);
    }
}
