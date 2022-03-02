package sudoku;
import java.io.File;

public class SudokuMain {
	
	public static void main(String[] args) {
		File file = new File("C:\\Users\\ramel\\OneDrive\\Dokumenter\\GitHub\\Fagprojekt\\sudoku\\test.txt");
		SudokuModel model = new SudokuModel(file);
		SudokuView view = new SudokuView(model);
		SudokuController controller = new SudokuController(model, view);
		view.setVisible(view.f);
	}
}