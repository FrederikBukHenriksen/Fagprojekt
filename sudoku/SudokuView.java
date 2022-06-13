package sudoku;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import sudoku.View.MenuBar.MenuBar;
import sudoku.View.SudokuBoard.*;
import sudoku.View.SudokuBoard.Classic.ClassicSudokuBoard;
import sudoku.View.SudokuBoard.Classic.SudokuNumpad;
import sudoku.View.SudokuBoard.Sandwich.SandwichSudoku;

public class SudokuView extends JFrame {

	public int n;
	public int k;
	public ArrayList <Cell> markedCells = new ArrayList<Cell>();
	int[][] sudoku;
	public SudokuExtend sudokuBoard;
	public MenuBar menuBar;
	public SudokuControls sudokuControls;
	public SudokuNumpad sudokuNumpad;


	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public SudokuView() {
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		setResizable(true);
		setVisible(true);
		setExtendedState(this.getExtendedState());
	}

	public void showFrame(int[][] sudoku, SudokuExtend sudokuBoard) {
		n = SudokuModel.n;
		k = SudokuModel.k;
		this.sudoku = sudoku;
		// sudokuBoard = new ClassicSudokuBoard(sudoku, n, k);
		this.sudokuBoard = sudokuBoard;

		menuBar = new MenuBar();
		sudokuControls = new SudokuControls();
		sudokuNumpad = new SudokuNumpad(n, k);

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		setJMenuBar(menuBar);

		c.gridx = 0;
		c.gridy = 0;
		// c.fill = GridBagConstraints.BOTH;

		add(sudokuBoard, c);

		//Add all cells to markedCells arrayList
		for (Cell[] array : sudokuBoard.getCells()) {
			for (Cell cell : array) {
				markedCells.add(cell);
			}
		}
		// add menubar to frame
		// setJMenuBar(sudokuUI.createMenubar());

		c.gridx = 0;
		c.gridy = 1;

		add(sudokuNumpad, c);

		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;

		add(sudokuControls, c);
		pack();
	}


	public void updateCellValues(int[][] sudoku) {
		for (int x = 0; x < n * k; x++) {
			for (int y = 0; y < n * k; y++) {
				if (sudoku[x][y] != 0) {
					Cell button = sudokuBoard.getCellFromCoord(x, y);
					button.setText(String.valueOf(sudoku[x][y]));
				} else {
					Cell button = sudokuBoard.getCellFromCoord(x, y);
					button.setText("");
				}
			}
		}
	}


	public void markCells() {
		// ###PRESSED BUTTON###
		Cell pressedButton;
		try {
			pressedButton = sudokuBoard.getButtonSelected();
			int[] coordinates = sudokuBoard.getCellCoordinate(pressedButton);
			if (coordinates[0] != -1) {
				String cellText = pressedButton.getText();

				// ###SQUARE###
				// Determent the position of upper left corner of the square
				int squareX = coordinates[0] / n;
				int squareY = coordinates[1] / n;

				// Run through the square
				for (int i = squareX * n; i < squareX * n + n; i++) {
					for (int j = squareY * n; j < squareY * n + n; j++) {
						sudokuBoard.getCellFromCoord(i, j).square();
						markedCells.add(sudokuBoard.getCellFromCoord(i, j));
					}
				}
				// ###PEERS###
				for (int i = 0; i < (n * k); i++) {
					sudokuBoard.getCellFromCoord(coordinates[0], i).peer();
					markedCells.add(sudokuBoard.getCellFromCoord(coordinates[0], i));

					sudokuBoard.getCellFromCoord(i, coordinates[1]).peer();

					markedCells.add(sudokuBoard.getCellFromCoord(i, coordinates[1]));

				}

				// ###SIMILAR NUMBER###
				for (Cell[] array : sudokuBoard.getCells()) {
					for (Cell button : array) {
						if (!cellText.equals("") && button.getText().equals(cellText)) {
							button.similar();
							markedCells.add(button);
						}
					}
				}
			}
		} catch (Exception exc) {
			// System.out.println(exc.getMessage());
		}

	}
	public void clearMarkedCells() {
		for (Cell cell : markedCells) {
			cell.defaultColor();
		}
		markedCells.clear();
	}


}
