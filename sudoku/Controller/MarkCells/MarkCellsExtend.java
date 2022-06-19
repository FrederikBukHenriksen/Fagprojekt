package sudoku.Controller.MarkCells;

import java.util.ArrayList;

import sudoku.Controller.Exceptions.CellDoesNotExist;
import sudoku.Controller.Exceptions.NoCellSelected;
import sudoku.View.SudokuBoard.Cell;

public abstract class MarkCellsExtend {

    public ArrayList<Cell> markedCells = new ArrayList<Cell>();

    public abstract void clearMarkedCells();


    public abstract void markCells(Cell cell) throws NoCellSelected, CellDoesNotExist, Exception;
}
