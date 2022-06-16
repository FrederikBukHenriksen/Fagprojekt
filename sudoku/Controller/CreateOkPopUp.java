package sudoku.Controller;

import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.FlowLayout;

public class CreateOkPopUp extends JDialog {

    protected Controller sudokuController;

    protected JButton okButton;
    protected boolean moveOn;

    public CreateOkPopUp(String text, Controller sudokuController) {
        this.sudokuController = sudokuController;
        this.setLayout(new FlowLayout());
        moveOn = false;

        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        int x = (int) screenSize.getWidth() / 2;
        int y = (int) screenSize.getHeight() / 2;
        int height = 200;
        int width = 400;
        try {
            x = sudokuController.view.getX();
            y = sudokuController.view.getY();
            height = sudokuController.view.getHeight();
            width = sudokuController.view.getWidth();
        } catch (Exception e) {
        }

        // this.setBounds((width / 2) - 200 + x, (height / 2) - 75 + y, 400, 150);
        int xScreen = (screenSize.width / 2) - (this.getWidth() / 2);
        int yScreen = (screenSize.height / 2) - (this.getHeight() / 2);
        this.setLocation(xScreen, yScreen);
        JLabel jLabel = new JLabel(text);
        jLabel.setFont(new Font(jLabel.getFont().getName(), Font.PLAIN, 20));
        okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                moveOn = true;
                return;
            }
        });
        this.add(jLabel);
        this.add(okButton);
        this.setVisible(true);
        this.pack();

        whileLoop();
    }

    protected void whileLoop() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            if (moveOn) {
                moveOn = false;
                break;
            }
        }
    }
}
