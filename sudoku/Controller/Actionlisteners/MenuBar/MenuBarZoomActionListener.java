package sudoku.Controller.Actionlisteners.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import sudoku.Controller.Controller;
import sudoku.View.SudokuBoard.*;

public class MenuBarZoomActionListener implements ActionListener {

    Controller sudokuController;
    int change = 5;

    public MenuBarZoomActionListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        int sizeChange = 0;
        switch (((AbstractButton) e.getSource()).getActionCommand().toLowerCase()) {
            case "zoom in":
                sizeChange = change;
                break;
            case "zoom out":
                sizeChange = -change;
                break;
            default:
                break;
        }
        sudokuController.zoom(sizeChange);
    }
}
