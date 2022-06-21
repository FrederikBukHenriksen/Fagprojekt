package sudoku.View.SudokuBoard.Sandwich;

import sudoku.View.SudokuBoard.Classic.ClassicSudokuBoard;

import javax.swing.*;

import java.awt.*;

public class SandwichSudoku extends ClassicSudokuBoard {

    protected SandwichSumPanel ySumPanel;
    protected SandwichSumPanel xSumPanel;
	/*
	 * Author: Frederik
	 * Function: Creates sudoku board for sandwich sudoku
	 * Input: Simple 2d sudoku array, n and k and xSum and ySum arrays
	 */
    public SandwichSudoku(int[][] sudoku, int n, int k, int[] xSum, int[] ySum) {
        super(sudoku, n, k);
        ySumPanel = new SandwichSumPanel(xSum, 1);
        xSumPanel = new SandwichSumPanel(ySum, 0);
    }
	/*
	 * Author: Frederik
	 * Function: Creates layout of main sudoku board, and adds numpad
	 */
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
        this.add(numpad, gbc);
    }
	/*
	 * Author: Frederik
	 * Function: Assemples squares in one panel
	 * output: Assempled squares as panel
	 */
    protected JPanel createBoardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(ySumPanel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.VERTICAL;
        panel.add(xSumPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(super.createBoardPanel(), gbc);

        return panel;
    }

}
