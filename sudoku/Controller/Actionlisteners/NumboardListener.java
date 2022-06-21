package sudoku.Controller.Actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import sudoku.Controller.Controller;
import sudoku.View.SudokuBoard.Cell;
import sudoku.Model.Stack;

public class NumboardListener implements ActionListener {
    /**
     *
     */
    private Controller sudokuController;

    public NumboardListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        // Grabs the button pressed
        JButton pressedNumboard = (JButton) e.getSource();
        // Find the placement of the pressed board button
        try {
            Cell pressedSudokuboard = this.sudokuController.sudokuControls.getCellSelected();
            if (pressedSudokuboard.getEnabled()) {
                this.sudokuController.model.stack.clearRedoStack();
                String cellNew = "";
                String cellCurrent = pressedSudokuboard.getText();
                if (!cellCurrent.equals("")) { // Hvis der står noget i cellen
                    cellNew = cellCurrent;
                }
                cellNew = cellNew + pressedNumboard.getText();

                int maxNumber = this.sudokuController.model.getN() * this.sudokuController.model.getK(); // TODO: Er
                                                                                                         // dette det
                                                                                                         // maksimale
                                                                                                         // nummer pba.
                                                                                                         // n og
                // k?
                if (Integer.valueOf(cellNew) > maxNumber) {
                    cellNew = pressedNumboard.getText();
                }

                // Update sudoku cell
                int[] coordinate = this.sudokuController.sudokuControls.getCellCoordinate(pressedSudokuboard);
                int tempVal = this.sudokuController.model.getSudoku()[coordinate[0]][coordinate[1]];
                this.sudokuController.model.setSudokuCell(coordinate[0], coordinate[1], Integer.valueOf(cellNew));

                // update sudoku Stack
                this.sudokuController.model.stack.pushStack(
                        this.sudokuController.model.stack.createStackObj(coordinate[0], coordinate[1], tempVal,
                                Integer.valueOf(cellNew)));

                // Update the board visuals
                this.sudokuController.sudokuControls.updateCellValues(this.sudokuController.model.getSudoku());

                // TODO:NEDENSTÅENE BRUGES KUN TIL DE-BUG.
                // view.updateFrameTitle(model.checkValidity(model.getSudoku(), false),
                // model.isFilled());

                pressedSudokuboard.requestFocus();
                this.sudokuController.updateColours();
            }
        } catch (Exception exc) {
            // System.out.println(exc.getMessage());
        }

    }
}