package sudoku.View.SudokuBoard;

import java.util.ArrayList;

import javax.swing.JPanel;

import sudoku.Controller.Exceptions.CellDoesNotExist;
import sudoku.Controller.Exceptions.NoCellSelected;

public class SudokuExtend extends JPanel {
    protected int[][] sudoku;
    protected int n; // N antal celler i hver square
    protected int k; // K antal squares

    public Cell[][] cells;

    public SudokuExtend(int[][] sudoku, int n, int k) {
        this.sudoku = sudoku;
        this.n = n;
        this.k = k;
    }

    public Cell[][] getCells() {
        return cells;
    }







}
