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

public class SudokuControls extends JPanel {

    public MenuButton undo = new MenuButton("Undo");
    public MenuButton redo = new MenuButton("Redo");
    public MenuButton remove = new MenuButton("Remove");
    public MenuButton hint = new MenuButton("Hint");

    public SudokuControls() {
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.add(undo);
        this.add(redo);
        this.add(remove);
        this.add(hint);

        // this.setMaximumSize(new Dimension(1500, 200));
    }

}
