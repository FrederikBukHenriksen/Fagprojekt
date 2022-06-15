package sudoku.Controller.MarkCells;

import sudoku.Controller.Controller;
import sudoku.Controller.Exceptions.NoCellSelected;
import sudoku.View.ExceptionPopUp;
import sudoku.View.SudokuBoard.Cell;

public class ClassicSudokuMarkCells implements MarkCellsInterface {

    Controller controller;

    public void markCells(Controller controller) {
        this.controller = controller;
        try {
            Cell cell = controller.view.sudokuBoard.getButtonSelected();
            getPeersHorisontal(cell);
            getPeersVertical(cell);
            getPeersSquare(cell);
        } catch (NoCellSelected e) {
            new ExceptionPopUp(e);
        }
    }

    @Override
    public void markCells() {

    }

    protected void getPeersSquare(Cell cell) {

    }

    protected void getPeersVertical(Cell cell) {

    }

    protected void getPeersHorisontal(Cell cell) {

    }


}
