package sudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.Color;

public class Cell extends JToggleButton implements ActionListener {

    Color selected = Color.BLUE;
    Color conflict = Color.red;
    Color conflictFont = Color.green;

    Color square = new Color(0, 96, 255);
    Color similar = new Color(0, 32, 255);
    Color peer = new Color(0, 64, 255);

    Color def = Color.white;
    Color defFont = Color.black;

    boolean enabled = true;

    public Cell() {
        setText("");
        setBackground(def);
        setFont(new Font("Serif", Font.PLAIN, 32));
        this.addActionListener(this);
        setBorder(new LineBorder(Color.black, 1));
        UIManager.put("ToggleButton.highlight", Color.red);
        UIManager.put("ToggleButton.select", selected);
        SwingUtilities.updateComponentTreeUI(this);
    }

    @Override
    public void setEnabled(boolean b) {
        def = Color.gray;
        enabled = false;
        setBackground(def);
    }

    @Override
    public void setText(String text) {
        if (enabled) {
            super.setText(text);
        }
    }

    public void defaultColor() {
        setBackground(def);
        setForeground(defFont);
    }

    public void conflict() {
        if (enabled) {
            setForeground(conflictFont);
        }
        setBackground(conflict);
    }

    public void unSelected() { // TODO: Tjek om vi bruger
        setForeground(defFont);
        setBackground(def);
    }

    public void similar() {
        setBackground(similar);
    }

    public void peer() {
        setBackground(peer);
    }

    public void square() {
        setBackground(square);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (isSelected()) {
            System.out.println("LOLCAT1");

        } else {
            System.out.println("LOLCAT2");

        }
    }
}
