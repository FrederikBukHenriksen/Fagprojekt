package sudoku.Controller.Actionlisteners.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sudoku.Controller.Controller;

// Code for undo-button
public class SudokuUndoListener implements ActionListener {
    /**
     *
     */
    private Controller sudokuController;

    /*
     * Author: Rasmus
     * Function: Constructs an ActionListener for the "Undo" menu item
     * Inputs: Controller, used for calling the undoMove method below
     * Outputs: None
     */
    public SudokuUndoListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    /*
     * Author: Rasmus
     * Function: Calls the method undoMove when the menu item is pressed
     * Inputs: The ActionEvent e
     * Outputs: None
     */
    public void actionPerformed(ActionEvent e) {
        this.sudokuController.undoMove();
    }
}