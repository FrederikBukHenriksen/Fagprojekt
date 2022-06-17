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
        setBorder(new LineBorder(Color.black, 1));

        // setMinimumSize(new Dimension(25, 25));
        // setFont(new Font("Serif", Font.PLAIN, (int) (size * 0.8)));

        // setPreferredSize(new Dimension(size, size));
    }

    public void setSize(int size) {
        // int currentSize = (int) getSize().getWidth();
        // int currentFontSize = (int) getFont().getSize();
        // int newSize = currentSize + sizeAdjustment;
        // int newFontSize = currentFontSize + sizeAdjustment;
        setSize(new Dimension(size, size));
        this.setPreferredSize(new Dimension(size, size));
        setFont(new Font("Serif", Font.PLAIN, (int) (size * 0.8)));
    }

}
