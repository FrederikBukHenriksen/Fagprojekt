package sudoku;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;

import sudoku.SudokuBoard.Cell;
import sudoku.SudokuController.KeyboardSudokuListener;

/**
 * SudokuUI
 */
public class SudokuUI extends JPanel {

    MenuButton undo = new MenuButton("Undo");
    MenuButton remove = new MenuButton("Remove");
    MenuButton note = new MenuButton("note");
    MenuButton newSudoku = new MenuButton("newSudoku");

    int n = SudokuModel.n;
    int k = SudokuModel.k;

    public ArrayList<JButton> numboardButtons = new ArrayList();

    public SudokuUI() {

        GridLayout grid = new GridLayout(2, 1, 0, 10);
        setLayout(grid);

        JPanel specialButton = new JPanel(new GridLayout(1, 4, 0, 0));

        specialButton.add(undo);
        specialButton.add(remove);
        specialButton.add(note);
        specialButton.add(newSudoku);

        add(specialButton);

        JPanel buttonGui = new JPanel(new GridLayout(3, 3, 20, 20));// creates a 3/3 with numbers from 1-9

        JPanel numboard = new JPanel();
        numboard.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        for (int j = 0; j < 9; j++) {
            numboardButtons.add(new NumpadButton(j + 1 + ""));// adds number as label to button
            numboardButtons.get(j).setActionCommand(j + 1 + "");
            numboardButtons.get(j).setFont(new Font("Serif", Font.PLAIN, 20));
            numboard.add(numboardButtons.get(j));

        }

        add(buttonGui);
    }

    class MenuButton extends JButton {

        Color def = Color.white;
        Color defFont = new Color(80, 110, 242);

        public MenuButton(String text) {
            setText(text);
            setBackground(def);
            setForeground(defFont);
            setFont(new Font("Serif", Font.PLAIN, 16));
            setBorder(new LineBorder(Color.black, 1));
            setMaximumSize(new Dimension(50, 50));
            setPreferredSize(new Dimension(50, 50));
            setMinimumSize(new Dimension(50, 50));
        }
    }

    class NumpadButton extends JButton {

        Color def = Color.white;
        Color defFont = new Color(80, 110, 242);

        public NumpadButton(String text) {
            setText(text);
            setBackground(def);
            setForeground(defFont);
            setFont(new Font("Serif", Font.PLAIN, 16));
            setBorder(new LineBorder(Color.black, 1));
            setMaximumSize(new Dimension(50, 50));
            setPreferredSize(new Dimension(50, 50));
            setMinimumSize(new Dimension(50, 50));
        }
    }

}