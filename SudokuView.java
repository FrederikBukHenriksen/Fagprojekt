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


	// private SudokuModel model;

	public JFrame f = new JFrame();
	
	public SudokuView(SudokuModel model) {

		int[][] sudoku = model.sudoku;
	    f=new JFrame();
	    JPanel mainGui = new JPanel(new GridLayout(1,2,50,0));
	    JPanel panelGui = new JPanel(new GridLayout(3,3,10,10));
		ArrayList<ArrayList<JToggleButton>> buttons = createSudokuButtons();
		CreateActionlistenersSudokuBoard(buttons);

	    for (int l = 0; l<9; l++) {
	    	JPanel panel = new JPanel(new GridLayout(3,3));
	    	
	    	for(int i = 0; i<3; i++) {

				for (int j = 0; j < 3; j++) {
					panel.add(buttons.get(i + 3 * (l / 3)).get((j + 3 * l) % 9));
					// if(sudoku[i+3*(l/3)][(j+3*l)%9]==0) {
					// buttons.get(i + 3 * (l / 3)).get((j + 3 * l) % 9)
					// .setActionCommand(((i + 3 * (l / 3) + 1) * 10) + ((j + 3 * l) % 9 + 1) + "");
					// ;
					// ActionListener actionListener = new ActionListener() {
					// public void actionPerformed(ActionEvent actionEvent) {
					// AbstractButton abstractButton = (AbstractButton) actionEvent.getSource();
					// boolean selected = abstractButton.getModel().isSelected();
					// int p = Integer.parseInt(actionEvent.getActionCommand());
					// for (int i = 0; i<9; i++) {
					// for (int j = 0; j<9; j++) {
					// buttons.get(i).get(j).setSelected(false);
					// }
					// }
					// xGrid = (int) (Math.floor(p)/10)-1;
					// yGrid = p%10-1;
					// buttons.get(xGrid).get(yGrid).setSelected(true);

					// }
					// };
					// buttons.get(i + 3 * (l / 3)).get((j + 3 * l) %
					// 9).addActionListener(actionListener);
					// buttons.get(i + 3 * (l / 3)).get((j + 3 * l) % 9)
					// .setFont(new Font("Serif", Font.PLAIN, 72));
					// panel.add(buttons.get(i + 3 * (l / 3)).get((j + 3 * l) % 9));
	    					
	    					
					// }
					// else {
					// //JLabel l1 = new JLabel(String.valueOf(sudoku[i+3*(l/3)][(j+3*l)%9]));
					// JButton l1 = new JButton(String.valueOf(sudoku[i+3*(l/3)][(j+3*l)%9]));
					// l1.setFont(new Font("Serif", Font.PLAIN, 68));
					// l1.setEnabled(false);

					// panel.add(l1);
					// }
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
					if (buttons.get(xGrid).get(yGrid).isSelected()) {
						buttons.get(xGrid).get(yGrid).setText(null);
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
							if (buttons.get(xGrid).get(yGrid).isSelected()) {
								if (actionEvent.getActionCommand().equals(buttons.get(xGrid).get(yGrid).getText())) {
									buttons.get(xGrid).get(yGrid).setText(null);
			        			}
			        			else {
									buttons.get(xGrid).get(yGrid).setText(actionEvent.getActionCommand());
			        			
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

	public ArrayList<ArrayList<JToggleButton>> createSudokuButtons() {
		ArrayList<ArrayList<JToggleButton>> fields = new ArrayList();
		for (int i = 0; i < 9; i++) {
			ArrayList<JToggleButton> rows = new ArrayList();
			for (int j = 0; j < 9; j++) {
				rows.add(new JToggleButton((i + 1) + " " + (j + 1)));
				// System.out.println((i + 1) * (j + 1));
			}
			fields.add(rows);
		}
		return fields;
	}

	// NEDENSTÅENDE SKAL RYKKES TIL CONTROLLER-KLASSEN:
	public void CreateActionlistenersSudokuBoard(ArrayList<ArrayList<JToggleButton>> buttons) {
		for (int axisZero = 0; axisZero < buttons.size(); axisZero++) {
			for (int axisOne = 0; axisOne < buttons.get(0).size(); axisOne++) {
				JToggleButton button = buttons.get(axisZero).get(axisOne);
				button.setActionCommand(Integer.toString(axisZero) + "," + Integer.toString(axisOne));
				button.addActionListener(sudokuButtonActionListener());
			}
		}
	}

	public ActionListener sudokuButtonActionListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println(e.getActionCommand());
			}
		};
	}
}
