
package sudoku.View.SudokuBoard;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import sudoku.View.View;

import java.awt.*;
import java.awt.Color;

public class Square extends JPanel {

    protected GridBagConstraints gbc = new GridBagConstraints();

    int n;

    public Square(int n) {
        this.n = n;
        GridBagLayout grid = new GridBagLayout();
        setBackground(Color.red);
        setLayout(grid);
        setBorder(new LineBorder(Color.black, 1));

    }

    public void insertCellintoSquare(Cell cell, int axis0, int axis1) {
        if (axis0 >= n || axis1 >= n) {
            throw new ArrayIndexOutOfBoundsException();
        }
        gbc.gridx = axis0;
        gbc.gridy = axis1;

        this.add(cell, gbc);
    }

}
