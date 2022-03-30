package sudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import java.awt.*;
import java.awt.Color;

public class Cell extends JToggleButton implements ActionListener {

    Color selected = new Color(0, 0, 255);
    Color background = Color.white;
    Color conflict = Color.red;
    Color square = new Color(0, 96, 255);
    Color similar = new Color(0, 32, 255);
    Color peer = new Color(0, 64, 255);

    public Cell() {
        setText("");
        setBackground(Color.WHITE);
        setFont(new Font("Serif", Font.PLAIN, 32));
        this.addActionListener(this);

    }

    public void conflict() {
        setBackground(conflict);
    }

    public void unSelected() {
        setBackground(background);
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
            setBackground(selected);
        } else if (!isSelected()) {
            UIManager.put("ToggleButton.select", Color.WHITE);
            SwingUtilities.updateComponentTreeUI(this);
        }
    }
}
