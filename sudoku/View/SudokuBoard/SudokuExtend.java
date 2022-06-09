package sudoku.View.SudokuBoard;

import java.util.ArrayList;

import javax.swing.JPanel;

public class SudokuExtend extends JPanel {
    protected int[][] sudoku;
    protected int n; // N antal celler i hver square
    protected int k; // K antal squares

    int cellSize = 60;

    protected ArrayList<ArrayList<Cell>> cells = new ArrayList();

    public SudokuExtend(int[][] sudoku, int n, int k) {
        this.sudoku = sudoku;
        this.n = n;
        this.k = k;
    }

    public ArrayList<ArrayList<Cell>> getCells() {
        return cells;
    }

    public ArrayList<Cell> getCellsLinear() {
        ArrayList<Cell> list = new ArrayList<>();
        for (ArrayList<Cell> arraylist : cells) {
            for (Cell cell : arraylist) {
                list.add(cell);
            }
        }
        return list;
    }

    public void onlySelectThePressed(Cell buttonSelected) {
        // Pressing an already selected button causes it to become unselected.
        if (!buttonSelected.isSelected()) {
            return;
        }
        getCellsLinear().forEach(b -> b.setSelected(false));
        buttonSelected.setSelected(true);
    }

    public Cell getButtonSelected() throws Exception {
        Cell selected = null;
        for (Cell cell : getCellsLinear()) {
            if (cell.isSelected()) {
                selected = cell;
            }
        }
        if (selected == null) {
            throw new Exception("No cell selected");
        }
        return selected;
    }

    public int[] getCellCoordinate(Cell selected) {
        int[] coordinate = new int[] { -1, -1 };
        for (int x = 0; x < n * k; x++) {
            for (int y = 0; y < n * k; y++) {
                Cell button = getCells().get(x).get(y);
                if (button.equals(selected)) {
                    coordinate[0] = x;
                    coordinate[1] = y;
                }
            }
        }
        return coordinate;
    }

}
