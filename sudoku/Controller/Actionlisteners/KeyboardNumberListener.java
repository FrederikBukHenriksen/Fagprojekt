package sudoku.Controller.Actionlisteners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import sudoku.Controller.Controller;
import sudoku.View.SudokuBoard.Cell;

// KEY EVENT FOR ALLE JTOGGLEBUTTONS PÅ BOARDET.
public class KeyboardNumberListener extends KeyAdapter {

    private Controller sudokuController;

    public KeyboardNumberListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        try {
            Cell pressedSudokuboard = this.sudokuController.sudokuControls.getButtonSelected();
            if (pressedSudokuboard.getEnabled()) { // Only the available buttons
                // Variables for the new cell-content and the button pressed
                String cellNew = "";
                String keyPressed = "";
                String cellCurrent = pressedSudokuboard.getText();
                // If the cell isn't empty, we attempt to concatinate the new entry on the old
                // one
                if (!cellCurrent.equals("")) {
                    cellNew = cellCurrent;
                }
                // Gets the digit entered
                if (keyCode == KeyEvent.VK_1 || keyCode == KeyEvent.VK_NUMPAD1) {
                    keyPressed = "1";
                } else if (keyCode == KeyEvent.VK_2 || keyCode == KeyEvent.VK_NUMPAD2) {
                    keyPressed = "2";
                } else if (keyCode == KeyEvent.VK_3 || keyCode == KeyEvent.VK_NUMPAD3) {
                    keyPressed = "3";
                } else if (keyCode == KeyEvent.VK_4 || keyCode == KeyEvent.VK_NUMPAD4) {
                    keyPressed = "4";
                } else if (keyCode == KeyEvent.VK_5 || keyCode == KeyEvent.VK_NUMPAD5) {
                    keyPressed = "5";
                } else if (keyCode == KeyEvent.VK_6 || keyCode == KeyEvent.VK_NUMPAD6) {
                    keyPressed = "6";
                } else if (keyCode == KeyEvent.VK_7 || keyCode == KeyEvent.VK_NUMPAD7) {
                    keyPressed = "7";
                } else if (keyCode == KeyEvent.VK_8 || keyCode == KeyEvent.VK_NUMPAD8) {
                    keyPressed = "8";
                } else if (keyCode == KeyEvent.VK_9 || keyCode == KeyEvent.VK_NUMPAD9) {
                    keyPressed = "9";
                } else if (keyCode == KeyEvent.VK_0 || keyCode == KeyEvent.VK_NUMPAD0) {
                    keyPressed = "0";
                    // Backspace deletes 1 digit of the number in the cell
                } else if (keyCode == KeyEvent.VK_BACK_SPACE || keyCode == KeyEvent.VK_DELETE) {
                    if (cellNew.length() > 1) {
                        cellNew = cellNew.substring(0, cellNew.length() - 1);
                    } else if (cellNew.length() == 1) {
                        cellNew = "0";
                    }
                } else {
                    return;
                }
                // Check if the concatinated number is larger than allowed, if so, just enter
                // the new number
                int maxNumber = this.sudokuController.model.getN() * this.sudokuController.model.getK(); // og k?
                if (!(cellNew + keyPressed).equals("")) {
                    if (Integer.valueOf(cellNew + keyPressed) > maxNumber) {
                        cellNew = keyPressed;
                    } else {
                        cellNew = cellNew + keyPressed;
                    }
                }
                if (!cellNew.equals("")) {
                    this.sudokuController.model.stack.clearRedoStack();
                    // Update board both in data and visually
                    int[] coordinate = this.sudokuController.sudokuControls.getCellCoordinate(pressedSudokuboard);
                    int tempVal = this.sudokuController.model.getSudoku()[coordinate[0]][coordinate[1]];
                    this.sudokuController.model.setSudokuCell(coordinate[0], coordinate[1],
                            Integer.valueOf(cellNew));
                    this.sudokuController.model.stack.pushStack(
                            this.sudokuController.model.stack.createStackObj(coordinate[0], coordinate[1], tempVal,
                                    Integer.valueOf(cellNew)));
                    this.sudokuController.sudokuControls.updateCellValues(this.sudokuController.model.getSudoku());
                    this.sudokuController.updateColours();
                }
            }
        } catch (Exception exc) {
            // System.out.println(exc.getMessage());
        }
    }
}