package sudoku.Controller.Actionlisteners.MenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import sudoku.Controller.Controller;
import sudoku.Model.Validity.ValidityClassic;
import sudoku.Model.Validity.ValiditySandwich;
import sudoku.View.ExceptionPopUp;
import sudoku.View.SudokuBoard.*;

public class MenuBarTestActionListener implements ActionListener {

    Controller sudokuController;

    public MenuBarTestActionListener(Controller sudokuController) {
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e) {

        // System.out.println(sudokuController.model.validity.checkValidity());
        // System.out.println(sudokuController.model.validity.getUniqueConflictPoints());

        ExceptionPopUp exceptionHandler = new ExceptionPopUp(new Exception("LOLCAT"));

    }
}
