package sudoku.Controller.MarkCells;

import java.util.ArrayList;

import sudoku.Controller.Exceptions.CellDoesNotExist;
import sudoku.Controller.Exceptions.NoCellSelected;
import sudoku.View.SudokuBoard.Cell;

public abstract class MarkCellsExtend {

    public ArrayList<Cell> markedCells = new ArrayList<Cell>();

    public void clearMarkedCells() {
        markedCells.clear();
    }

    public abstract void markCells(Cell cell) throws NoCellSelected, CellDoesNotExist;
}
