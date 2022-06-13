package sudoku.Controller.Actionlisteners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import sudoku.SudokuController;
import sudoku.View.SudokuBoard.Cell;

// KEY EVENT FOR ALLE JTOGGLEBUTTONS PÅ BOARDET.
public class KeyboardSudokuListener extends KeyAdapter {

    private final SudokuController sudokuController;

    public KeyboardSudokuListener(SudokuController sudokuController) {
        this.sudokuController = sudokuController;
    }

    boolean ctrlPressed = false;
    boolean zPressed = false;
    boolean yPressed = false;
    boolean hPressed = false;
    boolean plusPressed = false;
    boolean minusPressed = false;

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_Z) {
            zPressed = true;
            yPressed = false;
            hPressed = false;
            plusPressed = false;
            minusPressed = false;
            if (ctrlPressed) {
                this.sudokuController.undoMove();
            }
        } else if (keyCode == KeyEvent.VK_Y) {
            yPressed = true;
            zPressed = false;
            hPressed = false;
            plusPressed = false;
            minusPressed = false;
            if (ctrlPressed) {
                this.sudokuController.redoMove();
            }
        } else if (keyCode == KeyEvent.VK_CONTROL) {
            ctrlPressed = true;
            if (zPressed) {
                this.sudokuController.undoMove();
            } else if (yPressed) {
                this.sudokuController.redoMove();
            } else if (hPressed) {
                this.sudokuController.getHint();
            } else if (plusPressed) {
                this.sudokuController.zoom(5);
            } else if(minusPressed) {
                this.sudokuController.zoom(-5);
            }

        } else if (keyCode == KeyEvent.VK_DOWN) {
            int[] tempCoords = { -1, 0 };
            try {
                tempCoords = this.sudokuController.view.sudokuBoard
                        .getCellCoordinate(this.sudokuController.view.sudokuBoard.getButtonSelected());
            } catch (Exception h) {
            }
            Cell pressed = null;
            if (tempCoords[0] != (this.sudokuController.model.getN() * this.sudokuController.model.getK()) - 1) {
                pressed = this.sudokuController.view.sudokuBoard.getCellFromCoord(tempCoords[0] + 1, tempCoords[1]); // Grabs
                                                                                                                     // the
                                                                                                         // button
                                                                                                         // pressed
            } else {
                pressed = this.sudokuController.view.sudokuBoard.getCellFromCoord(0, tempCoords[1]);
            }
            pressed.setSelected(true);
            this.sudokuController.view.sudokuBoard.selectOnlyThisButton(pressed);
            this.sudokuController.updateColours();

        } else if (keyCode == KeyEvent.VK_UP) {
            int[] tempCoords = { 1, 0 };
            try {
                tempCoords = this.sudokuController.view.sudokuBoard
                        .getCellCoordinate(this.sudokuController.view.sudokuBoard.getButtonSelected());
            } catch (Exception h) {
            }
            Cell pressed = null;
            if (tempCoords[0] != 0) {
                pressed = this.sudokuController.view.sudokuBoard.getCellFromCoord(tempCoords[0] - 1, tempCoords[1]); // Grabs
                                                                                                                     // the
                                                                                                         // button
                                                                                                         // pressed
            } else {
                pressed = this.sudokuController.view.sudokuBoard.getCellFromCoord(
                        this.sudokuController.model.getN() * this.sudokuController.model.getK() - 1, tempCoords[1]);
            }
            pressed.setSelected(true);
            this.sudokuController.view.sudokuBoard.selectOnlyThisButton(pressed);
            this.sudokuController.updateColours();

        } else if (keyCode == KeyEvent.VK_LEFT) {
            int[] tempCoords = { 0, 1 };
            try {
                tempCoords = this.sudokuController.view.sudokuBoard
                        .getCellCoordinate(this.sudokuController.view.sudokuBoard.getButtonSelected());
            } catch (Exception h) {
            }
            Cell pressed = null;
            if (tempCoords[1] != 0) {
                pressed = this.sudokuController.view.sudokuBoard.getCellFromCoord(tempCoords[0], tempCoords[1] - 1); // Grabs
                                                                                                                     // the
                                                                                                         // button
                                                                                                         // pressed
            } else {
                pressed = this.sudokuController.view.sudokuBoard.getCellFromCoord(tempCoords[0],
                        this.sudokuController.model.getN() * this.sudokuController.model.getK() - 1);
            }
            pressed.setSelected(true);
            this.sudokuController.view.sudokuBoard.selectOnlyThisButton(pressed);
            this.sudokuController.updateColours();

        } else if (keyCode == KeyEvent.VK_RIGHT) {
            int[] tempCoords = { 0, -1 };
            try {
                tempCoords = this.sudokuController.view.sudokuBoard
                        .getCellCoordinate(this.sudokuController.view.sudokuBoard.getButtonSelected());
            } catch (Exception h) {
            }
            Cell pressed = null;
            if (tempCoords[1] != this.sudokuController.model.getN() * this.sudokuController.model.getK() - 1) {
                pressed = this.sudokuController.view.sudokuBoard.getCellFromCoord(tempCoords[0], tempCoords[1] + 1); // Grabs
                                                                                                                     // the
                                                                                                         // button
                                                                                                         // pressed
            } else {
                pressed = this.sudokuController.view.sudokuBoard.getCellFromCoord(tempCoords[0], 0);
            }
            pressed.setSelected(true);
            this.sudokuController.view.sudokuBoard.selectOnlyThisButton(pressed);
            this.sudokuController.updateColours();

        } else if (keyCode == KeyEvent.VK_H) {
            yPressed = false;
            zPressed = false;
            hPressed = true;
            plusPressed = false;
            minusPressed = false;
            if (ctrlPressed) {
                this.sudokuController.getHint();
            }
        } else if (keyCode == KeyEvent.VK_PLUS) {
            yPressed = false;
            zPressed = false;
            hPressed = false;
            plusPressed = true;
            minusPressed = false;
            if (ctrlPressed) {
                this.sudokuController.zoom(5);
            }
        } else if (keyCode == KeyEvent.VK_MINUS) {
            yPressed = false;
            zPressed = false;
            hPressed = false;
            plusPressed = false;
            minusPressed = true;
            if (ctrlPressed) {
                this.sudokuController.zoom(-5);
            }
        } 
        else {
            try {
                Cell pressedSudokuboard = this.sudokuController.view.sudokuBoard.getButtonSelected();
                if (pressedSudokuboard.enabled) { // Only the available buttons

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
                    int maxNumber = this.sudokuController.model.getN() * this.sudokuController.model.getK(); // TODO: Er
                                                                                                             // dette
                                                                                                             // det
                                                                                                             // maksimale
                                                                                                             // nummer
                                                                                                             // pba. n
                                                                                                             // og k?
                    if (!(cellNew + keyPressed).equals("")) {
                        if (Integer.valueOf(cellNew + keyPressed) > maxNumber) {
                            cellNew = keyPressed;
                        } else {
                            cellNew = cellNew + keyPressed;
                        }
                    }

                    if (!cellNew.equals("")) {
                        this.sudokuController.model.clearRedoStack();
                        // Update board both in data and visually
                        int[] coordinate = this.sudokuController.view.sudokuBoard.getCellCoordinate(pressedSudokuboard);
                        int tempVal = this.sudokuController.model.getSudoku()[coordinate[0]][coordinate[1]];
                        this.sudokuController.model.setSudokuCell(coordinate[0], coordinate[1],
                                Integer.valueOf(cellNew));
                        this.sudokuController.model.pushStack2(
                                this.sudokuController.model.createStackObj(coordinate[0], coordinate[1], tempVal,
                                        Integer.valueOf(cellNew)));
                        this.sudokuController.view.updateCellValues(this.sudokuController.model.getSudoku());
                        this.sudokuController.updateColours();
                    }
                }
            } catch (Exception exc) {
                // System.out.println(exc.getMessage());
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        try {
            // Gets the digit entered
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_Z) {
                zPressed = false;
            } else if (keyCode == KeyEvent.VK_CONTROL) {
                ctrlPressed = false;
            } else if (keyCode == KeyEvent.VK_Y) {
                yPressed = false;
            } else if (keyCode == KeyEvent.VK_H) {
                hPressed = false;
            } else if (keyCode == KeyEvent.VK_PLUS){
                plusPressed = false;
            } else if (keyCode == KeyEvent.VK_MINUS){
                minusPressed = false;
            } else {
                return;
            }

        } catch (Exception exc) {
            // System.out.println(exc.getMessage());
        }
    }
}