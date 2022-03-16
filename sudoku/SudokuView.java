package sudoku;

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
	public int n;
	public int k;

	ArrayList<ArrayList<JToggleButton>> sudokuboardCells = new ArrayList();
	ArrayList<JButton> numboardButtons = new ArrayList();
	JButton undo = new JButton("Undo");
	JButton remove = new JButton("Remove");
	JButton note = new JButton("note");
	JButton newSudoku = new JButton("newSudoku");

	int[][] sudoku;

	public JFrame f;

	public SudokuView() {
		f = new JFrame();
		setVisible(f);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void showFrame(int[][] sudoku) {

		JPanel mainGui = new JPanel(new GridLayout(1, 2, 50, 0));
		JPanel panelGui = new JPanel(new GridLayout(k,k, 10, 10));
		createFields(sudoku);
		
		for (int l = 0; l < k*k; l++) {
			JPanel panel = new JPanel(new GridLayout(n, n));

			for (int i = 0; i < n; i++) {

				for (int j = 0; j < n; j++) {//l/k benytter sig af hvordan java runder op. det er n hvor mange felter den skal rygge, og den skal rygge det hver gang l har bev�get sig k felter.
					if (sudoku[(i + n * (l /k))][(j + n* l) % (k*n)] == 0) { 
						
						
						sudokuboardCells.get((i + n * (l /k))).get((j + n* l) % (k*n))
								.setFont(new Font("Serif", Font.PLAIN, 12));
						// fields.get(i + 3 * (l / 3)).get((j + 3 * l) % 9).setEnabled(false);
						panel.add(sudokuboardCells.get((i + n * (l /k))).get((j + n* l) % (k*n)));

					} else {
						// JLabel l1 = new JLabel(String.valueOf(sudoku[i+3*(l/3)][(j+3*l)%9]));
						sudokuboardCells.get((i + n * (l /k))).get((j + n* l) % (k*n)).setText(
								String.valueOf(sudoku[(i + n * (l /k))][(j + n* l) % (k*n)]));
						sudokuboardCells.get((i + n * (l /k))).get((j + n* l) % (k*n)).setEnabled(false);
						panel.add(sudokuboardCells.get((i + n * (l /k))).get((j + n* l) % (k*n)));

					}
				}
			}
			panelGui.add(panel);
		}
		mainGui.add(panelGui);
		JPanel sideButtonGui = new JPanel(new GridLayout(2, 1, 0, 10));// creates buttons panels on the right side
		JPanel specialButton = new JPanel(new GridLayout(1, 4, 0, 0));
		ActionListener specialAction = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
				boolean selected = abstractButton.getModel().isSelected();
				if (xGrid != -1) {
					if (sudokuboardCells.get(xGrid).get(yGrid).isSelected()) {
						// deletes number from grid, if a cell is selected
						// Does nothing if no cell is selected
						sudokuboardCells.get(xGrid).get(yGrid).setText(null);
						sudoku[xGrid][yGrid] = 0;
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
									.equals(sudokuboardCells.get(xGrid).get(yGrid).getText())) {
								// Check for what field is selected. Sets number to 0, if the same number was
								// already selected
								sudokuboardCells.get(xGrid).get(yGrid).setText(null);
								sudoku[xGrid][yGrid] = 0;
							} else {
								sudokuboardCells.get(xGrid).get(yGrid).setText(actionEvent.getActionCommand());
								sudoku[xGrid][yGrid] = Integer.parseInt(actionEvent.getActionCommand());
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
		numboardButtons.forEach(b -> b.addActionListener(listenForNumboardButtons));
	}
	void addSudokuControlsListener(ActionListener listenForUndo, ActionListener listenForRemove,
			ActionListener listenForNote, ActionListener listenForNew) {
		undo.addActionListener(listenForUndo);
		remove.addActionListener(listenForRemove);
		note.addActionListener(listenForNote);
		newSudoku.addActionListener(listenForNew);
	}
	void addSudokuboardListener(ActionListener listenForSudokuboardButtons) {
		int x = 0, y = 0; // Used to give the button an ActionCommand
		for (ArrayList<JToggleButton> arraylist : sudokuboardCells) {
			x++;
			for (JToggleButton button : arraylist) {
				y++;
				button.addActionListener(listenForSudokuboardButtons);
			}
		}
	}

	public void createFields(int[][] sudoku) {
		for (int i = 0; i < n*k; i++) {
			ArrayList<JToggleButton> rows = new ArrayList();
			for (int j = 0; j < n*k; j++) {
				rows.add(new JToggleButton(""));
				// System.out.println((i + 1) * (j + 1));
			}
			sudokuboardCells.add(rows);
		}
	}

	public void getSelected(JToggleButton buttonSelected) {
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

	public JToggleButton getButtonSelected() { // TODO: ret til abstractbutton

		ArrayList<JToggleButton> result = (ArrayList<JToggleButton>) getButtons().stream()
				.filter(b -> b.isSelected())
				.collect(Collectors.toList());
		return result.get(0);

	}

	public int[] getCellCoordinate(JToggleButton selected) {
		int[] coordinate = new int[2];
		for (int x = 0; x < n*k; x++) {
			for (int y = 0; y < n*k; y++) {
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
		for (int x = 0; x < n*k; x++) {
			for (int y = 0; y < n*k; y++) {
				if (sudoku[x][y] != 0) {
					JToggleButton button = sudokuboardCells.get(x).get(y);
					button.setText(String.valueOf(sudoku[x][y]));
				}
				else {
					JToggleButton button = sudokuboardCells.get(x).get(y);
					button.setText("");
				}
			}
		}
	}


  public void getBoardValues(int n, int k) {
	  this.n = n;
	  this.k = k;
  }
	public void setTitle(String string) {
		f.setTitle(string);
	}

	public void updateFrameTitle(boolean checkValidity, boolean isFilled) {
		if (checkValidity) {
			if (isFilled) {
				f.setTitle("Filled and valid");
			} else {
				f.setTitle("Valid");
			}
		} else {
			if (isFilled) {
				f.setTitle("Filled and invalid");
			} else {
				f.setTitle("Invalid");
			}
		}
	}
}
