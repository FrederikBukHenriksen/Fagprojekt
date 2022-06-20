package sudoku.View.SudokuBoard.Sandwich;

import sudoku.View.SudokuBoard.Classic.ClassicSudokuBoard;
import sudoku.View.SudokuBoard.Classic.Square;

import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import sudoku.View.View;
import sudoku.View.SudokuBoard.Cell;
import sudoku.View.SudokuBoard.SudokuBoardAbstract;

import java.awt.*;
import java.awt.Color;

public class SandwichSudoku extends ClassicSudokuBoard {

    SandwichSumPanel xSumPanel;
    SandwichSumPanel ySumPanel;

    ClassicSudokuBoard classicSudokuBoard;

    public SandwichSudoku(int[][] sudoku, int n, int k, int[] xSum, int[] ySum) {
        super(sudoku, n, k);
        xSumPanel = new SandwichSumPanel(xSum, 1);
        ySumPanel = new SandwichSumPanel(ySum, 0);
    }

    @Override
    public void assembleBoard() {
        GridBagConstraints gbc = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        this.add(createBoardPanel(), gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.anchor = GridBagConstraints.WEST;
        this.add(sudokuNumpad, gbc);
    }

    protected JPanel createBoardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(xSumPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(ySumPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(super.createBoardPanel(), gbc);

        return panel;
    }

}
