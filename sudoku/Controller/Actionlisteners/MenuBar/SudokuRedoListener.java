package sudoku.Controller.Actionlisteners.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sudoku.Controller.Controller;

// Code for redo-button
public class SudokuRedoListener implements ActionListener {
    /**
     *
     */
    private Controller sudokuController;

    /*
     * Author: Rasmus
     * Function: Constructs an ActionListener for the "Redo" menu item
     * Inputs: The Controller, used to call redoMove below
     * Outputs: None
     */
    public SudokuRedoListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    /*
     * Author: Rasmus
     * Function: Tells the ActionListener to call the method redoMove
     * Inputs: The ActionEvent e
     * Outputs: None
     */
    public void actionPerformed(ActionEvent e) {
        this.sudokuController.redoMove();
    }
}