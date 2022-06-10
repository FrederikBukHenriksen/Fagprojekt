package sudoku.Controller.Actionlisteners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import sudoku.SudokuController;
import sudoku.Model.Validity.ValidityClassic;
import sudoku.Model.Validity.ValiditySandwich;
import sudoku.View.SudokuBoard.*;

public class MenuBarTestActionListener implements ActionListener {

    SudokuController sudokuController;

    public MenuBarTestActionListener(SudokuController sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {
        ValidityClassic validity = new ValidityClassic(sudokuController.model.getSudoku(),
                sudokuController.model.getN(),
                sudokuController.model.getK());

        ValiditySandwich sandwichValidity = new ValiditySandwich(sudokuController.model.getSudoku(),
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

        // System.out.println(sandwichValidity.checkValidity());

        System.out.println(sudokuController.model.validity.checkValidity());

    }
}
