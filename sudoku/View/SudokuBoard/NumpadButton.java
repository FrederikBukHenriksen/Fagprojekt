package sudoku.View.SudokuBoard;

import java.awt.Font;
import javax.swing.*;
import javax.swing.border.LineBorder;

import sudoku.Controller.Zoom.ZoomObjectInterface;

import java.awt.*;

public class NumpadButton extends JButton implements ZoomObjectInterface {

    Color def = Color.gray;
    Color defFont = Color.white;

    public NumpadButton(String text) {
        this.setFocusable(false);
        this.setText(text);
        this.setFocusable(false);
        this.setBackground(def);
        this.setForeground(defFont);
        this.setBorder(new LineBorder(Color.black, 1));
    }

    public void setZoomedSize(int size) {
        this.setSize(new Dimension(size, size));
        this.setPreferredSize(new Dimension(size, size));
        this.setFont(new Font("Serif", Font.PLAIN, (int) (size * 0.8)));
    }

}
