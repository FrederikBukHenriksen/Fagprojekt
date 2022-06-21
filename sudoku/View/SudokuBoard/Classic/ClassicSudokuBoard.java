package sudoku.View.SudokuBoard.Classic;

import javax.swing.*;
import sudoku.View.SudokuBoard.Cell;
import sudoku.View.SudokuBoard.SudokuBoardAbstract;

import java.awt.*;

public class ClassicSudokuBoard extends SudokuBoardAbstract {

    protected ClassicSquare[][] squares;
	/*
	 * Author: Frederik
	 * Function: Creates sudoku board
	 * Input: Simple 2d sudoku array, n and k
	 */
    public ClassicSudokuBoard(int[][] sudoku, int n, int k) {
        // this.setLayout(new BorderLayout(0, 0)); // No gap to outer panel.
        numpad = new ClassicNumpadBar(n, k);
        this.sudoku = sudoku;
        this.cells = createCells(n, k);
        this.cells = loadSudokuIntoCells(sudoku, this.cells);
        this.squares = createSquares(n, k);
        this.squares = loadCellsIntoSquares(this.cells, this.squares, n, k);
    }

	/*
	 * Author: Frederik
	 * Function: Creates layout of main sudoku board, and adds numpad
	 */
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
        this.add(numpad, gbc);
    }
	/*
	 * Author: Frederik
	 * Function: Assemples squares in one panel
	 * output: Assempled squares as panel
	 */
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
	/*
	 * Author: Frederik
	 * Function: Creates correct amount of cells
	 * Input: n and k
	 * Output: 2d Array of all cells
	 */
    protected Cell[][] createCells(int n, int k) {
        Cell[][] cells = new Cell[n * k][n * k];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                cells[i][j] = new Cell();
            }
        }
        return cells;
    }
	/*
	 * Author: Frederik
	 * Function: Loads all numbers into cells 
	 * Input: Sudoku in simple 2d array and cell 2d array 
	 * Output: Filled cell 2d array
	 */
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
    /*
	 * Author: Frederik
	 * Function: Creates correct amount of squares
	 * Input: n and k 
	 * Output: Correct amount of squares in 2d array
	 */
    protected ClassicSquare[][] createSquares(int n, int k) {
        ClassicSquare[][] squares = new ClassicSquare[k][k];
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                squares[i][j] = new ClassicSquare(n);
            }
        }
        return squares;
    }
    /*
	 * Author: Frederik
	 * Function: Loads cells into squares
	 * Input: Cell 2d array, Square 2d array, n and k
	 * Output: Squares with correct cells, as a 2d square array
	 */
    protected ClassicSquare[][] loadCellsIntoSquares(Cell[][] cells, ClassicSquare[][] squares, int n, int k) {
        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                ClassicSquare selectedSquare = squares[i][j];
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
