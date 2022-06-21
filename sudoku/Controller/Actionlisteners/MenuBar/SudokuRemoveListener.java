package sudoku.Controller.Actionlisteners.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sudoku.Controller.Controller;

public class SudokuRemoveListener implements ActionListener {
    /**
     *
     */
    private Controller controller;

    /*
     * Author: Rasmus, updates by Frederik
     * Function: Constructs an ActionListener for the "Remove" menu item
     * Inputs: The Controller, used for calling a couple of different methods below
     * Outputs: None
     */
    public SudokuRemoveListener(Controller sudokuController) {
        this.controller = sudokuController;
    }

    /*
     * Author: Rasmus
     * Function: This determines what to do when "Remove" is pressed. If the chosen
     * cell is not active (I.E. it contains a number from start, and cannot be
     * altered), nothing is done. Otherwise, if the cell is active, and isn't empty,
     * the content of the cell is removed, and the change is pushed to the stack.
     * Inputs: The ActionEvent e
     * Outputs: None
     */
    public void actionPerformed(ActionEvent e) {
        // System.out.println("Remove"); //Prints "Remove" for DEBUG
        try {
            if (this.controller.sudokuControls.getCellSelected().getEnabled()) {
                int[] coordinate = this.controller.sudokuControls
                        .getCellCoordinate(this.controller.sudokuControls.getCellSelected());
                if (!(this.controller.model.sudoku[coordinate[0]][coordinate[1]] == 0)) {
                    this.controller.model.stack.clearRedoStack();
                    int tempVal = this.controller.model.getSudoku()[coordinate[0]][coordinate[1]];
                    this.controller.model.setSudokuCell(coordinate[0], coordinate[1], 0);
                    this.controller.model.stack.pushStack(
                            this.controller.model.stack.createStackObj(coordinate[0], coordinate[1], tempVal, 0));
                    this.controller.sudokuControls.updateCellValues(this.controller.model.getSudoku());
                    // view.updateFrameTitle(model.checkValidity(model.getSudoku(), false),
                    // model.isFilled());
                    this.controller.updateColours();
                }
            }
        } catch (Exception exc) {
            // System.out.println(exc.getMessage());
        }
        this.controller.updateColours();
    }
}