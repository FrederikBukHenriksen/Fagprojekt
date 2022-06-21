package sudoku.Model.Validity;

import java.util.ArrayList;
import java.awt.Point;

public class ValidityClassic implements ValidityInterface {

    // Class variables

    protected int n;
    protected int k;

    /*
     * Author: Rasmus, updated by Frederik
     * Function: Constructs the validitychecker for a classic Sudoku
     * Inputs: The sudoku board as an int[][] array, as well as the variables n and
     * k
     * Outputs: None
     */
    public ValidityClassic(int[][] sudoku, int n, int k) {
        // this.sudoku = sudoku;
        this.n = n;
        this.k = k;
    }

    // Interface methods
    /*
     * Author: Rasmus, updated by Frederik
     * Function: Checks if the Arraylist allConflicts is empty, meaning that the
     * input sudoku is valid. allConflicts is made using methods described below
     * Inputs: The Sudokuboard to be validated
     * Outputs: The boolean valid, corresponding to the validity ofthe sudoku
     */
    public boolean checkValidity(int[][] sudoku) {
        // this.sudoku = sudoku;
        boolean valid = false;
        ArrayList<Point> allConflicts = uniquePoints(collectConflictPointMatches(sudoku));
        if (allConflicts.isEmpty()) {
            valid = true;
        }
        return valid;
    }

    /*
     * Author: Rasmus, updated by Frederik
     * Function: Gets all conflicts from the input Sudokuboard, and returns an
     * ArrayList without duplicate values, containing all conflicts
     * Inputs: The sudokuBoard, on which to find conflicts
     * Outputs: The new ArrayList containing all conflicts, without duplicates
     */
    public ArrayList<Point> getUniqueConflictPoints(int[][] sudoku) {
        return uniquePoints(collectConflictPointMatches(sudoku));
    }

    // Class methods
    /*
     * Author: Frederik
     * Function: Goes through the ArrayList from input, and adds each value to the
     * output if it isn't already present
     * Inputs: An ArrayList
     * Outputs:
     */
    protected ArrayList<Point> uniquePoints(ArrayList<ArrayList<Point>> input) {
        ArrayList<Point> uniqueConflicts = new ArrayList<>();
        for (ArrayList<Point> list : input) {
            for (Point point : list) {
                if (!uniqueConflicts.contains(point)) {
                    uniqueConflicts.add(point);
                }
            }
        }
        return uniqueConflicts;
    }

    /*
     * Author: Frederik
     * Function: Takes a sudoku, and calls the three methods for finding conflicts
     * in rows, columns and squares
     * Inputs: The sudoku board to be examined
     * Outputs: The ArrayList of points, containing all conflicts found (including
     * duplicates)
     */
    protected ArrayList<ArrayList<Point>> collectConflictPointMatches(int[][] sudoku) {
        ArrayList<ArrayList<Point>> allConflicts = new ArrayList<>();

        for (ArrayList<Point> conflict : findRowConflicts(sudoku)) {
            allConflicts.add(conflict);
        }
        for (ArrayList<Point> conflict : findColConflicts(sudoku)) {
            allConflicts.add(conflict);
        }
        for (ArrayList<Point> conflict : findSquareConflicts(sudoku)) {
            allConflicts.add(conflict);
        }
        return allConflicts;
    }

    /*
     * Author: Rasmus, updated by Frederik
     * Function: Finds all the conflicts in the rows of a sudoku
     * Inputs: The sudoku board to be examined
     * Outputs: The ArrayList of points, containing all conflicts found (including
     * duplicates)
     */
    protected ArrayList<ArrayList<Point>> findRowConflicts(int[][] sudoku) {
        ArrayList<ArrayList<Point>> allConflicts = new ArrayList<>();

        for (int i = 0; i < sudoku.length; i++) {
            // Set-up array, with empty arraylists inside.
            ArrayList<Point>[] numberRangeRow = new ArrayList[n * k];
            for (int q = 0; q < n * k; q++) {
                numberRangeRow[q] = new ArrayList<Point>();
            }

            for (int j = 0; j < sudoku.length; j++) {
                if ((sudoku[i][j]) > 0) {

                    numberRangeRow[sudoku[i][j] - 1].add(new Point(i, j));
                }
            }

            for (int q = 0; q < numberRangeRow.length; q++) {
                if (numberRangeRow[q].size() > 1) {
                    allConflicts.add(numberRangeRow[q]);
                }
            }
        }
        return allConflicts;
    }

    /*
     * Author: Rasmus, updated by Frederik
     * Function: Finds all the conflicts in the columns of a sudoku
     * Inputs: The sudoku board to be examined
     * Outputs: The ArrayList of points, containing all conflicts found (including
     * duplicates)
     */
    protected ArrayList<ArrayList<Point>> findColConflicts(int[][] sudoku) {
        ArrayList<ArrayList<Point>> allConflicts = new ArrayList<>();

        for (int i = 0; i < sudoku.length; i++) {

            // Set-up array, with empty arraylists inside.
            ArrayList<Point>[] numberRangeCol = new ArrayList[n * k];
            for (int q = 0; q < n * k; q++) {
                numberRangeCol[q] = new ArrayList<Point>();
            }

            for (int j = 0; j < sudoku.length; j++) {
                if ((sudoku[j][i]) > 0) {
                    numberRangeCol[(sudoku[j][i] - 1)].add(new Point(j, i));
                }
            }

            for (int q = 0; q < numberRangeCol.length; q++) {
                if (numberRangeCol[q].size() > 1) {
                    allConflicts.add(numberRangeCol[q]);
                }
            }
        }
        return allConflicts;
    }

    /*
     * Author: Rasmus, updated by Frederik
     * Function: Finds all the conflicts in the squares of a sudoku
     * Inputs: The sudoku board to be examined
     * Outputs: The ArrayList of points, containing all conflicts found (including
     * duplicates)
     */
    protected ArrayList<ArrayList<Point>> findSquareConflicts(int[][] sudoku) {
        ArrayList<ArrayList<Point>> allConflicts = new ArrayList<>();

        // Double for-loop for visiting each square in the sudoku
        for (int s1 = 0; s1 < k; s1++) {
            for (int s2 = 0; s2 < k; s2++) {

                // Set-up array, with empty arraylists inside.
                ArrayList<Point>[] numberRangeSquare = new ArrayList[n * n];
                for (int q = 0; q < n * n; q++) {
                    numberRangeSquare[q] = new ArrayList<Point>();
                }
                // Double for-loop for visiting each cell in the current square
                for (int c1 = 0; c1 < n; c1++) {
                    for (int c2 = 0; c2 < n; c2++) {
                        int axis0 = s1 * n + c1;
                        int axis1 = s2 * n + c2;
                        // Adds the number to the array, if the cell isn't empty
                        if (sudoku[axis0][axis1] > 0) {
                            numberRangeSquare[(sudoku[axis0][axis1] - 1)].add(new Point(axis0, axis1));
                        }

                    }
                }
                // If an index of the array contains more than one pair of coordinates, it means
                // that there are duplicate values, and these are added to the output
                for (int q = 0; q < numberRangeSquare.length; q++) {
                    if (numberRangeSquare[q].size() > 1) {
                        allConflicts.add(numberRangeSquare[q]);
                    }
                }
            }
        }
        return allConflicts;
    }
}
