package sudoku.Controller.Actionlisteners.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import sudoku.Controller.Controller;

public class MenuBarSolveListener implements ActionListener {

    private Controller sudokuController;

    /*
     * Author: Frederik, Rasmus
     * Function: Constructs an ActionListener for the "Solve Sudoku" menu item
     * Inputs: The Controller, used to call solveSudoku below
     * Outputs: None
     */
    public MenuBarSolveListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    /*
     * Author: Frederik, Rasmus
     * Function: Sets the action performed when the item is pressed to solveSudoku()
     * Inputs: The ActionEvent e
     * Outputs: None
     */
    public void actionPerformed(ActionEvent e) {
        sudokuController.solveSudoku();
    }

}
