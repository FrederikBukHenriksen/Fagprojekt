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
    Color conflict = Color.red;
    Color square = new Color(199, 219, 235);
    Color similar = new Color(144, 182, 212);
    Color peer = new Color(199, 219, 235);

    Color def = Color.white;

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

    public boolean getEnabled() {
        return enabled;
    }

    @Override
    public void setText(String text) {
        if (enabled) {
            super.setText(text);
        }
    }

    public void defaultColor() {
        setBackground(def);
    }

    public void conflict() {
        setBackground(conflict);
    }

    public void unSelected() {
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
