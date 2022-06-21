package sudoku.Controller.MarkCells;

import sudoku.Controller.Exceptions.ExceptionCellDoesNotExist;
import sudoku.Controller.SudokuControls.SudokuControlsInterface;
import sudoku.Model.Validity.ValidityInterface;
import sudoku.View.SudokuBoard.Cell;
import sudoku.View.SudokuBoard.Classic.ClassicSudokuColors;

import java.awt.*;
import java.util.ArrayList;

public class ClassicMarkCells implements MarkCellsInterface {

    protected SudokuControlsInterface sudokuControls;
    protected ValidityInterface validity;
    protected ClassicSudokuColors sudokuColor = new ClassicSudokuColors();
    protected int[][] sudoku;
    protected int n;
    protected int k;

    protected ArrayList<Cell> markedCells = new ArrayList<Cell>();

    public ClassicMarkCells(int[][] sudoku, int n, int k,
            SudokuControlsInterface sudokuControls,
            ValidityInterface validity) {
        this.sudokuControls = sudokuControls;
        this.validity = validity;
        this.sudoku = sudoku;
        this.n = n;
        this.k = k;
    }

    public void markCells(Cell cell) throws Exception {
        getPeersHorisontal(cell);
        getPeersVertical(cell);
        getPeersSquare(cell);
        markSimilarCells(cell);
        markConflictCells(); // Conflict cells at last, in order to see the conflict color
    }

    public void clearMarkedCells() {
        for (Cell cell : markedCells) {
            sudokuColor.colorDefault(cell);
        }
        markedCells.clear();
        // Redraw conflict cells, even when no cell is pressed
        markConflictCells();
    }

    protected void markConflictCells() {
        for (Point point : validity.getUniqueConflictPoints(sudoku)) {
            Cell cell = sudokuControls.getCellFromCoord(point.x, point.y);
            sudokuColor.colorConflict(cell);
            markedCells.add(cell);
        }
    }

    protected void getPeersSquare(Cell pressedCell) throws ExceptionCellDoesNotExist {

        int[] coordinates = sudokuControls.getCellCoordinate(pressedCell);

        int squareX = coordinates[0] / n;
        int squareY = coordinates[1] / n;

        // Run through the square
        for (int i = squareX * n; i < squareX * n + n; i++) {
            for (int j = squareY * n; j < squareY * n + n; j++) {
                sudokuColor.colorSquare(sudokuControls.getCellFromCoord(i, j));
                markedCells.add(sudokuControls.getCellFromCoord(i, j));
            }
        }
    }

    protected void getPeersVertical(Cell pressedCell) throws ExceptionCellDoesNotExist {
        int[] coordinates = sudokuControls.getCellCoordinate(pressedCell);
        for (int i = 0; i < (n * k); i++) {
            sudokuColor.colorPeer(sudokuControls.getCellFromCoord(coordinates[0], i));
            markedCells.add(sudokuControls.getCellFromCoord(coordinates[0], i));
        }
    }

    protected void getPeersHorisontal(Cell pressedCell) throws ExceptionCellDoesNotExist {
        int[] coordinates = sudokuControls.getCellCoordinate(pressedCell);
        for (int i = 0; i < (n * k); i++) {
            sudokuColor.colorPeer(sudokuControls.getCellFromCoord(i, coordinates[1]));
            markedCells.add(sudokuControls.getCellFromCoord(i, coordinates[1]));
        }
    }

    protected void markSimilarCells(Cell pressedCell) throws ExceptionCellDoesNotExist {

        if (!pressedCell.getText().equals("")) {
            for (Cell cell : sudokuControls.getCells1d()) {
                if (cell.getText().equals(pressedCell.getText())) {
                    sudokuColor.colorSimilar(cell);
                    int[] coordinates = sudokuControls.getCellCoordinate(cell);
                    markedCells.add(sudokuControls.getCellFromCoord(coordinates[0], coordinates[1]));

                }
            }
        }
    }
}
