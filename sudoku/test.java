package sudoku;

import java.io.File;
import java.io.IOException;
import java.awt.Desktop;
import java.lang.Object;
import java.nio.file.Path;

import javax.swing.JFileChooser;


public class test {
	public static void main(String[] args) {
		Path test = getInputPath("C:\\Users\\Candytom\\Documents\\GitHub\\sudoku\\Puzzles_1");
		System.out.print(test);
	}
	public static Path getInputPath(String s) {//https://stackoverflow.com/questions/51973636/how-to-return-the-file-path-from-the-windows-file-explorer-using-java
	        
	         /*Send a path (a String path) to open in a specific directory
	         or if null default directory */
	         JFileChooser jd = s == null ? new JFileChooser() : new JFileChooser(s);
	         jd.setDialogTitle("Choose where Sudoku exist");
	         int returnVal= jd.showOpenDialog(null);
	         /* If user didn't select a file and click ok, return null Path object*/
	         if (returnVal != JFileChooser.APPROVE_OPTION) return null;
	         return jd.getSelectedFile().toPath();
	    
	    }
}
	

