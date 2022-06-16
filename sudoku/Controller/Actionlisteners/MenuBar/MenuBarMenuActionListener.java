package sudoku.Controller.Actionlisteners.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import sudoku.Controller.Controller;
import sudoku.View.ExceptionPopUp;

public class MenuBarMenuActionListener implements ActionListener {

    Controller sudokuController;

    public MenuBarMenuActionListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        sudokuController.solveSudoku();
    }

}
