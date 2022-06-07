package sudoku.View.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sudoku.SudokuController;

public class MenuBarActionListener implements ActionListener {

    SudokuController sudokuController;

    MenuBarActionListener(SudokuController sudokuController) {
        this.sudokuController = sudokuController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

}
