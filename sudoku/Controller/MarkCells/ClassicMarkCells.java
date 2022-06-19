package sudoku.Controller.MarkCells;

import sudoku.Controller.Controller;
import sudoku.Controller.ClassicSudokuControls;
import sudoku.Controller.Exceptions.CellDoesNotExist;
import sudoku.Model.Validity.ValidityInterface;
import sudoku.View.SudokuBoard.Cell;
import java.awt.*;
import java.util.ArrayList;

public class ClassicMarkCells implements MarkCellsInterface {

    protected ClassicSudokuControls sudokuControls;
    protected ValidityInterface validity;
    protected int[][] sudoku;
    protected int n;
    protected int k;

    protected ArrayList<Cell> markedCells = new ArrayList<Cell>();

    public ClassicMarkCells(int[][] sudoku, int n, int k, ClassicSudokuControls sudokuControls,
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
            colorDefault(cell);
        }
        markedCells.clear();
        // Redraw conflict cells, even when no cell is pressed
        markConflictCells();
    }

    protected void markConflictCells() {
        for (Point point : validity.getUniqueConflictPoints(sudoku)) {
            Cell cell = sudokuControls.getCellFromCoord(point.x, point.y);
            colorConflict(cell);
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
                colorSquare(sudokuControls.getCellFromCoord(i, j));
                markedCells.add(sudokuControls.getCellFromCoord(i, j));
            }
        }
    }

    protected void getPeersVertical(Cell pressedCell) throws CellDoesNotExist {
        int[] coordinates = sudokuControls.getCellCoordinate(pressedCell);
        for (int i = 0; i < (n * k); i++) {
            colorPeer(sudokuControls.getCellFromCoord(coordinates[0], i));
            markedCells.add(sudokuControls.getCellFromCoord(coordinates[0], i));
        }
    }

    protected void getPeersHorisontal(Cell pressedCell) throws CellDoesNotExist {
        int[] coordinates = sudokuControls.getCellCoordinate(pressedCell);
        for (int i = 0; i < (n * k); i++) {
            colorPeer(sudokuControls.getCellFromCoord(i, coordinates[1]));
            markedCells.add(sudokuControls.getCellFromCoord(i, coordinates[1]));
        }
    }

    protected void markSimilarCells(Cell pressedCell) throws CellDoesNotExist {

        if (!pressedCell.getText().equals("")) {
            for (Cell cell : sudokuControls.getCells1d()) {
                if (cell.getText().equals(pressedCell.getText())) {
                    colorSimilar(cell);
                    int[] coordinates = sudokuControls.getCellCoordinate(cell);
                    markedCells.add(sudokuControls.getCellFromCoord(coordinates[0], coordinates[1]));

                }
            }
        }
    }

    public void colorDefault(Cell cell) {
        if (cell.enabled) {
            cell.setTextColor(Cell.colorDefaultFontEnabled);
        } else {
            cell.setTextColor(Cell.colorDefaultFont);
        }
        cell.setBackgroundColor(Cell.colorDefaultBackground);
    }

    public void colorConflict(Cell cell) {
        if (cell.getEnabled()) {
            cell.setTextColor(new Color(230, 67, 70));
        }
        cell.setBackgroundColor(new Color(240, 192, 193));
    }

    public void colorSimilar(Cell cell) {
        cell.setBackgroundColor(new Color(144, 182, 212));
    }

    public void colorPeer(Cell cell) {
        cell.setBackgroundColor(new Color(199, 219, 235));
    }

    public void colorSquare(Cell cell) {
        cell.setBackgroundColor(new Color(199, 219, 235));
    }
}
