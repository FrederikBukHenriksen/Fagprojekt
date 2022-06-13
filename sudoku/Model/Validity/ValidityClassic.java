package sudoku.Model.Validity;

import java.util.ArrayList;
import java.awt.Point;

public class ValidityClassic extends ValidityExtend {

    // Class variables

    protected int n;
    protected int k;

    // Constructor

    public ValidityClassic(int[][] sudoku, int n, int k) {
        this.sudoku = sudoku;
        this.n = n;
        this.k = k;
    }

    // Interface methods

    public boolean checkValidity() {
        boolean valid = false;
        ArrayList<Point> allConflicts = uniquePoints(collectConflictPointMatches());
        if (allConflicts.isEmpty()) {
            valid = true;
        }
        return valid;
    }

    public boolean checkValidity(int[][] sudoku) {
        this.sudoku = sudoku;
        boolean valid = false;
        ArrayList<Point> allConflicts = uniquePoints(collectConflictPointMatches());
        if (allConflicts.isEmpty()) {
            valid = true;
        }
        return valid;
    }

    public ArrayList<Point> getUniqueConflictPoints() {
        return uniquePoints(collectConflictPointMatches());
    }

    // Set methods

    public void setN(int n) {
        this.n = n;
    }

    public void setK(int k) {
        this.k = k;
    }

    // Get methods

    public int getN() {
        return n;
    }

    public int getK() {
        return k;
    }

    // Class methods

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

    protected ArrayList<ArrayList<Point>> collectConflictPointMatches() {
        ArrayList<ArrayList<Point>> allConflicts = new ArrayList<>();

        for (ArrayList<Point> conflict : findRowConflicts()) {
            allConflicts.add(conflict);
        }
        for (ArrayList<Point> conflict : findColConflicts()) {
            allConflicts.add(conflict);
        }
        for (ArrayList<Point> conflict : findSquareConflicts()) {
            allConflicts.add(conflict);
        }
        return allConflicts;
    }

    protected ArrayList<ArrayList<Point>> findRowConflicts() {
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

    protected ArrayList<ArrayList<Point>> findColConflicts() {
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

    protected ArrayList<ArrayList<Point>> findSquareConflicts() {
        ArrayList<ArrayList<Point>> allConflicts = new ArrayList<>();

        for (int s1 = 0; s1 < k; s1++) {
            for (int s2 = 0; s2 < k; s2++) {

                // Set-up array, with empty arraylists inside.
                ArrayList<Point>[] numberRangeSquare = new ArrayList[n * n];
                for (int q = 0; q < n * k; q++) {
                    numberRangeSquare[q] = new ArrayList<Point>();
                }

                for (int c1 = 0; c1 < n; c1++) {
                    for (int c2 = 0; c2 < n; c2++) {
                        int axis0 = s1 * 3 + c1;
                        int axis1 = s2 * 3 + c2;

                        if (sudoku[axis0][axis1] > 0) {
                            numberRangeSquare[(sudoku[axis0][axis1] - 1)].add(new Point(axis0, axis1));
                        }

                    }
                }

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
