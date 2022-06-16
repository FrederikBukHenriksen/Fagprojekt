package sudoku.Controller.Actionlisteners.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sudoku.Controller.Controller;

public class SudokuHintListener implements ActionListener {
    /**
     *
     */
    private Controller sudokuController;

    /**
     * @param sudokuController
     */
    public SudokuHintListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        sudokuController.getHint();
        sudokuController.hintPressed = true;
    }
}