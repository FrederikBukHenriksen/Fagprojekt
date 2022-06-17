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

public class SudokuNumpad extends JPanel {

    public ArrayList<NumpadButton> numpadButtons = new ArrayList();

    public SudokuNumpad(int n, int k) {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.setBorder(new LineBorder(Color.black, 1));

        for (int i = 1; i <= k * n; i++) {
            NumpadButton button = new NumpadButton(String.valueOf(i));// adds number as label to button
            button.setFont(new Font("Serif", Font.PLAIN, 16));
            button.setBorder(new LineBorder(Color.black, 1));

            numpadButtons.add(button);

            this.add(button);

            // Label spacer at every square border for astetichs.
            if (i % n == 0 && i < k * n) {
                JLabel spacer = new JLabel();
                spacer.setPreferredSize(new Dimension(2, 0));
                this.add(spacer);
            }
        }
    }
}
