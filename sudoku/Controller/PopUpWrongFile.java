package sudoku.Controller;

import sudoku.Controller.Actionlisteners.*;
import sudoku.Controller.Actionlisteners.MenuBar.MenuBarSolveListener;
import sudoku.Controller.Actionlisteners.MenuBar.MenuBarNewSudokuListener;
import sudoku.Controller.Actionlisteners.MenuBar.MenuBarZoomListener;
import sudoku.Controller.Actionlisteners.MenuBar.SudokuHintListener;
import sudoku.Controller.Actionlisteners.MenuBar.SudokuRedoListener;
import sudoku.Controller.Actionlisteners.MenuBar.SudokuRemoveListener;
import sudoku.Controller.Actionlisteners.MenuBar.SudokuUndoListener;
import sudoku.Model.Model;
import sudoku.Model.Solver.BacktrackAlgorithm;
import sudoku.Model.Solver.CrooksAlgorithm;
import sudoku.Model.Validity.ValidityClassic;
import sudoku.Model.Validity.ValiditySandwich;
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
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Container;
import java.awt.FlowLayout;

public class PopUpWrongFile extends PopUp {

    protected Controller controller;

    protected boolean moveOn = false;

    public PopUpWrongFile(String text, Controller controller) {
        super(text);
        this.controller = controller;

        while (true) { // Wait-loop. Implementing into actionlistener causes bugs.
            try {
                Thread.sleep(20);
            } catch (InterruptedException e1) {
            }
            if (moveOn) {
                moveOn = false;
                break;
            }
        }
        try {
            controller.fileLoader.LoadSudokuBoardDoc(controller.model);
        } catch (Exception e) {
            new PopUpWrongFile("wrong filetype", controller);
        }
    }

    @Override
    protected JPanel buttonPanel() {
        JButton okButton = new JButton("Ok");
        JDialog dialog = this;
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.view.dispose();
                } catch (Exception exc) { // If no view is present
                }
                dialog.dispose();
                moveOn = true;
                return;
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(okButton);
        return panel;
    }

}