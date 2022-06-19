package sudoku.Controller.Actionlisteners.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sudoku.Controller.Controller;

public class SudokuRemoveListener implements ActionListener {
    /**
     *
     */
    private final Controller sudokuController;

    /**
     * @param sudokuController
     */
    public SudokuRemoveListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        // System.out.println("Remove"); //Prints "Remove" for DEBUG
        try {
            if (this.sudokuController.sudokuControls.getButtonSelected().enabled) {
                int[] coordinate = this.sudokuController.sudokuControls
                        .getCellCoordinate(this.sudokuController.sudokuControls.getButtonSelected());
                if (!(this.sudokuController.model.sudoku[coordinate[0]][coordinate[1]] == 0)) {
                    this.sudokuController.model.stack.clearRedoStack();
                    int tempVal = this.sudokuController.model.getSudoku()[coordinate[0]][coordinate[1]];
                    this.sudokuController.model.setSudokuCell(coordinate[0], coordinate[1], 0);
                    this.sudokuController.model.stack.pushStack(
                            this.sudokuController.model.stack.createStackObj(coordinate[0], coordinate[1], tempVal, 0));
                    this.sudokuController.sudokuControls.updateCellValues(this.sudokuController.model.getSudoku());
                    // view.updateFrameTitle(model.checkValidity(model.getSudoku(), false),
                    // model.isFilled());
                    this.sudokuController.updateColours();
                }
            }
        } catch (Exception exc) {
            // System.out.println(exc.getMessage());
        }
        this.sudokuController.updateColours();
    }
}