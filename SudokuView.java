package sudoku;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SudokuView {
	
	private SudokuModel model;
	
	public JFrame f = new JFrame();
	
	public SudokuView(SudokuModel model) {
		int[][] sudoku = model.sudoku;
	    f=new JFrame();
	    JPanel mainGui = new JPanel(new GridLayout(1,2,50,0));
	    JPanel panelGui = new JPanel(new GridLayout(3,3,10,10));
	    for (int l = 0; l<9; l++) {
	    	JPanel panel = new JPanel(new GridLayout(3,3));
	    	for(int i = 0; i<3; i++) {
	    		for(int j = 0; j<3; j++) {
	    				if(sudoku[i+3*(l/3)][(j+3*l)%9]==0) {
	    					String numbers[]={"","1","2","3","4","5","6","7","8","9"}; 
	    					 JToggleButton b1 = new JToggleButton("");
	    					panel.add(b1);
	    				}
	    				else {
	    					JLabel l1 = new JLabel(String.valueOf(sudoku[i+3*(l/3)][(j+3*l)%9]));
	    					panel.add(l1);
	    				}     	       
	    }}
	    panelGui.add(panel);
	    }
	    mainGui.add(panelGui);
	    JPanel buttonGui = new JPanel(new GridLayout(3,3,20,20));
	    for (int i = 1; i<10; i++) {
	    	JButton t1=new JButton(String.valueOf(i));
	    	buttonGui.add(t1);	
	    }
	    mainGui.add(buttonGui);
	    
	    f.add(mainGui);
	    // setting grid layout of 3 rows and 3 columns           
	    f.setSize(1000,1000);  
		} 
	
	public void button() {
		JFrame frame = new JFrame("Selecting Toggle");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    JToggleButton toggleButton = new JToggleButton("Toggle Button");
	    // Define ActionListener
	    ActionListener actionListener = new ActionListener() {
	      public void actionPerformed(ActionEvent actionEvent) {
	        AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
	        boolean selected = abstractButton.getModel().isSelected();
	        System.out.println();
	      }
	    };
	    // Attach Listeners
	    toggleButton.addActionListener(actionListener);
	    frame.add(toggleButton);
	    frame.setSize(125, 125);
	    frame.setVisible(true);
	  }
	
	public void setVisible(JFrame frame) {
		f.setVisible(true);
	}
	
}
