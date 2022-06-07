package sudoku.Controller.Actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sudoku.SudokuController;

public class SudokuHintListener implements ActionListener {
    /**
     *
     */
    private final SudokuController sudokuController;

    /**
     * @param sudokuController
     */
    public SudokuHintListener(SudokuController sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        sudokuController.getHint();
    }
}