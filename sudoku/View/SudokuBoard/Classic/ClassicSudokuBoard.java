package sudoku.View.SudokuBoard.Classic;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import sudoku.SudokuView;
import sudoku.View.SudokuBoard.Cell;
import sudoku.View.SudokuBoard.Square;
import sudoku.View.SudokuBoard.SudokuInterface;
import sudoku.View.SudokuBoard.SudokuExtend;

import java.awt.*;
import java.awt.Color;

public class ClassicSudokuBoard extends SudokuExtend {

    protected GridBagConstraints gbc = new GridBagConstraints();

    double screenOccupationFactor = 0.25;

    // ArrayList<Square> squares = new ArrayList<>();
    // Square[][] squares;
    public Square[][] squares;

    public ClassicSudokuBoard(int[][] sudoku, int n, int k) {
        super(sudoku, n, k);
        GridBagLayout grid = new GridBagLayout();
        this.setLayout(grid);

        // createSquares(n, k);

        // createCells();
        // createBoard();

        this.cells = createCellsNew(n, k);
        this.cells = loadSudokuIntoCells(sudoku, this.cells);
        this.squares = createSquares(n, k);
        this.squares = loadCellsIntoSquares(this.cells, this.squares, k);

        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                gbc.gridx = i;
                gbc.gridy = j;
                this.add(squares[i][j], gbc);
            }
        }
        // this.add(squares[0][0]);
    }

    protected Cell[][] createCellsNew(int n, int k) {
        Cell[][] cells = new Cell[n * k][n * k];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j] = new Cell();
            }
        }
        return cells;
    }

    protected Cell[][] loadSudokuIntoCells(int[][] sudoku, Cell[][] cellsMethod) {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku.length; j++) {
                if (sudoku[i][j] == 0) {
                    cellsMethod[i][j].setEnabled(true);
                } else {
                    cellsMethod[i][j].setText(String.valueOf(sudoku[i][j]));
                    cellsMethod[i][j].setEnabled(false);

                    // cellsMethod[i][j].setText(String.valueOf(sudoku[i][j]));
                    // cellsMethod[i][j].setEnabled(false);
                }
            }
        }
        return cellsMethod;
    }

    protected Square[][] createSquares(int n, int k) {
        Square[][] squares = new Square[k][k];
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                squares[i][j] = new Square(n);
            }
        }
        return squares;
    }

    protected Square[][] loadCellsIntoSquares(Cell[][] cells, Square[][] squares, int k) {
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                Square selectedSquare = squares[i][j];
                for (int l = 0; l < n; l++) {
                    for (int m = 0; m < n; m++) {
                        selectedSquare.insertCellintoSquare(cells[j * n + m][i * k + l], l, m);
                    }
                }

            }
        }

        return squares;
    }

    // public void createCells() {
    // ArrayList<ArrayList<Cell>> board = new ArrayList<>();
    // for (int i = 0; i < n * k; i++) {
    // ArrayList<Cell> rows = new ArrayList();
    // for (int j = 0; j < n * k; j++) {
    // Cell cell = new Cell();
    // rows.add(cell);
    // }
    // board.add(rows);
    // }
    // cells = board;
    // }

    // public void createBoard() {
    // GridBagConstraints gbc = new GridBagConstraints();

    // for (int i = 0; i < k; i++) {
    // ArrayList<Square> rows = new ArrayList();

    // }

    // for (int l = 0; l < k * k; l++) {
    // Square square = new Square();

    // for (int i = 0; i < n; i++) {
    // for (int j = 0; j < n; j++) {
    // gbc.fill = GridBagConstraints.BOTH;
    // gbc.gridx = j;
    // gbc.gridy = i;
    // gbc.weightx = 1;

    // int[] BoardCoords = { (i + n * (l / k)), (j + n * l) % (k * n) };
    // int boardValue = sudoku[BoardCoords[0]][BoardCoords[1]];
    // Cell cell = cells.get(BoardCoords[0]).get(BoardCoords[1]);

    // if (boardValue != 0) {
    // cell.setText(String.valueOf(boardValue));
    // cell.setEnabled(false);
    // }
    // square.add(cell, gbc);

    // }
    // }
    // gbc.gridx = l % k;
    // gbc.gridy = l / k;
    // gbc.weightx = 1;
    // gbc.weightx = 1;
    // gbc.fill = GridBagConstraints.NONE;

    // this.add(square, gbc);
    // }
    // }
}
