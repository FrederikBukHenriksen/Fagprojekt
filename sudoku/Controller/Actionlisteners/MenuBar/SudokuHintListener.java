package sudoku.Controller.Actionlisteners.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sudoku.Controller.Controller;

public class SudokuHintListener implements ActionListener {

    private Controller sudokuController;

    /*
     * Author: Rasmus
     * Function: Constructs an ActionListener for the "Hint" menu item
     * Inputs: Controller, used for setting a flag below
     * Outputs: None
     */
    public SudokuHintListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    /*
     * Author: Rasmus
     * Function: Defines the ActionListener to set the flag hintPressed to true on
     * press
     * Inputs: The ActionEvent e
     * Outputs: None
     */
    public void actionPerformed(ActionEvent e) {
        // Sets flag for running hint-method in
        sudokuController.hintPressed = true;
    }
}