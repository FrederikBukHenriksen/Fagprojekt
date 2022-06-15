package sudoku.Controller;

import sudoku.Controller.Actionlisteners.*;
import sudoku.Controller.Actionlisteners.MenuBar.MenuBarMenuActionListener;
import sudoku.Controller.Actionlisteners.MenuBar.MenuBarNewSudokuActionListener;
import sudoku.Controller.Actionlisteners.MenuBar.MenuBarTestActionListener;
import sudoku.Controller.Actionlisteners.MenuBar.MenuBarZoomActionListener;
import sudoku.Controller.Actionlisteners.MenuBar.SudokuHintListener;
import sudoku.Controller.Actionlisteners.MenuBar.SudokuRedoListener;
import sudoku.Controller.Actionlisteners.MenuBar.SudokuRemoveListener;
import sudoku.Controller.Actionlisteners.MenuBar.SudokuUndoListener;
import sudoku.Model.Model;
import sudoku.Model.Solver.BacktrackAlgorithm;
import sudoku.Model.Solver.CrooksAlgorithm;
import sudoku.Model.Validity.ValidityClassic;
import sudoku.Model.Validity.ValiditySandwich;
import sudoku.View.ExceptionPopUp;
import sudoku.View.View;
import sudoku.View.SudokuBoard.*;
import sudoku.View.SudokuBoard.Classic.ClassicSudokuBoard;
import sudoku.View.SudokuBoard.Sandwich.SandwichSudoku;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Container;
import java.awt.FlowLayout;

public class Controller {

	// Creating variables
	public Model model;
	public View view;
	public boolean okPressed = false;
	public boolean hintPressed = false;

	private SudokuExtend sudokuBoard;

	public void updateColours() {
		view.clearMarkedCells();
		view.markCells();
		markConflictCells();
		if (model.validity.checkValidity(model.getSudoku()) && model.isFilled()) {
			createPopUp("Congratulations, you solved the puzzle!");
		}
	}

	public void markConflictCells() {
		for (Point point : model.validity.getUniqueConflictPoints(model.getSudoku())) {
			Cell cell = view.sudokuBoard.getCellFromCoord(point.x, point.y);
			cell.conflict();
			view.markedCells.add(cell);
		}
	}

	public void markSimilarCells() {
		try {
			Cell cellPressed = sudokuBoard.getButtonSelected();
			if (cellPressed.getText().equals("")) {
				for (Cell cell : view.sudokuBoard.getCellsLinear()) {
					if (cell.getText().equals(cellPressed.getText())) {
						cell.similar();
					}
				}
			}
		} catch (Exception e) {
			new CreateOkPopUp(e.getMessage(), this);
		}
	}

	public void redoMove() {
		if (model.stack.redoes > 0) {
			// System.out.println("Redo"); // Prints "Redo" FOR DEBUG
			try {
				view.sudokuBoard.getButtonSelected().setSelected(false);
				// TODO: indsæt Rasmus' generelle funktion for farver
			} catch (Exception exc) {
				// System.out.println(exc.getMessage());
			}
			view.sudokuBoard
					.getCellFromCoord(model.stack.getRedoStackCoords()[0], model.stack.getRedoStackCoords()[1])
					.setSelected(true);
			model.stack.pushStack(model.stack.popRedoStack()); // Removes the last element of the stack
			// model.setSudoku(model.getSudoku()); // Updates the board
			view.updateCellValues(model.getSudoku()); // Updates the visuals
			// view.updateFrameTitle(model.checkValidity(model.getSudoku(), false),
			// model.isFilled());
			updateColours();
		}
	}

	public void undoMove() {
		if (model.stack.moves > 0) {
			// System.out.println("Undo"); // Prints "Undo" FOR DEBUG
			try {
				view.sudokuBoard.getButtonSelected().setSelected(false);
				// TODO: indsæt Rasmus' generelle funktion for farver
			} catch (Exception exc) {
				// System.out.println(exc.getMessage());
			}
			view.sudokuBoard.getCellFromCoord(model.stack.getStackCoords()[0], model.stack.getStackCoords()[1])
					.setSelected(true);
			model.stack.pushRedoStack(model.stack.popStack()); // Removes the last element of the stack
			// model.setSudoku(model.getSudoku()); // Updates the board
			view.updateCellValues(model.getSudoku()); // Updates the visuals
			// view.updateFrameTitle(model.checkValidity(model.getSudoku(), false),
			// model.isFilled());
			updateColours();
		}
		// System.out.println("UNDO"); //For debug
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
				// Rick-roll user on exit?
				/*
				 * String url
				 * ="https://www.youtube.com/watch?v=dQw4w9WgXcQ&ab_channel=RickAstley";
				 * 
				 * String myOS = System.getProperty("os.name").toLowerCase();
				 * 
				 * try {
				 * if(Desktop.isDesktopSupported()) { // Probably Windows
				 * Desktop desktop = Desktop.getDesktop();
				 * desktop.browse(new URI(url));
				 * } else { // Definitely Non-windows
				 * Runtime runtime = Runtime.getRuntime();
				 * if(myOS.contains("mac")) { // Apples
				 * runtime.exec("open " + url);
				 * }
				 * else if(myOS.contains("nix") || myOS.contains("nux")) { // Linux flavours
				 * runtime.exec("xdg-open " + url);
				 * }
				 * }
				 * }
				 * catch(IOException | URISyntaxException eek) {
				 * }
				 */

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

	public void LoadSudokuBoardFile() {
		view = new View();
		try {
			model = new Model(view);
			// break;
		} catch (IOException e) {
			// System.out.println("Wrong filetype");
			CreateOkPopUp wrongFile = new CreateOkPopUpExtend("wrong filetype", this);
		} catch (NumberFormatException ez) {
			// System.out.println("Wrong filetype");
			CreateOkPopUp wrongFile = new CreateOkPopUpExtend("wrong filetype", this);
		} catch (NoSuchElementException ex) {
			// System.out.println("Sudoku formatet wrong. Hint: Check for newlines");
			CreateOkPopUp IllegalContent = new CreateOkPopUpExtend("Illegal file content. Check for newlines", this);
		}
	}

	// Simple constructor
	public Controller() {
		LoadSudokuBoardFile();
		if (model.getSandwich()) {
			view.setSudoku(new SandwichSudoku(model.getSudoku(), model.getN(), model.getK(), model.xSums, model.ySums));

			model.setValidity(
					new ValiditySandwich(model.getSudoku(), model.getN(), model.getK(), model.xSums, model.ySums));

			model.setSolver(
					new BacktrackAlgorithm(model.getN(), model.getN(), model.xSums, model.ySums, model.getSudoku(),
							model));
		} else {
			view.setSudoku(new ClassicSudokuBoard(model.getSudoku(), model.getN(), model.getK()));

			model.setValidity(new ValidityClassic(model.getSudoku(), model.getN(), model.getK()));
			model.setSolver(new CrooksAlgorithm(model.getN(), model.getK(), model.getSudoku(), model));
		}
		try {
			model.solver.solve();
		} catch (Exception exc) {
			new CreateOkPopUp(exc.getMessage(), this);
		}

		sudokuBoard = new SandwichSudoku(model.getSudoku(), model.getN(), model.getK(), model.xSums, model.ySums);
		view.showFrame(model.getSudoku());
		for (Cell cell : view.sudokuBoard.getCellsLinear()) {
			cell.addActionListener(new SudokuboardListener(this));
			cell.addKeyListener(new KeyboardNumberListener(this));
			cell.addKeyListener(new KeyboardShortcutListener(this));
		}
		view.sudokuNumpad.numpadButtons.forEach(b -> b.addActionListener(new NumboardListener(this)));
		view.menuBar.zoomIn.addActionListener(new MenuBarZoomActionListener(this));
		view.menuBar.zoomOut.addActionListener(new MenuBarZoomActionListener(this));
		view.menuBar.undo.addActionListener(new SudokuUndoListener(this));
		view.menuBar.remove.addActionListener(new SudokuRemoveListener(this));

		view.menuBar.redo.addActionListener(new SudokuRedoListener(this));
		view.menuBar.solve.addActionListener(new MenuBarMenuActionListener(this));
		view.menuBar.hint.addActionListener(new SudokuHintListener(this));

		view.menuBar.test.addActionListener(new MenuBarTestActionListener(this));
		view.menuBar.newPuzzle.addActionListener(new MenuBarNewSudokuActionListener(this));

		if (!model.getSandwich()) {
			try {
				if (model.crooks.getSolvedSudoku()[0][0] == 0) {
					createPopUp("This sudoku has no solutions \n");
				}
			} catch (Exception e) {
			} // maybe add for sandwich
		} else {
			if (model.backtrack.getSolvedSudoku()[0][0] == 0) {
				createPopUp("This sudoku has no solutions \n");
			}
		}
		updateColours();

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
			if (view.sudokuBoard.getButtonSelected().enabled) {
				int[] coordinate = view.sudokuBoard.getCellCoordinate(view.sudokuBoard.getButtonSelected());
				int tempVal = model.getSudoku()[coordinate[0]][coordinate[1]];
				if (model.getSandwich()) {
					if (model.backtrack.getUniqueness()) {
						model.setSudokuCell(coordinate[0], coordinate[1],
								model.backtrack.getSolvedSudoku()[coordinate[0]][coordinate[1]]);
					}
				} else {
					if (model.crooks.getUniqueness()) {
						model.setSudokuCell(coordinate[0], coordinate[1],
								model.crooks.getSolvedSudoku()[coordinate[0]][coordinate[1]]);
					} else {
						int[][] tempSudoku = model.crooks.getSolvedSudoku();
						model.crooks.solve();
						if (model.crooks.getSolvedSudoku()[0][0] == 0) {
							for (int i = 0; i < model.getN() * model.getK(); i++) {
								for (int j = 0; j < model.getN() * model.getK(); j++) {
									if (model.getSudoku()[i][j] != tempSudoku[i][j]) {
										view.sudokuBoard.getCellFromCoord(i, j).conflict();
									}
								}
							}
						}
						new CreateOkPopUp(
								"This sudoku can't be solved with current entries!\n Please remove incorrect entries before trying again",
								this);
					}

					model.setSudokuCell(coordinate[0], coordinate[1],
							model.crooks.getSolvedSudoku()[coordinate[0]][coordinate[1]]);
				}
				if (model.getSandwich()) {
					model.stack.pushStack(model.stack.createStackObj(coordinate[0], coordinate[1], tempVal,
							model.backtrack.getSolvedSudoku()[coordinate[0]][coordinate[1]]));
				} else {
					model.stack.pushStack(model.stack.createStackObj(coordinate[0], coordinate[1], tempVal,
							model.crooks.getSolvedSudoku()[coordinate[0]][coordinate[1]]));
				}
				view.updateCellValues(model.getSudoku());
				updateColours();
			}
		} catch (Exception e) {
			new CreateOkPopUp(e.getMessage(), this);
		}
	}

	public boolean getOkPressed() {
		return okPressed;
	}

	public void zoom(int sizeChange) {
		for (Cell cell : view.sudokuBoard.getCellsLinear()) {
			cell.adjustSize(sizeChange);
		}
		for (NumpadButton numpadButton : view.sudokuNumpad.numpadButtons) {
			numpadButton.adjustSize(sizeChange);
		}
		view.pack();
	}
}