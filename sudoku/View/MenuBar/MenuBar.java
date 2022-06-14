package sudoku.View.MenuBar;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
    // create menuitems
    public JButton undo = new JButton("Undo");
    public JButton redo = new JButton("Redo");

    public JMenuItem solve = new JMenuItem("Solve Sudoku");
    public JMenuItem zoomIn = new JMenuItem("Zoom in");
    public JMenuItem zoomOut = new JMenuItem("Zoom out");
    public JMenuItem test = new JMenuItem("TEST");

    public MenuBar() {
        // create a menu
        JMenu menu = new JMenu("Menu");
        JMenu tools = new JMenu("Tools");

        undo.setActionCommand("Undo");
        redo.setActionCommand("Redo");

        zoomIn.setActionCommand("Zoom in");
        zoomOut.setActionCommand("Zoom out");
        solve.setActionCommand("Solve Sudoku");

        tools.add(zoomIn);
        tools.add(zoomOut);

        // add menu items to menu
        menu.add(solve);
        menu.add(test);

        // add menu to menu bar
        this.add(tools);
        this.add(menu);
        this.add(undo);
        this.add(redo);

    }
}
