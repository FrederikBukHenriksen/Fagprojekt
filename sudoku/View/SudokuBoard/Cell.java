package sudoku.View.SudokuBoard;

import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

import sudoku.Controller.Zoom.ZoomObjectInterface;

import java.awt.*;
import java.awt.Color;

public class Cell extends JToggleButton implements ZoomObjectInterface {

    public static Color colorDefaultBackground = Color.white;
    public static Color colorDefaultFont = Color.black;
    public static Color colorDefaultFontEnabled = new Color(80, 110, 242);
    public static Color cellSelected = new Color(161, 205, 240);

    public boolean enabled = true;

    public Cell() {
        setText("");
        setBackground(colorDefaultBackground);
        setForeground(colorDefaultFont);
        setBorder(new LineBorder(Color.black, 1));
        setEnabled(true);
        UIManager.put("ToggleButton.select", cellSelected); // Needs to be put into UImanager. Manually coloring
                                                            // selected cell will be overwritten.
        SwingUtilities.updateComponentTreeUI(this);
    }

    // Set methods
    @Override
    public void setEnabled(boolean enable) {
        if (enable == true) {
            enabled = true;
        } else {
            enabled = false;
        }
    }

    @Override
    public void setText(String text) {
        if (enabled) {
            super.setText(text);
        }
    }

    @Override
    public void setSize(int size) {
        this.setSize(new Dimension(size, size));
        this.setPreferredSize(new Dimension(size, size));
        this.setFont(new Font("Serif", Font.PLAIN, (int) (size * 0.8)));
    }

    public void setTextColor(Color color) {
        this.setForeground(color);
    }

    public void setBackgroundColor(Color color) {
        this.setBackground(color);
    }

    // Get methods

    public Color getBackgroundColor() {
        return this.getBackground();
    }

    public Color getTextColor() {
        return this.getForeground();
    }

    public boolean getEnabled() {
        return enabled;
    }
}
