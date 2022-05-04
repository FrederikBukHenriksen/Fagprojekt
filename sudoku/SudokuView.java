package sudoku;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.LineBorder;

import sudoku.SudokuBoard.Cell;
import sudoku.SudokuController.KeyboardSudokuListener;

import java.awt.*;

public class SudokuView extends JFrame {

	public int n;
	public int k;
	SudokuBoard sudokuBoard;
	ArrayList<JButton> numboardButtons = new ArrayList();
	JButton undo = new JButton("Undo");
	JButton remove = new JButton("Remove");
	JButton note = new JButton("note");
	JButton newSudoku = new JButton("newSudoku");
	

	public SudokuView() {
		setVisible(true);
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}

	public void showFrame(int[][] sudoku) {
		n = SudokuModel.n;
		k = SudokuModel.k;
		sudokuBoard = new SudokuBoard(sudoku);

		JPanel sideButtonGui = new JPanel(new GridLayout(2, 1, 0, 10));// creates buttons panels on the right side
		sideButtonGui.setBounds(500, 0, 500, 500);

		JPanel specialButton = new JPanel(new GridLayout(1, 4, 0, 0));

		specialButton.add(undo);
		specialButton.add(remove);
		specialButton.add(note);
		specialButton.add(newSudoku);

		sideButtonGui.add(specialButton);

		JPanel buttonGui = new JPanel(new GridLayout(3, 3, 20, 20));// creates a 3/3 with numbers from 1-9
		for (int j = 0; j < 9; j++) {
			numboardButtons.add(new JButton(j + 1 + ""));// adds number as label to button
			numboardButtons.get(j).setActionCommand(j + 1 + "");
			numboardButtons.get(j).setFont(new Font("Serif", Font.PLAIN, 72));
			buttonGui.add(numboardButtons.get(j));
		}

		sideButtonGui.add(buttonGui);

		add(sudokuBoard);
		add(sideButtonGui);
		// setting grid layout of 3 rows and 3 columns
		setLayout(null);
		setSize(1280, 720);
	}

	// Actionlistener

	void addNumboardListener(ActionListener listenForNumboardButtons) {
		numboardButtons.forEach(b -> b.addActionListener(listenForNumboardButtons));
	}

	void addSudokuControlsListener(ActionListener listenForUndo, ActionListener listenForRemove,
			ActionListener listenForNote, ActionListener listenForNew) {
		undo.addActionListener(listenForUndo);
		remove.addActionListener(listenForRemove);
		note.addActionListener(listenForNote);
		newSudoku.addActionListener(listenForNew);
	}

	void addSudokuboardListener(ActionListener listenForSudokuboardButtons) {
		for (Cell cell : sudokuBoard.getCells()) {
			cell.addActionListener(listenForSudokuboardButtons);

		}
	}

	public void addSudokuboardKeyboardBinding(KeyboardSudokuListener keysListenerLolcat) {
		for (Cell cell : sudokuBoard.getCells()) {
			cell.addKeyListener(keysListenerLolcat);
		}
	}

	public void onlySelectThePressed(Cell buttonSelected) {

		// Pressing an already selected button causes it to become unselected.
		if (buttonSelected.isSelected() == false) {
			return;
		}

		sudokuBoard.getCells().forEach(b -> b.setSelected(false));
		buttonSelected.setSelected(true);

	}

	public Cell getButtonSelected() throws Exception {
		// ArrayList<Cell> result = (ArrayList<Cell>) sudokuBoard.getCells().stream()
		// .filter(b -> b.isSelected())
		// .collect(Collectors.toList());
		Cell selected = null;
		for (Cell cell : sudokuBoard.getCells()) {
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
					}
				}
				// ###PEERS###
				for (int i = 0; i < (n * k); i++) {
					sudokuBoard.cells.get(coordinates[0]).get(i).peer();
					sudokuBoard.cells.get(i).get(coordinates[1]).peer();
				}

				// ###SIMILAR NUMBER###
				for (ArrayList<Cell> array : sudokuBoard.cells) {
					for (Cell button : array) {
						if (!cellText.equals("") && button.getText().equals(cellText)) {
							button.similar();
						}
					}
				}
			}
		} catch (Exception exc) {
			System.out.println(exc.getMessage());
		}

	}

	public void clearMarkedCells() {
		for (Cell cell : sudokuBoard.getCells()) {
			cell.defaultColor();

		}
	}	

	public void updateFrameTitle(boolean checkValidity, boolean isFilled) {
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
	}

	public Cell getCellFromCoord(int x, int y){
		return sudokuBoard.cells.get(x).get(y);
	}
}
