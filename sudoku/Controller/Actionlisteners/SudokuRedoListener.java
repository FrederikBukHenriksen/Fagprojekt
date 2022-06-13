package sudoku.Controller.Actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sudoku.Controller.Controller;

// Code for redo-button
public class SudokuRedoListener implements ActionListener {
    /**
     *
     */
    private final Controller sudokuController;

    /**
     * @param sudokuController
     */
    public SudokuRedoListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        this.sudokuController.redoMove();
    }
}