package sudoku.Controller.Actionlisteners.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import sudoku.Controller.Controller;

public class MenuBarZoomActionListener implements ActionListener {

    Controller sudokuController;

    /*
     * Author: Frederik, Rasmus
     * Function: Constructs an ActionListener for the "zoom in" and "zoom out" menu
     * items
     * Inputs: Controller
     * Outputs: None
     */
    public MenuBarZoomActionListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    /*
     * Author: Frederik, Rasmus
     * Function: Selects what to do depending on which of the two menu items that
     * were pressed
     * Inputs: The ActionEvent e, used to determine which button was pressed
     * Outputs: None
     */
    public void actionPerformed(ActionEvent e) {
        switch (((AbstractButton) e.getSource()).getActionCommand().toLowerCase()) {
            case "zoom in":
                sudokuController.zoom.zoomIn();
                break;
            case "zoom out":
                sudokuController.zoom.zoomOut();
                break;
            default:
                break;
        }
    }
}
