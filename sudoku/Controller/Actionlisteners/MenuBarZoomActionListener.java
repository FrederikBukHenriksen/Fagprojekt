package sudoku.Controller.Actionlisteners;

import sudoku.View.Cell.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import sudoku.SudokuController;

public class MenuBarZoomActionListener implements ActionListener {

    SudokuController sudokuController;
    int change = 5;

    public MenuBarZoomActionListener(SudokuController sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        int sizeChange = 0;
        switch (((AbstractButton) e.getSource()).getActionCommand().toLowerCase()) {
            case "Zoom in":
                sizeChange = change;
                break;
            case "Zoom out":
                sizeChange = -change;
                break;
            default:
                break;
        }
        for (Cell cell : sudokuController.view.sudokuBoard.getCellsLinear()) {
            cell.adjustSize(sizeChange);
        }
        sudokuController.view.pack();
    }
}
