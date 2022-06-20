package sudoku.Controller.MarkCells;

import java.util.ArrayList;

import sudoku.Controller.Exceptions.CellDoesNotExist;
import sudoku.Controller.Exceptions.NoCellSelected;
import sudoku.View.SudokuBoard.Cell;

public interface MarkCellsInterface {

    public abstract void clearMarkedCells();

    public abstract void markCells(Cell pressedCell) throws NoCellSelected, CellDoesNotExist, Exception;
}
