import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.lang.Math;

import javax.swing.*;

public class SudokuView {
	public int test = 1;
	public int xGrid = -1;
	public int yGrid = -1;

	ArrayList<ArrayList<JToggleButton>> sudokuboardCells = new ArrayList();
	ArrayList<JButton> numboardButtons = new ArrayList();

	int[][] sudoku;

	public JFrame f;

	public SudokuView() {
		f = new JFrame();
		setVisible(f);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void showFrame() {

		JPanel mainGui = new JPanel(new GridLayout(1, 2, 50, 0));
		JPanel panelGui = new JPanel(new GridLayout(3, 3, 10, 10));

		createFields(sudoku);

		for (int l = 0; l < 9; l++) {
			JPanel panel = new JPanel(new GridLayout(3, 3));

			for (int i = 0; i < 3; i++) {

				for (int j = 0; j < 3; j++) {
					if (sudoku[i + 3 * (l / 3)][(j + 3 * l) % 9] == 0) {
						// ActionListener actionListener = new ActionListener() {
						// public void actionPerformed(ActionEvent actionEvent) {
						// AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
						// boolean selected = abstractButton.getModel().isSelected();
						// int p = Integer.parseInt(actionEvent.getActionCommand());
						// for (int i = 0; i<9; i++) {
						// for (int j = 0; j<9; j++) {
						// fields.get(i).get(j).setSelected(false);
						// }
						// }
						// xGrid = (int) (Math.floor(p)/10)-1;
						// yGrid = p%10-1;
						// fields.get(xGrid).get(yGrid).setSelected(true);

						// }
						// };
						// fields.get(i+3*(l/3)).get((j+3*l)%9).addActionListener(actionListener);
						sudokuboardCells.get(i + 3 * (l / 3)).get((j + 3 * l) % 9)
								.setFont(new Font("Serif", Font.PLAIN, 72));
						// fields.get(i + 3 * (l / 3)).get((j + 3 * l) % 9).setEnabled(false);
						panel.add(sudokuboardCells.get(i + 3 * (l / 3)).get((j + 3 * l) % 9));

					} else {
						// JLabel l1 = new JLabel(String.valueOf(sudoku[i+3*(l/3)][(j+3*l)%9]));
						sudokuboardCells.get(i + 3 * (l / 3)).get((j + 3 * l) % 9).setText(
								String.valueOf(sudoku[i + 3 * (l / 3)][(j + 3 * l) % 9]));
						sudokuboardCells.get(i + 3 * (l / 3)).get((j + 3 * l) % 9).setEnabled(false);
						panel.add(sudokuboardCells.get(i + 3 * (l / 3)).get((j + 3 * l) % 9));
					}
				}
			}
			panelGui.add(panel);
		}
		mainGui.add(panelGui);
		JPanel sideButtonGui = new JPanel(new GridLayout(2, 1, 0, 10));// creates buttons panels on the right side
		JPanel specialButton = new JPanel(new GridLayout(1, 4, 0, 0));
		JButton undo = new JButton("undo");
		JButton remove = new JButton("remove");
		JButton note = new JButton("note");
		JButton newSudoku = new JButton("newSudoku");
		ActionListener specialAction = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				boolean selected = abstractButton.getModel().isSelected();
				if (xGrid != -1) {
					if (sudokuboardCells.get(xGrid).get(yGrid).isSelected()) { // deletes number from grid, if a cell
																				// is selected.
						// Does nothing if no cell is selected
						sudokuboardCells.get(xGrid).get(yGrid).setText(null);
						sudoku[xGrid][yGrid] = 0;
						if (SudokuController.checkValidity(sudoku)) { // calls controller to check if valid
							f.setTitle("Valid");
						} else {
							f.setTitle("Invalid");
						}
					}

				}
			}
		};
		remove.addActionListener(specialAction);
		specialButton.add(undo);
		specialButton.add(remove);
		specialButton.add(note);
		specialButton.add(newSudoku);
		sideButtonGui.add(specialButton);
		JPanel buttonGui = new JPanel(new GridLayout(3, 3, 20, 20));// creates a 3/3 with numbers from 1-9
		for (int j = 0; j < 9; j++) {
			numboardButtons.add(new JButton(j + 1 + ""));// adds number as label to button
			numboardButtons.get(j).setActionCommand(j + 1 + "");
		}

		for (int i = 1; i < 10; i++) {
			test = i;
			ActionListener actionListener = new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
					boolean selected = abstractButton.getModel().isSelected();
					if (xGrid != -1) {
						if (sudokuboardCells.get(xGrid).get(yGrid).isSelected()) {
							if (actionEvent.getActionCommand()
									.equals(sudokuboardCells.get(xGrid).get(yGrid).getText())) {// check
								// for
								// what
								// fields
								// is
								// selected.
								// Sets
								// number
								// to 0,
								// if
								// the
								// same
								// number
								// was
								// already
								// selected
								sudokuboardCells.get(xGrid).get(yGrid).setText(null);
								sudoku[xGrid][yGrid] = 0;
							} else {
								sudokuboardCells.get(xGrid).get(yGrid).setText(actionEvent.getActionCommand());// check
																												// for
																												// fields
								// is selected,
								// to cell to
								// number
								sudoku[xGrid][yGrid] = Integer.parseInt(actionEvent.getActionCommand());

							}
							if (SudokuController.checkValidity(sudoku)) { // checks for validity
								f.setTitle("Valid");
							} else {
								f.setTitle("Invalid");
							}
						}
					}
				}

			};
			numboardButtons.get(i - 1).addActionListener(actionListener);
			numboardButtons.get(i - 1).setFont(new Font("Serif", Font.PLAIN, 72));
			buttonGui.add(numboardButtons.get(i - 1));

		}
		sideButtonGui.add(buttonGui);
		mainGui.add(sideButtonGui);

		f.add(mainGui);
		// setting grid layout of 3 rows and 3 columns
		f.setSize(1000, 1000);
	}

	public void setVisible(JFrame frame) {
		f.setVisible(true);
	}

	// Actionlisteners
	void addNumboardListener(ActionListener listenForNumboardButtons) {
		// for (JButton button : numboardButtons) {
		// button.addActionListener(listenForNumboardButtons);
		// }
		numboardButtons.forEach(b -> b.addActionListener(listenForNumboardButtons));
	}

	void addSudokuboardListener(ActionListener listenForSudokuboardButtons) {
		int x = 0, y = 0; // Used to give the button an ActionCommand
		for (ArrayList<JToggleButton> arraylist : sudokuboardCells) {
			x++;
			for (JToggleButton button : arraylist) {
				y++;
				button.setActionCommand(x + " " + y);
				button.addActionListener(listenForSudokuboardButtons);
			}
		}
	}

	public void setBoard(int[][] sudoku) {
		this.sudoku = sudoku;
	}

	public void createFields(int[][] sudoku) {
		for (int i = 0; i < 9; i++) {
			ArrayList<JToggleButton> rows = new ArrayList();
			for (int j = 0; j < 9; j++) {
				rows.add(new JToggleButton(""));
				// System.out.println((i + 1) * (j + 1));
			}
			sudokuboardCells.add(rows);
		}
	}

	public void boardButtonSelected(JToggleButton buttonSelected) {
		getButtons().forEach(b -> b.setSelected(false));
		buttonSelected.setSelected(true);

	}

	public ArrayList<JToggleButton> getButtons() {
		ArrayList<JToggleButton> buttonsArray = new ArrayList<>();

		for (ArrayList<JToggleButton> arraylist : sudokuboardCells) {
			for (JToggleButton button : arraylist) {
				buttonsArray.add(button);
			}
		}
		return buttonsArray;
	}

	public void setBoardButton(int i) {
		getButtons().forEach(b -> {
			if (b.isSelected())
				b.setText(String.valueOf(i));
		});
	}

	public JToggleButton isSelected() { // TODO: ret til abstractbutton
		// return (JToggleButton) getButtons()
		// .stream()
		// .filter(b -> b.isSelected().Collectors.to);

		ArrayList<JToggleButton> result = (ArrayList<JToggleButton>) getButtons().stream()
				.filter(b -> b.isSelected() == true)
				.collect(Collectors.toList());
		return result.get(0);

	}

	public int[] getCellCoordinate(JToggleButton selected) {
		int[] coordinate = new int[2];

		for (int x = 0; x < 9; x++) {
			for (int y = 0; y < 9; y++) {
				JToggleButton button = sudokuboardCells.get(x).get(y);
				if (button.equals(selected)) {
					coordinate[0] = x;
					coordinate[1] = y;
				}
			}
		}
		return coordinate;
	}

	public void updateBoard(int[][] sudoku) {
		for (int x = 0; x < sudoku.length; x++) {
			for (int y = 0; y < sudoku.length; y++) {
				if (sudoku[x][y] != 0) {
					JToggleButton button = sudokuboardCells.get(x).get(y);
					button.setText(String.valueOf(sudoku[x][y]));
				}
			}
		}
	}

}
