package sudoku;

import java.util.ArrayList;

import javax.swing.JPanel;

import sudoku.View.SudokuBoard.Cell;

public class SudokuPanel extends JPanel {
    protected int[][] sudoku;
    protected int n; // N antal celler i hver square
    protected int k; // K antal squares

    int cellSize = 60;

    protected ArrayList<ArrayList<Cell>> cells = new ArrayList();

    public SudokuPanel(int[][] sudoku, int n, int k) {
        this.sudoku = sudoku;
        this.n = n;
        this.k = k;
    }

}
