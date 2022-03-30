package sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;


public class SudokuModel {
	// Setting up variables
	int[][] sudoku = new int[0][0];
	int[][][] sudokuStack = new int[1000][sudoku.length][sudoku.length];
	static int k = 0;
	static int n = 0;
	int moves = 0;
	boolean change = false;


	// constructor for the model
	public SudokuModel() {
		File file = new File("E:\\Eclibse\\Sudoku\\src\\Puzzle_3_06.dat");

		Scanner scanner;
		// reading the input
		try {
			scanner = new Scanner(file);
			String setup = scanner.next();
			Scanner setupScanner = new Scanner(setup);
			setupScanner.useDelimiter(";");
			// reading k & n
			while (setupScanner.hasNext()) {
				try {
					String str = setupScanner.next();
					k = Integer.parseInt(str);
					str = setupScanner.next();
					n = Integer.parseInt(str);
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
				}
			}
			setupScanner.close();

			// Creating the board
			sudoku = new int[n * k][n * k];
			// Creating variables for looping through input
			sudokuStack = new int[1000][sudoku.length][sudoku.length];
			int c = 0;
			int d = 0;
			scanner.nextLine();
			while (scanner.hasNextLine()) {
				// Reads the next line
				String line = scanner.nextLine();
				Scanner lineScanner = new Scanner(line);
				lineScanner.useDelimiter(";");
				while (lineScanner.hasNext()) {
					// Reads the next input on the line, separated by ";"
					String str = lineScanner.next();
					if (str.equals(".")) {
						// If input is ".", convert to a "0"
						sudoku[c][d] = 0;
						// Go to next entry
						d++;
					} else {
						try {
							// If input isn't ".", read the number and insert into array
							sudoku[c][d] = Integer.parseInt(str);
						} catch (NumberFormatException ex) {
							ex.printStackTrace();
						}
						// Go to next entry
						d++;
					}
				}
				// Go to next line, and start from first entry
				c++;
				d = 0;
				lineScanner.close();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<ArrayList<ArrayList<Integer>>> prem = preemtiveSets(singleton(markUpCells()));
		for(int i = 0; i<10; i++) {
			prem = preemtiveSets(singleton(prem));
		}
		System.out.print(prem);
		}

	//Method for getting the board
	public int[][] getSudoku() {
		return sudoku;
	}


	//Method for setting the entire board

	public void setSudoku(int[][] board){
		sudoku = board;
	}


	//Method for changing a single cell in the board
	public void setSudokuCell(int x, int y, int value) {
		sudoku[x][y] = value;
	}

	//Method for checking if the entire board is filled
	public boolean isFilled() {
		boolean result = true;
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku.length; j++) {
				if (sudoku[i][j] == 0) {
					result = false;
				}
			}
		}
		return result;
	}

	//Methods for returning N and K
	public int getN() {
		return n;
	}

	public int getK() {
		return k;
	}

	public ArrayList<ArrayList<ArrayList<Integer>>> preemtiveSets(ArrayList<ArrayList<ArrayList<Integer>>> sudokuPre) {
		//System.out.println(sudokuPre);
		change = false;
		int sizeOfSet = 2;
		int flag = 0;
		int numberOfRuns = 0;
		while (change == false) {
			ArrayList<Integer> xcord = new ArrayList<Integer>(); //arraylist to find all elements that matches 
			ArrayList<Integer> ycord = new ArrayList<Integer>();
			ArrayList<Integer> xcordSend = new ArrayList<Integer>(); //arraylist i send. Have required size
			ArrayList<Integer> ycordSend = new ArrayList<Integer>();
			ArrayList<Integer> numbers = new ArrayList<Integer>();  //array of numbers for algorithm
				for (int i = 0; i < k*n; i++) {
					for (int j = 0; j < k*n; j++) {
						xcord = new ArrayList<Integer>(); 
						ycord = new ArrayList<Integer>();
						if(sudokuPre.get(i).get(j).size() == sizeOfSet) { //checks if given square is an arraylist of the size we are intrested in
							numbers = sudokuPre.get(i).get(j);
							//System.out.println("set: " + numbers);
							xcord.add(i);
							ycord.add(j);
							int skipX = 0;
							int skipY = 0;
								for (int l = skipX; l< k*n; l++) { 
									for (int m = skipY; m< k*n; m++) {
										if(numbers.containsAll(sudokuPre.get(l).get(m)) && (l!=i || m!=j) && sudokuPre.get(l).get(m).size()>1) { //checks if all of another array is in the main array and is larger than 1					
											xcord.add(l); //adds if true
											ycord.add(m);			
											}	
										}
									}	
								//System.out.println("xcord: " + xcord);
								//System.out.println("ycord: " + ycord);
		
				if (xcord.size() >= sizeOfSet) { //checks if xcord is bigger than sets. We are not intrested in sending sets, if they are not bigger
					
						for (int l = 0; l <n*k; l++) { //goes through all numbers in sudoku
							if(Collections.frequency(xcord, l) == sizeOfSet) { //checks if there are more in a given xcord 
								for(int m=0; m < xcord.size();m++) {
									if (xcord.get(m) == l) {
										xcordSend.add(xcord.get(m));
										ycordSend.add(ycord.get(m));
									}
								}
								//System.out.print(numbers);
								sudokuPre = updateMarkup(sudokuPre,numbers,xcordSend,ycordSend, 2); 
								xcordSend.removeAll(xcordSend);
								ycordSend.removeAll(ycordSend);
							}
							if(Collections.frequency(ycord, l) == sizeOfSet) { //checks if there are more in a gived ycord
								//System.out.println("set: " + numbers + " xCoords: " +xcord +" yCoords: " + ycord);
								for(int m=0; m < xcord.size();m++) {
									if (ycord.get(m) == l) {	
										xcordSend.add(xcord.get(m));
										ycordSend.add(ycord.get(m));
									}
								}
								//System.out.println("ycord: " +ycord);
								sudokuPre = updateMarkup(sudokuPre,numbers,xcordSend,ycordSend, 1); 
								xcordSend.removeAll(xcordSend);
								ycordSend.removeAll(ycordSend);
							}
							int[] kArray = new int[k*k]; //checks if there are more in a given box
							for(int m=0; m<xcord.size();m++) {
								/*System.out.println("numbers: "+numbers);
								System.out.println("xcord: " +xcord);
								System.out.println("ycord: " +ycord);
								System.out.println("xcord:"+xcord.get(m)+" ycord: " +ycord.get(m));
								System.out.println("cellxcord: "+ (xcord.get(m)/k+1) + " cellycord: "+ (ycord.get(m)/k+1)*(3-1));
								int a = (xcord.get(m)/k+1) +(ycord.get(m)/k+1)*(3-1)-1;
								System.out.println("kArray: "+ a);*/
								kArray[(ycord.get(m)/k+1) +(xcord.get(m)/k+1)*(k-1)-1] += 1;
							}
							for(int m = 0; m<kArray.length; m++) {
								if (kArray[m] == sizeOfSet) {
									for (int p = 0; p<xcord.size(); p++) {
										if((ycord.get(p)/k+1) +(xcord.get(p)/k+1)*(k-1)-1 == m) {
											
											xcordSend.add(xcord.get(p));
											ycordSend.add(ycord.get(p));
										}
									}
									for (int b = 0; b<kArray.length;b++) {
									//System.out.println(kArray[b]);
									}
									//System.out.println("");
									//System.out.print(numbers);
									//System.out.println("set: " + numbers + " xCoords: " +xcordSend +" yCoords: " + ycordSend);
									sudokuPre = updateMarkup(sudokuPre,numbers,xcordSend,ycordSend, 3); 
									xcordSend.removeAll(xcordSend);
									ycordSend.removeAll(ycordSend);

								}}}}}}
					//System.out.println(sudokuPre);
					}
			
				//System.out.println("m: " + sizeOfSet);	
			sizeOfSet++;
			
			if (sizeOfSet > 8) {		
				break;
			}
		}
		return sudokuPre;
	}

	public int[][] createSudoku() {

		List<Integer> chooseNumberList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		Random random = new Random();

		int[][] newSudoku = new int[n * k][n * k];

		for (int i = 0; i < newSudoku[0].length; i++) {
			for (int j = 0; j < newSudoku[1].length; j++) {

				while (true) {
					int number = chooseNumberList.get(random.nextInt(chooseNumberList.size()));
					int[] square = getSquare(i, j, newSudoku);
					int[] peers = getPeers(i, j, newSudoku);
					boolean cond1 = Arrays.stream(square).anyMatch(l -> l != number);
					boolean cond2 = Arrays.stream(peers).anyMatch(l -> l != number);

					if (cond1 && cond2) {
						newSudoku[i][j] = number;
						break;
					}
				}

			}
		}

		return newSudoku;
	}

	public int[] getSquare(int axis0, int axis1, int[][] board) {
		ArrayList<Integer> square = new ArrayList<Integer>();

		// Determent the position of upper left corner of the square
		int squareX = axis0 / n; // 2
		int squareY = axis1 / n; // 1

		// Run through the scare
		for (int i = squareX * n; i < squareX * n + n; i++) {
			for (int j = squareY * n; j < squareY * n + n; j++) {
				square.add(board[i][j]);
			}
		}
		// Convert arraylist to primitive array
		return square.stream().mapToInt(i -> i).toArray();

	}

	public int[] getPeers(int axis0, int axis1, int[][] board) {
		ArrayList<Integer> peers = new ArrayList<Integer>();

		// Run through the cells two axis
		for (int i = 0; i < n * k; i++) {
			peers.add(board[axis0][i]);
		}
		for (int i = 0; i < n * k; i++) {
			peers.add(board[i][axis1]);
		}

		// Remove the duplicated cell itself.
		peers.remove((Integer) board[axis0][axis1]);

		// Convert arraylist to primitive array
		int[] lol = peers.stream().mapToInt(i -> i).toArray();

		return peers.stream().mapToInt(i -> i).toArray();
	}

  //Methods for pushing, popping and peeking stack
	public void pushStack(int[][] newBoard){
		for(int i = 0; i < sudoku.length; i++){
			for(int j = 0; j < sudoku.length; j++){
				sudokuStack[moves][i][j] = sudoku[i][j];
			}
		}
		moves++;
	}

	public int[][] popStack() {
		int[][] temp = new int[sudoku.length][sudoku.length];
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku.length; j++) {
				temp[i][j] = sudokuStack[moves][i][j];
			}
		}
		moves--;
		return temp;
	}

	public int[][] peekStack() {
		int[][] temp = new int[sudoku.length][sudoku.length];
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku.length; j++) {
				temp[i][j] = sudokuStack[moves - 1][i][j];
			}
		}
		return temp;
	}

	//Returns the size of the stack
	public int getStackSize(){
		return moves;
	}
	
	//Method for updating the markUp board, given a set of possible entries and their coordinates
	public ArrayList<ArrayList<ArrayList<Integer>>> updateMarkup(ArrayList<ArrayList<ArrayList<Integer>>> markupBoard, ArrayList<Integer> set, ArrayList<Integer> xCoords, ArrayList<Integer> yCoords, int mode){
		//System.out.print("Ycords markup: "+ yCoords);
		//System.out.print("Xcords markup: "+ xCoords);
		//System.out.print("set: "+ set);
		//System.out.println("mode: "+ mode);
		int m = set.size();
		boolean sameRow = true;
		boolean sameCol = true;
		boolean sameSquare = true;
		if (xCoords.get(0)== xCoords.get(1) && yCoords.get(0)== yCoords.get(1)) {
			System.out.println("test");
		}
		//The next 3 loops check if the entries are in the same row, column and/or square
		
		/*for(int i = 1; i < m; i++){
			if(xCoords.get(0) != xCoords.get(i)){
				sameCol = false;
			}
		}
		for(int i = 1; i < m; i++){
			if(yCoords.get(0) != yCoords.get(i)){
				sameRow = false;
			}
		}
		for(int i = 1; i < m; i++){
			if(!((xCoords.get(0) % n == xCoords.get(i) % n) && (yCoords.get(0) % n == yCoords.get(i) % n))){
				sameSquare = false;
			}
		}*/

		if(mode == 1){
			for(int i = 0; i < getSudoku().length; i++){
				if(!(xCoords.contains(i))){					
					markupBoard.get(i).get(yCoords.get(0)).removeAll(set);			
					change = true;
				}
			}
		}

		if(mode == 2){
			for(int i = 0; i < getSudoku().length; i++){
				if(!(yCoords.contains(i))){
					markupBoard.get(xCoords.get(0)).get(i).removeAll(set);
					change = true;
				}
			}
		}

		if(mode == 3){
			boolean delete = true;
			for(int i = (xCoords.get(0) - xCoords.get(0) % n); i < ((xCoords.get(0) - xCoords.get(0) % n) + n); i++){
				for(int j = (yCoords.get(0) - yCoords.get(0) % n); j < ((yCoords.get(0) - yCoords.get(0) % n) + n); j++){
					for(int l = 0; l < xCoords.size(); l++){
						if(((i == xCoords.get(l)) && j == yCoords.get(l))){
							delete = false;
							break;
							
						}
					}
					if(delete){
						markupBoard.get(i).get(j).removeAll(set);
						change = true;
					}
					delete = true;
				}
			}
		}


		return markupBoard;
	}


	public ArrayList<ArrayList<ArrayList<Integer>>> markUpCells() {

		ArrayList<ArrayList<ArrayList<Integer>>> markUpBoard = new ArrayList<>();
		for (int j = 0; j < 9; j++) {
			ArrayList<ArrayList<Integer>> rows = new ArrayList<>();
			for (int k = 0; k < 9; k++) {
				ArrayList<Integer> markUpsCells = new ArrayList<>();
				rows.add(markUpsCells);
			}
			markUpBoard.add(rows);
		}

		// Overskriv det primitive array's værider til 3D-ArrayList
		int SudukoSize = n * k;
		for (int i = 0; i < SudukoSize; i++) {
			for (int j = 0; j < SudukoSize; j++) {
				if (getSudoku()[i][j] != 0) {
					markUpBoard.get(i).get(j).add(getSudoku()[i][j]);
				}
			}
		}

		// Find mulige tal
		for (int i = 0; i < SudukoSize; i++) {
			for (int j = 0; j < SudukoSize; j++) {
				if (getSudoku()[i][j] == 0) {

					// KOPIER SUDOKUBOARDET
					int[][] copyOfSudoku = new int[getSudoku().length][];
					for (int p = 0; p < copyOfSudoku.length; ++p) {

						// allocating space for each row of destination array
						copyOfSudoku[p] = new int[getSudoku()[p].length];

						for (int o = 0; o < copyOfSudoku[p].length; ++o) {
							copyOfSudoku[p][o] = getSudoku()[p][o];
						}
					}

					for (int q = 1; q <= 9; q++) { // Mulige tal som kan indsættes på boarded

						// Indsæt gyldige tal fra 1-9
						copyOfSudoku[i][j] = q;
						if (checkValidity(copyOfSudoku)) {
							markUpBoard.get(i).get(j).add(q);
						}
					}
				}
			}
		}
		return markUpBoard;
	}
	public ArrayList<ArrayList<ArrayList<Integer>>> singleton(ArrayList<ArrayList<ArrayList<Integer>>> sudokuSing) {
		//System.out.println("start: "+sudokuSing);
		for(int i = 0; i< n*k ; i++) {
			for(int j = 0; j< n*k ; j++) {
				if (sudokuSing.get(i).get(j).size() == 1) {

					for (int l = 0; l < n*k; l++ ) {
						if ( j!=l) {
							sudokuSing.get(i).get(l).remove(sudokuSing.get(i).get(j).get(0));
						} if (i!=l ) {
				
							sudokuSing.get(l).get(j).remove(sudokuSing.get(i).get(j).get(0));
						} if (i!=(i/n)*n+l%n || j!=(j/n)*n+l%n) {
							
							sudokuSing.get((i/n)*n+l%n).get((j/n)*n+l%n).remove(sudokuSing.get(i).get(j).get(0));
						}
				
		}
		
	}}
}


		//System.out.println("end: "+sudokuSing);	
		return sudokuSing;
	}
	
	public static boolean checkValidity(int[][] sudoku) {
		boolean valid = new Boolean(true);
		// Grid for storing already found values
		// int[][] sortedGrid = new int[sudoku.length+1][sudoku.length+1];
		int[][] sortedGrid = new int[sudoku.length][sudoku.length];
		// for(int i = sortedGrid.length-1; i >= 0; i--){
		for (int i = sortedGrid.length - 1; i >= 0; i--) {
			for (int j = 0; j < sortedGrid.length; j++) {
				sortedGrid[i][j] = 0;
			}
		}

		// Checking rows for duplicates
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku.length; j++) {
				int cur = (sudoku[i][j]);
				if (cur != 0) {
					if (sortedGrid[i][cur - 1] == 0) {
						sortedGrid[i][cur - 1] = 1;
					} else {
						valid = false;
					}
				}

			}
		}

		/*
		 * for(int i = 0; i < sortedGrid.length; i++){
		 * for(int k = 0; k < sortedGrid.length; k++){
		 * System.out.print(sortedGrid[i][k] + " ");
		 * }
		 * System.out.println();
		 * }
		 * System.out.println();
		 */

		// Resetting the sorted grid
		for (int i = sortedGrid.length - 1; i >= 0; i--) {
			for (int j = 0; j < sortedGrid.length; j++) {
				sortedGrid[i][j] = 0;
			}
		}

		// Checking columns for duplicates
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku.length; j++) {
				int cur = (sudoku[j][i]);
				if (cur != 0) {
					if (sortedGrid[i][cur - 1] == 0) {
						sortedGrid[i][cur - 1] = 1;
					} else {
						valid = false;
					}
				}
			}
		}

		// Resetting the sorted grid
		for (int i = sortedGrid.length - 1; i >= 0; i--) {
			for (int j = 0; j < sortedGrid.length; j++) {
				sortedGrid[i][j] = 0;
			}
		}

		// Checking each square
		/*
		 * for (int r = 0; r < Math.sqrt(sudoku.length); r++) {
		 * for (int c = 0; c < Math.sqrt(sudoku.length); c++) {
		 * for (int br = 0; br < Math.sqrt(sudoku.length); br++) {
		 * for (int bc = 0; bc < Math.sqrt(sudoku.length); bc++) {
		 * int cur = sudoku[(c * 3) + bc][(r * 3) + br];
		 * if (cur != 0) {
		 * if (sortedGrid[c + r * 3][cur - 1] == 0) {
		 * sortedGrid[c + r * 3][cur - 1] = 1;
		 * } else {
		 * valid = false;
		 * }
		 * }
		 * }
		 * }
		 * }
		 * }
		 */

		for (int l = 0; l < k * k; l++) {

			for (int i = 0; i < n; i++) {

				for (int j = 0; j < n; j++) {// l/k benytter sig af hvordan java runder op. det er n hvor mange felter
												// den skal rygge, og den skal rygge det hver gang l har bev�get sig k
												// felter.
					int cur = sudoku[(i + n * (l / k))][(j + n * l) % (k * n)];
					if (cur != 0) {
						if (sortedGrid[l][cur - 1] == 0) {
							sortedGrid[l][cur - 1] = 1;
						} else {
							valid = false;
						}
					}
				}
			}
		}

		/*
		 * for(int i = 0; i < sortedGrid.length; i++){
		 * for(int k = 0; k < sortedGrid.length; k++){
		 * System.out.print(sortedGrid[i][k] + " ");
		 * }
		 * System.out.println();
		 * }
		 */

		return valid;
	}

	public void printSudoku(int[][] sudokuBoard) {
		for (int i = 0; i < sudokuBoard.length; i++) {
			for (int k = 0; k < sudokuBoard.length; k++) {
				System.out.print(sudokuBoard[i][k] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

}
