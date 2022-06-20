package sudoku.View.SudokuBoard;

import java.util.ArrayList;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

import sudoku.Controller.Zoom.ZoomObjectInterface;
import sudoku.View.SudokuBoard.Classic.ClassicSudokuColors;

import java.awt.*;
import java.awt.Color;

public class Cell extends JToggleButton implements ZoomObjectInterface {

    public boolean enabled = true;
    ClassicSudokuColors sudokuColors = new ClassicSudokuColors();

    public Cell() {
        setText("");
        setBorder(new LineBorder(Color.black, 1));
        setEnabled(true);
        sudokuColors.colorDefault(this);
        UIManager.put("ToggleButton.select", sudokuColors.cellSelected); // Needs to be put into UImanager.
                                                                         // Manually
        // coloring
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
        sudokuColors.colorDefault(this);
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
