package sudoku.View.SudokuBoard;

import java.awt.Font;
import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;

public class NumpadButton extends JButton {

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

        setMinimumSize(new Dimension(25, 25));
        setPreferredSize(new Dimension(40, 40));
    }

    public void adjustSize(int sizeAdjustment) {
        int currentSize = (int) getSize().getWidth();
        int newSize = currentSize + sizeAdjustment;
        this.setSize(new Dimension(newSize, newSize));
        this.setPreferredSize(new Dimension(newSize, newSize));
    }

}
