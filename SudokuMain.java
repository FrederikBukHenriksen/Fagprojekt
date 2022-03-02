import java.io.File;

public class SudokuMain {
	
	public static void main(String[] args) {
		File file = new File("test.txt");
<<<<<<< HEAD
		SudokuModel model = new SudokuModel();
=======
		SudokuModel model = new SudokuModel(file);
>>>>>>> parent of 0d2340b (funktionalitet i Model)
		SudokuView view = new SudokuView(model);
		model.loadASCIIFile(file);
		SudokuController controller = new SudokuController(model, view);
		view.setVisible(view.f);
	}
}
