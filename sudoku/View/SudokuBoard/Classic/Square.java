
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

class Square extends JPanel {
    public Square() {
        GridBagLayout grid = new GridBagLayout();
        setBackground(Color.red);
        setLayout(grid);
        setBorder(new LineBorder(Color.black, 1));
    }

}