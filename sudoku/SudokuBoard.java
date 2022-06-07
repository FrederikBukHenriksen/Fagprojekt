package sudoku;

import sudoku.View.Cell.*;
import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;


import java.awt.*;
import java.awt.Color;

public class SudokuBoard extends JPanel {

    SudokuView sudokuView;

    double screenOccupationFactor = 0.25;

    ArrayList<ArrayList<Cell>> cells = new ArrayList();
    int n = SudokuModel.n;
    int k = SudokuModel.k;

    int cellSize = 60;

    int[][] sudoku;


    public SudokuBoard(SudokuView sudokuView) {
        this.sudokuView = sudokuView;
        this.sudoku = sudokuView.sudoku;

        GridBagLayout grid = new GridBagLayout();
        // this.setPreferredSize(new Dimension(1000, 1000));

        this.setLayout(grid);

        setBackground(Color.green);
        createCells();
        createBoard();

    }

    // @Override
    // public void reshape(int x, int y, int width, int height) {
    // int currentWidth = getWidth();
    // int currentHeight = getHeight();

    // // if (currentWidth > currentHeight) {
    // // width = currentHeight;
    // // } else if (currentWidth < currentHeight) {
    // // height = currentWidth;
    // // }
    // System.out.println(width + " " + height);
    // super.reshape(x, y, width, height);
    // }

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
                    gbc.weightx = 1;

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

    class Square extends JPanel {
        public Square() {
            GridBagLayout grid = new GridBagLayout();
            setBackground(Color.red);
            setLayout(grid);
            setBorder(new LineBorder(Color.black, 1));
        }

    }

    // class Cell extends JToggleButton {

    // Color selected = new Color(161, 205, 240);
    // Color conflict = new Color(240, 192, 193);
    // Color square = new Color(199, 219, 235);
    // Color similar = new Color(144, 182, 212);
    // Color peer = new Color(199, 219, 235);
    // Color conflictFont = new Color(230, 67, 70);
    // Color def = Color.white;
    // Color defFont = new Color(80, 110, 242);

    // boolean enabled = true;

    // public Cell() {
    // setText("");
    // setBackground(def);
    // setForeground(defFont);
    // setFont(new Font("Serif", Font.PLAIN, 28));
    // setBorder(new LineBorder(Color.black, 1));
    // // UIManager.put("ToggleButton.highlight", Color.red);
    // UIManager.put("ToggleButton.select", selected);
    // SwingUtilities.updateComponentTreeUI(this);
    // cellSize = (int) Math.floor((sudokuView.screenSize.getHeight() *
    // screenOccupationFactor) / (n * k));

    // setMinimumSize(new Dimension(25, 25));
    // setPreferredSize(new Dimension(40, 40));


    // }

    // @Override
    // public void setEnabled(boolean b) {
    // defFont = Color.black;
    // enabled = false;
    // setBackground(def);
    // setForeground(defFont);
    // }

    // @Override
    // public void setText(String text) {
    // if (enabled) {
    // super.setText(text);
    // }
    // }

    // public void adjustSize(int sizeAdjustment) {
    // int currentSize = (int) getSize().getWidth();
    // int newSize = currentSize + sizeAdjustment;
    // setSize(new Dimension(newSize, newSize));
    // this.setPreferredSize(new Dimension(newSize, newSize));
    // }

    // public void defaultColor() {
    // setBackground(def);
    // setForeground(defFont);
    // }

    // public void conflict() {
    // if (enabled) {
    // setForeground(conflictFont);
    // }
    // setBackground(conflict);
    // }

    // public void unSelected() { // TODO: Tjek om vi bruger
    // setForeground(defFont);
    // setBackground(def);
    // }

    // public void similar() {
    // if (!(this.getBackground().equals(this.conflict))) {
    // setBackground(similar);
    // }
    // }

    // public void peer() {
    // if (!(this.getBackground().equals(this.conflict))) {
    // setBackground(peer);
    // }
    // }

    // public void square() {
    // if (!(this.getBackground().equals(this.conflict))) {
    // setBackground(square);
    // }
    // }
    // }

    public ArrayList<Cell> getCellsLinear() {
        ArrayList<Cell> temp = new ArrayList<>();
        for (ArrayList<Cell> arraylist : cells) {
            for (Cell cell : arraylist) {
                temp.add(cell);
            }
        }
        return temp;
    }

}
