package sudoku.Controller;

import sudoku.Controller.Actionlisteners.*;
import sudoku.Controller.Actionlisteners.MenuBar.*;
import sudoku.Controller.MarkCells.*;
import sudoku.Controller.SudokuControls.ClassicSudokuControls;
import sudoku.Controller.SudokuControls.SudokuControlsInterface;
import sudoku.Controller.Zoom.*;
import sudoku.Model.*;
import sudoku.Model.Solver.*;
import sudoku.Model.Validity.*;
import sudoku.View.View;
import sudoku.View.SudokuBoard.*;
import sudoku.View.SudokuBoard.Classic.ClassicSudokuBoard;
import sudoku.View.SudokuBoard.Sandwich.SandwichSudoku;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.io.IOException;

public class Controller {

	// Creating variables
	public Model model;
	public View view;
	public ValidityInterface validity;
	public SolverInterface solver;
	public SudokuBoardAbstract sudokuBoard;
	public SudokuFileLoader fileLoader;
	public SudokuControlsInterface sudokuControls;
	public MarkCellsInterface markCells;
	public Zoom zoom;
	public boolean okPressed = false;
	public boolean hintPressed = false;
	public boolean isSolved = false;
	/*
     * Author: Rasmus. Edited by Frederik
     * Function: Updates board colors
     * Inputs: None
     * Outputs: None
     */
	public void updateColours() {
		markCells.clearMarkedCells();
		try {
			markCells.markCells(sudokuControls.getCellSelected());
		} catch (Exception e) {
		}
	}
	/*
	 * Author: Rasmus
	 * Function: Gets last element in redo stack, and updates board
	 * accordingly
	 * Inputs: None
     * Outputs: None
	 */
	public void redoMove() {
		if (model.stack.getRedoStackSize() > 0) {
			try {
				sudokuControls.getCellSelected().setSelected(false);
			} catch (Exception exc) {
			}
			sudokuControls
					.getCellFromCoord(model.stack.getRedoStackCoords()[0], model.stack.getRedoStackCoords()[1])
					.setSelected(true);
			model.stack.pushStack(model.stack.popRedoStack()); // Removes the last element of the stack
			sudokuControls.updateCellValues(model.getSudoku()); // Updates the visuals
			updateColours();
		}
	}
	/*
	 * Author: Rasmus
	 * Function: Gets last element in undo stack, and updates board
	 * accordingly
	 * Inputs: None
     * Outputs: None
	 */
	public void undoMove() {
		if (model.stack.getUndoStackSize() > 0) {
			try {
				sudokuControls.getCellSelected().setSelected(false);
			} catch (Exception exc) {
			}
			sudokuControls.getCellFromCoord(model.stack.getUndoStackCoords()[0], model.stack.getUndoStackCoords()[1])
					.setSelected(true);
			model.stack.pushRedoStack(model.stack.popStack()); // Removes the last element of the stack
			sudokuControls.updateCellValues(model.getSudoku()); // Updates the visuals
			updateColours();
		}
	}
	/*
	 * Author: All
	 * Function: Creates controller object. This were everything happens from
	 * accordingly
	 * Inputs: None
     * Outputs: None
	 */
	public Controller() {
		model = new Model();//constructs model 
		ArrayList<Object> gameInfo = new ArrayList<>();//Creates gameboard, if possible, from file
		try {
			gameInfo = fileLoader.LoadSudokuBoardDoc();
		} catch (IOException e) {
			new PopUpWrongFile("wrong filetype", this);
		} catch (NumberFormatException ez) {
			new PopUpWrongFile("wrong filetype", this);
		} catch (NoSuchElementException ex) {
			new PopUpWrongFile("Illegal file content. Check for newlines",
					this);
		} finally {
			// Import the data from the fileloader into model.
			switch (String.valueOf(gameInfo.get(0)).toLowerCase()) {
				case "classic": //checks if classic mode
					int[][] sudoku = (int[][]) gameInfo.get(1);
					int n = (int) gameInfo.get(2);
					int k = (int) gameInfo.get(3);
					validity = new ValidityClassic(sudoku, n, k); //starts validity
					solver = new CrooksAlgorithm(n, k, sudoku, model); //starts solver

					model = new Model(sudoku, n, k, validity, solver); //starts model from values

					view = new View(model.getN(), model.getK(), //starts view from values
							new ClassicSudokuBoard(model.getSudoku(), model.getN(), model.getK()));
					break;

				case "sandwich": //checks if sandwich mode
					sudoku = (int[][]) gameInfo.get(1);
					n = (int) gameInfo.get(2);
					k = (int) gameInfo.get(3);
					int[] xSums = (int[]) gameInfo.get(4);
					int[] ySums = (int[]) gameInfo.get(5);
					validity = new ValiditySandwich(sudoku, n, k, xSums, ySums); //starts validity
					solver = new BacktrackAlgorithm(n, k, xSums, ySums, sudoku, model); //starts solver
					model = new Model(sudoku, n, k, xSums, ySums, validity, solver); //starts model

					view = new View(model.getN(), model.getK(),
							new SandwichSudoku(model.getSudoku(), model.getN(), model.getK(), model.xSums,
									model.ySums)); //starts view
					break;
				default:
					break;
			}
		}

		sudokuControls = new ClassicSudokuControls(view.sudokuBoard.getCells()); //creates controls for sudoku
		markCells = new ClassicMarkCells(model.getSudoku(), model.getN(), model.getK(), sudokuControls, validity); //creates cellmarking
		ZoomObjectInterface[][] objectList = { sudokuControls.getCells1d(),
				view.sudokuBoard.numpad.getNumpadButtons() };
		zoom = new Zoom(objectList, view); //adds zoom
		// Assign actionlisteners

		for (Cell cell : sudokuControls.getCells1d()) { //creates all the action listeners
			cell.addActionListener(new SudokuboardListener(this));
			cell.addKeyListener(new KeyboardNumberListener(this));
			cell.addKeyListener(new KeyboardShortcutListener(this));
		}
		for (NumpadButton numpadButton : view.sudokuBoard.numpad.getNumpadButtons()) {
			numpadButton.addActionListener(new NumboardListener(this));
		}

		view.menuBar.zoomIn.addActionListener(new MenuBarZoomListener(this));
		view.menuBar.zoomOut.addActionListener(new MenuBarZoomListener(this));
		view.menuBar.undo.addActionListener(new SudokuUndoListener(this));
		view.menuBar.remove.addActionListener(new SudokuRemoveListener(this));
		view.menuBar.redo.addActionListener(new SudokuRedoListener(this));
		view.menuBar.solve.addActionListener(new MenuBarSolveListener(this));
		view.menuBar.hint.addActionListener(new SudokuHintListener(this));
		view.menuBar.newPuzzle.addActionListener(new MenuBarNewSudokuListener(this));

		try {
			if (model.solver.getSolvedSudoku()[0][0] == 0) { //checks if sudoku has no solution
				new PopUp("This sudoku has no solutions \n");
			}
		} catch (Exception e) {
		}

		whileLoop(); //creates while loop where sudoku is stuck
	}
	/*
	 * Author: Rasmus
	 * Function: Runs solver, solves cell for user, if possible
	 * Inputs: None
     * Outputs: None
	 */
	public void getHint() {
		try {
			model.runSolver();
			int[] coordinate = sudokuControls.getCellCoordinate(sudokuControls.getCellSelected());
			int tempVal = model.getSudoku()[coordinate[0]][coordinate[1]];
			int hintValue = model.solver.getSolvedSudoku()[coordinate[0]][coordinate[1]];
			if (model.solver.isSolved() && model.solver.getUniqueness()) {
				model.setSudokuCell(coordinate[0], coordinate[1], hintValue);
			}
			model.stack.pushStack(model.stack.createStackObj(coordinate[0], coordinate[1], tempVal,
					hintValue));
			sudokuControls.updateCellValues(model.getSudoku());
			updateColours();
		} catch (Exception e) {
			new PopUp(e.getMessage());
		}
	}
	/*
	 * Author: All
	 * Function: Runs solver
	 * Inputs: None
     * Outputs: None
	 */
	public void solveSudoku() {
		try {
			model.runSolver();
			if (model.solver.getUniqueness()) {
				for (int i = 0; i < model.getN()
						* model.getK(); i++) {
					for (int j = 0; j < model.getN()
							* model.getK(); j++) {
						if (sudokuControls.getCellFromCoord(i, j).getEnabled()) {
							model.setSudokuCell(i, j,
									model.solver.getSolvedSudoku()[i][j]);
						}
					}
				}
				sudokuControls
						.updateCellValues(model.getSudoku());
				updateColours();
			} else {
				model.solver.solve();
				if (model.solver.getSolvedSudoku()[0][0] != 0) {
					for (int i = 0; i < model.getN()
							* model.getK(); i++) {
						for (int j = 0; j < model.getN()
								* model.getK(); j++) {
							if (sudokuControls.getCellFromCoord(i,
									j).getEnabled()) {
								model.setSudokuCell(i, j,
										model.solver
												.getSolvedSudoku()[i][j]);
							}
						}
					}
					sudokuControls
							.updateCellValues(model.getSudoku());
					updateColours();
				}
			}
		} catch (Exception exc) {
			new PopUp(exc.getMessage());
		}
	}
	/*
	 * Author: Christian, Edits by all
	 * Function: This is where the code ends up, when it is not in a listener. 
	 * Inputs: None
     * Outputs: None
	 */
	private void whileLoop() {
		while (true) {
			okPressed = false;
			hintPressed = false;
			while (true) {
				if (model.validity.checkValidity(model.getSudoku()) && model.isFilled() && !isSolved) {
					isSolved = true;
					new PopUpSudokuSolved("Congratulations, you solved the puzzle!", this);
				}
				try {
					Thread.sleep(20);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (okPressed || hintPressed) {
					break;
				}
			}
			if (okPressed) {
				new Controller();
			} else if (hintPressed) {
				getHint();
			}
		}
	}

}