package sudoku.Controller;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.*;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.FlowLayout;

public class PopUp extends JDialog {

    public PopUp(String text) {
        this.setVisible(true);
        this.setResizable(false);
        this.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

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

    protected void centerOnScreen() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int xScreen = (screenSize.width / 2) - ((int) getSize().getWidth() / 2);
        int yScreen = (screenSize.height / 2) - ((int) getSize().getHeight() / 2);
        this.setLocation(xScreen, yScreen);
    }

    protected JPanel buttonPanel() {

        JButton okButton = new JButton("Ok");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                return;
            }
        });
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(okButton);

        return panel;
    }

}
