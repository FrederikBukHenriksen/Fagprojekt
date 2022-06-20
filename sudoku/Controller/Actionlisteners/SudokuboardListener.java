package sudoku.Controller.Actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sudoku.Controller.Controller;
import sudoku.View.SudokuBoard.Cell;

// ACTIONLISTENER FOR SUDOKUBOARDET.
public class SudokuboardListener implements ActionListener {

    private Controller sudokuController;

    public SudokuboardListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        Cell pressed = (Cell) e.getSource(); // Grabs the button pressed
        this.sudokuController.sudokuControls.selectOnlyThisButton(pressed);
        this.sudokuController.updateColours();
    }
}