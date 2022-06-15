package sudoku.Controller.Actionlisteners.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import sudoku.Controller.Controller;
import sudoku.View.ExceptionPopUp;

public class MenuBarMenuActionListener implements ActionListener {

    Controller sudokuController;

    public MenuBarMenuActionListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (sudokuController.model.solver.getUniqueness()) {
                for (int i = 0; i < sudokuController.model.getN() * sudokuController.model.getK(); i++) {
                    for (int j = 0; j < sudokuController.model.getN() * sudokuController.model.getK(); j++) {
                        if (sudokuController.view.sudokuBoard.getCellFromCoord(i, j).enabled) {
                            sudokuController.model.setSudokuCell(i, j,
                                    sudokuController.model.solver.getSolvedSudoku()[i][j]);
                        }
                    }
                }
                sudokuController.view.updateCellValues(sudokuController.model.getSudoku());
                sudokuController.updateColours();
            } else {
                sudokuController.model.solver.solve();
                if (sudokuController.model.solver.getSolvedSudoku()[0][0] != 0) {
                    for (int i = 0; i < sudokuController.model.getN() * sudokuController.model.getK(); i++) {
                        for (int j = 0; j < sudokuController.model.getN()
                                * sudokuController.model.getK(); j++) {
                            if (sudokuController.view.sudokuBoard.getCellFromCoord(i, j).enabled) {
                                sudokuController.model.setSudokuCell(i, j,
                                        sudokuController.model.solver.getSolvedSudoku()[i][j]);
                            }
                        }
                    }
                    sudokuController.updateColours();
                }
            }
        } catch (Exception exc) {
            new ExceptionPopUp(exc);
        }
    }
}
