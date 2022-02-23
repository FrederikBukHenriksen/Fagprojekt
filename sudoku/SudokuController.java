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
		

		return valid;
	}
}
