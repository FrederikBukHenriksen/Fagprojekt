import java.io.File;

public class SudokuMain {
	
	public static void main(String[] args) {
		File file = new File("test.txt");
		SudokuModel model = new SudokuModel();
		model.loadASCIIFile(file);
		model.createSudokuSections();
		SudokuView view = new SudokuView(model);
		SudokuController controller = new SudokuController(model, view);
		view.setVisible(view.f);
	}
}
