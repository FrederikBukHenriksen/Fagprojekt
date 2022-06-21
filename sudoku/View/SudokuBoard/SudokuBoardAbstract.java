package sudoku.View.SudokuBoard;

import java.util.ArrayList;

import javax.swing.JPanel;
import sudoku.View.SudokuBoard.SudokuBoardInterface;
import sudoku.View.SudokuBoard.Classic.ClassicNumpadBar;
import sudoku.Controller.Exceptions.ExceptionCellDoesNotExist;
import sudoku.Controller.Exceptions.ExceptionNoCellSelected;

public abstract class SudokuBoardAbstract extends JPanel implements SudokuBoardInterface {
    protected int[][] sudoku;
    protected Cell[][] cells;
    public SudokuNumpadBarAbstract numpad;

    public Cell[][] getCells() {
        return cells;
    }


}
