package sudoku;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.lang.Math;

import javax.swing.*;

public class SudokuView {
	public int test = 1;
	public int xGrid = -1;
	public int yGrid = -1;

	
	private SudokuModel model;
	
	public JFrame f = new JFrame();
	
	public SudokuView(SudokuModel model) {
		int[][] sudoku = model.sudoku;
	    f=new JFrame();
	    JPanel mainGui = new JPanel(new GridLayout(1,2,50,0));
	    JPanel panelGui = new JPanel(new GridLayout(3,3,10,10));
	    ArrayList<ArrayList<JToggleButton>> fields = new ArrayList();
	    for (int i = 0; i < 9; i++) {
			ArrayList<JToggleButton> rows = new ArrayList();
			for (int j = 0; j < 9; j++) {
				rows.add(new JToggleButton(""));
				// System.out.println((i + 1) * (j + 1));
			}
			fields.add(rows);
		}
	    for (int l = 0; l<9; l++) {
	    	JPanel panel = new JPanel(new GridLayout(3,3));
	    	
	    	for(int i = 0; i<3; i++) {

	    		for(int j = 0; j<3; j++) {
	    				if(sudoku[i+3*(l/3)][(j+3*l)%9]==0) {
	    					fields.get(i+3*(l/3)).get((j+3*l)%9).setActionCommand(((i+3*(l/3)+1)*10)+((j+3*l)%9+1)+"");;
	    					 ActionListener actionListener = new ActionListener() {
	    					      public void actionPerformed(ActionEvent actionEvent) {
	    					        AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
	    					        boolean selected = abstractButton.getModel().isSelected();
	    					        int p = Integer.parseInt(actionEvent.getActionCommand());
	    					        for (int i = 0; i<9; i++) {
	    					        	for (int j = 0; j<9; j++) {	        		
	    					        		fields.get(i).get(j).setSelected(false);
	    					        	}
	    					        }
	    					        xGrid = (int) (Math.floor(p)/10)-1;
	    					        yGrid = p%10-1;
	    					        fields.get(xGrid).get(yGrid).setSelected(true);
	    					        
	    					      }
	    					    };
	    					fields.get(i+3*(l/3)).get((j+3*l)%9).addActionListener(actionListener);
	    					panel.add(fields.get(i+3*(l/3)).get((j+3*l)%9));
	    					
	    					
	    				}
	    				else {
	    					JLabel l1 = new JLabel(String.valueOf(sudoku[i+3*(l/3)][(j+3*l)%9]));
	    					panel.add(l1);
	    				}     	       
	    }}
	    panelGui.add(panel);
	    }
	    mainGui.add(panelGui);
	    JPanel sideButtonGui = new JPanel(new GridLayout(2,1,0,10));
	    JPanel specialButton = new JPanel(new GridLayout(1,4,0,0));
	    JButton undo = new JButton("undo");
	    JButton remove = new JButton("remove");
	    JButton note = new JButton("note");
	    JButton newSudoku = new JButton("newSudoku");
	    ActionListener specialAction = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		        AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
		        boolean selected = abstractButton.getModel().isSelected();
                    if (xGrid != -1) {
		        		if (fields.get(xGrid).get(yGrid).isSelected()) {
		        				fields.get(xGrid).get(yGrid).setText(null);     			
		        			}}}};
		remove.addActionListener(specialAction);       			
	    specialButton.add(undo); specialButton.add(remove); specialButton.add(note); specialButton.add(newSudoku);
	    sideButtonGui.add(specialButton);
 	    JPanel buttonGui = new JPanel(new GridLayout(3,3,20,20));
			ArrayList<JButton> button = new ArrayList();
			for (int j = 0; j < 9; j++) {
				button.add(new JButton(j+1 +""));
				button.get(j).setActionCommand(j+1+ "");
			}
		
	    for (int i = 1; i <10; i++) {
	    	test = i;
			 ActionListener actionListener = new ActionListener() {
			      public void actionPerformed(ActionEvent actionEvent) {
			        AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
			        boolean selected = abstractButton.getModel().isSelected();
                          if (xGrid != -1) {
			        		if (fields.get(xGrid).get(yGrid).isSelected()) {
			        			if(actionEvent.getActionCommand().equals(fields.get(xGrid).get(yGrid).getText())){
			        				fields.get(xGrid).get(yGrid).setText(null);
			        			}
			        			else {
			        				fields.get(xGrid).get(yGrid).setText(actionEvent.getActionCommand());
			        			
			        			}
			        		}
			        	}
			        }
			      
			 };
			button.get(i-1).addActionListener(actionListener);
			buttonGui.add(button.get(i-1));	
			
	    }
	    sideButtonGui.add(buttonGui);
	    mainGui.add(sideButtonGui);
	   
	    
	    f.add(mainGui);
	    // setting grid layout of 3 rows and 3 columns           
	    f.setSize(1000,1000);  
		} 

	
	
	public void setVisible(JFrame frame) {
		f.setVisible(true);
	}
	
}
