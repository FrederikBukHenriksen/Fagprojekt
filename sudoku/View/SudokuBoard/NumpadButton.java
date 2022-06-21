package sudoku.View.SudokuBoard;

import java.awt.Font;
import javax.swing.*;
import javax.swing.border.LineBorder;

import sudoku.Controller.Zoom.ZoomObjectInterface;

import java.awt.*;

public class NumpadButton extends JButton implements ZoomObjectInterface {

	/*
	 * Author: Frederik
	 * Function: Creates numpad buttons
	 * Input: string
	 */
    public NumpadButton(String text) {
        this.setFocusable(false);
        this.setText(text);
        this.setFocusable(false);
        this.setBackground(Color.gray);
        this.setForeground(Color.white);
        this.setBorder(new LineBorder(Color.black, 1));
    }
	/*
	 * Author: Frederik
	 * Function: set size of numpad buttons
	 * Input: int
	 */
    public void setSize(int size) {
        this.setSize(new Dimension(size, size));
        this.setPreferredSize(new Dimension(size, size));
        this.setFont(new Font("Serif", Font.PLAIN, (int) (size * 0.8)));
    }

}
