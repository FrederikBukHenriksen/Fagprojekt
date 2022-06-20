package sudoku.Controller;

import sudoku.Controller.Actionlisteners.*;
import sudoku.Controller.Actionlisteners.MenuBar.*;
import sudoku.Controller.MarkCells.ClassicMarkCells;
import sudoku.Controller.MarkCells.MarkCellsInterface;
import sudoku.Controller.Zoom.Zoom;
import sudoku.Controller.Zoom.ZoomObjectInterface;
import sudoku.Model.Model;
import sudoku.Model.Stack;
import sudoku.Model.Solver.*;
import sudoku.Model.Validity.*;
import sudoku.View.View;
import sudoku.View.SudokuBoard.*;
import sudoku.View.SudokuBoard.Classic.ClassicSudokuBoard;
import sudoku.View.SudokuBoard.Sandwich.SandwichSudoku;

import java.util.NoSuchElementException;
import javax.swing.*;

import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.Container;
import java.awt.FlowLayout;

public class Controller {

	// Creating variables
	public Model model;
	public View view;
	public ValidityInterface validity;
	public SolverInterface solver;
	public SudokuBoardInterface sudokuBoard;
	public LoadSudokuBoardFile loadSudokuBoardFile;
	public ClassicSudokuControls sudokuControls;
	public MarkCellsInterface markCells;
	public Zoom zoom;
	public boolean okPressed = false;
	public boolean hintPressed = false;

	public void updateColours() {
		markCells.clearMarkedCells();
		try {
			markCells.markCells(sudokuControls.getButtonSelected());
		} catch (Exception e) {
			if (model.validity.checkValidity(model.getSudoku()) && model.isFilled()) {
				// createPopUp("Congratulations, you solved the puzzle!");
				new SudokuSolvedPopUp("Congtaz", this);
			}
		}
	}

	public void redoMove() {
		if (model.stack.redoStack.size() > 0) {
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
		if (model.stack.sudokuStack.size() > 0) {
			try {
				sudokuControls.getButtonSelected().setSelected(false);
			} catch (Exception exc) {
			}
			sudokuControls.getCellFromCoord(model.stack.getStackCoords()[0], model.stack.getStackCoords()[1])
					.setSelected(true);
			model.stack.pushRedoStack(model.stack.popStack()); // Removes the last element of the stack
			sudokuControls.updateCellValues(model.getSudoku()); // Updates the visuals
			updateColours();
		}
	}

	// public void createPopUp(String text) {
	// okPressed = false;
	// JDialog jd = new JDialog();
	// jd.setLayout(new FlowLayout());
	// int x = view.getX();
	// int y = view.getY();
	// int height = view.getHeight();
	// int width = view.getWidth();
	// // jd.setBounds((width / 2) - 200 + x, (height / 2) - 75 + y, 400, 150);
	// JLabel jLabel = new JLabel(text);
	// jLabel.setFont(new Font(jLabel.getFont().getName(), Font.PLAIN, 20));
	// JButton closeButton = new JButton("Close");
	// closeButton.addActionListener(new ActionListener() {
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// System.exit(0);
	// }
	// });
	// JButton newButton = new JButton("New puzzle");
	// newButton.addActionListener(new ActionListener() {
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// // TODO: Generate new puzzle here
	// view.dispose();
	// jd.dispose();
	// okPressed = true;
	// }
	// });

	// JButton continueButton = new JButton("Back to Puzzle");
	// continueButton.addActionListener(new ActionListener() {
	// @Override
	// public void actionPerformed(ActionEvent e) {
	// jd.dispose();
	// }
	// });
	// // Container contentPane = new Container();

	// Panel outerPanel = new Panel();
	// outerPanel.setLayout(new GridBagLayout());
	// GridBagConstraints gbc = new GridBagConstraints();

	// Panel innerPanel = new Panel();
	// innerPanel.setLayout(new FlowLayout());
	// // innerPanel.setBackground(Color.GREEN);
	// gbc.gridx = 0;
	// gbc.gridy = 0;
	// outerPanel.add(jLabel, gbc);
	// innerPanel.add(closeButton);
	// innerPanel.add(newButton);
	// innerPanel.add(continueButton);
	// gbc.gridx = 0;
	// gbc.gridy = 1;
	// outerPanel.add(innerPanel, gbc);
	// // contentPane.add(outerPanel, BorderLayout.CENTER);
	// jd.add(outerPanel);
	// jd.setVisible(true);
	// jd.pack();
	// }

	// Simple constructor
	public Controller() {
		model = new Model();
		try {
			loadSudokuBoardFile.LoadSudokuBoardDoc(this, model);
		} catch (IOException e) {
			// System.out.println("Wrong filetype");
			new CreateOkPopUpWrongFile("wrong filetype", this);
		} catch (NumberFormatException ez) {
			// System.out.println("Wrong filetype");
			new CreateOkPopUpWrongFile("wrong filetype", this);
		} catch (NoSuchElementException ex) {
			// System.out.println("Sudoku formatet wrong. Hint: Check for newlines");
			new CreateOkPopUpWrongFile("Illegal file content. Check for newlines",
					this);
		}

		if (model.getSandwich()) {
			view = new View(model.getSudoku(), model.getN(), model.getK(),
					new SandwichSudoku(model.getSudoku(), model.getN(), model.getK(), model.xSums, model.ySums));
			validity = new ValiditySandwich(model.getSudoku(), model.getN(), model.getK(), model.xSums, model.ySums);

			solver = new BacktrackAlgorithm(model.getN(), model.getN(), model.xSums, model.ySums, model.getSudoku(),
					model);
		} else {
			view = new View(model.getSudoku(), model.getN(), model.getK(),
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
		ZoomObjectInterface[][] objectList = { sudokuControls.getCells1d(), view.sudokuNumpad.numpadButtons };
		zoom = new Zoom(objectList, view);
		// Assign actionlisteners
		// SudokuSolvedPopUp lolcat = new SudokuSolvedPopUp("DEBUG", this);

		for (Cell cell : sudokuControls.getCells1d()) {
			cell.addActionListener(new SudokuboardListener(this));
			cell.addKeyListener(new KeyboardNumberListener(this));
			cell.addKeyListener(new KeyboardShortcutListener(this));
		}
		for (NumpadButton numpadButton : view.sudokuNumpad.numpadButtons) {
			numpadButton.addActionListener(new NumboardListener(this));
		}
		// view.sudokuNumpad.numpadButtons.forEach(b -> b.addActionListener(new
		// NumboardListener(this)));

		view.sudokuMenuBar.zoomIn.addActionListener(new MenuBarZoomActionListener(this));
		view.sudokuMenuBar.zoomOut.addActionListener(new MenuBarZoomActionListener(this));
		view.sudokuMenuBar.undo.addActionListener(new SudokuUndoListener(this));
		view.sudokuMenuBar.remove.addActionListener(new SudokuRemoveListener(this));
		view.sudokuMenuBar.redo.addActionListener(new SudokuRedoListener(this));
		view.sudokuMenuBar.solve.addActionListener(new MenuBarMenuActionListener(this));
		view.sudokuMenuBar.hint.addActionListener(new SudokuHintListener(this));
		view.sudokuMenuBar.newPuzzle.addActionListener(new MenuBarNewSudokuActionListener(this));

		try {
			if (model.solver.getSolvedSudoku()[0][0] == 0) {
				new CreateOkPopUp("This sudoku has no solutions \n");
			}
		} catch (Exception e) {
		}

		while (true) {
			okPressed = false;
			hintPressed = false;
			while (true) {
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
			new CreateOkPopUp(e.getMessage());
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
						if (sudokuControls.getCellFromCoord(i, j).enabled) {
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
									j).enabled) {
								model.setSudokuCell(i, j,
										model.solver
												.getSolvedSudoku()[i][j]);
							}
						}
					}
					updateColours();
				}
			}
		} catch (Exception exc) {
			new CreateOkPopUp(exc.getMessage());
		}
	}

}