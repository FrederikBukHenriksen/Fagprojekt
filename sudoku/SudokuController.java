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
			view.onlySelectThePressed(pressed);
		}
	}

	class SudokuUndoListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton pressed = (JButton) e.getSource(); // Grabs the button pressed
			System.out.println("Undo");
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

	class SudokuNewBoardListener implements ActionListener {
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
			// Update the board visuals
			view.updateBoard(model.getSudoku()); // TODO: Ændr til peekStack()

			// NEDENSTÅENE BRUGES KUN TIL DE-BUG.
			view.updateFrameTitle(model.checkValidity(model.getSudoku(), model.getN(), model.getK()), model.isFilled());

		}

	}

	// Simple constructor
	public SudokuController() {
		model = new SudokuModel();
		view = new SudokuView();
		view.getBoardValues(model.getN(), model.getK());
		view.showFrame(model.getSudoku());

		view.addSudokuboardListener(new SudokuboardListener());
		view.addNumboardListener(new NumboardListener());

		view.addSudokuControlsListener(new SudokuUndoListener(), new SudokuRemoveListener(), new SudokuNoteListener(),
				new SudokuNewBoardListener());

		model.markUpCells();

	}

}
