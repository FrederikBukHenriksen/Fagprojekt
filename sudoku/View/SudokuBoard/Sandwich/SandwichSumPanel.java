package sudoku.View.SudokuBoard.Sandwich;

import javax.swing.*;
import java.awt.*;

public class SandwichSumPanel extends JPanel {
    public SandwichSumPanel(int[] sums, int axis) {
        if (axis == 0) {
            this.setLayout(new GridLayout(sums.length, 0));
        }
        if (axis == 1) {
            this.setLayout(new GridLayout(0, sums.length));
        }
        for (int i = 0; i < sums.length; i++) {
            this.add(new JLabel(String.valueOf(sums[i]), SwingConstants.CENTER)); // Place in center of its label
                                                                                  // container
        }
    }
}