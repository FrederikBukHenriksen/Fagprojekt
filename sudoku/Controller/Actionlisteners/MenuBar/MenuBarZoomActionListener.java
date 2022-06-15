package sudoku.Controller.Actionlisteners.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import sudoku.Controller.Controller;

public class MenuBarZoomActionListener implements ActionListener {

    Controller sudokuController;

    public MenuBarZoomActionListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        switch (((AbstractButton) e.getSource()).getActionCommand().toLowerCase()) {
            case "zoom in":
                sudokuController.zoomIn();
                break;
            case "zoom out":
                sudokuController.zoomOut();
                break;
            default:
                break;
        }
    }
}
