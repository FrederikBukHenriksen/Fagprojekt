package sudoku;

import sudoku.Controller.Actionlisteners.*;
import sudoku.View.SudokuBoard.*;

import java.io.IOException;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
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

public class SudokuController {

	// Creating variables
	public SudokuModel model;
	public SudokuView view;
	boolean okPressed = false;

	public void updateColours() {
		view.clearMarkedCells();
		view.markCells();
		if (model.checkValidity(model.getSudoku(), false, true) && model.isFilled()) {
			createPopUp("Congratulations, you solved the puzzle!");
		}
	}

	public void redoMove() {
		if (model.redoes > 0) {
			// System.out.println("Redo"); // Prints "Redo" FOR DEBUG
			try {
				view.sudokuBoard.getButtonSelected().setSelected(false);
				// TODO: indsæt Rasmus' generelle funktion for farver
			} catch (Exception exc) {
				// System.out.println(exc.getMessage());
			}
			view.sudokuBoard
					.getCellFromCoord(model.getRedoStackCoords()[0], model.getRedoStackCoords()[1]).setSelected(true);
			model.pushStack2(model.popRedoStack()); // Removes the last element of the stack
			// model.setSudoku(model.getSudoku()); // Updates the board
			view.updateCellValues(model.getSudoku()); // Updates the visuals
			// view.updateFrameTitle(model.checkValidity(model.getSudoku(), false),
			// model.isFilled());
			updateColours();
		}
	}

	public void undoMove() {
		if (model.moves > 0) {
			// System.out.println("Undo"); // Prints "Undo" FOR DEBUG
			try {
				view.sudokuBoard.getButtonSelected().setSelected(false);
				// TODO: indsæt Rasmus' generelle funktion for farver
			} catch (Exception exc) {
				// System.out.println(exc.getMessage());
			}
			view.sudokuBoard.getCellFromCoord(model.getStackCoords()[0], model.getStackCoords()[1]).setSelected(true);
			model.pushRedoStack(model.popStack2()); // Removes the last element of the stack
			// model.setSudoku(model.getSudoku()); // Updates the board
			view.updateCellValues(model.getSudoku()); // Updates the visuals
			// view.updateFrameTitle(model.checkValidity(model.getSudoku(), false),
			// model.isFilled());
			updateColours();
		}
		System.out.println("UNDO"); // For debug
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
				setOkPressed();
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

	public void sudokuBoard() {
		view = new SudokuView();
		model = new SudokuModel(view);
		try {
			model.boardCreater();
			// break;
		} catch (IOException e) {
			// System.out.println("Wrong filetype");
			createSimplePopUp("wrong filetype");
		} catch (NumberFormatException ez) {
			// System.out.println("Wrong filetype");
			createSimplePopUp("wrong filetype");
		} catch (NoSuchElementException ex) {
			// System.out.println("Sudoku formatet wrong. Hint: Check for newlines");
			createSimplePopUp("Illegal file content. Check for newlines");
		}
	}

	// Simple constructor
	public SudokuController() {
		sudokuBoard();
		view.showFrame(model.getSudoku());
		for (Cell cell : view.sudokuBoard.getCellsLinear()) {
			cell.addActionListener(new SudokuboardListener(this));
			cell.addKeyListener(new KeyboardSudokuListener(this));
		}
		view.sudokuNumpad.numpadButtons.forEach(b -> b.addActionListener(new NumboardListener(this)));
		view.sudokuControls.undo.addActionListener(new SudokuUndoListener(this));
		view.sudokuControls.redo.addActionListener(new SudokuRedoListener(this));
		view.sudokuControls.remove.addActionListener(new SudokuRemoveListener(this));
		view.sudokuControls.hint.addActionListener(new SudokuHintListener(this));
		view.menuBar.zoomIn.addActionListener(new MenuBarZoomActionListener(this));
		view.menuBar.zoomOut.addActionListener(new MenuBarZoomActionListener(this));
		view.menuBar.solve.addActionListener(new MenuBarMenuActionListener(this));
		view.menuBar.test.addActionListener(new MenuBarTestActionListener(this));
		view.menuBar.newPuzzle.addActionListener(new MenuBarMenuActionListener(this));

		model.crooks.solver();
		if (!model.crooks.isSandwich) {
			if (model.crooks.getSolvedSudoku()[0][0] == 0) {
				createPopUp("This sudoku has no solutions \n");
			}
		}
		updateColours();

	while(true) {
		okPressed = false;
		while (true) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (okPressed) {
				break;
				}
			}
		new SudokuController();
		}
	}

	public void createSimplePopUp(String text) {
		okPressed = false;
		JDialog jd = new JDialog();
		jd.setLayout(new FlowLayout());
		int x = view.getX();
		int y = view.getY();
		int height = view.getHeight();
		int width = view.getWidth();
		jd.setBounds((width / 2) - 200 + x, (height / 2) - 75 + y, 400, 150);
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int xScreen = (screenSize.width / 2) - (jd.getWidth() / 2);
		int yScreen = (screenSize.height / 2) - (jd.getHeight() / 2);
		jd.setLocation(xScreen, yScreen);
		JLabel jLabel = new JLabel(text);
		jLabel.setFont(new Font(jLabel.getFont().getName(), Font.PLAIN, 20));
		JButton okButton = new JButton("Ok");
		okButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				view.dispose();
				jd.dispose();
				setOkPressed();
				return;
			}
		});
		jd.add(jLabel);
		jd.add(okButton);
		jd.setVisible(true);
		while (true) {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (okPressed) {
				break;
			}
		}
		sudokuBoard();
	}

	public void getHint() {
		try {
			if (view.sudokuBoard.getButtonSelected().enabled) {
				int[] coordinate = view.sudokuBoard.getCellCoordinate(view.sudokuBoard.getButtonSelected());
				int tempVal = model.getSudoku()[coordinate[0]][coordinate[1]];
				if (model.crooks.getUniqueness()) {
					model.setSudokuCell(coordinate[0], coordinate[1],
							model.crooks.getSolvedSudoku()[coordinate[0]][coordinate[1]]);
				} else {
					int[][] tempSudoku = model.crooks.getSolvedSudoku();
					model.crooks.solver();
					if (model.crooks.getSolvedSudoku()[0][0] == 0) {
						for (int i = 0; i < model.getN() * model.getK(); i++) {
							for (int j = 0; j < model.getN() * model.getK(); j++) {
								if (model.getSudoku()[i][j] != tempSudoku[i][j]) {
									view.sudokuBoard.getCellFromCoord(i, j).conflict();
								}
							}
						}
						createPopUp(
								"This sudoku can't be solved with current entries!\n Please remove incorrect entries before trying again");
					}

					model.setSudokuCell(coordinate[0], coordinate[1],
							model.crooks.getSolvedSudoku()[coordinate[0]][coordinate[1]]);
				}
				model.pushStack2(model.createStackObj(coordinate[0], coordinate[1], tempVal,
						model.crooks.getSolvedSudoku()[coordinate[0]][coordinate[1]]));
				view.updateCellValues(model.getSudoku());
				updateColours();
			}
		} catch (Exception exc) {
			// System.out.println(exc.getMessage());
		}
	}

	public void setOkPressed() {
		okPressed = true;
		
	}
	public boolean getOkPressed() {
		return okPressed;
	}

	public void zoom(int sizeChange) {
		for (Cell cell : view.sudokuBoard.getCellsLinear()) {
			cell.adjustSize(sizeChange);
		}
		view.pack();
	}
}