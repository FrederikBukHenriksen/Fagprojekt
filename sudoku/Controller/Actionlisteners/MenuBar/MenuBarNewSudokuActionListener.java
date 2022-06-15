package sudoku.Controller.Actionlisteners.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import sudoku.Controller.Controller;
import sudoku.View.SudokuBoard.*;

public class MenuBarNewSudokuActionListener implements ActionListener {

    Controller sudokuController;

    public MenuBarNewSudokuActionListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        sudokuController.view.dispose();
        sudokuController.setOkPressed();
    }

}
