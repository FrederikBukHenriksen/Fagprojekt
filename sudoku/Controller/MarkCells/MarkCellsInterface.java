package sudoku.Controller.MarkCells;

import sudoku.Controller.Exceptions.ExceptionCellDoesNotExist;
import sudoku.Controller.Exceptions.ExceptionNoCellSelected;
import sudoku.View.SudokuBoard.Cell;

public interface MarkCellsInterface {
	/*
	 * Author: Frederik
	 * Function: Interface, the rest of the code uses to get marked cells
	 */
    public abstract void clearMarkedCells();

    public abstract void markCells(Cell pressedCell)
            throws ExceptionNoCellSelected, ExceptionCellDoesNotExist, Exception;
}
