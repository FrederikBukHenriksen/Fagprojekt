package sudoku.View.SudokuBoard.Sandwich;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import sudoku.SudokuController;
import sudoku.SudokuView;
import sudoku.View.SudokuBoard.Cell;
import sudoku.View.SudokuBoard.SudokuControls;
import sudoku.View.SudokuBoard.SudokuInterface;
import sudoku.View.SudokuBoard.SudokuExtend;

import java.awt.*;
import java.awt.Color;

public class SandwichSumPanel extends JPanel {
    public SandwichSumPanel(int[] sums, int axis) {
        if (axis == 0) {
            this.setLayout(new GridLayout(sums.length, 0));

        }
        if (axis == 1) {
            this.setLayout(new GridLayout(0, sums.length));
        }


        for (int i = 0; i < sums.length; i++) {
            this.add(new SandwichSumLabel(String.valueOf(sums[i])));
        }
    }

}
