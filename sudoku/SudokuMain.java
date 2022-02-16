package sudoku;
import java.io.File;

public class SudokuMain {
	
	public static void main(String[] args) {
		File file = new File("C:\\Users\\HP G1\\Documents\\GitHub\\Fagprojekt\\sudoku\\test.txt");
		SudokuModel model = new SudokuModel(file);
		SudokuView view = new SudokuView(model);
		SudokuController controller = new SudokuController(model, view);
		view.setVisible(view.frame);
	}
}
