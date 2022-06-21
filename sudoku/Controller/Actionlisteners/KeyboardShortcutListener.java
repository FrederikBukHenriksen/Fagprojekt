package sudoku.Controller.Actionlisteners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import sudoku.Controller.Controller;
import sudoku.View.SudokuBoard.Cell;

// KEY EVENT FOR ALLE JTOGGLEBUTTONS PÅ BOARDET.
public class KeyboardShortcutListener extends KeyAdapter {

    private Controller sudokuController;

    /*
     * Author: Rasmus
     * Function: Constructs an ActionListener for the shortcuts activated with the
     * keyboard
     * Inputs: Controller
     * Outputs: None
     */
    public KeyboardShortcutListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    private boolean ctrlPressed = false;
    private boolean zPressed = false;
    private boolean yPressed = false;
    private boolean hPressed = false;
    private boolean plusPressed = false;
    private boolean minusPressed = false;

    /*
     * Author: Rasmus
     * Function: Contains the actions for all the different shortcut buttons
     * pressed, calls the relevant method from Controller
     * Inputs: The KeyEvent e, used to determine which buttons were pressed
     * Outputs: None
     */
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        // Z was pressed, undo if CTRL is also pressed
        if (keyCode == KeyEvent.VK_Z) {
            zPressed = true;
            yPressed = false;
            hPressed = false;
            plusPressed = false;
            minusPressed = false;
            if (ctrlPressed) {
                this.sudokuController.undoMove();
            }
        }
        // Y was pressed, redo if CTRL is also pressed
        else if (keyCode == KeyEvent.VK_Y) {
            yPressed = true;
            zPressed = false;
            hPressed = false;
            plusPressed = false;
            minusPressed = false;
            if (ctrlPressed) {
                this.sudokuController.redoMove();
            }
        }
        // CTRL was pressed, determine action based on other button pressed
        else if (keyCode == KeyEvent.VK_CONTROL) {
            ctrlPressed = true;
            if (zPressed) {
                this.sudokuController.undoMove();
            } else if (yPressed) {
                this.sudokuController.redoMove();
            } else if (hPressed) {
                sudokuController.hintPressed = true;
            } else if (plusPressed) {
                this.sudokuController.zoom.zoomIn();
            } else if (minusPressed) {
                this.sudokuController.zoom.zoomOut();
            }

        }
        // Arrow down was pressed, move active cell down
        else if (keyCode == KeyEvent.VK_DOWN) {
            int[] tempCoords = { -1, 0 };
            try {
                tempCoords = this.sudokuController.sudokuControls
                        .getCellCoordinate(this.sudokuController.sudokuControls.getCellSelected());
            } catch (Exception h) {
            }
            Cell pressed = null;
            if (tempCoords[0] != (this.sudokuController.model.getN() * this.sudokuController.model.getK()) - 1) {
                pressed = this.sudokuController.sudokuControls.getCellFromCoord(tempCoords[0] + 1, tempCoords[1]);
            } else {
                pressed = this.sudokuController.sudokuControls.getCellFromCoord(0, tempCoords[1]);
            }
            pressed.setSelected(true);
            this.sudokuController.sudokuControls.selectOnlyThisButton(pressed);
            this.sudokuController.updateColours();
        }
        // Arrow up was pressed, move active cell up
        else if (keyCode == KeyEvent.VK_UP) {
            int[] tempCoords = { 1, 0 };
            try {
                tempCoords = this.sudokuController.sudokuControls
                        .getCellCoordinate(this.sudokuController.sudokuControls.getCellSelected());
            } catch (Exception h) {
            }
            Cell pressed = null;
            if (tempCoords[0] != 0) {
                pressed = this.sudokuController.sudokuControls.getCellFromCoord(tempCoords[0] - 1, tempCoords[1]);
            } else {
                pressed = this.sudokuController.sudokuControls.getCellFromCoord(
                        this.sudokuController.model.getN() * this.sudokuController.model.getK() - 1, tempCoords[1]);
            }
            pressed.setSelected(true);
            this.sudokuController.sudokuControls.selectOnlyThisButton(pressed);
            this.sudokuController.updateColours();

        }
        // Arrow left was pressed, move active cell left
        else if (keyCode == KeyEvent.VK_LEFT) {
            int[] tempCoords = { 0, 1 };
            try {
                tempCoords = this.sudokuController.sudokuControls
                        .getCellCoordinate(this.sudokuController.sudokuControls.getCellSelected());
            } catch (Exception h) {
            }
            Cell pressed = null;
            if (tempCoords[1] != 0) {
                pressed = this.sudokuController.sudokuControls.getCellFromCoord(tempCoords[0], tempCoords[1] - 1);
            } else {
                pressed = this.sudokuController.sudokuControls.getCellFromCoord(tempCoords[0],
                        this.sudokuController.model.getN() * this.sudokuController.model.getK() - 1);
            }
            pressed.setSelected(true);
            this.sudokuController.sudokuControls.selectOnlyThisButton(pressed);
            this.sudokuController.updateColours();

        }
        // Arrow right was pressed, move active cell right
        else if (keyCode == KeyEvent.VK_RIGHT) {
            int[] tempCoords = { 0, -1 };
            try {
                tempCoords = this.sudokuController.sudokuControls
                        .getCellCoordinate(this.sudokuController.sudokuControls.getCellSelected());
            } catch (Exception h) {
            }
            Cell pressed = null;
            if (tempCoords[1] != this.sudokuController.model.getN() * this.sudokuController.model.getK() - 1) {
                pressed = this.sudokuController.sudokuControls.getCellFromCoord(tempCoords[0], tempCoords[1] + 1);
            } else {
                pressed = this.sudokuController.sudokuControls.getCellFromCoord(tempCoords[0], 0);
            }
            pressed.setSelected(true);
            this.sudokuController.sudokuControls.selectOnlyThisButton(pressed);
            this.sudokuController.updateColours();
        }

        // H was pressed, give hint if CTRL is also pressed
        else if (keyCode == KeyEvent.VK_H) {
            yPressed = false;
            zPressed = false;
            hPressed = true;
            plusPressed = false;
            minusPressed = false;
            if (ctrlPressed) {
                sudokuController.hintPressed = true;
            }
        }
        // + was pressed, zoom in if CTRL is also pressed
        else if (keyCode == KeyEvent.VK_PLUS) {
            yPressed = false;
            zPressed = false;
            hPressed = false;
            plusPressed = true;
            minusPressed = false;
            if (ctrlPressed) {
                this.sudokuController.zoom.zoomIn();
            }
        }
        // - was pressed, zoom out if CTRL is also pressed
        else if (keyCode == KeyEvent.VK_MINUS) {
            yPressed = false;
            zPressed = false;
            hPressed = false;
            plusPressed = false;
            minusPressed = true;
            if (ctrlPressed) {
                this.sudokuController.zoom.zoomOut();
            }
        }
    }

    // Resets the flags for each key when it is released
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