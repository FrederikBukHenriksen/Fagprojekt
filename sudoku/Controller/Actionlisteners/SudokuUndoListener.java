package sudoku.Controller.Actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sudoku.SudokuController;

// Code for undo-button
public class SudokuUndoListener implements ActionListener {
    /**
     *
     */
    private final SudokuController sudokuController;

    /**
     * @param sudokuController
     */
    public SudokuUndoListener(SudokuController sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        this.sudokuController.undoMove();
    }
}