package sudoku;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewMenubar extends JMenuBar {

    JMenuItem zoomIn = new JMenuItem("Zoom in");
    JMenuItem zoomOut = new JMenuItem("Zoom out");

    public ViewMenubar() {
        // create a menu
        JMenu menu = new JMenu("Menu");
        JMenuItem m1 = new JMenuItem("Load new sudoku");
        menu.add(m1);

        ButtonGroup group = new ButtonGroup();
        JMenu gamemode = new JMenu("Select gamemode");
        JRadioButtonMenuItem classic = new JRadioButtonMenuItem("Classic sudoku");
        classic.setActionCommand("classic");
        classic.setSelected(true);

        group.add(classic);
        gamemode.add(classic);
        JRadioButtonMenuItem sandwich = new JRadioButtonMenuItem("Sandwich sudoku");
        group.add(sandwich);
        gamemode.add(sandwich);
        menu.add(gamemode);

        JMenu view = new JMenu("View");
        zoomIn.setActionCommand("zoom in");
        zoomOut.setActionCommand("zoom out");

        view.add(zoomIn);
        view.add(zoomOut);

        JMenu tools = new JMenu("tools");

        // create menuitems

        // add menu items to menu

        // add menu to menu bar
        this.add(menu);
        this.add(view);
        this.add(tools);
    }

}
