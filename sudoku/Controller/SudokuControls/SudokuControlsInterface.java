package sudoku.Controller.SudokuControls;

import sudoku.Controller.Exceptions.ExceptionCellDoesNotExist;
import sudoku.Controller.Exceptions.ExceptionNoCellSelected;
import sudoku.View.SudokuBoard.Cell;

public interface SudokuControlsInterface {
	/*
	 * Author: Frederik
	 * Function: Interface, the rest of the code uses to get cells
	 */
    public Cell getCellSelected() throws ExceptionNoCellSelected;

    public int[] getCellCoordinate(Cell cell) throws ExceptionCellDoesNotExist;

    public Cell[][] getCells2d();

    public Cell[] getCells1d();

    public Cell getCellFromCoord(int x, int y);

    public void selectOnlyThisButton(Cell cell);

    public void updateCellValues(int[][] sudoku);
}
