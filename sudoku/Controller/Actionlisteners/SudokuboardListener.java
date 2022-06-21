package sudoku.Controller.Actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sudoku.Controller.Controller;
import sudoku.View.SudokuBoard.Cell;

// ACTIONLISTENER FOR SUDOKUBOARDET.
public class SudokuboardListener implements ActionListener {

    private final Controller sudokuController;

    /*
     * Author: All of us
     * Function: Constructs an ActionListener for the Sudoku board, listens for when
     * a cell is selected
     * Inputs: Controller
     * Outputs: None
     */
    public SudokuboardListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    /*
     * Author: All of us
     * Function: When a cell is selected, the cell is set as the only selected cell,
     * and the visuals of the board are updated
     * Inputs: ActionEvent e, used to determine which cell was pressed
     * Outputs: None
     */
    public void actionPerformed(ActionEvent e) {
        Cell pressed = (Cell) e.getSource(); // Grabs the button pressed
        this.sudokuController.sudokuControls.selectOnlyThisButton(pressed);
        this.sudokuController.updateColours();
    }
}