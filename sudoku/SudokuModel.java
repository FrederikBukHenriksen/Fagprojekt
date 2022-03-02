package sudoku;

import java.util.Stack;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SudokuModel {

	int[][] sudoku = new int[0][0];
	Stack<int[][]> sudokuStack = new Stack<int[][]>();
	
    public SudokuModel (File file){
        Scanner scanner;
		try {
			scanner = new Scanner(file);
			String setup = scanner.next();
			int n = Character.getNumericValue(setup.charAt(0));
			int k = Character.getNumericValue(setup.charAt(2));

			sudoku = new int[n*n][n*n];
	    	for (int i = 0; i< n*n; i++) {
	        String line = scanner.next();
	    		for(int j = 0; j < n*n; j++) {
	    			if(line.charAt(j*2)=='.') {
	    				sudoku[i][j]=0;
	    			} else {
	    				sudoku[i][j]=Character.getNumericValue(line.charAt(j*2));
	    			}
	    		}
	    	}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	public int[][] getSudoku(){
		return sudoku;
	}

}
