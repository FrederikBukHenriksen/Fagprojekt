package sudoku.Controller;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
            controller.fileLoader.LoadSudokuBoardDoc();
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