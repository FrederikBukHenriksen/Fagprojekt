package sudoku.Controller;

import sudoku.View.SudokuBoard.NumpadButton;
import sudoku.View.SudokuBoard.ZoomObjectInterface;

import java.util.ArrayList;

import sudoku.View.SudokuBoard.Cell;

public class Zoom {

    private Controller controller;

    private int zoomStatus = 28;
    private int zoomSizeIncrementChange = 5; // Amount of zoom.

    private ZoomObjectInterface[][] objectArray;

    public Zoom(ZoomObjectInterface[][] objectList, Controller controller) {
        this.objectArray = objectList;
        this.controller = controller;
        zoom();
    }

    // public Zoom(Controller controller) {
    // this.controller = controller;
    // zoom(zoomStatus);

    // }

    public void zoomIn() {
        this.zoomStatus = this.zoomStatus + zoomSizeIncrementChange;
        zoom();
    }

    public void zoomOut() {
        this.zoomStatus = this.zoomStatus - zoomSizeIncrementChange;
        zoom();
    }

    private void zoom() {
        for (ZoomObjectInterface[] array : objectArray) {
            for (ZoomObjectInterface object : array) {
                object.setSize(zoomStatus);
            }
        }
        controller.view.pack();
    }
}

// private void zoom(int cellSize) {

// for (Cell cell : controller.sudokuControls.getCells1d()) {
// cell.setSize(cellSize);
// }

// for (NumpadButton numpadButton : controller.view.sudokuNumpad.numpadButtons)
// {
// numpadButton.setSize(cellSize);
// }
// }

// }
