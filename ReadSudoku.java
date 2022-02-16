import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ReadSudoku {
	public static int[][] Read(){		
		File file = new File("C:\\Users\\Candytom\\eclipse-workspace\\Sudoku\\src\\filename.txt");
		Scanner scanner;
		try {
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
	    	return sudoku;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
}
