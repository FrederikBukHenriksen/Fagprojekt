package sudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.Color;

public class Cell extends JToggleButton implements ActionListener {

    Color selected = new Color(161, 205, 240);
    Color conflict = new Color(240, 192, 193);
    Color square = new Color(199, 219, 235);
    Color similar = new Color(144, 182, 212);
    Color peer = new Color(199, 219, 235);
    Color conflictFont = new Color(230, 67, 70);
    Color def = Color.white;
    Color defFont = new Color(80, 110, 242);

    boolean enabled = true;

    public Cell() {
        setText("");
        setBackground(def);
        setForeground(defFont);
        setFont(new Font("Serif", Font.PLAIN, 32));
        this.addActionListener(this);
        setBorder(new LineBorder(Color.black, 1));
        UIManager.put("ToggleButton.highlight", Color.red);
        UIManager.put("ToggleButton.select", selected);
        SwingUtilities.updateComponentTreeUI(this);
    }

    @Override
    public void setEnabled(boolean b) {
        defFont = Color.black;
        enabled = false;
        setBackground(def);
        setForeground(defFont);
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
        if(!(this.getBackground().equals(this.conflict))){
            setBackground(similar);
        }
    }

    public void peer() {
        if(!(this.getBackground().equals(this.conflict))){
            setBackground(peer);
        }
    }

    public void square() {
        if(!(this.getBackground().equals(this.conflict))){
            setBackground(square);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /*if (isSelected()) {
            System.out.println("LOLCAT1");

        } else {
            System.out.println("LOLCAT2");

        }*/
    }
}
