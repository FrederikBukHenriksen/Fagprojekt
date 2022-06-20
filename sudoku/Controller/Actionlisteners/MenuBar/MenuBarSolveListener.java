package sudoku.Controller.Actionlisteners.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import sudoku.Controller.Controller;

public class MenuBarSolveListener implements ActionListener {

    private Controller sudokuController;

    public MenuBarSolveListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        sudokuController.solveSudoku();
    }

}
