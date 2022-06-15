package sudoku.View.MenuBar;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar extends JMenuBar {
    // create menuitems
    public MenuBarButton undo = new MenuBarButton("Undo");
    public MenuBarButton remove = new MenuBarButton("Remove");
    public MenuBarButton redo = new MenuBarButton("Redo");

    public JMenuItem solve = new JMenuItem("Solve Sudoku");
    public JMenuItem hint = new JMenuItem("Cell hint");

    public JMenuItem zoomIn = new JMenuItem("Zoom in");
    public JMenuItem zoomOut = new JMenuItem("Zoom out");
    public JMenuItem test = new JMenuItem("TEST");
    public JMenuItem newPuzzle = new JMenuItem("Open new Sudoku");

    public MenuBar() {
        // create a menu
        JMenu menu = new JMenu("Menu");
        JMenu tools = new JMenu("Tools");

        undo.setActionCommand("Undo");
        redo.setActionCommand("Redo");

        zoomIn.setActionCommand("Zoom in");
        zoomOut.setActionCommand("Zoom out");
        solve.setActionCommand("Solve Sudoku");
        newPuzzle.setActionCommand("Open new Sudoku");

        tools.add(zoomIn);
        tools.add(zoomOut);

        // add menu items to menu
        menu.add(hint);
        menu.add(solve);
        menu.add(test);
        menu.add(newPuzzle);

        // add menu to menu bar
        this.add(tools);
        this.add(menu);
        this.add(undo);
        this.add(remove);
        this.add(redo);

    }
}
