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

import sudoku.View.Cell.*;

/**
 * SudokuUI
 */
public class SudokuUI {


    public MenuButton undo = new MenuButton("Undo");
    public MenuButton redo = new MenuButton("Redo");
    public MenuButton remove = new MenuButton("Remove");
    public MenuButton hint = new MenuButton("Hint");

    int n;
    int k;

    SudokuView sudokuView;

    public ArrayList<JButton> numpadButtons = new ArrayList();

    public SudokuUI(SudokuView sudokuView) {
        this.sudokuView = sudokuView;
        this.n = sudokuView.n;
        this.k = sudokuView.k;
    }

    public JPanel createNumpad() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        panel.setBorder(new LineBorder(Color.black, 1));

        for (int i = 1; i <= k * n; i++) {
            JButton button = new NumpadButton(String.valueOf(i));// adds number as label to button
            button.setFont(new Font("Serif", Font.PLAIN, 16));
            button.setBorder(new LineBorder(Color.black, 1));

            numpadButtons.add(button);

            panel.add(button);

            // Label spacer at every square border for astetichs.
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
        // panel.setLayout(new FlowLayout());
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.add(undo);
        panel.add(redo);
        panel.add(remove);
        panel.add(hint);

        panel.setMaximumSize(new Dimension(1500, 200));
        return panel;

    }

    // public JMenuBar createMenubar() {

    // JMenuBar mb = new JMenuBar();

    // // create a menu
    // JMenu x = new JMenu("Menu");
    // JMenu tools = new JMenu("Tools");

    // JMenuItem zoomIn = new JMenuItem("Zoom in");
    // JMenuItem zoomOut = new JMenuItem("Zoom out");

    // tools.add(zoomIn);
    // tools.add(zoomOut);

    // // create menuitems
    // JMenuItem m1 = new JMenuItem("MenuItem1");
    // JMenuItem m2 = new JMenuItem("MenuItem2");
    // JMenuItem m3 = new JMenuItem("MenuItem3");

    // // add menu items to menu
    // x.add(m1);
    // x.add(m2);
    // x.add(m3);

    // // add menu to menu bar
    // mb.add(tools);
    // mb.add(x);
    // return mb;
    // }

    class MenuButton extends JButton {
        Color def = Color.gray;
        Color defFont = Color.white;

        public MenuButton(String text) {
            setText(text);
            setFocusable(false);
            setBackground(def);
            setForeground(defFont);
            setFont(new Font("Serif", Font.PLAIN, 16));
            setBorder(new LineBorder(Color.black, 1));
            setMaximumSize(new Dimension(300, 200));
            // setPreferredSize(new Dimension(50, 50));
            // setMinimumSize(new Dimension(50, 50));
        }
    }

    class NumpadButton extends JButton {

        Color def = Color.gray;
        Color defFont = Color.white;

        public NumpadButton(String text) {
            setFocusable(false);
            setText(text);
            setFocusable(false);
            setBackground(def);
            setForeground(defFont);
            setFont(new Font("Serif", Font.PLAIN, 16));
            setBorder(new LineBorder(Color.black, 1));
            // setPreferredSize(new Dimension(sudokuView.sudokuBoard.cellSize,
            // sudokuView.sudokuBoard.cellSize));
            // setPreferredSize(new Dimension(100, 100));
            setPreferredSize(sudokuView.sudokuBoard.getCellsLinear().get(0).getPreferredSize());

        }
    }

}