package sudoku.View.SudokuBoard.Classic;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import sudoku.View.View;
import sudoku.View.SudokuBoard.Cell;
import sudoku.View.SudokuBoard.SudokuBoardAbstract;
import sudoku.View.SudokuBoard.SudokuBoardInterface;

import java.awt.*;
import java.awt.Color;

public class ClassicSudokuBoard extends SudokuBoardAbstract {

    protected Square[][] squares;

    public ClassicSudokuBoard(int[][] sudoku, int n, int k) {
        // this.setLayout(new BorderLayout(0, 0)); // No gap to outer panel.
        sudokuNumpad = new ClassicSudokuNumpad(n, k);
        this.sudoku = sudoku;
        this.cells = createCells(n, k);
        this.cells = loadSudokuIntoCells(sudoku, this.cells);
        this.squares = createSquares(n, k);
        this.squares = loadCellsIntoSquares(this.cells, this.squares, n, k);
    }

    @Override
    public void assembleBoard() {
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        this.add(createBoardPanel(), gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.WEST;
        this.add(sudokuNumpad, gbc);
    }

    protected JPanel createBoardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                gbc.gridx = i;
                gbc.gridy = j;
                panel.add(squares[i][j], gbc);
            }
        }
        return panel;
    }


    protected Cell[][] createCells(int n, int k) {
        Cell[][] cells = new Cell[n * k][n * k];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j] = new Cell();
            }
        }
        return cells;
    }

    protected Cell[][] loadSudokuIntoCells(int[][] sudoku, Cell[][] cells) {
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku.length; j++) {
                if (sudoku[i][j] == 0) {
                    cells[i][j].setEnabled(true);
                } else {
                    cells[i][j].setText(String.valueOf(sudoku[i][j]));
                    cells[i][j].setEnabled(false);
                }
            }
        }
        return cells;
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

    protected Square[][] loadCellsIntoSquares(Cell[][] cells, Square[][] squares, int n, int k) {
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
}
