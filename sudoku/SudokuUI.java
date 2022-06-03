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
public class SudokuUI {

    public MenuButton undo = new MenuButton("Undo");
    public MenuButton redo = new MenuButton("Redo");
    public MenuButton remove = new MenuButton("Remove");

    int n = SudokuModel.n;
    int k = SudokuModel.k;

    public ArrayList<JButton> numpadButtons = new ArrayList();

    public JPanel createNumpad() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.setBorder(new LineBorder(Color.black, 1));

        for (int i = 1; i <= k * n; i++) {
            JButton button = new NumpadButton(String.valueOf(i));// adds number as label to button
            button.setFont(new Font("Serif", Font.PLAIN, 16));
            button.setPreferredSize(new Dimension(50, 50));
            button.setBorder(new LineBorder(Color.black, 1));

            numpadButtons.add(button);

            panel.add(button);

            // Label spacer at every square border.
            if (i % n == 0 && i < k * n) {
                JLabel spacer = new JLabel();
                spacer.setPreferredSize(new Dimension(2, 0));
                panel.add(spacer);
            }
        }
        return panel;


    }

    public JPanel createControls() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(undo);
        panel.add(redo);
        panel.add(remove);
        return panel;
    }

    public JMenuBar createMenubar() {

        JMenuBar mb = new JMenuBar();

        // create a menu
        JMenu x = new JMenu("Menu");

        // create menuitems
        JMenuItem m1 = new JMenuItem("MenuItem1");
        JMenuItem m2 = new JMenuItem("MenuItem2");
        JMenuItem m3 = new JMenuItem("MenuItem3");

        // add menu items to menu
        x.add(m1);
        x.add(m2);
        x.add(m3);

        // add menu to menu bar
        mb.add(x);
        return mb;
    }

    class MenuButton extends JButton {
        Color def = Color.gray;
        Color defFont = Color.white;

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

        Color def = Color.gray;
        Color defFont = Color.white;

        public NumpadButton(String text) {
            setText(text);
            setBackground(def);
            setForeground(defFont);
            setFont(new Font("Serif", Font.PLAIN, 16));
            setBorder(new LineBorder(Color.black, 1));
        }
    }

}