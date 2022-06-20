package sudoku.Controller;

import sudoku.Controller.Actionlisteners.*;
import sudoku.Controller.Actionlisteners.MenuBar.*;
import sudoku.Controller.MarkCells.*;
import sudoku.Controller.Zoom.*;
import sudoku.Model.*;
import sudoku.Model.Stack;
import sudoku.Model.Solver.*;
import sudoku.Model.Validity.*;
import sudoku.View.View;
import sudoku.View.SudokuBoard.*;
import sudoku.View.SudokuBoard.Classic.ClassicSudokuBoard;
import sudoku.View.SudokuBoard.Sandwich.SandwichSudoku;

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
	public ClassicSudokuControls sudokuControls;
	public MarkCellsInterface markCells;
	public Zoom zoom;
	public boolean okPressed = false;
	public boolean hintPressed = false;
	public boolean isSolved = false;

	public void updateColours() {
		markCells.clearMarkedCells();
		try {
			markCells.markCells(sudokuControls.getButtonSelected());
		} catch (Exception e) {
		}
	}

	public void redoMove() {
		if (model.stack.getRedoStackSize() > 0) {
			try {
				sudokuControls.getButtonSelected().setSelected(false);
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

	public void undoMove() {
		if (model.stack.getUndoStackSize() > 0) {
			try {
				sudokuControls.getButtonSelected().setSelected(false);
			} catch (Exception exc) {
			}
			sudokuControls.getCellFromCoord(model.stack.getUndoStackCoords()[0], model.stack.getUndoStackCoords()[1])
					.setSelected(true);
			model.stack.pushRedoStack(model.stack.popStack()); // Removes the last element of the stack
			sudokuControls.updateCellValues(model.getSudoku()); // Updates the visuals
			updateColours();
		}
	}

	public Controller() {
		model = new Model();
		try {
			fileLoader.LoadSudokuBoardDoc(model);
		} catch (IOException e) {
			new PopUpWrongFile("wrong filetype", this);
		} catch (NumberFormatException ez) {
			new PopUpWrongFile("wrong filetype", this);
		} catch (NoSuchElementException ex) {
			new PopUpWrongFile("Illegal file content. Check for newlines",
					this);
		}

		if (model.getSandwich()) {
			view = new View(model.getN(), model.getK(),
					new SandwichSudoku(model.getSudoku(), model.getN(), model.getK(), model.xSums, model.ySums));
			validity = new ValiditySandwich(model.getSudoku(), model.getN(), model.getK(), model.xSums, model.ySums);

			solver = new BacktrackAlgorithm(model.getN(), model.getN(), model.xSums, model.ySums, model.getSudoku(),
					model);
		} else {
			view = new View(model.getN(), model.getK(),
					new ClassicSudokuBoard(model.getSudoku(), model.getN(), model.getK()));

			validity = new ValidityClassic(model.getSudoku(), model.getN(), model.getK());
			solver = new CrooksAlgorithm(model.getN(), model.getK(), model.getSudoku(), model);
		}
		view.sudokuBoard.assembleBoard();
		model.setValidity(validity);
		model.setSolver(solver);
		model.setStack(new Stack(model.getSudoku()));

		sudokuControls = new ClassicSudokuControls(view.sudokuBoard.getCells());
		markCells = new ClassicMarkCells(model.getSudoku(), model.getN(), model.getK(), sudokuControls, validity);
		ZoomObjectInterface[][] objectList = { sudokuControls.getCells1d(),
				view.sudokuBoard.numpad.getNumpadButtons() };
		zoom = new Zoom(objectList, view);
		// Assign actionlisteners

		for (Cell cell : sudokuControls.getCells1d()) {
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
			if (model.solver.getSolvedSudoku()[0][0] == 0) {
				new PopUp("This sudoku has no solutions \n");
			}
		} catch (Exception e) {
		}

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

	public boolean getOkPressed() {
		return okPressed;
	}

	public void getHint() {
		try {
			model.runSolver();
			int[] coordinate = sudokuControls.getCellCoordinate(sudokuControls.getButtonSelected());
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

}