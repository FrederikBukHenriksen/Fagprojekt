package sudoku.Controller;

import sudoku.Controller.Actionlisteners.*;
import sudoku.Controller.Actionlisteners.MenuBar.*;
import sudoku.Controller.MarkCells.ClassicSudokuMarkCells;
import sudoku.Controller.MarkCells.MarkCellsExtend;
import sudoku.Model.Model;
import sudoku.Model.Solver.*;
import sudoku.Model.Validity.*;
import sudoku.View.ExceptionPopUp;
import sudoku.View.View;
import sudoku.View.SudokuBoard.*;
import sudoku.View.SudokuBoard.Classic.ClassicSudokuBoard;
import sudoku.View.SudokuBoard.Sandwich.SandwichSudoku;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
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
	public ValidityExtend validity;
	public SolverAbstract solver;
	public SudokuExtend sudokuBoard;
	public LoadSudokuBoardFile loadSudokuBoardFile;
	public SudokuControls sudokuControls;
	public MarkCellsExtend markCells;
	public boolean okPressed = false;
	public boolean hintPressed = false;

	int zoomSizeIncrementChange = 5; // Amount of zoom.

	public void updateColours() {
		markCells.clearMarkedCells();
		try {
			markCells.markCells(sudokuControls.getButtonSelected());
		} catch (Exception e) {
		}
		if (model.validity.checkValidity(model.getSudoku()) && model.isFilled()) {
			createPopUp("Congratulations, you solved the puzzle!");
		}
	}

	public void redoMove() {
		if (model.stack.redoes > 0) {
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
		if (model.stack.moves > 0) {
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

	public void createPopUp(String text) {
		okPressed = false;
		JDialog jd = new JDialog();
		jd.setLayout(new FlowLayout());
		int x = view.getX();
		int y = view.getY();
		int height = view.getHeight();
		int width = view.getWidth();
		// jd.setBounds((width / 2) - 200 + x, (height / 2) - 75 + y, 400, 150);
		JLabel jLabel = new JLabel(text);
		jLabel.setFont(new Font(jLabel.getFont().getName(), Font.PLAIN, 20));
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		JButton newButton = new JButton("New puzzle");
		newButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: Generate new puzzle here
				view.dispose();
				jd.dispose();
				okPressed = true;
			}
		});

		JButton continueButton = new JButton("Back to Puzzle");
		continueButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jd.dispose();
			}
		});
		Container contentPane = new Container();

		Panel outerPanel = new Panel();
		// outerPanel.setBackground(Color.RED);
		outerPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		Panel innerPanel = new Panel();
		innerPanel.setLayout(new FlowLayout());
		// innerPanel.setBackground(Color.GREEN);
		gbc.gridx = 0;
		gbc.gridy = 0;
		outerPanel.add(jLabel, gbc);
		innerPanel.add(closeButton);
		innerPanel.add(newButton);
		innerPanel.add(continueButton);
		gbc.gridx = 0;
		gbc.gridy = 1;
		outerPanel.add(innerPanel, gbc);
		contentPane.add(outerPanel, BorderLayout.CENTER);
		jd.add(outerPanel);
		jd.setVisible(true);
		jd.pack();

	}

	// Simple constructor
	public Controller() {
		model = new Model();
		try {
			loadSudokuBoardFile.LoadSudokuBoardDoc(this, model);

		} catch (IOException e) {
			// System.out.println("Wrong filetype");
			CreateOkPopUp wrongFile = new CreateOkPopUpExtend("wrong filetype", this);
		} catch (NumberFormatException ez) {
			// System.out.println("Wrong filetype");
			CreateOkPopUp wrongFile = new CreateOkPopUpExtend("wrong filetype", this);
		} catch (NoSuchElementException ex) {
			// System.out.println("Sudoku formatet wrong. Hint: Check for newlines");
			CreateOkPopUp IllegalContent = new CreateOkPopUpExtend("Illegal file content. Check for newlines",
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
		model.setValidity(validity);
		model.setSolver(solver);

		sudokuControls = new SudokuControls(view.sudokuBoard.cells);
		markCells = new ClassicSudokuMarkCells(model.getN(), model.getK(), sudokuControls, validity);

		// Assign actionlisteners

		for (Cell cell : sudokuControls.getCellsLinear()) {
			cell.addActionListener(new SudokuboardListener(this));
			cell.addKeyListener(new KeyboardNumberListener(this));
			cell.addKeyListener(new KeyboardShortcutListener(this));
		}
		view.sudokuNumpad.numpadButtons.forEach(b -> b.addActionListener(new NumboardListener(this)));
		view.sudokuMenuBar.zoomIn.addActionListener(new MenuBarZoomActionListener(this));
		view.sudokuMenuBar.zoomOut.addActionListener(new MenuBarZoomActionListener(this));
		view.sudokuMenuBar.undo.addActionListener(new SudokuUndoListener(this));
		view.sudokuMenuBar.remove.addActionListener(new SudokuRemoveListener(this));

		view.sudokuMenuBar.redo.addActionListener(new SudokuRedoListener(this));
		view.sudokuMenuBar.solve.addActionListener(new MenuBarMenuActionListener(this));
		view.sudokuMenuBar.hint.addActionListener(new SudokuHintListener(this));

		view.sudokuMenuBar.newPuzzle.addActionListener(new MenuBarMenuActionListener(this));

			try {
				if (model.solver.getSolvedSudoku()[0][0] == 0) {
					createPopUp("This sudoku has no solutions \n");
				}
			} catch (Exception e) {
			} // maybe add for sandwich


		while (true) {
			okPressed = false;
			hintPressed = false;
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (okPressed) {
					break;
				} else if (hintPressed) {
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
			new CreateOkPopUp(e.getMessage(), this);
		}
	}


	public boolean getOkPressed() {
		return okPressed;
	}

	public void zoomIn() {
		zoom(zoomSizeIncrementChange);
	}

	public void zoomOut() {
		zoom(-zoomSizeIncrementChange);
	}

	public void zoom(int size) {
		for (Cell cell : sudokuControls.getCellsLinear()) {
			cell.adjustSize(size);
		}
		for (NumpadButton numpadButton : view.sudokuNumpad.numpadButtons) {
			numpadButton.adjustSize(size);
		}
		view.pack();
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
			new ExceptionPopUp(exc);
		}
	}

}