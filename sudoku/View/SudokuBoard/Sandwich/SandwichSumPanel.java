package sudoku.View.SudokuBoard.Sandwich;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import sudoku.SudokuView;
import sudoku.View.SudokuBoard.Cell;
import sudoku.View.SudokuBoard.SudokuInterface;
import sudoku.View.SudokuBoard.SudokuExtend;

import java.awt.*;
import java.awt.Color;

import javax.swing.JPanel;

public class SandwichSumPanel extends JPanel {
    public SandwichSumPanel(int[] sums, int axis) {
        if (axis == 0) {
            this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        } else if (axis == 1) {
            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        }

        for (int i = 0; i < sums.length; i++) {
            this.add(new SandwichSumButton(String.valueOf(sums[i])));
        }
    }

}
