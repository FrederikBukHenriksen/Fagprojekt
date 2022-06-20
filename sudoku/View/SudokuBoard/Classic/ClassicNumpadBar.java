package sudoku.View.SudokuBoard.Classic;

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

public class ClassicNumpadBar extends SudokuNumpadBarAbstract {

    public ClassicNumpadBar(int n, int k) {
        this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        this.setBorder(new LineBorder(Color.black, 1));
        numpadButtons = new NumpadButton[n * n];

        for (int i = 0; i < k * n; i++) {
            NumpadButton button = new NumpadButton(String.valueOf(i + 1));// adds number as label to button
            button.setFont(new Font("Serif", Font.PLAIN, 16));
            button.setBorder(new LineBorder(Color.black, 1));

            numpadButtons[i] = (button);

            // Label spacer at every square border for astetichs.
            if (i % n == 0 && i < k * n && i != 0) {
                // Layoutmanager ignores padding of button, therefore a spacer is needed.
                JLabel spacer = new JLabel();
                spacer.setPreferredSize(new Dimension(2, 0));
                this.add(spacer);
            }
            this.add(button);
        }
    }
}
