package sudoku.View.SudokuBoard;

import java.util.ArrayList;

import javax.swing.JPanel;

import sudoku.View.SudokuBoard.*;

public interface SudokuInterface {

    public ArrayList<ArrayList<Cell>> getCells();

    public Cell getButtonSelected() throws Exception;

    public void onlySelectThePressed(Cell buttonSelected) throws Exception;

    public int[] getCellCoordinate(Cell selected) throws Exception;

    public Cell getCellFromCoord(int x, int y) throws Exception;

    // public void markCells() throws Exception;

    // public void clearMarkedCells() throws Exception;

}
