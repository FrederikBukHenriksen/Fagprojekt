package sudoku.Controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Scanner;

import sudoku.Model.Model;
import sudoku.Model.Solver.BacktrackAlgorithm;
import sudoku.Model.Solver.CrooksAlgorithm;

public class LoadSudokuBoardFile {
	/*
	 * Author: Christian
	 * Function: Loads sudokuboard, and assign if it is a sandwich or normal sudoku
	 * Inputs: Takes model and controller
	 */
    public static void LoadSudokuBoardDoc(Controller controller, Model model)
            throws IOException,
            NumberFormatException, NoSuchElementException {

        Path file = null;
        file = controller.model.findSudokuPath("C:\\");
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
            model.k = Integer.parseInt(str);
            str = setupScanner.next();
            model.n = Integer.parseInt(str);

        }
        setupScanner.close();
        if (model.k > model.n) {
        } else {// Creating the board
            controller.model.sudoku = new int[model.n * model.k][model.n * model.k];
            // Creating variables for sandwich Sums
            model.xSums = new int[model.n * model.k];
            model.ySums = new int[model.n * model.k];
            // Creating variables for looping through input
            int c = 0;
            int d = 0;
            scanner.nextLine();
            for (int j = 0; j < model.n * model.k; j++) {
                // Reads the next line
                String line = scanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(";");
                while (lineScanner.hasNext()) {
                    // Reads the next input on the line, separated by ";"
                    String str = lineScanner.next();
                    if (str.equals(".")) {
                        // If input is ".", convert to a "0"
                        controller.model.sudoku[c][d] = 0;
                        // Go to next entry
                        d++;
                    } else {
                        // If input isn't ".", read the number and insert into array
                        controller.model.sudoku[c][d] = Integer.parseInt(str);
                        // Go to next entry
                        d++;
                    }
                }
                // Go to next line, and start from first entry
                c++;
                d = 0;
                lineScanner.close();
            }
            controller.model.crooks = new CrooksAlgorithm(controller.model.getN(), controller.model.getK(),
                    controller.model.getSudoku(), controller.model);
            if (scanner.hasNextLine()) {
                controller.model.setSandwich(true);
                String line = scanner.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(":");
                int index = 0;
                while (lineScanner.hasNext()) {
                    String str = lineScanner.next();
                    // If input isn't ".", read the number and insert into array
                    model.xSums[index] = Integer.parseInt(str);
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
                    model.ySums[index] = Integer.parseInt(str);
                    index++;
                }
                controller.model.backtrack = new BacktrackAlgorithm(controller.model.getN(),
                        controller.model.getK(), model.xSums, model.ySums, controller.model.sudoku,
                        controller.model);
            }
        }
        // break;

        // view = new View(model.getSudoku(), model.getN(), model.getK());

    }

}
