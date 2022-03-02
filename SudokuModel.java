import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class SudokuModel {

	int[][] sudoku = new int[0][0];

	public SudokuModel(File file) {
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			String setup = scanner.next();
			int n = Character.getNumericValue(setup.charAt(0));
			int k = Character.getNumericValue(setup.charAt(2));

			sudoku = new int[n * n][n * n];
			for (int i = 0; i < n * n; i++) {
				String line = scanner.next();
				for (int j = 0; j < n * n; j++) {
					if (line.charAt(j * 2) == '.') {
						sudoku[i][j] = 0;
					} else {
						sudoku[i][j] = Character.getNumericValue(line.charAt(j * 2));
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

<<<<<<< HEAD
	public ArrayList<ArrayList<int[][]>> getSections() {
		return allSections;
	}

	public int[][] getSection(int x, int y) {
		return allSections.get(x).get(y);
	}

	/**
	 * @param coordinates of a section
	 * @return a primitive array containing the numbers of the section
	 */
	public int[] getSectionList(int x, int y) {
		int[][] section = allSections.get(x).get(y);

		ArrayList<Integer> temp = new ArrayList<>();
		for (int i = 0; i < (section.length); i++) {
			for (int j = 0; j < section.length; j++) {
				temp.add(section[i][j]);
			}
		}
		return temp.stream().mapToInt(i -> i).toArray();
	}

	/**
	 * @return a primitive 2D-array containing the sudoku board's numbers
	 */
=======
>>>>>>> parent of 0d2340b (funktionalitet i Model)
	public int[][] getSudoku() {
		return sudoku;
	}

}
