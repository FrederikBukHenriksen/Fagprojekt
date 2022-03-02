package sudoku;
import java.awt.Color;
import java.awt.Font;
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
	    f=new JFrame(); //creating frame
	    JPanel mainGui = new JPanel(new GridLayout(1,2,50,0)); //creating to halfs, given them 50 pixels space to the side
	    JPanel panelGui = new JPanel(new GridLayout(3,3,10,10)); //creating sudoku grid on left side
	    ArrayList<ArrayList<JToggleButton>> fields = new ArrayList(); //creating arraylist of togglebuttons, so we can interact with them individually 
	    for (int i = 0; i < 9; i++) {
			ArrayList<JToggleButton> rows = new ArrayList();
			for (int j = 0; j < 9; j++) {
				rows.add(new JToggleButton(""));
				// System.out.println((i + 1) * (j + 1));
			}
			fields.add(rows);
		}
	    for (int l = 0; l<9; l++) { //creating sudoku grid
	    	JPanel panel = new JPanel(new GridLayout(3,3));//creating individual 3 by 3's. 
	    	
	    	for(int i = 0; i<3; i++) { 

	    		for(int j = 0; j<3; j++) {
	    				if(sudoku[i+3*(l/3)][(j+3*l)%9]==0) { //goes through sudoku that is read in.
	    					fields.get(i+3*(l/3)).get((j+3*l)%9).setActionCommand(((i+3*(l/3)+1)*10)+((j+3*l)%9+1)+"");;//creates action listener for all cells with no number yet.
	    					 ActionListener actionListener = new ActionListener() {
	    					      public void actionPerformed(ActionEvent actionEvent) {
	    					        AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
	    					        boolean selected = abstractButton.getModel().isSelected();
	    					        int p = Integer.parseInt(actionEvent.getActionCommand());
	    					        for (int i = 0; i<9; i++) {
	    					        	for (int j = 0; j<9; j++) {	        		
	    					        		fields.get(i).get(j).setSelected(false); //set all cells to unselected
	    					        	}
	    					        }
	    					        xGrid = (int) (Math.floor(p)/10)-1; //creates global x-coordinat of selected cell
	    					        yGrid = p%10-1;  //creates global y-coordinat of selected cell
	    					        fields.get(xGrid).get(yGrid).setSelected(true); //sets original sell to selected
	    					        
	    					      }
	    					    };
	    					fields.get(i+3*(l/3)).get((j+3*l)%9).addActionListener(actionListener); 
	    					fields.get(i+3*(l/3)).get((j+3*l)%9).setFont(new Font("Serif", Font.PLAIN, 72));
	    					panel.add(fields.get(i+3*(l/3)).get((j+3*l)%9)); //adds cell with actionlistener to panel
	    					
	    					
	    				}
	    				else {
	    					//JLabel l1 = new JLabel(String.valueOf(sudoku[i+3*(l/3)][(j+3*l)%9]));
	    					JButton l1 = new JButton(String.valueOf(sudoku[i+3*(l/3)][(j+3*l)%9]));//creates a disabled button for known numbers
	    					l1.setFont(new Font("Serif", Font.PLAIN, 68));
	    					l1.setEnabled(false);

	    					panel.add(l1); //add those to the panel
	    				}     	       
	    }}
	    panelGui.add(panel);
	    }
	    mainGui.add(panelGui);
	    JPanel sideButtonGui = new JPanel(new GridLayout(2,1,0,10));//creates buttons panels on the right side
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
		        		if (fields.get(xGrid).get(yGrid).isSelected()) { //deletes number from grid, if a cell is selected. Does nothing if no cell is selected
		        				fields.get(xGrid).get(yGrid).setText(null);
		        				model.sudoku[xGrid][yGrid]=0;
		        				if (SudokuController.checkValidity(sudoku)) { //calls controller to check if valid
		        					f.setTitle("Valid");
		        				}
		        				else {
		        					f.setTitle("Invalid");
		        				}
		        			}
		        		
		        		}}};
		remove.addActionListener(specialAction);       			
	    specialButton.add(undo); specialButton.add(remove); specialButton.add(note); specialButton.add(newSudoku);
	    sideButtonGui.add(specialButton);
 	    JPanel buttonGui = new JPanel(new GridLayout(3,3,20,20));//creates a 3/3 with numbers from 1-9
			ArrayList<JButton> button = new ArrayList();
			for (int j = 0; j < 9; j++) {
				button.add(new JButton(j+1 +""));//adds number as label to button
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
			        			if(actionEvent.getActionCommand().equals(fields.get(xGrid).get(yGrid).getText())){//check for what fields is selected. Sets number to 0, if the same number was already selected
			        				fields.get(xGrid).get(yGrid).setText(null);
			        				model.sudoku[xGrid][yGrid]=0;
			        			}
			        			else {
			        				fields.get(xGrid).get(yGrid).setText(actionEvent.getActionCommand());//check for fields is selected, to cell to number
			        				model.sudoku[xGrid][yGrid]=Integer.parseInt(actionEvent.getActionCommand());
			        				

			        			}
		        				if (SudokuController.checkValidity(sudoku)) { //checks for validity
		        					f.setTitle("Valid");
		        				}
		        				else {
		        					f.setTitle("Invalid");
		        				}
			        		}
			        	}
			        }
			      
			 };
			button.get(i-1).addActionListener(actionListener);
			button.get(i-1).setFont(new Font("Serif", Font.PLAIN, 72));
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
