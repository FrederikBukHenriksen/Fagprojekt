package sudoku.View.SudokuBoard;

import java.util.ArrayList;

import javax.swing.JPanel;
import sudoku.View.SudokuBoard.SudokuBoardInterface;

import sudoku.Controller.Exceptions.CellDoesNotExist;
import sudoku.Controller.Exceptions.NoCellSelected;

public abstract class SudokuBoardExtend extends JPanel implements SudokuBoardInterface {
    protected int[][] sudoku;
    protected Cell[][] cells;

    public Cell[][] getCells() {
        return cells;
    }

}
