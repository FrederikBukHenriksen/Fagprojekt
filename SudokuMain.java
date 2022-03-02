package sudoku;
import java.io.File;

public class SudokuMain {
	
	public static void main(String[] args) {
<<<<<<< HEAD
		File file = new File("test.txt");
<<<<<<< HEAD
		SudokuModel model = new SudokuModel();
=======
=======
		File file = new File("E:\\Eclibse\\Sudoku\\src\\test.txt");
>>>>>>> parent of 39df90c (stable merge af de to)
		SudokuModel model = new SudokuModel(file);
>>>>>>> parent of 0d2340b (funktionalitet i Model)
		SudokuView view = new SudokuView(model);
		model.loadASCIIFile(file);
		SudokuController controller = new SudokuController(model, view);
		view.setVisible(view.f);
	}
}
