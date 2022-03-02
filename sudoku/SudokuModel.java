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
		int k = 0;
		int n = 0;
		try {
			scanner = new Scanner(file);
			String setup = scanner.next();
			Scanner setupScanner = new Scanner(setup);
			setupScanner.useDelimiter(";");
			while(setupScanner.hasNext()){
				try{
					String str = setupScanner.next();
					k = Integer.parseInt(str);
					str = setupScanner.next();
					n = Integer.parseInt(str);
				}
				catch (NumberFormatException ex){
					ex.printStackTrace();
				}
			}
			setupScanner.close();

			sudoku = new int[n*k][n*k];
	    	/*for (int i = 0; i< n*n; i++) {
	        String line = scanner.next();
	    		for(int j = 0; j < n*n; j++) {
	    			if(line.charAt(j*2)=='.') {
	    				sudoku[i][j]=0;
	    			} else {
	    				sudoku[i][j]=Character.getNumericValue(line.charAt(j*2));
	    			}
	    		}
	    	}*/
			int c = 0;
			int d = 0;
			scanner.nextLine();
			while(scanner.hasNextLine()){
				String line = scanner.nextLine();

				Scanner lineScanner = new Scanner(line);
				lineScanner.useDelimiter(";");
				while(lineScanner.hasNext()){
					String str = lineScanner.next();
					if(str.equals(".")){
						//System.out.println("c: " + c);
						sudoku[c][d] = 0;
						//System.out.println("i: " + d + ", j: " + c);
						d++;
						/*for(int a = 0; a < sudoku.length; a++){
							for(int b = 0; b < sudoku.length; b++){
								System.out.print(sudoku[a][b] + " ");
							}
							System.out.println();
						}
						System.out.println();*/
					} 
					else{
						try{
							sudoku[c][d] = Integer.parseInt(str);
							/*System.out.println("i: " + d + ", j: " + c);
							for(int a = 0; a < sudoku.length; a++){
								for(int b = 0; b < sudoku.length; b++){
									System.out.print(sudoku[a][b] + " ");
								}
								System.out.println();
							}
							System.out.println();*/
						}
						catch (NumberFormatException ex){
							ex.printStackTrace();
						}
						d++;
					}
				}
				c++;
				d = 0;
				lineScanner.close();
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
