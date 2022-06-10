package sudoku.View.SudokuBoard;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javax.swing.*;
import javax.swing.border.LineBorder;

import sudoku.View.SudokuBoard.*;

import java.awt.*;

public class MenuButton extends JButton {

    Color def = Color.gray;
    Color defFont = Color.white;

    public MenuButton(String text) {
        setText(text);
        setFocusable(false);
        setBackground(def);
        setForeground(defFont);
        setFont(new Font("Serif", Font.PLAIN, 16));
        setBorder(new LineBorder(Color.black, 1));
        setMaximumSize(new Dimension(300, 200));
        // setPreferredSize(new Dimension(50, 50));
        // setMinimumSize(new Dimension(50, 50));
    }

}
