package sudoku.View.SudokuBoard;

import javax.swing.JPanel;

public abstract class SudokuBoardAbstract extends JPanel implements SudokuBoardInterface {
    protected int[][] sudoku;
    protected Cell[][] cells;
    public SudokuNumpadBarAbstract numpad;

    public Cell[][] getCells() {
        return cells;
    }

}
