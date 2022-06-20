package sudoku.View;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

import sudoku.View.MenuBar.SudokuMenuBar;
import sudoku.View.SudokuBoard.*;
import sudoku.View.SudokuBoard.Classic.ClassicSudokuNumpad;

public class View extends JFrame {

	// Containers
	public SudokuBoardAbstract sudokuBoard;
	public SudokuMenuBar sudokuMenuBar;
	public ClassicSudokuNumpad sudokuNumpad;

	// Class variables
	private int n;
	private int k;
	private int[][] sudoku;
	GridBagConstraints gbc = new GridBagConstraints();


	public View(int[][] sudoku, int n, int k, SudokuBoardAbstract sudokuBoard) {
		this.sudoku = sudoku;
		this.n = n;
		this.k = k;

		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setResizable(false);
		// this.setUndecorated(true); // Removes title bar
		this.setVisible(true);
		// this.setExtendedState(this.getExtendedState());

		sudokuMenuBar = new SudokuMenuBar();
		this.setJMenuBar(sudokuMenuBar);

		this.sudokuBoard = sudokuBoard;
		this.add(sudokuBoard);

		this.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		centerOnScreen((screenSize.width / 2), (screenSize.height / 2));

	}

	public void centerOnScreen(int xCenter, int yCenter) {
		int xScreen = xCenter - ((int) getSize().getWidth() / 2);
		int yScreen = yCenter - ((int) getSize().getHeight() / 2);
		this.setLocation(xScreen, yScreen);
	}



	public SudokuBoardAbstract getSudokuBoard() {
		return sudokuBoard;
	}

	public ClassicSudokuNumpad getSudokuNumpad() {
		return sudokuNumpad;
	}

}