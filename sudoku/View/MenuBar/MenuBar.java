package sudoku.View.MenuBar;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
    // create menuitems
    public JMenuItem solve = new JMenuItem("Solve Sudoku");
    public JMenuItem newPuzzle = new JMenuItem("Open new Sudoku");
    public JMenuItem zoomIn = new JMenuItem("Zoom in");
    public JMenuItem zoomOut = new JMenuItem("Zoom out");

    public MenuBar() {
        // create a menu
        JMenu x = new JMenu("Menu");
        JMenu tools = new JMenu("Tools");

        zoomIn.setActionCommand("Zoom in");
        zoomOut.setActionCommand("Zoom out");
        solve.setActionCommand("Solve Sudoku");
        newPuzzle.setActionCommand("Open new Sudoku");

        tools.add(zoomIn);
        tools.add(zoomOut);

        // add menu items to menu
        x.add(solve);
        x.add(newPuzzle);

        // add menu to menu bar
        this.add(tools);
        this.add(x);
    }
}
