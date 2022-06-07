package sudoku.View.MenuBar;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuBar {

    public JMenuItem zoomIn = new JMenuItem("Zoom in");
    public JMenuItem zoomOut = new JMenuItem("Zoom out");

    public MenuBar() {

    }

    public JMenuBar createMenu() {

        JMenuBar mb = new JMenuBar();

        // create a menu
        JMenu x = new JMenu("Menu");
        JMenu tools = new JMenu("Tools");

        zoomIn.setActionCommand("zoom in");
        zoomOut.setActionCommand("zoom out");

        tools.add(zoomIn);
        tools.add(zoomOut);

        // create menuitems
        JMenuItem m1 = new JMenuItem("MenuItem1");

        // add menu items to menu
        x.add(m1);

        // add menu to menu bar
        mb.add(tools);
        mb.add(x);

        return mb;
    }
}
