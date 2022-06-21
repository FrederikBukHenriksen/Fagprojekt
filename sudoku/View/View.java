package sudoku.View;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

import sudoku.View.MenuBar.SudokuMenuBar;
import sudoku.View.SudokuBoard.*;
import sudoku.View.SudokuBoard.Classic.ClassicNumpadBar;

public class View extends JFrame {

	// Containers
	public SudokuBoardAbstract sudokuBoard;
	public SudokuMenuBar menuBar;

	public View(int n, int k, SudokuBoardAbstract sudokuBoard) {

		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setResizable(false);
		// this.setUndecorated(true); // Removes title bar
		this.setVisible(true);
		// this.setExtendedState(this.getExtendedState());

		menuBar = new SudokuMenuBar();
		this.setJMenuBar(menuBar);

		this.sudokuBoard = sudokuBoard;
		this.add(sudokuBoard);
		sudokuBoard.assembleBoard();

		this.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		centerOnScreen((screenSize.width / 2), (screenSize.height / 2));

	}

	public void centerOnScreen(int xCenter, int yCenter) {
		int xScreen = xCenter - ((int) getSize().getWidth() / 2);
		int yScreen = yCenter - ((int) getSize().getHeight() / 2);
		this.setLocation(xScreen, yScreen);
	}
}