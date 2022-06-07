package sudoku;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import sudoku.SudokuBoard.Cell;

public class MenuBarZoomActionListener implements ActionListener {

    SudokuController sudokuController;
    int change = 5;

    public MenuBarZoomActionListener(SudokuController sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        int sizeChange = 0;
        switch (((AbstractButton) e.getSource()).getActionCommand().toLowerCase()) {
            case "zoom in":
                sizeChange = change;
                break;
            case "zoom out":
                sizeChange = -change;
                break;
            default:
                break;
        }
        for (Cell cell : sudokuController.view.sudokuBoard.getCellsLinear()) {
            cell.adjustSize(5);
        }
        sudokuController.view.pack();
    }

}
