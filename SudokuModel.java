import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JToggleButton;

import java.io.File;
import java.io.FileNotFoundException;

public class SudokuModel {

	int[][] sudoku = new int[0][0];
	ArrayList<ArrayList<int[][]>> allSections = new ArrayList<>();

	int n, k;

	public SudokuModel() {
	}

	public void loadASCIIFile(File file) {
		Scanner scanner;
		try {
			scanner = new Scanner(file);
			String setup = scanner.next();
			n = Character.getNumericValue(setup.charAt(0));
			k = Character.getNumericValue(setup.charAt(2));

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

	public void createSudokuSections() {
		// Controls the shifting between sections.
		for (int x = 0; x < 3; x++) {
			ArrayList<int[][]> rowSections = new ArrayList<>();
			for (int i = 0; i < 3; i++) {

				// Runs through a single section.
				int[][] section = new int[3][3];
				for (int l = 0; l < 3; l++) {
					for (int m = 0; m < 3; m++) {
						section[l][m] = sudoku[l + x * 3][m + i * 3];
					}
				}
				rowSections.add(section);
			}
			allSections.add(rowSections);
		}
	}

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
	public int[][] getSudoku() {
		return sudoku;
	}

}
