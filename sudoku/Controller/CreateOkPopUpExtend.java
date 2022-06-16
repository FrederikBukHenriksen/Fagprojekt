package sudoku.Controller;

import sudoku.Controller.Actionlisteners.*;
import sudoku.Controller.Actionlisteners.MenuBar.MenuBarMenuActionListener;
import sudoku.Controller.Actionlisteners.MenuBar.MenuBarNewSudokuActionListener;
import sudoku.Controller.Actionlisteners.MenuBar.MenuBarZoomActionListener;
import sudoku.Controller.Actionlisteners.MenuBar.SudokuHintListener;
import sudoku.Controller.Actionlisteners.MenuBar.SudokuRedoListener;
import sudoku.Controller.Actionlisteners.MenuBar.SudokuRemoveListener;
import sudoku.Controller.Actionlisteners.MenuBar.SudokuUndoListener;
import sudoku.Model.Model;
import sudoku.Model.Solver.BacktrackAlgorithm;
import sudoku.Model.Solver.CrooksAlgorithm;
import sudoku.Model.Validity.ValidityClassic;
import sudoku.Model.Validity.ValiditySandwich;
import sudoku.View.ExceptionPopUp;
import sudoku.View.View;
import sudoku.View.SudokuBoard.*;
import sudoku.View.SudokuBoard.Classic.ClassicSudokuBoard;
import sudoku.View.SudokuBoard.Sandwich.SandwichSudoku;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Container;
import java.awt.FlowLayout;

public class CreateOkPopUpExtend extends CreateOkPopUp {

    public CreateOkPopUpExtend(String text, Controller sudokuController) {
        super(text, sudokuController);
        // TODO Auto-generated constructor stub

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sudokuController.view.dispose();
                dispose();
                moveOn = true;
                return;
            }
        });
    }

    @Override
    protected void whileLoop() {
        // TODO Auto-generated method stub
        super.whileLoop();
        try {
            sudokuController.loadSudokuBoardFile.LoadSudokuBoardDoc(sudokuController, sudokuController.model);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}