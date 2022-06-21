package sudoku.Model.Validity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ValiditySandwich extends ValidityClassic {

    // Class variables
    protected int[] xSums;
    protected int[] ySums;

    // Constructor
    /*
     * Author: Rasmus, updated by Frederik
     * Function: Constructs a Validity Checker for sandwich Sudoku's
     * Inputs: The sudoku board to be examined
     * Outputs: The ArrayList of points, containing all conflicts found (including
     * duplicates)
     */
    public ValiditySandwich(int[][] sudoku, int n, int k, int[] xSums, int[] ySums) {
        super(sudoku, n, k);
        this.xSums = xSums;
        this.ySums = ySums;
    }

    // Class methods
    /*
     * Author: Rasmus, updated by Frederik
     * Function: Goes through all the standard row, column and square checks, and
     * then adds the new sandwich checks for rows and columns
     * Inputs: The sudoku board to be examined
     * Outputs: The ArrayList of points, containing all conflicts found (including
     * duplicates)
     */
    @Override
    protected ArrayList<ArrayList<Point>> collectConflictPointMatches(int[][] sudoku) {
        ArrayList<ArrayList<Point>> allConflicts = super.collectConflictPointMatches(sudoku);

        for (ArrayList<Point> conflict : findRowSumConflicts(sudoku)) {
            allConflicts.add(conflict);
        }

        for (ArrayList<Point> conflict : findColSumConflicts(sudoku)) {
            allConflicts.add(conflict);
        }

        return allConflicts;
    }

    /*
     * Author: Rasmus, updated by Frederik
     * Function: Goes through all rows, and checks if the sum between 1 and 9 (if
     * they exist) is equal to the expected sum
     * Inputs: The sudoku board to be examined
     * Outputs: The ArrayList of points, containing all conflicts found (including
     * duplicates)
     */
    protected ArrayList<ArrayList<Point>> findRowSumConflicts(int[][] sudoku) {
        ArrayList<ArrayList<Point>> sandwichConflicts = new ArrayList<>();

        for (int i = 0; i < sudoku.length; i++) {
            ArrayList<Point> sandwichRow = new ArrayList<>();

            Point sandwichStart = null;
            Point sandwichEnd = null;

            for (int j = 0; j < sudoku.length; j++) {
                // A start has been found, but no ending
                if (sandwichStart != null && sandwichEnd == null) {
                    if ((sudoku[i][j]) == 1 || (sudoku[i][j]) == n * k) {
                        sandwichEnd = new Point(i, j);
                    } else {
                        sandwichRow.add(new Point(i, j));
                    }
                }
                // A start hasn't been found
                if (sandwichStart == null) {
                    if ((sudoku[i][j]) == 1 || (sudoku[i][j]) == n * k) {
                        sandwichStart = new Point(i, j);
                    }
                }
            }

            boolean valid = true;
            int sandwichSum = 0;

            // Check for unfilled elements and calculate sum
            for (Point point : sandwichRow) {
                int sudokuValue = sudoku[point.x][point.y];
                if (sudokuValue == 0) {
                    valid = false;
                } else {
                    sandwichSum += sudokuValue;
                }
            }

            if (valid && sandwichSum != ySums[i] && sandwichStart != null && sandwichEnd != null) {
                sandwichRow.add(sandwichStart);
                sandwichRow.add(sandwichEnd);
                sandwichConflicts.add(sandwichRow);
            }
        }
        return sandwichConflicts;
    }

    /*
     * Author: Rasmus, updated by Frederik
     * Function: Goes through all columns, and checks if the sum between 1 and 9 (if
     * they exist) is equal to the expected sum
     * Inputs: The sudoku board to be examined
     * Outputs: The ArrayList of points, containing all conflicts found (including
     * duplicates)
     */
    protected ArrayList<ArrayList<Point>> findColSumConflicts(int[][] sudoku) {
        ArrayList<ArrayList<Point>> sandwichConflicts = new ArrayList<>();

        for (int i = 0; i < sudoku.length; i++) {
            ArrayList<Point> sandwichRow = new ArrayList<>();

            Point sandwichStart = null;
            Point sandwichEnd = null;

            for (int j = 0; j < sudoku.length; j++) {
                // Der fundet en start, men ingen ende.
                if (sandwichStart != null && sandwichEnd == null) {
                    if ((sudoku[j][i]) == 1 || (sudoku[j][i]) == n * k) {
                        sandwichEnd = new Point(j, i);
                    } else {
                        sandwichRow.add(new Point(j, i));
                    }
                }
                // Der ikke fundet en start
                if (sandwichStart == null) {
                    if ((sudoku[j][i]) == 1 || (sudoku[j][i]) == n * k) {
                        sandwichStart = new Point(j, i);
                    }
                }
            }

            boolean valid = true;
            int sandwichSum = 0;

            // Check for unfilled elements and calculate sum
            for (Point point : sandwichRow) {
                int sudokuValue = sudoku[point.x][point.y];
                if (sudokuValue == 0) {
                    valid = false;
                } else {
                    sandwichSum += sudokuValue;
                }
            }

            if (valid && sandwichSum != xSums[i] && sandwichStart != null && sandwichEnd != null) {
                sandwichRow.add(sandwichStart);
                sandwichRow.add(sandwichEnd);
                sandwichConflicts.add(sandwichRow);
            }
        }
        return sandwichConflicts;
    }

}
