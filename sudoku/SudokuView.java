package sudoku;

import java.awt.event.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.*;
import java.awt.Component; //import these 3 header files
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.*;

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
	public JPanel controls;
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

	public void onlySelectThePressed(Cell buttonSelected) {

		// Pressing an already selected button causes it to become unselected.
		if (buttonSelected.isSelected() == false) {
			return;
		}

		sudokuBoard.getCellsLinear().forEach(b -> b.setSelected(false));
		buttonSelected.setSelected(true);

	}

	public Cell getButtonSelected() throws Exception {
		Cell selected = null;
		for (Cell cell : sudokuBoard.getCellsLinear()) {
			if (cell.isSelected()) {
				selected = cell;
			}
		}
		if (selected == null) {
			throw new Exception("No cell selected");
		}
		return selected;
	}

	public int[] getCellCoordinate(Cell selected) {
		int[] coordinate = new int[] { -1, -1 };
		for (int x = 0; x < n * k; x++) {
			for (int y = 0; y < n * k; y++) {
				Cell button = sudokuBoard.getCells().get(x).get(y);
				if (button.equals(selected)) {
					coordinate[0] = x;
					coordinate[1] = y;
				}
			}
		}
		return coordinate;
	}

	public void updateBoard(int[][] sudoku) {
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
			pressedButton = getButtonSelected();
			int[] coordinates = getCellCoordinate(pressedButton);
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

	public Cell getCellFromCoord(int x, int y) {
		return sudokuBoard.getCells().get(x).get(y);
	}
}
