package sudoku.Controller;

import java.util.ArrayList;

import sudoku.Controller.Exceptions.CellDoesNotExist;
import sudoku.Controller.Exceptions.NoCellSelected;
import sudoku.View.SudokuBoard.Cell;

public class ClassicSudokuControls {

    // protected Controller controller;

    protected Cell[][] cells;

    public ClassicSudokuControls(Cell[][] cells) {
        this.cells = cells;
    }

    public void updateCellValues(int[][] sudoku) {
        for (int x = 0; x < sudoku.length; x++) {
            for (int y = 0; y < sudoku.length; y++) {
                if (sudoku[x][y] != 0) {
                    Cell button = getCellFromCoord(x, y);
                    button.setText(String.valueOf(sudoku[x][y]));
                } else {
                    Cell button = getCellFromCoord(x, y);
                    button.setText("");
                }
            }
        }

    }

    public Cell getButtonSelected() throws NoCellSelected {
        Cell selected = null;
        for (Cell cell : getCells1d()) {
            if (cell.isSelected()) {
                selected = cell;
            }
        }
        if (selected == null) {
            throw new NoCellSelected();
        }
        return selected;
    }

    public int[] getCellCoordinate(Cell selected) throws CellDoesNotExist {
        int[] coordinate = new int[] { -1, -1 };
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells.length; y++) {
                Cell button = cells[x][y];
                if (button.equals(selected)) {
                    coordinate[0] = x;
                    coordinate[1] = y;
                }
            }
        }
        if (coordinate[0] == -1 || coordinate[1] == -1) {
            throw new CellDoesNotExist();
        }
        return coordinate;
    }

    public Cell[][] getCells2d() {
        return cells;
    }

    public Cell[] getCells1d() {
        Cell[] list = new Cell[cells.length * cells.length];
        for (int i = 0; i < list.length; i++) {
            list[i] = cells[i % (cells.length)][i / (cells[0].length)];

        }
        return list;
    }

    public void selectOnlyThisButton(Cell buttonSelected) {
        // Pressing an already selected button causes it to become unselected.
        if (!buttonSelected.isSelected()) {
            return;
        }
        for (Cell cell : getCells1d()) {
            cell.setSelected(false);
        }
        buttonSelected.setSelected(true);
    }

    public Cell getCellFromCoord(int x, int y) {
        // try {
        return cells[x][y];
        // } catch (ArrayIndexOutOfBoundsException e) {
        // throw new CellDoesNotExist();
        // }
    }

}