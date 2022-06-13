package sudoku.View.SudokuBoard.Sandwich;

import sudoku.View.SudokuBoard.Classic.ClassicSudokuBoard;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import sudoku.SudokuView;
import sudoku.View.SudokuBoard.Cell;
import sudoku.View.SudokuBoard.SudokuInterface;
import sudoku.View.SudokuBoard.SudokuExtend;

import java.awt.*;
import java.awt.Color;

public class SandwichSudoku extends ClassicSudokuBoard {

    protected GridBagConstraints lolcat = new GridBagConstraints();

    public SandwichSudoku(int[][] sudoku, int n, int k, int[] xSum, int[] ySum) {

        super(sudoku, n, k);

        // gbc.gridx = 1;
        // gbc.gridy = 0;
        // this.add(new SandwichSumPanel(ySum, 0), lolcat);

        // gbc.gridx = 0;
        // gbc.gridy = 4;
        // this.add(new SandwichSumPanel(xSum, 1), lolcat);

    }

}
