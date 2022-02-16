package sudoku;

public class SudokuMain {
	
	public static void main(String[] args) {
		SudokuModel model = new SudokuModel();
		SudokuView view = new SudokuView(model);
		SudokuController controller = new SudokuController(model, view);
		view.setVisible(view.frame);
	}
}
