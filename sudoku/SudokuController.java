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

	//Code for undo-button
	class SudokuUndoListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton pressed = (JButton) e.getSource(); // Grabs the button pressed
			System.out.println("Undo"); //Prints "Undo" FOR DEBUG
			model.popStack(); //Removes the last element of the stack
			model.setSudoku(model.peekStack()); //Updates the board 
			view.updateBoard(model.peekStack()); //Updates the visuals
			/*int[][] temp = new int[model.getSudoku().length][model.getSudoku().length];
			for(int i = 0; i < model.moves; i++){
				for(int j = 0; j < model.getSudoku().length; j++){
					for(int z = 0; z < model.getSudoku().length; z++){
						temp[j][z] = model.sudokuStack[i][j][z];
					}
				}
				printSudoku(temp);
			}*/
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

			// update sudoku Stack
			model.pushStack(model.getSudoku());

			// Update the board visuals
			view.updateBoard(model.peekStack());

			// NEDENSTÅENE BRUGES KUN TIL DE-BUG.
			view.updateFrameTitle(model.checkValidity(model.getSudoku(), model.getN(), model.getK()), model.isFilled());

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
				new SudokuNewBoardListener());

		model.markUpCells();

	}
	//Method for printing the sudoku-board
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
