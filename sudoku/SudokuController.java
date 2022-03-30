package sudoku;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuController {

	// Creating variables
	SudokuModel model;
	SudokuView view;

	// KEY EVENT FOR ALLE JTOGGLEBUTTONS PÅ BOARDET.
	class KeyboardSudokuListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			JToggleButton pressedSudokuboard = view.getButtonSelected();

			String cellNew = "";
			String cellCurrent = pressedSudokuboard.getText();
			if (!cellCurrent.equals("")) {
				cellNew = cellCurrent;
			}

			int keyCode = e.getKeyCode();
			if (keyCode == KeyEvent.VK_1 || keyCode == KeyEvent.VK_NUMPAD1) {
				cellNew += "1";
			} else if (keyCode == KeyEvent.VK_2 || keyCode == KeyEvent.VK_NUMPAD2) {
				cellNew += "2";
			} else if (keyCode == KeyEvent.VK_3 || keyCode == KeyEvent.VK_NUMPAD3) {
				cellNew += "3";
			} else if (keyCode == KeyEvent.VK_4 || keyCode == KeyEvent.VK_NUMPAD4) {
				cellNew += "4";
			} else if (keyCode == KeyEvent.VK_5 || keyCode == KeyEvent.VK_NUMPAD5) {
				cellNew += "5";
			} else if (keyCode == KeyEvent.VK_6 || keyCode == KeyEvent.VK_NUMPAD6) {
				cellNew += "6";
			} else if (keyCode == KeyEvent.VK_7 || keyCode == KeyEvent.VK_NUMPAD7) {
				cellNew += "7";
			} else if (keyCode == KeyEvent.VK_8 || keyCode == KeyEvent.VK_NUMPAD8) {
				cellNew += "8";
			} else if (keyCode == KeyEvent.VK_9 || keyCode == KeyEvent.VK_NUMPAD9) {
				cellNew += "9";
			} else if (keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_DELETE) {
				if (cellNew.length() > 1) {
					cellNew = cellNew.substring(0, cellNew.length() - 1);
				} else if (cellNew.length() == 1) {
					cellNew = "0";
				}
			} else {
				return;
			}

			// Grab the inital cell number.
			int maxNumber = model.getN() * model.getK(); // TODO: Er dette det maksimale nummer pba. n og k?
			if (Integer.valueOf(cellNew) > maxNumber) {
				cellNew = cellCurrent;
			}

			int[] coordinate = view.getCellCoordinate(pressedSudokuboard);
			model.setSudokuCell(coordinate[0], coordinate[1], Integer.valueOf(cellNew));
			view.updateBoard(model.getSudoku());
		}
	}

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
			String cellNew = "";
			String cellCurrent = pressedSudokuboard.getText();
			if (!cellCurrent.equals("")) {
				cellNew = cellCurrent;
			}
			cellNew = cellNew + pressedNumboard.getText();

			int maxNumber = model.getN() * model.getK(); // TODO: Er dette det maksimale nummer pba. n og
			// k?
			if (Integer.valueOf(cellNew) > maxNumber) {
				cellNew = pressedNumboard.getText();
			}

			// Update sudoku cell
			int[] coordinate = view.getCellCoordinate(pressedSudokuboard);
			model.setSudokuCell(coordinate[0], coordinate[1], Integer.valueOf(cellNew));


			//update sudoku Stack

			model.pushStack(model.getSudoku());

			// Update the board visuals
			view.updateBoard(model.peekStack());


			// TODO:NEDENSTÅENE BRUGES KUN TIL DE-BUG.
			view.updateFrameTitle(model.checkValidity(model.getSudoku()), model.isFilled());


		}

	}

	// Simple constructor
	public SudokuController() {
		model = new SudokuModel();
		view = new SudokuView();
		view.setViewGlobals(model.getN(), model.getK());
		model.pushStack(model.getSudoku());
		view.showFrame(model.peekStack());

		view.addSudokuboardListener(new SudokuboardListener());

		view.addNumboardListener(new NumboardListener());

		view.addSudokuControlsListener(new SudokuUndoListener(), new SudokuRemoveListener(), new SudokuNoteListener(),

				new SudokuNewBoardListener());
		view.addSudokuboardKeyboardBinding(new KeyboardSudokuListener());

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
