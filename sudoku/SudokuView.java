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
import java.awt.Component; //import these 3 header files
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.*;
import sudoku.SudokuBoard.Cell;
import sudoku.SudokuController.KeyboardSudokuListener;

public class SudokuView extends JFrame {

	public int n;
	public int k;
	SudokuBoard sudokuBoard;
	SudokuUI sudokuUI;

	// ArrayList<JButton> numboardButtons = new ArrayList();
	JButton undo = new JButton("Undo");
	JButton remove = new JButton("Remove");
	JButton note = new JButton("Redo");
	JButton newSudoku = new JButton("newSudoku");

	public SudokuView() {
		// setVisible(true);
		setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		setExtendedState(this.getExtendedState());

		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				int newSize = sudokuBoard.cellSize;
				if (getSize().getWidth() <= getSize().getHeight()) {
					// Width the limiting factor
					newSize = (((int) getSize().getWidth()) - 12) / (n * k); // 12 border
				} else if (getSize().getWidth() > getSize().getHeight()) {
					// Height the limiting factor
					newSize = (((int) getSize().getHeight()) - 12) / (n * k); // 12 border
				}
				for (Cell cell : sudokuBoard.getCellsLinear()) {
					cell.setSize(newSize);
				}
				System.out.println(newSize);
				System.out.println(sudokuBoard.getCellsLinear().get(0).getPreferredSize());

				pack();
			}
		});
	}

	public void showFrame(int[][] sudoku) {
		n = SudokuModel.n;
		k = SudokuModel.k;
		sudokuBoard = new SudokuBoard(sudoku);
		sudokuUI = new SudokuUI();

		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		c.gridx = 0;
		c.gridy = 0;

		add(sudokuBoard, c);

		// add menubar to frame
		setJMenuBar(sudokuUI.createMenubar());

		c.gridx = 0;
		c.gridy = 1;

		// JPanel numboard = new JPanel();
		// numboard.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		// numboard.setBorder(new LineBorder(Color.black, 1));

		// for (int i = 1; i <= k * n; i++) {
		// JButton button = new JButton(String.valueOf(i));// adds number as label to
		// button
		// button.setFont(new Font("Serif", Font.PLAIN, 16));
		// button.setPreferredSize(new Dimension(50, 50));
		// button.setBorder(new LineBorder(Color.black, 1));

		// // button.setBorder(BorderFactory.createCompoundBorder(
		// // BorderFactory.createLineBorder(Color.CYAN, 5),
		// // BorderFactory.createEmptyBorder(5, 5, 10, 10)));

		// numboardButtons.add(button);

		// numboard.add(button);

		// if (i % n == 0 && i < k * n) {
		// JLabel lol = new JLabel();
		// lol.setPreferredSize(new Dimension(2, 0));
		// numboard.add(lol);
		// System.out.println("LOL");
		// }
		// }
		// JLabel lol = new JLabel();
		// lol.setSize(0, 1);
		// numboard.add(lol);

		add(sudokuUI.createNumpad(), c);

		c.gridx = 0;
		c.gridy = 2;

		add(sudokuUI.createControls(), c);
		pack();

	}

	public void onlySelectThePressed(Cell buttonSelected) {

		// Pressing an already selected button causes it to become unselected.
		if (buttonSelected.isSelected() == false) {
			return;
		}

		sudokuBoard.getCells().forEach(b -> b.setSelected(false));
		buttonSelected.setSelected(true);

	}

	public Cell getButtonSelected() throws Exception {
		// ArrayList<Cell> result = (ArrayList<Cell>) sudokuBoard.getCells().stream()
		// .filter(b -> b.isSelected())
		// .collect(Collectors.toList());
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
