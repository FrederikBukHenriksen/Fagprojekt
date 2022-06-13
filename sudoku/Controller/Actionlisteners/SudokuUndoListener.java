package sudoku.Controller.Actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sudoku.Controller.Controller;

// Code for undo-button
public class SudokuUndoListener implements ActionListener {
    /**
     *
     */
    private final Controller sudokuController;

    /**
     * @param sudokuController
     */
    public SudokuUndoListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        this.sudokuController.undoMove();
    }
}