package sudoku.Controller;

import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

public class SudokuSolvedPopUp extends CreateOkPopUp {

    protected Controller controller;

    public SudokuSolvedPopUp(String text, Controller controller) {
        super(text);

        this.controller = controller;
    }

    @Override
    public JPanel buttonPanel() {
        JDialog dialog = this;
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JButton newButton = new JButton("New puzzle");
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.view.dispose();
                dialog.dispose();
                controller.okPressed = true;
            }
        });

        JButton continueButton = new JButton("Back to Puzzle");
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(closeButton);
        panel.add(newButton);
        panel.add(continueButton);
        return panel;
    }

}
