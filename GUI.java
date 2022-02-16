import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.*;   
public class GUI {
	public static void main(String[] args) {
		new GUI();    
	}
	JFrame f;    
	GUI(){ 
		Scanner scanner;
		try {
			File file = new File("C:\\Users\\Candytom\\eclipse-workspace\\Sudoku\\src\\filename.txt");
			scanner = new Scanner(file);
			String setup = scanner.next();
			int n = Character.getNumericValue(setup.charAt(0));
			int k = Character.getNumericValue(setup.charAt(2));

			int[][] sudoku = new int[n*n][n*n];
	    	for (int i = 0; i< n*n; i++) {
	        String line = scanner.next();
	    		for(int j = 0; j < n*n; j++) {
	    			if(line.charAt(j*2)=='.') {
	    				sudoku[i][j]=0;
	    			} else {
	    				sudoku[i][j]=Character.getNumericValue(line.charAt(j*2));
	    			}
	    			System.out.print(sudoku[i][j]);
	    		}
	    		System.out.println();
	    	}
		
	    f=new JFrame(); 
	    for (int l = 0; l<9; l++) {
	    	JPanel panel = new JPanel(new GridLayout(3,3));
	    	for(int i = 0; i<3; i++) {
	    		for(int j = 0; j<3; j++) {
	    			
	    				if(sudoku[i+3*(l/3)][(j+3*l)%9]==0) {
	    					String numbers[]={"","1","2","3","4","5","6","7","8","9"}; 
	    					JComboBox b1= new JComboBox(numbers);
	    					panel.add(b1);
	    				}
	    				else {
	    					JLabel l1 = new JLabel(String.valueOf(sudoku[i+3*(l/3)][(j+3*l)%9]));
	    					panel.add(l1);
	    				}     	       
	    }}
	    f.add(panel);
	    }
	    // setting grid layout of 3 rows and 3 columns    
	    f.setLayout(new GridLayout(3,3,10,10));    
	    f.setSize(300,300);    
	    f.setVisible(true); 
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}    
}

