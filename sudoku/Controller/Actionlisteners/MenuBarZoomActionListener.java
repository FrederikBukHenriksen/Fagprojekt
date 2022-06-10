package sudoku.Controller.Actionlisteners;

import sudoku.Validity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import sudoku.SudokuController;
import sudoku.View.SudokuBoard.*;

public class MenuBarZoomActionListener implements ActionListener {

    SudokuController sudokuController;
    int change = 5;

    public MenuBarZoomActionListener(SudokuController sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        int sizeChange = 0;
        switch (((AbstractButton) e.getSource()).getActionCommand()) {
            case "Zoom in":
                sizeChange = change;
                break;
            case "Zoom out":
                sizeChange = -change;

                Validity lolcat = new Validity(sudokuController);
                System.out.println(lolcat.checkValidity(sudokuController.model.getSudoku()));
                lolcat.findLinearConflicts(sudokuController.model.sudoku, sudokuController.model.n,
                        sudokuController.model.k);
                System.out.println(lolcat.failedCoordsPoint);
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
