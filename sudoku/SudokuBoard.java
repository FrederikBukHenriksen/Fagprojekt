package sudoku;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.Color;

public class SudokuBoard extends JPanel {

    ArrayList<ArrayList<Cell>> cells = new ArrayList();
    int n = SudokuModel.n;
    int k = SudokuModel.k;

    int[][] sudoku;

    public SudokuBoard(int[][] sudoku) {
        this.sudoku = sudoku;

        GridLayout grid = new GridLayout(k, k);
        setLayout(grid);
        setBounds(0, 0, 500, 500);
        setBackground(Color.black);
        createCells();
        createBoard();
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
        for (int l = 0; l < k * k; l++) {
            Square square = new Square();

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (sudoku[(i + n * (l / k))][(j + n * l) % (k * n)] == 0) {
                        square.add(cells.get((i + n * (l / k))).get((j + n * l) % (k * n)));

                    } else {
                        cells.get((i + n * (l / k))).get((j + n * l) % (k * n)).setText(
                                String.valueOf(sudoku[(i + n * (l / k))][(j + n * l) % (k * n)]));
                        cells.get((i + n * (l / k))).get((j + n * l) % (k * n)).setEnabled(false);
                        square.add(cells.get((i + n * (l / k))).get((j + n * l) % (k * n)));
                    }
                }
            }
            this.add(square);
        }
    }

    class Square extends JPanel {
        public Square() {
            GridLayout grid = new GridLayout(n, n, 10, 10);
            grid.setHgap(1);
            grid.setVgap(1);
            setLayout(grid);
            setBorder(new LineBorder(Color.black, 1));
        }
    }


}
