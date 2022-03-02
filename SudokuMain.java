import java.io.File;

public class SudokuMain {
	
	public static void main(String[] args) {
		File file = new File("test.txt");
		SudokuModel model = new SudokuModel();
		SudokuView view = new SudokuView(model);
		model.loadASCIIFile(file);
		SudokuController controller = new SudokuController(model, view);
		view.setVisible(view.f);
	}
}
