package sudoku.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;

public class PopUpSudokuSolved extends PopUp {

    protected Controller controller;
	/*
	 * Author: Rasmus, Edit by Frederik
	 * Function: Creates pop up
	 * Inputs: None
     * Outputs: None
	 */
    public PopUpSudokuSolved(String text, Controller controller) {
        super(text);
        this.controller = controller;
    }
	/*
	 * Author: Rasmus, edited by Frederik
	 * Function: Creates button, and actionlistener for button
	 * Inputs: None
     * Outputs: None
	 */
    @Override
    protected JPanel buttonPanel() {
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
