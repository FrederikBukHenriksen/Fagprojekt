package sudoku.View.SudokuBoard;

import javax.swing.JPanel;

public abstract class SudokuNumpadBarAbstract extends JPanel {

    protected NumpadButton[] numpadButtons;

    public NumpadButton[] getNumpadButtons() {
        return numpadButtons;
    }
}