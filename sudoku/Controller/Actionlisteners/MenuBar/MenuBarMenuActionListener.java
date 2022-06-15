package sudoku.Controller.Actionlisteners.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

import javax.swing.AbstractButton;

import sudoku.Controller.Controller;

public class MenuBarMenuActionListener implements ActionListener {

    Controller sudokuController;

    public MenuBarMenuActionListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        switch (((AbstractButton) e.getSource()).getActionCommand().toLowerCase()) {
            case "solve sudoku":
                if (sudokuController.model.getSandwich()) {
                    if (sudokuController.model.backtrack.getUniqueness()) {
                        for (int i = 0; i < sudokuController.model.getN() * sudokuController.model.getK(); i++) {
                            for (int j = 0; j < sudokuController.model.getN() * sudokuController.model.getK(); j++) {
                                if (sudokuController.view.sudokuBoard.getCellFromCoord(i, j).enabled) {
                                    sudokuController.model.setSudokuCell(i, j,
                                            sudokuController.model.backtrack.getSolvedSudoku()[i][j]);
                                }
                            }
                        }
                        sudokuController.view.updateCellValues(sudokuController.model.getSudoku());
                        sudokuController.updateColours();
                    }
                } else {
                    if (sudokuController.model.crooks.getUniqueness()) {
                        for (int i = 0; i < sudokuController.model.getN() * sudokuController.model.getK(); i++) {
                            for (int j = 0; j < sudokuController.model.getN() * sudokuController.model.getK(); j++) {
                                if (sudokuController.view.sudokuBoard.getCellFromCoord(i, j).enabled) {
                                    sudokuController.model.setSudokuCell(i, j,
                                            sudokuController.model.crooks.getSolvedSudoku()[i][j]);
                                }
                            }
                        }
                        sudokuController.view.updateCellValues(sudokuController.model.getSudoku());
                        sudokuController.updateColours();
                    } else {
                        sudokuController.model.crooks.solver();
                        if (sudokuController.model.crooks.getSolvedSudoku()[0][0] != 0) {
                            for (int i = 0; i < sudokuController.model.getN() * sudokuController.model.getK(); i++) {
                                for (int j = 0; j < sudokuController.model.getN()
                                        * sudokuController.model.getK(); j++) {
                                    if (sudokuController.view.sudokuBoard.getCellFromCoord(i, j).enabled) {
                                        sudokuController.model.setSudokuCell(i, j,
                                                sudokuController.model.crooks.getSolvedSudoku()[i][j]);
                                    }
                                }
                            }
                            sudokuController.updateColours();
                        }
                    }
                }

                break;
            case "open new sudoku":
                sudokuController.view.dispose();
                sudokuController.setOkPressed();
                break;
        }

    }

}
