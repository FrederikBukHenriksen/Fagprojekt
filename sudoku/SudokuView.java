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
import sudoku.SudokuBoard.Cell;

public class SudokuView extends JFrame {

	public int n;
	public int k;
	public ArrayList <Cell> markedCells = new ArrayList<Cell>();
	int[][] sudoku;
	SudokuBoard sudokuBoard;
	SudokuUI sudokuUI;
	JPanel controls;

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	public SudokuView() {
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		setResizable(true);
		setVisible(true);
		setExtendedState(this.getExtendedState());

		// addComponentListener(new ComponentAdapter() {
		// public void componentResized(ComponentEvent evt) {
		// int newSize = sudokuBoard.cellSize;
		// if (getSize().getWidth() <= getSize().getHeight()) {
		// // Width the limiting factor
		// newSize = (((int) getSize().getWidth()) - 12) / (n * k); // 12 border
		// } else if (getSize().getWidth() > getSize().getHeight()) {
		// // Height the limiting factor
		// newSize = (((int) getSize().getHeight()) - 12) / (n * k); // 12 border
		// }
		// for (Cell cell : sudokuBoard.getCellsLinear()) {
		// cell.changeSize(newSize);
		// }
		// System.out.println(newSize);
		// System.out.println(sudokuBoard.getCellsLinear().get(0).getPreferredSize());

		// pack();
		// }
		// });

	}

	public void showFrame(int[][] sudoku) {
		n = SudokuModel.n;
		k = SudokuModel.k;
		this.sudoku = sudoku;
		sudokuBoard = new SudokuBoard(this);
		sudokuUI = new SudokuUI(this);

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weightx = 1;
		c.anchor = GridBagConstraints.PAGE_START;

		c.fill = GridBagConstraints.VERTICAL;

		add(sudokuBoard, c);

		//Add all cells to markedCells arrayList
		for (Cell cell : sudokuBoard.getCellsLinear()) {
			markedCells.add(cell);
		}

		// add menubar to frame
		setJMenuBar(sudokuUI.createMenubar());

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;

		add(sudokuUI.createNumpad(), c);

		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0;
		c.fill = GridBagConstraints.HORIZONTAL;

		controls = sudokuUI.createControls();
		add(controls, c);
		pack();
	}

	public void onlySelectThePressed(Cell buttonSelected) {

		// Pressing an already selected button causes it to become unselected.
		if (buttonSelected.isSelected() == false) {
			return;
		}

		sudokuBoard.getCellsLinear().forEach(b -> b.setSelected(false));
		buttonSelected.setSelected(true);

		// BRUGES TIL DEBUG AF SKÆRM OPLØSNING
		// System.out.println(controls.getWidth());
		// System.out.println(sudokuBoard.getWidth() + " " + sudokuBoard.getHeight());
		// System.out.println(sudokuBoard.getMaximumSize());
		// System.out.println(sudokuBoard.getMaximumSize());
		// System.out.println(sudokuBoard.getCellsLinear().get(0).getPreferredSize());
		// System.out.println(getSize());
		// System.out.println(sudokuBoard.getX() + " " + sudokuBoard.getY());
		// System.out.println(this.getSize());
		// System.out.println(sudokuBoard.getSize());
		// System.out.println(sudokuBoard.getCellsLinear().get(0).getPreferredSize());

		// for (Cell cell : sudokuBoard.getCellsLinear()) {
		// cell.setSize(100);
		// }
		// System.out.println(sudokuBoard.getCellsLinear().get(0).getPreferredSize());

		pack();
		//System.out.println(sudokuBoard.getCellsLinear().get(0).getPreferredSize());

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
				Cell button = sudokuBoard.cells.get(x).get(y);
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
					Cell button = sudokuBoard.cells.get(x).get(y);
					button.setText(String.valueOf(sudoku[x][y]));
				} else {
					Cell button = sudokuBoard.cells.get(x).get(y);
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
						sudokuBoard.cells.get(i).get(j).square();
						markedCells.add(sudokuBoard.cells.get(i).get(j));
					}
				}
				// ###PEERS###
				for (int i = 0; i < (n * k); i++) {
					sudokuBoard.cells.get(coordinates[0]).get(i).peer();
					markedCells.add(sudokuBoard.cells.get(coordinates[0]).get(i));
					sudokuBoard.cells.get(i).get(coordinates[1]).peer();
					markedCells.add(sudokuBoard.cells.get(i).get(coordinates[1]));
				}

				// ###SIMILAR NUMBER###
				for (ArrayList<Cell> array : sudokuBoard.cells) {
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

	//Method for debugging checkValidity
	/*public void updateFrameTitle(boolean checkValidity, boolean isFilled) {
		if (checkValidity) {
			if (isFilled) {
				setTitle("Filled and valid");
			} else {
				setTitle("Valid");
			}
		} else {
			if (isFilled) {
				setTitle("Filled and invalid");
			} else {
				setTitle("Invalid");
			}
		}
	}*/

	public Cell getCellFromCoord(int x, int y) {
		return sudokuBoard.cells.get(x).get(y);
	}
}
