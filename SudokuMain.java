package sudoku;
import java.io.File;

public class SudokuMain {
	
	public static void main(String[] args) {
		File file = new File("C:\\Users\\Candytom\\eclipse-workspace\\Sudoku\\src\\test.txt");
		SudokuModel model = new SudokuModel(file);
		SudokuView view = new SudokuView(model);
		SudokuController controller = new SudokuController(model, view);
		view.setVisible(view.f);
	}
}
