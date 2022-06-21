package sudoku.Controller.MarkCells;

import java.util.ArrayList;

import sudoku.Controller.Exceptions.ExceptionCellDoesNotExist;
import sudoku.Controller.Exceptions.ExceptionNoCellSelected;
import sudoku.View.SudokuBoard.Cell;

public interface MarkCellsInterface {

    public abstract void clearMarkedCells();

    public abstract void markCells(Cell pressedCell)
            throws ExceptionNoCellSelected, ExceptionCellDoesNotExist, Exception;
}
