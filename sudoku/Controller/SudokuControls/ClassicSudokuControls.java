package sudoku.Controller.SudokuControls;

import sudoku.Controller.Exceptions.ExceptionCellDoesNotExist;
import sudoku.Controller.Exceptions.ExceptionNoCellSelected;
import sudoku.View.SudokuBoard.Cell;

public class ClassicSudokuControls implements SudokuControlsInterface {

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

    public Cell getCellSelected() throws ExceptionNoCellSelected {
        Cell selected = null;
        for (Cell cell : getCells1d()) {
            if (cell.isSelected()) {
                selected = cell;
            }
        }
        if (selected == null) {
            throw new ExceptionNoCellSelected();
        }
        return selected;
    }

    public int[] getCellCoordinate(Cell cell) throws ExceptionCellDoesNotExist {
        int[] coordinate = new int[] { -1, -1 };
        for (int x = 0; x < cells.length; x++) {
            for (int y = 0; y < cells.length; y++) {
                Cell button = cells[x][y];
                if (button.equals(cell)) {
                    coordinate[0] = x;
                    coordinate[1] = y;
                }
            }
        }
        if (coordinate[0] == -1 || coordinate[1] == -1) {
            throw new ExceptionCellDoesNotExist();
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
        return cells[x][y];
    }
}