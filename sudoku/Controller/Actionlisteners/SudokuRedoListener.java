package sudoku.Controller.Actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sudoku.SudokuController;

// Code for redo-button
public class SudokuRedoListener implements ActionListener {
    /**
     *
     */
    private final SudokuController sudokuController;

    /**
     * @param sudokuController
     */
    public SudokuRedoListener(SudokuController sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        this.sudokuController.redoMove();
    }
}