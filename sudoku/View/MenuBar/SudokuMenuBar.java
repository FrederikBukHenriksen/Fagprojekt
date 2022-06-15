package sudoku.View.MenuBar;

import javax.swing.*;

public class SudokuMenuBar extends JMenuBar {
    // create menuitems
    public MenuBarButton undo = new MenuBarButton("Undo");
    public MenuBarButton remove = new MenuBarButton("Remove");
    public MenuBarButton redo = new MenuBarButton("Redo");
    public MenuBarButton hint = new MenuBarButton("Hint");

    public JMenuItem solve = new JMenuItem("Solve Sudoku");

    public JMenuItem zoomIn = new JMenuItem("Zoom in");
    public JMenuItem zoomOut = new JMenuItem("Zoom out");
    public JMenuItem newPuzzle = new JMenuItem("Open new Sudoku");

    public SudokuMenuBar() {
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
        menu.add(newPuzzle);

        // add menu to menu bar
        this.add(tools);
        this.add(menu);
        this.add(undo);
        this.add(remove);
        this.add(redo);
        this.add(hint);

    }
}
