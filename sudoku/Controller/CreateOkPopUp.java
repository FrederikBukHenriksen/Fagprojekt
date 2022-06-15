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
        moveOn = false;
        this.setLayout(new FlowLayout());
        int x = sudokuController.view.getX();
        int y = sudokuController.view.getY();
        int height = sudokuController.view.getHeight();
        int width = sudokuController.view.getWidth();
        this.setBounds((width / 2) - 200 + x, (height / 2) - 75 + y, 400, 150);
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
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

    // class OkButton extends JButton {
    // public OkButton(String input) {
    // super(input);
    // }

    // }
}
