package sudoku.Controller;

import sudoku.View.SudokuBoard.NumpadButton;
import sudoku.View.SudokuBoard.Cell;

public class Zoom {

    private Controller controller;

    private int cellSize = 28;
    private int zoomSizeIncrementChange = 5; // Amount of zoom.

    public Zoom(Controller controller) {
        this.controller = controller;
        zoom(cellSize);

    }

    public void zoomIn() {
        this.cellSize = this.cellSize + zoomSizeIncrementChange;
        zoom(cellSize);
    }

    public void zoomOut() {
        this.cellSize = this.cellSize - zoomSizeIncrementChange;
        zoom(cellSize);
    }

    private void zoom(int cellSize) {

        for (Cell cell : controller.sudokuControls.getCells1d()) {
            cell.setSize(cellSize);
        }

        for (NumpadButton numpadButton : controller.view.sudokuNumpad.numpadButtons) {
            numpadButton.setSize(cellSize);
        }
        controller.view.pack();
    }

}
