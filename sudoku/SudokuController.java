package sudoku;

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
	class SudokuboardListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JToggleButton pressed = (JToggleButton) e.getSource(); // Grabs the button pressed
			view.getSelected(pressed);
		}
	}

	class SudokuUndoListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton pressed = (JButton) e.getSource(); // Grabs the button pressed
			System.out.println("Undo");
			//System.out.println("Stack: " + model.getStackSize());
			model.popStack();
			//System.out.println("Stack: " + model.getStackSize());
			model.setSudoku(model.peekStack());
			view.updateBoard(model.peekStack());
			System.out.println(model.sudokuStack);
		}
	}

	class SudokuRemoveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton pressed = (JButton) e.getSource(); // Grabs the button pressed
			System.out.println("Remove");
		}
	}

	class SudokuNoteListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton pressed = (JButton) e.getSource(); // Grabs the button pressed
			System.out.println("Note");

		}
	}

	class SudokuNewListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton pressed = (JButton) e.getSource(); // Grabs the button pressed
			System.out.println("New Sudoku");

		}
	}

	class NumboardListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Grabs the button pressed
			JButton pressedNumboard = (JButton) e.getSource();

			// Find the placement of the pressed board button
			JToggleButton pressedSudokuboard = view.getButtonSelected();
			int[] coordinate = view.getCellCoordinate(pressedSudokuboard);

			// Update sudoku board
			model.setSudokuCell(coordinate[0], coordinate[1], Integer.valueOf(pressedNumboard.getText()));

			//update sudoku Stack
			model.pushStack(model.getSudoku());

			// Update the board visuals
			view.updateBoard(model.peekStack());

			// NEDENSTÅENE BRUGES KUN TIL DE-BUG.
			view.updateFrameTitle(checkValidity(model.getSudoku(), model.getN(), model.getK()), model.isFilled());

		}

	}

	// Simple constructor
	public SudokuController() {
		model = new SudokuModel();
		view = new SudokuView();
		view.getBoardValues(model.getN(), model.getK());
		model.pushStack(model.getSudoku());
		view.showFrame(model.peekStack());

		view.addSudokuboardListener(new SudokuboardListener());
		view.addNumboardListener(new NumboardListener());

		view.addSudokuControlsListener(new SudokuUndoListener(), new SudokuRemoveListener(), new SudokuNoteListener(),
				new SudokuNewListener());
	}


	public static boolean checkValidity(int[][] sudoku, int n, int k) {
		boolean valid = new Boolean(true);
		// Grid for storing already found values
		// int[][] sortedGrid = new int[sudoku.length+1][sudoku.length+1];
		int[][] sortedGrid = new int[sudoku.length][sudoku.length];
		// for(int i = sortedGrid.length-1; i >= 0; i--){
		for (int i = sortedGrid.length - 1; i >= 0; i--) {
			for (int j = 0; j < sortedGrid.length; j++) {
				sortedGrid[i][j] = 0;
			}
		}

		// Checking rows for duplicates
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku.length; j++) {
				int cur = (sudoku[i][j]);
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
			for (int j = 0; j < sortedGrid.length; j++) {
				sortedGrid[i][j] = 0;
			}
		}

		// Checking columns for duplicates
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku.length; j++) {
				int cur = (sudoku[j][i]);
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
			for (int j = 0; j < sortedGrid.length; j++) {
				sortedGrid[i][j] = 0;
			}
		}

		//Checking each square
		/*
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
		*/

		for (int l = 0; l < k*k; l++) {

			for (int i = 0; i < n; i++) {

				for (int j = 0; j < n; j++) {//l/k benytter sig af hvordan java runder op. det er n hvor mange felter den skal rygge, og den skal rygge det hver gang l har bev�get sig k felter.
					int cur = sudoku[(i + n * (l /k))][(j + n* l) % (k*n)];
					if (cur != 0) { 
						if (sortedGrid[l][cur - 1] == 0){
							sortedGrid[l][cur - 1] = 1;
						}
						else{
							valid = false;
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

	public void printSudoku(int[][] sudokuBoard){
		for(int i = 0; i < sudokuBoard.length; i++){
			for(int k = 0; k < sudokuBoard.length; k++){
				System.out.print(sudokuBoard[i][k] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

}
