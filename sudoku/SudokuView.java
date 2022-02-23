package sudoku;
import javax.swing.*;

public class SudokuView {
	
	private SudokuModel model;
	
	public JFrame frame = new JFrame();
	
	public SudokuView(SudokuModel model) {
		this.model = model;

		JButton button = new JButton("Start");
		button.setBounds(130,100,100,40);
		frame.add(button);
		frame.setSize(400,500);
		frame.setLayout(null);
		if (SudokuController.checkValidity(model.sudoku)){
			frame.setTitle("Valid puzzle");
		}
		else{
			frame.setTitle("Invalid puzzle");
		}
	}
	
	public void setVisible(JFrame frame) {
		frame.setVisible(true);
	}
	
}
