package sudoku.Controller.Actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sudoku.SudokuController;
import sudoku.View.SudokuBoard.Cell;

// ACTIONLISTENER FOR SUDOKUBOARDET.
public class SudokuboardListener implements ActionListener {

    private final SudokuController sudokuController;

    public SudokuboardListener(SudokuController sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        Cell pressed = (Cell) e.getSource(); // Grabs the button pressed
        this.sudokuController.view.onlySelectThePressed(pressed);
        this.sudokuController.updateColours();
    }
}