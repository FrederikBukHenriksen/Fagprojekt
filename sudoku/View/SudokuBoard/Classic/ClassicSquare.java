
package sudoku.View.SudokuBoard.Classic;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import sudoku.View.View;
import sudoku.View.SudokuBoard.Cell;

import java.awt.*;
import java.awt.Color;

public class ClassicSquare extends JPanel {

    private int n;

    public ClassicSquare(int n) {
        this.n = n;
        this.setLayout(new GridBagLayout());
        this.setBorder(new LineBorder(Color.black, 1));
    }

    public void insertCellintoSquare(Cell cell, int axis0, int axis1) {
        if (axis0 >= n || axis1 >= n) {
            throw new ArrayIndexOutOfBoundsException();
        }
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = axis0;
        gbc.gridy = axis1;

        this.add(cell, gbc);
    }

}
