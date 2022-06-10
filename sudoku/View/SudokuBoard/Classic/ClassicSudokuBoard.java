package sudoku.View.SudokuBoard.Classic;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import sudoku.SudokuView;
import sudoku.View.SudokuBoard.Cell;
import sudoku.View.SudokuBoard.SudokuInterface;
import sudoku.View.SudokuBoard.SudokuExtend;

import java.awt.*;
import java.awt.Color;

public class ClassicSudokuBoard extends SudokuExtend implements SudokuInterface {

    SudokuView sudokuView;

    double screenOccupationFactor = 0.25;

    public ClassicSudokuBoard(int[][] sudoku, int n, int k) {
        super(sudoku, n, k);

        GridBagLayout grid = new GridBagLayout();
        this.setLayout(grid);

        createSquares();

        createCells();
        createBoard();

    }

    private void createSquares() {

    }

    public void createCells() {
        ArrayList<ArrayList<Cell>> board = new ArrayList<>();
        for (int i = 0; i < n * k; i++) {
            ArrayList<Cell> rows = new ArrayList();
            for (int j = 0; j < n * k; j++) {
                Cell cell = new Cell();
                rows.add(cell);
            }
            board.add(rows);
        }
        cells = board;
    }

    public void createBoard() {
        GridBagConstraints gbc = new GridBagConstraints();

        for (int i = 0; i < k; i++) {
            ArrayList<Square> rows = new ArrayList();

        }

        for (int l = 0; l < k * k; l++) {
            Square square = new Square();

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    gbc.fill = GridBagConstraints.BOTH;
                    gbc.gridx = j;
                    gbc.gridy = i;

                    int[] BoardCoords = { (i + n * (l / k)), (j + n * l) % (k * n) };
                    int boardValue = sudoku[BoardCoords[0]][BoardCoords[1]];
                    Cell cell = cells.get(BoardCoords[0]).get(BoardCoords[1]);

                    if (boardValue != 0) {
                        cell.setText(String.valueOf(boardValue));
                        cell.setEnabled(false);
                    }
                    square.add(cell, gbc);

                }
            }
            gbc.gridx = l % k;
            gbc.gridy = l / k;
            gbc.weightx = 1;
            gbc.weightx = 1;
            gbc.fill = GridBagConstraints.NONE;

            this.add(square, gbc);
        }
    }
}
