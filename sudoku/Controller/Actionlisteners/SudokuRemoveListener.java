package sudoku.Controller.Actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sudoku.SudokuController;

public class SudokuRemoveListener implements ActionListener {
    /**
     *
     */
    private final SudokuController sudokuController;

    /**
     * @param sudokuController
     */
    public SudokuRemoveListener(SudokuController sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        // System.out.println("Remove"); //Prints "Remove" for DEBUG
        try {
            if (this.sudokuController.view.getButtonSelected().enabled) {
                int[] coordinate = this.sudokuController.view
                        .getCellCoordinate(this.sudokuController.view.getButtonSelected());
                if (!(this.sudokuController.model.getSudoku()[coordinate[0]][coordinate[1]] == 0)) {
                    this.sudokuController.model.clearRedoStack();
                    int tempVal = this.sudokuController.model.getSudoku()[coordinate[0]][coordinate[1]];
                    this.sudokuController.model.setSudokuCell(coordinate[0], coordinate[1], 0);
                    this.sudokuController.model.pushStack2(
                            this.sudokuController.model.createStackObj(coordinate[0], coordinate[1], tempVal, 0));
                    this.sudokuController.view.updateBoard(this.sudokuController.model.getSudoku());
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