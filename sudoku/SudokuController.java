package sudoku;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JToggleButton;

import sudoku.SudokuBoard.Cell;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuController {

	// Creating variables
	SudokuModel model;
	SudokuView view;

	// KEY EVENT FOR ALLE JTOGGLEBUTTONS PÅ BOARDET.
	class KeyboardSudokuListener extends KeyAdapter {
		boolean ctrlPressed = false;
		boolean zPressed = false;
		boolean yPressed = false;
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			if( keyCode == KeyEvent.VK_Z){
				zPressed = true;
				yPressed = false;
				if(ctrlPressed){
					undoMove();
				}
			} else if( keyCode == KeyEvent.VK_Y){
				yPressed = true;
				zPressed = false;
				if(ctrlPressed){
					redoMove();
				}
			} else if( keyCode == KeyEvent.VK_CONTROL){
				ctrlPressed = true;
				if(zPressed){
					undoMove();
				}
				else if(yPressed){
					redoMove();
				}
			} else if( keyCode == KeyEvent.VK_DOWN){
				int[] tempCoords = {-1,0};
				try {
					tempCoords = view.getCellCoordinate(view.getButtonSelected());
				}
				catch(Exception h){
				}
				Cell pressed = null;
				if(tempCoords[0] != (model.getN() * model.getK()) - 1 ){
					pressed = view.getCellFromCoord(tempCoords[0] + 1,tempCoords[1]); // Grabs the button pressed
				}
				else{
					pressed = view.getCellFromCoord(0,tempCoords[1]);
				}
				pressed.setSelected(true);
				view.onlySelectThePressed(pressed);
				updateColours();
			} else if(keyCode == KeyEvent.VK_UP){
				int[] tempCoords = {1,0};
				try {
					tempCoords = view.getCellCoordinate(view.getButtonSelected());
				}
				catch(Exception h){
				}
				Cell pressed = null;
				if(tempCoords[0] != 0){
					pressed = view.getCellFromCoord(tempCoords[0] - 1,tempCoords[1]); // Grabs the button pressed
				}
				else{
					pressed = view.getCellFromCoord(model.getN() * model.getK() - 1,tempCoords[1]);
				}
				pressed.setSelected(true);
				view.onlySelectThePressed(pressed);
				updateColours();
			} else if(keyCode == KeyEvent.VK_LEFT){
				int[] tempCoords = {0,1};
				try {
					tempCoords = view.getCellCoordinate(view.getButtonSelected());
				}
				catch(Exception h){
				}
				Cell pressed = null;
				if(tempCoords[1] != 0 ){
					pressed = view.getCellFromCoord(tempCoords[0],tempCoords[1] - 1); // Grabs the button pressed
				}
				else{
					pressed = view.getCellFromCoord(tempCoords[0],model.getN() * model.getK() - 1);
				}
				pressed.setSelected(true);
				view.onlySelectThePressed(pressed);
				updateColours();
			} else if(keyCode == KeyEvent.VK_RIGHT){
				int[] tempCoords = {0,-1};
				try {
					tempCoords = view.getCellCoordinate(view.getButtonSelected());
				}
				catch(Exception h){
				}
				Cell pressed = null;
				if(tempCoords[1] != model.getN() * model.getK() - 1 ){
					pressed = view.getCellFromCoord(tempCoords[0],tempCoords[1] + 1); // Grabs the button pressed
				}
				else{
					pressed = view.getCellFromCoord(tempCoords[0], 0);
				}
				pressed.setSelected(true);
				view.onlySelectThePressed(pressed);
				updateColours();
			} 
			else{
			try {
				Cell pressedSudokuboard = view.getButtonSelected();
				if (pressedSudokuboard.enabled) { // Only the available buttons

					// Variables for the new cell-content and the button pressed
					String cellNew = "";
					String keyPressed = "";
					String cellCurrent = pressedSudokuboard.getText();
					// If the cell isn't empty, we attempt to concatinate the new entry on the old
					// one
					if (!cellCurrent.equals("")) {
						cellNew = cellCurrent;
					}

					// Gets the digit entered
					if (keyCode == KeyEvent.VK_1 || keyCode == KeyEvent.VK_NUMPAD1) {
						keyPressed = "1";
					} else if (keyCode == KeyEvent.VK_2 || keyCode == KeyEvent.VK_NUMPAD2) {
						keyPressed = "2";
					} else if (keyCode == KeyEvent.VK_3 || keyCode == KeyEvent.VK_NUMPAD3) {
						keyPressed = "3";
					} else if (keyCode == KeyEvent.VK_4 || keyCode == KeyEvent.VK_NUMPAD4) {
						keyPressed = "4";
					} else if (keyCode == KeyEvent.VK_5 || keyCode == KeyEvent.VK_NUMPAD5) {
						keyPressed = "5";
					} else if (keyCode == KeyEvent.VK_6 || keyCode == KeyEvent.VK_NUMPAD6) {
						keyPressed = "6";
					} else if (keyCode == KeyEvent.VK_7 || keyCode == KeyEvent.VK_NUMPAD7) {
						keyPressed = "7";
					} else if (keyCode == KeyEvent.VK_8 || keyCode == KeyEvent.VK_NUMPAD8) {
						keyPressed = "8";
					} else if (keyCode == KeyEvent.VK_9 || keyCode == KeyEvent.VK_NUMPAD9) {
						keyPressed = "9";
					} else if (keyCode == KeyEvent.VK_0 || keyCode == KeyEvent.VK_NUMPAD0) {
						keyPressed = "0";
						// Backspace deletes 1 digit of the number in the cell
					} else if (keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_DELETE) {
						if (cellNew.length() > 1) {
							cellNew = cellNew.substring(0, cellNew.length() - 1);
						} else if (cellNew.length() == 1) {
							cellNew = "0";
						}
					} 
				    else {
						return;
					}

					// Check if the concatinated number is larger than allowed, if so, just enter
					// the new number
					int maxNumber = model.getN() * model.getK(); // TODO: Er dette det maksimale nummer pba. n og k?
					if (!(cellNew + keyPressed).equals("")) {
						if (Integer.valueOf(cellNew + keyPressed) > maxNumber) {
							cellNew = keyPressed;
						} else {
							cellNew = cellNew + keyPressed;
						}
					}

					if (!cellNew.equals("")) {
						model.clearRedoStack();
						// Update board both in data and visually
						int[] coordinate = view.getCellCoordinate(pressedSudokuboard);
						int tempVal = model.getSudoku()[coordinate[0]][coordinate[1]];
						model.setSudokuCell(coordinate[0], coordinate[1], Integer.valueOf(cellNew));
						model.pushStack2(
								model.createStackObj(coordinate[0], coordinate[1], tempVal, Integer.valueOf(cellNew)));
						view.updateBoard(model.getSudoku());
						updateColours();
					}
				}
			} catch (Exception exc) {
				System.out.println(exc.getMessage());
			}
		}
		}
		public void keyReleased(KeyEvent e){
			try{
				// Gets the digit entered
				int keyCode = e.getKeyCode();
				if( keyCode == KeyEvent.VK_Z){
					zPressed = false;
				} else if( keyCode == KeyEvent.VK_CONTROL){
					ctrlPressed = false;
				} else if( keyCode == KeyEvent.VK_Y){
					yPressed = false;
				} else {
					return;
				}

			}
			catch (Exception exc){
				//System.out.println(exc.getMessage());
			}
		}
	}

	// ACTIONLISTENER FOR SUDOKUBOARDET.
	class SudokuboardListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Cell pressed = (Cell) e.getSource(); // Grabs the button pressed
			view.onlySelectThePressed(pressed);
			updateColours();
		}
	}

	// Code for undo-button
	class SudokuUndoListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			//undoMove();

			//Below is code for the hint-button, to be moved when this button is added
			try {
				if (view.getButtonSelected().enabled) {
					int[] coordinate = view.getCellCoordinate(view.getButtonSelected());
					int tempVal = model.getSudoku()[coordinate[0]][coordinate[1]];
					if(model.getUniqueness()){
						model.setSudokuCell(coordinate[0], coordinate[1], model.getSolvedSudoku()[coordinate[0]][coordinate[1]]);
					}
					else{
						model.solver();
						model.setSudokuCell(coordinate[0], coordinate[1], model.getSolvedSudoku()[coordinate[0]][coordinate[1]]);
					}
					model.pushStack2(model.createStackObj(coordinate[0], coordinate[1], tempVal, model.getSolvedSudoku()[coordinate[0]][coordinate[1]]));
					view.updateBoard(model.getSudoku());
					updateColours();
				}
			} catch (Exception exc) {
				// System.out.println(exc.getMessage());
			}
		}
	}

	class SudokuRemoveListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// System.out.println("Remove"); //Prints "Remove" for DEBUG
			try {
				if (view.getButtonSelected().enabled) {
					int[] coordinate = view.getCellCoordinate(view.getButtonSelected());
					if (!(model.sudoku[coordinate[0]][coordinate[1]] == 0)) {
						model.clearRedoStack();
						int tempVal = model.getSudoku()[coordinate[0]][coordinate[1]];
						model.setSudokuCell(coordinate[0], coordinate[1], 0);
						model.pushStack2(model.createStackObj(coordinate[0], coordinate[1], tempVal, 0));
						view.updateBoard(model.getSudoku());
						//view.updateFrameTitle(model.checkValidity(model.getSudoku(), false), model.isFilled());
						updateColours();
					}
				}
			} catch (Exception exc) {
				// System.out.println(exc.getMessage());
			}
			
		}
	}

	class SudokuNewBoardListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// System.out.println("New Sudoku"); //Prints "New Sudoku" for DEBUG
		}
	}

	class NumboardListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Grabs the button pressed
			JButton pressedNumboard = (JButton) e.getSource();
			// Find the placement of the pressed board button
			try {
				Cell pressedSudokuboard = view.getButtonSelected();
				if (pressedSudokuboard.enabled) {
					model.clearRedoStack();
					String cellNew = "";
					String cellCurrent = pressedSudokuboard.getText();
					if (!cellCurrent.equals("")) { // Hvis der står noget i cellen
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
					int tempVal = model.getSudoku()[coordinate[0]][coordinate[1]];
					model.setSudokuCell(coordinate[0], coordinate[1], Integer.valueOf(cellNew));

					// update sudoku Stack
					model.pushStack2(model.createStackObj(coordinate[0], coordinate[1], tempVal, Integer.valueOf(cellNew)));

					// Update the board visuals
					view.updateBoard(model.getSudoku());

					// TODO:NEDENSTÅENE BRUGES KUN TIL DE-BUG.
					//view.updateFrameTitle(model.checkValidity(model.getSudoku(), false), model.isFilled());

					pressedSudokuboard.requestFocus();
					updateColours();
				}
			} catch (Exception exc) {
				// System.out.println(exc.getMessage());
			}
			
		}
	}

	// Code for redo-button
	class SudokuRedoListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			redoMove();
		}
	}

	public void updateColours() {
		view.clearMarkedCells();
		view.markCells();
		if(model.checkValidity(model.getSudoku(), false) && model.isFilled()){
			view.createPopUp();
		}
	}

	public void redoMove(){
		if (model.redoes > 0) {
			// System.out.println("Redo"); // Prints "Redo" FOR DEBUG
			try {
				view.getButtonSelected().setSelected(false);
				// TODO: indsæt Rasmus' generelle funktion for farver
			} catch (Exception exc) {
				// System.out.println(exc.getMessage());
			}
			model.pushStack2(model.popRedoStack()); // Removes the last element of the stack
			// model.setSudoku(model.getSudoku()); // Updates the board
			view.updateBoard(model.getSudoku()); // Updates the visuals
			//view.updateFrameTitle(model.checkValidity(model.getSudoku(), false), model.isFilled());
			updateColours();
		}
	}

	public void undoMove(){
		if (model.moves > 0) {
			// System.out.println("Undo"); // Prints "Undo" FOR DEBUG
			try {
				view.getButtonSelected().setSelected(false);
				// TODO: indsæt Rasmus' generelle funktion for farver
			} catch (Exception exc) {
				// System.out.println(exc.getMessage());
			}
			model.pushRedoStack(model.popStack2()); // Removes the last element of the stack
			// model.setSudoku(model.getSudoku()); // Updates the board
			view.updateBoard(model.getSudoku()); // Updates the visuals
			//view.updateFrameTitle(model.checkValidity(model.getSudoku(), false), model.isFilled());
			updateColours();
		}
		System.out.println("UNDO"); //For debug
	}

	// Simple constructor
	public SudokuController() {
		view = new SudokuView();
		model = new SudokuModel(view);
		view.showFrame(model.getSudoku());
		model.solver();
		// model.createPreemtiveSets();

		for (Cell cell : view.sudokuBoard.getCells()) {
			cell.addActionListener(new SudokuboardListener());
			cell.addKeyListener(new KeyboardSudokuListener());
		}
		view.sudokuUI.numpadButtons.forEach(b -> b.addActionListener(new NumboardListener()));
		view.sudokuUI.undo.addActionListener(new SudokuUndoListener());
		view.sudokuUI.redo.addActionListener(new SudokuRedoListener());
		view.sudokuUI.remove.addActionListener(new SudokuRemoveListener());

		updateColours();
	}

}
