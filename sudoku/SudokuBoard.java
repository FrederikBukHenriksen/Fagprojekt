package sudoku;

import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    class Cell extends JToggleButton {

        Color selected = new Color(161, 205, 240);
        Color conflict = new Color(240, 192, 193);
        Color square = new Color(199, 219, 235);
        Color similar = new Color(144, 182, 212);
        Color peer = new Color(199, 219, 235);
        Color conflictFont = new Color(230, 67, 70);
        Color def = Color.white;
        Color defFont = new Color(80, 110, 242);

        boolean enabled = true;

        public Cell() {
            setText("");
            setBackground(def);
            setForeground(defFont);
            setFont(new Font("Serif", Font.PLAIN, 32));
            setBorder(new LineBorder(Color.black, 1));
            UIManager.put("ToggleButton.highlight", Color.red);
            UIManager.put("ToggleButton.select", selected);
            SwingUtilities.updateComponentTreeUI(this);
        }

        @Override
        public void setEnabled(boolean b) {
            defFont = Color.black;
            enabled = false;
            setBackground(def);
            setForeground(defFont);
        }

        @Override
        public void setText(String text) {
            if (enabled) {
                super.setText(text);
            }
        }

        public void defaultColor() {
            setBackground(def);
            setForeground(defFont);
        }

        public void conflict() {
            if (enabled) {
                setForeground(conflictFont);
            }
            setBackground(conflict);
        }

        public void unSelected() { // TODO: Tjek om vi bruger
            setForeground(defFont);
            setBackground(def);
        }

        public void similar() {
            if (!(this.getBackground().equals(this.conflict))) {
                setBackground(similar);
            }
        }

        public void peer() {
            if (!(this.getBackground().equals(this.conflict))) {
                setBackground(peer);
            }
        }

        public void square() {
            if (!(this.getBackground().equals(this.conflict))) {
                setBackground(square);
            }
        }
    }

    public ArrayList<Cell> getCells() {
        ArrayList<Cell> temp = new ArrayList<>();
        for (ArrayList<Cell> arraylist : cells) {
            for (Cell cell : arraylist) {
                temp.add(cell);
            }
        }
        return temp;
    }

}
