package sudoku;

public class SudokuController {
	
	//Creating variables
	private SudokuModel model;
	
	private SudokuView view;
	
	//Simple constructor
	public SudokuController(SudokuModel model, SudokuView view) {
		this.model = model;
		this.view = view;
	}
	
	public static boolean checkValidity(int[][] sudoku){
		boolean valid = new Boolean(true);
		int[][] sortedGrid = new int[sudoku.length][sudoku.length];
		for(int i = 0; i < sudoku.length; i++){
			for(int k = 0; k < sudoku.length; k++){
				sortedGrid[i][k] = 0;
			}
		}
		
		for(int i = 0; i < sudoku.length; i++){
			for(int k = 0; k < sudoku.length; k++){
				int cur = sudoku[k][i];
				if(sortedGrid[i][cur] == 0){
					sortedGrid[i][cur] = 1;
				}
				else{
					valid = false;
				}
			}
		}

		return valid;
	}
}
