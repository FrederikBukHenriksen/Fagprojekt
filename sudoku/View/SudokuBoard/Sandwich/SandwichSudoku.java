package sudoku.View.SudokuBoard.Sandwich;

import sudoku.View.SudokuBoard.Classic.ClassicSudokuBoard;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import sudoku.View.View;
import sudoku.View.SudokuBoard.Cell;
import sudoku.View.SudokuBoard.Square;
import sudoku.View.SudokuBoard.SudokuInterface;
import sudoku.View.SudokuBoard.SudokuExtend;

import java.awt.*;
import java.awt.Color;

public class SandwichSudoku extends ClassicSudokuBoard {

    // protected GridBagConstraints lolcat = new GridBagConstraints();

    protected GridBagConstraints sandwichGcb = new GridBagConstraints();

    public SandwichSudoku(int[][] sudoku, int n, int k, int[] xSum, int[] ySum) {

        super(sudoku, n, k);
        this.setLayout(new GridBagLayout());

        sandwichGcb.gridx = 0;
        sandwichGcb.gridy = 0;
        sandwichGcb.fill = GridBagConstraints.HORIZONTAL;
        this.add(new SandwichSumPanel(ySum, 1), sandwichGcb);

        sandwichGcb.gridx = 1;
        sandwichGcb.gridy = 1;
        sandwichGcb.fill = GridBagConstraints.VERTICAL;

        this.add(new SandwichSumPanel(xSum, 0), sandwichGcb);

        // Only assemble this design if it is a Sandeich game
        if (this.getClass().getSimpleName().equals("SandwichSudoku")) {

            sandwichGcb.gridx = 0;
            sandwichGcb.gridy = 1;

            this.add(assembleBoard(squares), sandwichGcb);
        }

    }

    // Create the board as a JPanel-object
    protected JPanel assembleBoard(Square[][] squares) {
        // Use createBoard to make a panel
        JPanel panel = new JPanel();
        GridBagLayout grid = new GridBagLayout();
        panel.setLayout(grid);

        for (int i = 0; i < squares.length; i++) {
            for (int j = 0; j < squares[0].length; j++) {
                gbc.gridx = i;
                gbc.gridy = j;
                panel.add(squares[i][j], gbc);
            }
        }
        return panel;
    }

}
