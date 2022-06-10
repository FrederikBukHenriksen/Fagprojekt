package sudoku.Exceptions;

public class NoCellSelected extends Exception {

    public NoCellSelected() {
        super("No cell is selected");
    }

    public NoCellSelected(String text) {
        super(text);
    }

}
