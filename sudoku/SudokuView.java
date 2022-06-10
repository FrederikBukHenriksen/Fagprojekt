package sudoku;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import sudoku.View.MenuBar.MenuBar;
import sudoku.View.SudokuBoard.*;
import sudoku.View.SudokuBoard.Classic.ClassicSudokuBoard;
import sudoku.View.SudokuBoard.Classic.SudokuNumpad;

public class SudokuView extends JFrame {

	public int n;
	public int k;
	public ArrayList <Cell> markedCells = new ArrayList<Cell>();
	int[][] sudoku;
	public ClassicSudokuBoard sudokuBoard;
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

	public void showFrame(int[][] sudoku) {
		n = SudokuModel.n;
		k = SudokuModel.k;
		this.sudoku = sudoku;
		sudokuBoard = new ClassicSudokuBoard(sudoku, n, k);
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
		for (Cell cell : sudokuBoard.getCellsLinear()) {
			markedCells.add(cell);
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
					Cell button = sudokuBoard.getCells().get(x).get(y);
					button.setText(String.valueOf(sudoku[x][y]));
				} else {
					Cell button = sudokuBoard.getCells().get(x).get(y);
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
						sudokuBoard.getCells().get(i).get(j).square();
						markedCells.add(sudokuBoard.getCells().get(i).get(j));
					}
				}
				// ###PEERS###
				for (int i = 0; i < (n * k); i++) {
					sudokuBoard.getCells().get(coordinates[0]).get(i).peer();
					markedCells.add(sudokuBoard.getCells().get(coordinates[0]).get(i));
					sudokuBoard.getCells().get(i).get(coordinates[1]).peer();
					markedCells.add(sudokuBoard.getCells().get(i).get(coordinates[1]));
				}

				// ###SIMILAR NUMBER###
				for (ArrayList<Cell> array : sudokuBoard.getCells()) {
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
