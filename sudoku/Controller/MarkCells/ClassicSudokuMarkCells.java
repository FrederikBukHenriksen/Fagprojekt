package sudoku.Controller.MarkCells;

import sudoku.Controller.Controller;
import sudoku.Controller.CreateOkPopUp;
import sudoku.Controller.SudokuControls;
import sudoku.Controller.Exceptions.CellDoesNotExist;
import sudoku.Controller.Exceptions.NoCellSelected;
import sudoku.Model.Validity.ValidityExtend;
import sudoku.View.ExceptionPopUp;
import sudoku.View.SudokuBoard.Cell;
import java.awt.*;

public class ClassicSudokuMarkCells extends MarkCellsExtend {

    Controller controller;
    SudokuControls sudokuControls;
    ValidityExtend validity;
    int n;
    int k;

    public ClassicSudokuMarkCells(int n, int k, SudokuControls sudokuControls, ValidityExtend validityExtend) {
        this.sudokuControls = sudokuControls;
        this.validity = validityExtend;
        this.n = n;
        this.k = k;
    }

    public void markCells(Cell cell) throws NoCellSelected, CellDoesNotExist {
            getPeersHorisontal(cell);
            getPeersVertical(cell);
            getPeersSquare(cell);
            markConflictCells();
        }

        protected void markConflictCells() {
            for (Point point : validity.getUniqueConflictPoints(controller.model.getSudoku())) {
                Cell cell = sudokuControls.getCellFromCoord(point.x, point.y);
                cell.conflict();
                markedCells.add(cell);
        }
    }

    protected void getPeersSquare(Cell pressedCell) throws CellDoesNotExist {

        int[] coordinates = sudokuControls.getCellCoordinate(pressedCell);

        int squareX = coordinates[0] / n;
        int squareY = coordinates[1] / n;

        // Run through the square
        for (int i = squareX * n; i < squareX * n + n; i++) {
            for (int j = squareY * n; j < squareY * n + n; j++) {
                sudokuControls.getCellFromCoord(i, j).square();
                markedCells.add(sudokuControls.getCellFromCoord(i, j));
            }
        }
    }

    protected void getPeersVertical(Cell pressedCell) throws CellDoesNotExist {
        int[] coordinates = sudokuControls.getCellCoordinate(pressedCell);
        for (int i = 0; i < (n * k); i++) {
            sudokuControls.getCellFromCoord(coordinates[0], i).peer();
            markedCells.add(sudokuControls.getCellFromCoord(coordinates[0], i));
        }
    }

    protected void getPeersHorisontal(Cell pressedCell) throws CellDoesNotExist {
        int[] coordinates = sudokuControls.getCellCoordinate(pressedCell);
        for (int i = 0; i < (n * k); i++) {
            sudokuControls.getCellFromCoord(i, coordinates[1]).peer();
            markedCells.add(sudokuControls.getCellFromCoord(i, coordinates[1]));
        }
    }

    public void markSimilarCells(Cell pressedCell) {
        if (pressedCell.getText().equals("")) {
            for (Cell cell : sudokuControls.getCellsLinear()) {
                if (cell.getText().equals(pressedCell.getText())) {
                    cell.similar();
                }
            }
        }
    }
}
