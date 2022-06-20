package sudoku.Controller;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import sudoku.View.View;

import java.awt.Dimension;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.FlowLayout;

public class CreateOkPopUp extends JDialog {


    protected boolean moveOn = false;

    protected GridBagConstraints gbc = new GridBagConstraints();

    public CreateOkPopUp(String text) {
        // this.setUndecorated(true); // Removes title bar
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(new GridBagLayout());

        JLabel label = new JLabel(text);
        label.setFont(new Font(label.getFont().getName(), Font.PLAIN, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(label, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        this.add(buttonPanel(), gbc);

        this.pack();
        centerOnScreen();
    }

    private void centerOnScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int xScreen = (screenSize.width / 2) - ((int) getSize().getWidth() / 2);
        int yScreen = (screenSize.height / 2) - ((int) getSize().getHeight() / 2);
        this.setLocation(xScreen, yScreen);
    }

    public JPanel buttonPanel() {

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
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
