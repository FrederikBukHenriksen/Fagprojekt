package sudoku.View.MenuBar;

import javax.swing.JButton;
import javax.swing.JMenu;

public class MenuBarButton extends JButton {

    public MenuBarButton(String text) {
        super(text);
        this.setBorderPainted(false);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setOpaque(false);
        this.setMargin(new JMenu("").getMargin());
    }
}
