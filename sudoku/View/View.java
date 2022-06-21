package sudoku.View;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

import sudoku.View.MenuBar.SudokuMenuBar;
import sudoku.View.SudokuBoard.*;

public class View extends JFrame {

	// Containers
	public SudokuBoardExtend sudokuBoard;
	public SudokuMenuBar sudokuMenuBar;
	public SudokuNumpad sudokuNumpad;

	// Class variables
	private int n;
	private int k;
	private int[][] sudoku;
	GridBagConstraints gbc = new GridBagConstraints();

	public View(int[][] sudoku, int n, int k, SudokuBoardExtend sudokuBoard) {
		this.sudoku = sudoku;
		this.n = n;
		this.k = k;

		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setResizable(false);
		// this.setUndecorated(true); // Removes title bar
		this.setVisible(true);
		// this.setExtendedState(this.getExtendedState());

		this.sudokuBoard = sudokuBoard;
		sudokuMenuBar = new SudokuMenuBar();
		sudokuNumpad = new SudokuNumpad(n, k);
		assembleBoard();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		centerOnScreen((screenSize.width / 2), (screenSize.height / 2));

	}

	public void centerOnScreen(int xCenter, int yCenter) {
		int xScreen = xCenter - ((int) getSize().getWidth() / 2);
		int yScreen = yCenter - ((int) getSize().getHeight() / 2);
		this.setLocation(xScreen, yScreen);
	}

	public void assembleBoard() {

		this.setLayout(new GridBagLayout());
		this.setJMenuBar(sudokuMenuBar);

		gbc.gridx = 0;
		gbc.gridy = 0;

		this.add(sudokuBoard, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;

		this.add(sudokuNumpad, gbc);

		this.pack();
	}

	public SudokuBoardExtend getSudokuBoard() {
		return sudokuBoard;
	}

	public SudokuNumpad getSudokuNumpad() {
		return sudokuNumpad;
	}

}