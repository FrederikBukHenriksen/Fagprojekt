// package sudoku;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuController {

	// Creating variables
	SudokuModel model;

	SudokuView view;

	// ACTIONLISTENER FOR SUDOKUBOARDET.
	class SodukoboardListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JToggleButton pressed = (JToggleButton) e.getSource(); // Grabs the button pressed
			view.boardButtonSelected(pressed);
		}
	}

	class NumboardListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Grabs the button pressed
			JButton pressedNumboard = (JButton) e.getSource();

			// Find the placement of the pressed board button
			JToggleButton pressedSudokuboard = view.isSelected();
			int[] coordinate = view.getCellCoordinate(pressedSudokuboard);

			// Update sudoku board
			model.setSudokuCell(coordinate[0], coordinate[1], Integer.valueOf(pressedNumboard.getText()));

			// Update the board visuals
			view.updateBoard(model.getSudoku());

			// NEDENSTÅENE BRUGES KUN TIL DE-BUG.
			if (checkValidity(model.getSudoku())) {
				if (model.isFilled()) {
					view.setTitle("Filled and valid");
				} else {
					view.setTitle("Valid");
				}
			} else {
				if (model.isFilled()) {
					view.setTitle("Filled and invalid");
				} else {
					view.setTitle("Invalid");
				}

			}

		}

	}

	// Simple constructor
	public SudokuController() {
		model = new SudokuModel();
		view = new SudokuView();

		view.setBoard(model.getSudoku());
		view.showFrame();

		view.addSudokuboardListener(new SodukoboardListener());
		view.addNumboardListener(new NumboardListener());
	}

	public static boolean checkValidity(int[][] sudoku) {
		boolean valid = new Boolean(true);
		// Grid for storing already found values
		// int[][] sortedGrid = new int[sudoku.length+1][sudoku.length+1];
		int[][] sortedGrid = new int[sudoku.length][sudoku.length];
		// for(int i = sortedGrid.length-1; i >= 0; i--){
		for (int i = sortedGrid.length - 1; i >= 0; i--) {
			for (int k = 0; k < sortedGrid.length; k++) {
				sortedGrid[i][k] = 0;
			}
		}

		// Checking rows for duplicates
		for (int i = 0; i < sudoku.length; i++) {
			for (int k = 0; k < sudoku.length; k++) {
				int cur = (sudoku[i][k]);
				if (cur != 0) {
					if (sortedGrid[i][cur - 1] == 0) {
						sortedGrid[i][cur - 1] = 1;
					} else {
						valid = false;
					}
				}

			}
		}

		/*
		 * for(int i = 0; i < sortedGrid.length; i++){
		 * for(int k = 0; k < sortedGrid.length; k++){
		 * System.out.print(sortedGrid[i][k] + " ");
		 * }
		 * System.out.println();
		 * }
		 * System.out.println();
		 */

		// Resetting the sorted grid
		for (int i = sortedGrid.length - 1; i >= 0; i--) {
			for (int k = 0; k < sortedGrid.length; k++) {
				sortedGrid[i][k] = 0;
			}
		}

		// Checking columns for duplicates
		for (int i = 0; i < sudoku.length; i++) {
			for (int k = 0; k < sudoku.length; k++) {
				int cur = (sudoku[k][i]);
				if (cur != 0) {
					if (sortedGrid[i][cur - 1] == 0) {
						sortedGrid[i][cur - 1] = 1;
					} else {
						valid = false;
					}
				}
			}
		}

		// Resetting the sorted grid
		for (int i = sortedGrid.length - 1; i >= 0; i--) {
			for (int k = 0; k < sortedGrid.length; k++) {
				sortedGrid[i][k] = 0;
			}
		}

		for (int r = 0; r < Math.sqrt(sudoku.length); r++) {
			for (int c = 0; c < Math.sqrt(sudoku.length); c++) {
				for (int br = 0; br < Math.sqrt(sudoku.length); br++) {
					for (int bc = 0; bc < Math.sqrt(sudoku.length); bc++) {
						int cur = sudoku[(c * 3) + bc][(r * 3) + br];
						if (cur != 0) {
							if (sortedGrid[c + r * 3][cur - 1] == 0) {
								sortedGrid[c + r * 3][cur - 1] = 1;
							} else {
								valid = false;
							}
						}
					}
				}
			}
		}
		/*
		 * for(int i = 0; i < sortedGrid.length; i++){
		 * for(int k = 0; k < sortedGrid.length; k++){
		 * System.out.print(sortedGrid[i][k] + " ");
		 * }
		 * System.out.println();
		 * }
		 */

		return valid;
	}

}
