package sudoku.View;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class ExceptionPopUp extends JDialog {

    public ExceptionPopUp(Exception exception) {
        this.add(new JLabel(exception.getMessage()));
        this.setVisible(true);
        this.pack();
    }
}
