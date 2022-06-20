package sudoku.View.SudokuBoard;

import java.util.ArrayList;

import javax.swing.JPanel;
import sudoku.View.SudokuBoard.SudokuBoardInterface;
import sudoku.View.SudokuBoard.Classic.ClassicSudokuNumpad;
import sudoku.Controller.Exceptions.CellDoesNotExist;
import sudoku.Controller.Exceptions.NoCellSelected;

public abstract class SudokuBoardAbstract extends JPanel implements SudokuBoardInterface {
    protected int[][] sudoku;
    protected Cell[][] cells;
    protected ClassicSudokuNumpad sudokuNumpad;

    public Cell[][] getCells() {
        return cells;
    }

    public NumpadButton[] getNumpadButtons() {
        return sudokuNumpad.numpadButtons;
    }

}
