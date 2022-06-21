package sudoku.Controller.Actionlisteners.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sudoku.Controller.Controller;

public class SudokuRemoveListener implements ActionListener {
    /**
     *
     */
    private Controller controller;

    /**
     * @param sudokuController
     */
    public SudokuRemoveListener(Controller sudokuController) {
        this.controller = sudokuController;
    }

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