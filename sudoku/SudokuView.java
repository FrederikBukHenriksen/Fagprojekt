package sudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.*;

import sudoku.SudokuBoard.Cell;
import sudoku.SudokuController.KeyboardSudokuListener;

public class SudokuView extends JFrame {

	public int n;
	public int k;
	int[][] sudoku;
	SudokuBoard sudokuBoard;
	SudokuUI sudokuUI;
	JPanel controls;

	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	// ArrayList<JButton> numboardButtons = new ArrayList();
	JButton undo = new JButton("Undo");
	JButton remove = new JButton("Remove");
	JButton note = new JButton("Redo");
	JButton newSudoku = new JButton("newSudoku");

	public SudokuView() {
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		setResizable(true);
		setVisible(true);
		setExtendedState(this.getExtendedState());

		// System.out.println(screenSize.getHeight() + " " + screenSize.getWidth());

	}

	public void showFrame(int[][] sudoku) {
		n = SudokuModel.n;
		k = SudokuModel.k;
		this.sudoku = sudoku;
		sudokuBoard = new SudokuBoard(this);
		sudokuUI = new SudokuUI(this);

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1;
		c.weightx = 1;
		c.anchor = GridBagConstraints.PAGE_START;

		c.fill = GridBagConstraints.BOTH;

		add(sudokuBoard, c);

		// add menubar to frame
		// setJMenuBar(sudokuUI.createMenubar());

		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0;
		c.weightx = 0;

		add(sudokuUI.createNumpad(), c);

		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 0;
		c.weightx = 0;
		c.fill = c.fill = GridBagConstraints.BOTH;

		controls = sudokuUI.createControls();
		add(controls, c);
		pack();
	}

	public void onlySelectThePressed(Cell buttonSelected) {

		// Pressing an already selected button causes it to become unselected.
		if (buttonSelected.isSelected() == false) {
			return;
		}

		sudokuBoard.getCells().forEach(b -> b.setSelected(false));
		buttonSelected.setSelected(true);

		// BRUGES TIL DEBUG AF SKÆRM OPLØSNING
		// System.out.println(controls.getWidth());
		// System.out.println(sudokuBoard.getWidth() + " " + sudokuBoard.getHeight());
		System.out.println(sudokuBoard.getMaximumSize());
		System.out.println(sudokuBoard.getMaximumSize());

	}

	public Cell getButtonSelected() throws Exception {
		Cell selected = null;
		for (Cell cell : sudokuBoard.getCells()) {
			if (cell.isSelected()) {
				selected = cell;
			}
		}
		if (selected == null) {
			throw new Exception("No cell selected");
		}
		return selected;
	}

	public int[] getCellCoordinate(Cell selected) {
		int[] coordinate = new int[] { -1, -1 };
		for (int x = 0; x < n * k; x++) {
			for (int y = 0; y < n * k; y++) {
				Cell button = sudokuBoard.cells.get(x).get(y);
				if (button.equals(selected)) {
					coordinate[0] = x;
					coordinate[1] = y;
				}
			}
		}
		return coordinate;
	}

	public void updateBoard(int[][] sudoku) {
		for (int x = 0; x < n * k; x++) {
			for (int y = 0; y < n * k; y++) {
				if (sudoku[x][y] != 0) {
					Cell button = sudokuBoard.cells.get(x).get(y);
					button.setText(String.valueOf(sudoku[x][y]));
				} else {
					Cell button = sudokuBoard.cells.get(x).get(y);
					button.setText("");
				}
			}
		}
	}

	public void markCells() {
		// ###PRESSED BUTTON###
		Cell pressedButton;
		try {
			pressedButton = getButtonSelected();
			int[] coordinates = getCellCoordinate(pressedButton);
			if (coordinates[0] != -1) {
				String cellText = pressedButton.getText();

				// ###SQUARE###
				// Determent the position of upper left corner of the square
				int squareX = coordinates[0] / n;
				int squareY = coordinates[1] / n;

				// Run through the square
				for (int i = squareX * n; i < squareX * n + n; i++) {
					for (int j = squareY * n; j < squareY * n + n; j++) {
						sudokuBoard.cells.get(i).get(j).square();
					}
				}
				// ###PEERS###
				for (int i = 0; i < (n * k); i++) {
					sudokuBoard.cells.get(coordinates[0]).get(i).peer();
					sudokuBoard.cells.get(i).get(coordinates[1]).peer();
				}

				// ###SIMILAR NUMBER###
				for (ArrayList<Cell> array : sudokuBoard.cells) {
					for (Cell button : array) {
						if (!cellText.equals("") && button.getText().equals(cellText)) {
							button.similar();
						}
					}
				}
			}
		} catch (Exception exc) {
			// System.out.println(exc.getMessage());
		}

	}

	public void clearMarkedCells() {
		for (Cell cell : sudokuBoard.getCells()) {
			cell.defaultColor();

		}
	}

	public void updateFrameTitle(boolean checkValidity, boolean isFilled) {
		if (checkValidity) {
			if (isFilled) {
				setTitle("Filled and valid");
				createPopUp();
			} else {
				setTitle("Valid");
			}
		} else {
			if (isFilled) {
				setTitle("Filled and invalid");
			} else {
				setTitle("Invalid");
			}
		}
	}

	public Cell getCellFromCoord(int x, int y) {
		return sudokuBoard.cells.get(x).get(y);
	}

	public void createPopUp() {
		JDialog jd = new JDialog();
		jd.setLayout(new FlowLayout());
		int x = getX();
		int y = getY();
		int height = getHeight();
		int width = getWidth();
		jd.setBounds((width / 2) - 200 + x, (height / 2) - 75 + y, 400, 150);
		JLabel jLabel = new JLabel("Congratulations, you solved the puzzle!");
		jLabel.setFont(new Font(jLabel.getFont().getName(), Font.PLAIN, 20));
		JButton closeButton = new JButton("Close");
		closeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Rick-roll user on exit?
				/*
				 * String url =
				 * "https://www.youtube.com/watch?v=dQw4w9WgXcQ&ab_channel=RickAstley";
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
				jd.dispose();
			}
		});

		jd.add(jLabel);
		jd.add(closeButton);
		jd.add(newButton);
		jd.setVisible(true);
	}
}
