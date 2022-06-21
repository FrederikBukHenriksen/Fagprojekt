package sudoku.Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.JFileChooser;

public class SudokuFileLoader {

    static int n;
    static int k;
    static int[][] sudoku;
    static int[] xSums;
    static int[] ySums;
	/*
	 * Author: Christian, edited by all
	 * Function: Opens sudoku file, and creates sudoku in simple array
	 * Inputs: ArrayList
     * Outputs: None
	 */
    public static ArrayList<Object> LoadSudokuBoardDoc()
            throws IOException,
            NumberFormatException, NoSuchElementException {

        String gameMode = "classic"; // Standard value

        Path file = null;
        file = findSudokuPath("C:\\");
        // System.out.println(file);
        if (file == null) {
            System.exit(0);
        }
        Scanner scanner;
        // reading the input
        scanner = new Scanner(file);
        String setup = scanner.next();
        Scanner setupScanner = new Scanner(setup);
        setupScanner.useDelimiter(";");
        // reading k & n
        while (setupScanner.hasNext()) {
            String str = setupScanner.next();
            k = Integer.parseInt(str);
            str = setupScanner.next();
            n = Integer.parseInt(str);

        }
        setupScanner.close();
        if (k > n) {
        } else {// Creating the board
            sudoku = new int[n * k][n * k];
            // Creating variables for sandwich Sums
            xSums = new int[n * k];
            ySums = new int[n * k];
            // Creating variables for looping through input
            int c = 0;
            int d = 0;
            scanner.nextLine();
            for (int j = 0; j < n * k; j++) {
                // Reads the next line
                String line = scanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(";");
                while (lineScanner.hasNext()) {
                    // Reads the next input on the line, separated by ";"
                    String str = lineScanner.next();
                    if (str.equals(".")) {
                        // If input is ".", convert to a "0"
                        sudoku[c][d] = 0;
                        // Go to next entry
                        d++;
                    } else {
                        // If input isn't ".", read the number and insert into array
                        sudoku[c][d] = Integer.parseInt(str);
                        // Go to next entry
                        d++;
                    }
                }
                // Go to next line, and start from first entry
                c++;
                d = 0;
                lineScanner.close();
            }
            if (scanner.hasNextLine()) {
                gameMode = "sandwich";
                // model.setSandwich(true);
                String line = scanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(":");
                int index = 0;
                while (lineScanner.hasNext()) {
                    String str = lineScanner.next();
                    // If input isn't ".", read the number and insert into array
                    xSums[index] = Integer.parseInt(str);
                    index++;

                }
                lineScanner.close();
                line = scanner.nextLine();
                lineScanner = new Scanner(line);
                lineScanner.useDelimiter(":");
                index = 0;
                while (lineScanner.hasNext()) {
                    String str = lineScanner.next();
                    // If input isn't ".", read the number and insert into array
                    ySums[index] = Integer.parseInt(str);
                    index++;
                }
            }
        }

        // JSON object or similar with a connection between indexname and value would be
        // a better solution.
        ArrayList<Object> returnArray = new ArrayList<>();
        switch (gameMode.toLowerCase()) {
            case "classic":
                returnArray.add(gameMode);
                returnArray.add(sudoku);
                returnArray.add(n);
                returnArray.add(k);
                break;

            case "sandwich":
                returnArray.add(gameMode);
                returnArray.add(sudoku);
                returnArray.add(n);
                returnArray.add(k);
                returnArray.add(xSums);
                returnArray.add(ySums);
                break;

            default:
                break;
        }
        return returnArray;

    }
	/*
	 * Author: Christian, Originally by Stackoverflow
	 * Function: Creates filebrowser, and returns path
	 * Inputs: Starting string, we use the C:/ drive
     * Outputs: Path
	 */
    public static Path findSudokuPath(String s) {// https://stackoverflow.com/questions/51973636/how-to-return-the-file-path-from-the-windows-file-explorer-using-java
        // File file = new
        // File("C:\\Users\\Candytom\\Documents\\GitHub\\sudoku\\Puzzles_1\\Puzzle_3_evil.dat");
        Path file = null;
        JFileChooser jd = s == null ? new JFileChooser() : new JFileChooser(s);
        jd.setDialogTitle("Choose Sudoku you wish to solve");
        int returnVal = jd.showOpenDialog(null);
        /* If user didn't select a file and click ok, return null Path object */
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return file = jd.getSelectedFile().toPath();
        }
        return null;

    }

}
