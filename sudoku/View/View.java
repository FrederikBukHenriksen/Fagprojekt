package sudoku.View;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

import sudoku.Controller.SudokuControls;
import sudoku.View.MenuBar.SudokuMenuBar;
import sudoku.View.SudokuBoard.*;
import sudoku.View.SudokuBoard.Classic.SudokuNumpad;

public class View extends JFrame {

	// Class variables
	private int n;
	private int k;
	public ArrayList<Cell> markedCells = new ArrayList<Cell>();
	private int[][] sudoku;

	// Containers
	public SudokuExtend sudokuBoard;
	public SudokuMenuBar sudokuMenuBar;
	public SudokuNumpad sudokuNumpad;

	//
	GridBagConstraints gbc = new GridBagConstraints();

	public View(int[][] sudoku, int n, int k, SudokuExtend sudokuBoard) {
		this.sudoku = sudoku;
		this.n = n;
		this.k = k;
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setResizable(true);
		this.setVisible(true);
		this.setExtendedState(this.getExtendedState());

		sudokuMenuBar = new SudokuMenuBar();
		sudokuNumpad = new SudokuNumpad(n, k);
		this.sudokuBoard = sudokuBoard;

		assembleBoard();
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

	// public void updateCellValues(int[][] sudoku) {
	// for (int x = 0; x < n * k; x++) {
	// for (int y = 0; y < n * k; y++) {
	// if (sudoku[x][y] != 0) {
	// Cell button = sudokuBoard.getCellFromCoord(x, y);
	// button.setText(String.valueOf(sudoku[x][y]));
	// } else {
	// Cell button = sudokuBoard.getCellFromCoord(x, y);
	// button.setText("");
	// }
	// }
	// }
	// }

	public void markCells() {
		// ###PRESSED BUTTON###
		// Cell pressedButton;
		// try {
		// pressedButton = SudokuControls.getButtonSelected();
		// int[] coordinates = sudokuBoard.getCellCoordinate(pressedButton);
		// if (coordinates[0] != -1) {
		// String cellText = pressedButton.getText();

		// // ###SQUARE###
		// // Determent the position of upper left corner of the square
		// int squareX = coordinates[0] / n;
		// int squareY = coordinates[1] / n;

		// // Run through the square
		// for (int i = squareX * n; i < squareX * n + n; i++) {
		// for (int j = squareY * n; j < squareY * n + n; j++) {
		// sudokuBoard.getCellFromCoord(i, j).square();
		// markedCells.add(sudokuBoard.getCellFromCoord(i, j));
		// }
		// }
		// // ###PEERS###
		// for (int i = 0; i < (n * k); i++) {
		// sudokuBoard.getCellFromCoord(coordinates[0], i).peer();
		// markedCells.add(sudokuBoard.getCellFromCoord(coordinates[0], i));

		// sudokuBoard.getCellFromCoord(i, coordinates[1]).peer();

		// markedCells.add(sudokuBoard.getCellFromCoord(i, coordinates[1]));

		// }

		// // ###SIMILAR NUMBER###
		// for (Cell[] array : sudokuBoard.getCells()) {
		// for (Cell button : array) {
		// if (!cellText.equals("") && button.getText().equals(cellText)) {
		// button.similar();
		// markedCells.add(button);
		// }
		// }
		// }
		// }
		// } catch (Exception exc) {
		// // System.out.println(exc.getMessage());
		// }

	}

	public void clearMarkedCells() {
		for (Cell cell : markedCells) {
			cell.defaultColor();
		}
		markedCells.clear();
	}

	public SudokuExtend getSudokuBoard() {
		return sudokuBoard;
	}

	public SudokuNumpad getSudokuNumpad() {
		return sudokuNumpad;
	}

}
