package sudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.Color;

public class SudokuBoard extends JPanel {

    ArrayList<ArrayList<Cell>> sudokuboardCells = new ArrayList();
    int n = SudokuModel.n;
    int k = SudokuModel.k;

    public SudokuBoard(int[][] sudoku) {

    }

    class Square extends JPanel {
        public Square() {
            GridLayout grid = new GridLayout(n, n, 10, 10);
            grid.setHgap(1);
            grid.setVgap(1);
            setLayout(grid);
        }
    }

    public ArrayList<ArrayList<Cell>> createCells(int[][] sudoku) {
        ArrayList<ArrayList<Cell>> board = new ArrayList<>();
        for (int i = 0; i < n * k; i++) {
            ArrayList<Cell> rows = new ArrayList();
            for (int j = 0; j < n * k; j++) {
                Cell cell = new Cell();
                rows.add(cell);
            }
            board.add(rows);
        }
        return board;
    }

}
