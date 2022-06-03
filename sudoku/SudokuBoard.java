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

    GridBagConstraints c = new GridBagConstraints();

    public SudokuBoard(int[][] sudoku) {
        this.sudoku = sudoku;

        GridBagLayout grid = new GridBagLayout();
        setLayout(grid);
        setBounds(0, 0, 500, 500);
        setBackground(Color.black);
        createCells();
        createBoard();
        setSize(this.getPreferredSize());

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
                    c.gridx = j;
                    c.gridy = i;

                    int[] BoardCoords = { (i + n * (l / k)), (j + n * l) % (k * n) };
                    int boardValue = sudoku[BoardCoords[0]][BoardCoords[1]];
                    Cell cell = cells.get(BoardCoords[0]).get(BoardCoords[1]);

                    if (boardValue != 0) {
                        cell.setText(String.valueOf(boardValue));
                        cell.setEnabled(false);
                    }
                    square.add(cell, c);

                }
            }
            c.gridx = l % k;
            c.gridy = l / k;
            this.add(square, c);
        }
    }

    class Square extends JPanel {
        public Square() {
            GridBagLayout grid = new GridBagLayout();
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
            setPreferredSize(new Dimension(50, 50));
            setMinimumSize(new Dimension(50, 50));

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
