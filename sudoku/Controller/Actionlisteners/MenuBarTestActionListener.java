package sudoku.Controller.Actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import sudoku.SudokuController;
import sudoku.ClassicValidity;
import sudoku.SandwichValidity;
import sudoku.View.SudokuBoard.*;

public class MenuBarTestActionListener implements ActionListener {

    SudokuController sudokuController;

    public MenuBarTestActionListener(SudokuController sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        ClassicValidity validity = new ClassicValidity(sudokuController.model.getSudoku(),
                sudokuController.model.getN(),
                sudokuController.model.getK());

        SandwichValidity sandwichValidity = new SandwichValidity(sudokuController.model.getSudoku(),
                sudokuController.model.getN(),
                sudokuController.model.getK(), sudokuController.model.xSums, sudokuController.model.ySums);
        // validity.uniqueConflicts(
        // validity.findConflicts(sudokuController.model.getSudoku(),
        // sudokuController.model.getN(),
        // sudokuController.model.getK()));
        // System.out.println(validity.checkValidity(sudokuController.model.getSudoku(),
        // sudokuController.model.getN(),
        // sudokuController.model.getK()));

        // System.out.println(validity.checkValidity(sudokuController.model.getSudoku(),
        // sudokuController.model.getN(),
        // sudokuController.model.getK()));

        // System.out.println(
        // sandwichValidity.findRowSumConflicts(sudokuController.model.getSudoku(),
        // sudokuController.model.getN(),
        // sudokuController.model.getK(), sudokuController.model.xSums,
        // sudokuController.model.ySums));
        // System.out.println(
        // sandwichValidity.findColSumConflicts(sudokuController.model.getSudoku(),
        // sudokuController.model.getN(),
        // sudokuController.model.getK(), sudokuController.model.xSums,
        // sudokuController.model.ySums));

        System.out.println(sandwichValidity.checkValidity());

    }
}
