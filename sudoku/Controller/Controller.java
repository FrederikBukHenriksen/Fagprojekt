package sudoku.Controller;

import sudoku.Controller.Actionlisteners.*;
import sudoku.Controller.Actionlisteners.MenuBar.MenuBarMenuActionListener;
import sudoku.Controller.Actionlisteners.MenuBar.MenuBarNewSudokuActionListener;
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
	public SudokuControls sudokuControls;

	public boolean okPressed = false;
	public boolean hintPressed = false;

	private SudokuExtend sudokuBoard;


	int zoomSizeIncrementChange = 5;

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
			Cell cell = sudokuControls.getCellFromCoord(point.x, point.y);
			cell.conflict();
			view.markedCells.add(cell);
		}
	}

	public void markSimilarCells() {
		try {
			Cell cellPressed = sudokuControls.getButtonSelected();
			if (cellPressed.getText().equals("")) {
				for (Cell cell : sudokuControls.getCellsLinear()) {
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
				sudokuControls.getButtonSelected().setSelected(false);
				// TODO: indsæt Rasmus' generelle funktion for farver
			} catch (Exception exc) {
				// System.out.println(exc.getMessage());
			}
			sudokuControls
					.getCellFromCoord(model.stack.getRedoStackCoords()[0], model.stack.getRedoStackCoords()[1])
					.setSelected(true);
			model.stack.pushStack(model.stack.popRedoStack()); // Removes the last element of the stack
			// model.setSudoku(model.getSudoku()); // Updates the board
			sudokuControls.updateCellValues(model.getSudoku()); // Updates the visuals
			// view.updateFrameTitle(model.checkValidity(model.getSudoku(), false),
			// model.isFilled());
			updateColours();
		}
	}

	public void undoMove() {
		if (model.stack.moves > 0) {
			// System.out.println("Undo"); // Prints "Undo" FOR DEBUG
			try {
				sudokuControls.getButtonSelected().setSelected(false);
				// TODO: indsæt Rasmus' generelle funktion for farver
			} catch (Exception exc) {
				// System.out.println(exc.getMessage());
			}
			sudokuControls.getCellFromCoord(model.stack.getStackCoords()[0], model.stack.getStackCoords()[1])
					.setSelected(true);
			model.stack.pushRedoStack(model.stack.popStack()); // Removes the last element of the stack
			// model.setSudoku(model.getSudoku()); // Updates the board
			sudokuControls.updateCellValues(model.getSudoku()); // Updates the visuals
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
		try {

			model.boardCreater();
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
		// view = new View(model.getSudoku(), model.getN(), model.getK());

	}

	// Simple constructor
	public Controller() {
		model = new Model();
		LoadSudokuBoardFile();
		if (model.getSandwich()) {

			view = new View(model.getSudoku(), model.getN(), model.getK(),
					new SandwichSudoku(model.getSudoku(), model.getN(), model.getK(), model.xSums, model.ySums));

			model.setValidity(
					new ValiditySandwich(model.getSudoku(), model.getN(), model.getK(), model.xSums, model.ySums));

			model.setSolver(
					new BacktrackAlgorithm(model.getN(), model.getN(), model.xSums, model.ySums, model.getSudoku(),
							model));
		} else {
			view = new View(model.getSudoku(), model.getN(), model.getK(),
					new ClassicSudokuBoard(model.getSudoku(), model.getN(), model.getK()));

			model.setValidity(new ValidityClassic(model.getSudoku(), model.getN(), model.getK()));
			model.setSolver(new CrooksAlgorithm(model.getN(), model.getK(), model.getSudoku(), model));
		}

		sudokuControls = new SudokuControls(view.sudokuBoard.cells);

		try {
			model.solver.solve();
		} catch (Exception exc) {
			new CreateOkPopUp(exc.getMessage(), this);
		}

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
					/*
					System.out.print("{");
					for (int i = 0; i< 9; i++) {
						
						for (int j = 0; j< 9; j++) {
							if (j == 8) {
								System.out.print("" + model.getSudoku()[i][j] + "}");
							}else if (j == 0){
								System.out.print("{" + model.getSudoku()[i][j] + ",");
							}else {
								System.out.print("" + model.getSudoku()[i][j] + ",");
							}
							
						}
						System.out.print(",");
					}
					System.out.print("}");
					System.out.println("");
					*/
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
			int[] coordinate = sudokuControls.getCellCoordinate(sudokuControls.getButtonSelected());
			int tempVal = model.getSudoku()[coordinate[0]][coordinate[1]];
			model.solver.solve();
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

}