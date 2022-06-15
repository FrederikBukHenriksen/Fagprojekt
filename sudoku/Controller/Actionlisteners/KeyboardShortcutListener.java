package sudoku.Controller.Actionlisteners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import sudoku.Controller.Controller;
import sudoku.View.SudokuBoard.Cell;

// KEY EVENT FOR ALLE JTOGGLEBUTTONS PÅ BOARDET.
public class KeyboardShortcutListener extends KeyAdapter {

    private final Controller sudokuController;

    public KeyboardShortcutListener(Controller sudokuController) {
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
                sudokuController.hintPressed = true;
            } else if (plusPressed) {
                this.sudokuController.zoomIn();
            } else if (minusPressed) {
                this.sudokuController.zoomOut();
            }

        } else if (keyCode == KeyEvent.VK_DOWN) {
            int[] tempCoords = { -1, 0 };
            try {
                tempCoords = this.sudokuController.sudokuControls
                        .getCellCoordinate(this.sudokuController.sudokuControls.getButtonSelected());
            } catch (Exception h) {
            }
            Cell pressed = null;
            if (tempCoords[0] != (this.sudokuController.model.getN() * this.sudokuController.model.getK()) - 1) {
                pressed = this.sudokuController.sudokuControls.getCellFromCoord(tempCoords[0] + 1, tempCoords[1]); // Grabs
                                                                                                                     // the
                // button
                // pressed
            } else {
                pressed = this.sudokuController.sudokuControls.getCellFromCoord(0, tempCoords[1]);
            }
            pressed.setSelected(true);
            this.sudokuController.sudokuControls.selectOnlyThisButton(pressed);
            this.sudokuController.updateColours();

        } else if (keyCode == KeyEvent.VK_UP) {
            int[] tempCoords = { 1, 0 };
            try {
                tempCoords = this.sudokuController.sudokuControls
                        .getCellCoordinate(this.sudokuController.sudokuControls.getButtonSelected());
            } catch (Exception h) {
            }
            Cell pressed = null;
            if (tempCoords[0] != 0) {
                pressed = this.sudokuController.sudokuControls.getCellFromCoord(tempCoords[0] - 1, tempCoords[1]); // Grabs
                                                                                                                     // the
                // button
                // pressed
            } else {
                pressed = this.sudokuController.sudokuControls.getCellFromCoord(
                        this.sudokuController.model.getN() * this.sudokuController.model.getK() - 1, tempCoords[1]);
            }
            pressed.setSelected(true);
            this.sudokuController.sudokuControls.selectOnlyThisButton(pressed);
            this.sudokuController.updateColours();

        } else if (keyCode == KeyEvent.VK_LEFT) {
            int[] tempCoords = { 0, 1 };
            try {
                tempCoords = this.sudokuController.sudokuControls
                        .getCellCoordinate(this.sudokuController.sudokuControls.getButtonSelected());
            } catch (Exception h) {
            }
            Cell pressed = null;
            if (tempCoords[1] != 0) {
                pressed = this.sudokuController.sudokuControls.getCellFromCoord(tempCoords[0], tempCoords[1] - 1); // Grabs
                                                                                                                     // the
                // button
                // pressed
            } else {
                pressed = this.sudokuController.sudokuControls.getCellFromCoord(tempCoords[0],
                        this.sudokuController.model.getN() * this.sudokuController.model.getK() - 1);
            }
            pressed.setSelected(true);
            this.sudokuController.sudokuControls.selectOnlyThisButton(pressed);
            this.sudokuController.updateColours();

        } else if (keyCode == KeyEvent.VK_RIGHT) {
            int[] tempCoords = { 0, -1 };
            try {
                tempCoords = this.sudokuController.sudokuControls
                        .getCellCoordinate(this.sudokuController.sudokuControls.getButtonSelected());
            } catch (Exception h) {
            }
            Cell pressed = null;
            if (tempCoords[1] != this.sudokuController.model.getN() * this.sudokuController.model.getK() - 1) {
                pressed = this.sudokuController.sudokuControls.getCellFromCoord(tempCoords[0], tempCoords[1] + 1); // Grabs
                                                                                                                     // the
                // button
                // pressed
            } else {
                pressed = this.sudokuController.sudokuControls.getCellFromCoord(tempCoords[0], 0);
            }
            pressed.setSelected(true);
            this.sudokuController.sudokuControls.selectOnlyThisButton(pressed);
            this.sudokuController.updateColours();

        } else if (keyCode == KeyEvent.VK_H) {
            yPressed = false;
            zPressed = false;
            hPressed = true;
            plusPressed = false;
            minusPressed = false;
            if (ctrlPressed) {
                sudokuController.hintPressed = true;
            }
        } else if (keyCode == KeyEvent.VK_PLUS) {
            yPressed = false;
            zPressed = false;
            hPressed = false;
            plusPressed = true;
            minusPressed = false;
            if (ctrlPressed) {
                this.sudokuController.zoomIn();
            }
        } else if (keyCode == KeyEvent.VK_MINUS) {
            yPressed = false;
            zPressed = false;
            hPressed = false;
            plusPressed = false;
            minusPressed = true;
            if (ctrlPressed) {
                this.sudokuController.zoomOut();
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
            } else if (keyCode == KeyEvent.VK_PLUS) {
                plusPressed = false;
            } else if (keyCode == KeyEvent.VK_MINUS) {
                minusPressed = false;
            } else {
                return;
            }

        } catch (Exception exc) {
            // System.out.println(exc.getMessage());
        }
    }
}