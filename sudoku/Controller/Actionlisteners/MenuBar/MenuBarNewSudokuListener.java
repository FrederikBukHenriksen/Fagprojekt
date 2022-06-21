package sudoku.Controller.Actionlisteners.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import sudoku.Controller.Controller;
import sudoku.View.SudokuBoard.*;

public class MenuBarNewSudokuListener implements ActionListener {

    Controller sudokuController;

    /*
     * Author: Frederik, Rasmus
     * Function: Constructs an ActionListener for the "Open new Sudoku" menu item
     * Inputs: The Controller, used to call methods below
     * Outputs: None
     */
    public MenuBarNewSudokuListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    /*
     * Author: Rasmus
     * Function: Closes the window with view.dispose, and sets the flag okPressed to
     * true
     * Inputs: The ActionEvent e
     * Outputs: None
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        sudokuController.view.dispose();
        sudokuController.okPressed = true;
    }

}
