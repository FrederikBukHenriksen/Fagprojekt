package sudoku;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.*;

import sudoku.SudokuController.KeyboardSudokuListener;

public class SudokuView {
	public int n;
	public int k;

	ArrayList<ArrayList<JToggleButton>> sudokuboardCells = new ArrayList();
	ArrayList<JButton> numboardButtons = new ArrayList();
	JButton undo = new JButton("Undo");
	JButton remove = new JButton("Remove");
	JButton note = new JButton("note");
	JButton newSudoku = new JButton("newSudoku");

	public JFrame f;

	public SudokuView() {
		f = new JFrame();
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void setViewGlobals(int n, int k) {
		this.n = n;
		this.k = k;
	}

	public void showFrame(int[][] sudoku) {

		JPanel mainGui = new JPanel(new GridLayout(1, 2, 50, 0));
		JPanel board = new JPanel(new GridLayout(k, k, 10, 10));

		sudokuboardCells = createCells(sudoku);
		// Separate into squares.

		for (int l = 0; l < k * k; l++) {
			JPanel square = new JPanel(new GridLayout(n, n));
			// Løber gennem størrelsen på én square
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {// l/k benytter sig af hvordan java runder op. det er n hvor mange felter
												// den skal rygge, og den skal rygge det hver gang l har bev�get sig k
												// felter.
					if (sudoku[(i + n * (l / k))][(j + n * l) % (k * n)] == 0) {
						square.add(sudokuboardCells.get((i + n * (l / k))).get((j + n * l) % (k * n)));

					} else {
						// JLabel l1 = new JLabel(String.valueOf(sudoku[i+3*(l/3)][(j+3*l)%9]));
						sudokuboardCells.get((i + n * (l / k))).get((j + n * l) % (k * n)).setText(
								String.valueOf(sudoku[(i + n * (l / k))][(j + n * l) % (k * n)]));
						sudokuboardCells.get((i + n * (l / k))).get((j + n * l) % (k * n)).setEnabled(false);
						square.add(sudokuboardCells.get((i + n * (l / k))).get((j + n * l) % (k * n)));
					}
				}
			}
			board.add(square);
		}
		mainGui.add(board);

		JPanel sideButtonGui = new JPanel(new GridLayout(2, 1, 0, 10));// creates buttons panels on the right side
		JPanel specialButton = new JPanel(new GridLayout(1, 4, 0, 0));

		specialButton.add(undo);
		specialButton.add(remove);
		specialButton.add(note);
		specialButton.add(newSudoku);

		sideButtonGui.add(specialButton);

		JPanel buttonGui = new JPanel(new GridLayout(3, 3, 20, 20));// creates a 3/3 with numbers from 1-9
		for (int j = 0; j < 9; j++) {
			numboardButtons.add(new JButton(j + 1 + ""));// adds number as label to button
			numboardButtons.get(j).setActionCommand(j + 1 + "");
			numboardButtons.get(j).setFont(new Font("Serif", Font.PLAIN, 72));
			buttonGui.add(numboardButtons.get(j));
		}

		sideButtonGui.add(buttonGui);
		mainGui.add(sideButtonGui);

		f.add(mainGui);
		// setting grid layout of 3 rows and 3 columns
		f.setSize(1000, 1000);
	}

	// Actionlistener

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

	public void addSudokuboardKeyboardBinding(KeyboardSudokuListener keysListenerLolcat) {
		for (JToggleButton button : getButtons()) {
			button.addKeyListener(keysListenerLolcat);
		}

		// EKSEMPEL PÅ KEY-BINDING !!!! MÅ IKKE SLETTES.
		// button.getInputMap().put(KeyStroke.getKeyStroke("1"),
		// "check");

		// button.getActionMap().put("check", new AbstractAction() {
		// public void actionPerformed(ActionEvent e) {
		// button.setText("2");
		// }
		// });
		// }

	}

	public ArrayList<ArrayList<JToggleButton>> createCells(int[][] sudoku) {
		/*
		 * @return a simpel (n*K)*(n*K) 2d array with JToggleButtons
		 */
		ArrayList<ArrayList<JToggleButton>> board = new ArrayList<>();
		for (int i = 0; i < n * k; i++) {
			ArrayList<JToggleButton> rows = new ArrayList();
			for (int j = 0; j < n * k; j++) {
				JToggleButton button = new JToggleButton("");
				button.setFont(new Font("Serif", Font.PLAIN, 12));
				rows.add(button);
			}
			board.add(rows);
		}
		return board;
	}

	public void onlySelectThePressed(JToggleButton buttonSelected) {

		// Pressing an already selected button causes it to become unselected.
		if (buttonSelected.isSelected() == false) {
			return;
		}

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
		for (int x = 0; x < n * k; x++) {
			for (int y = 0; y < n * k; y++) {
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
		for (int x = 0; x < n * k; x++) {
			for (int y = 0; y < n * k; y++) {
				if (sudoku[x][y] != 0) {
					JToggleButton button = sudokuboardCells.get(x).get(y);
					button.setText(String.valueOf(sudoku[x][y]));
				} else {
					JToggleButton button = sudokuboardCells.get(x).get(y);
					button.setText("");
				}
			}
		}
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
