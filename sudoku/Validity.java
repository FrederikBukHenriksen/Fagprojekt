package sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.stream.Collectors;
import java.awt.Point;
import javax.swing.JFileChooser;

import sudoku.View.SudokuBoard.*;

import java.util.Random;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;

public class Validity {

    SudokuController sudokuController;
    public static ArrayList<Point> failedCoordsPoint = new ArrayList<Point>();

    int n;
    int k;

    public static int[] xSums;
    public static int[] ySums;

    public Validity(SudokuController sudokuController) {
        this.sudokuController = sudokuController;
        this.n = sudokuController.model.getN();
        this.k = sudokuController.model.getK();

        xSums = new int[n * k];
        ySums = new int[n * k];
    }

    public void addPoint(int x, int y) {
        if (!failedCoordsPoint.stream().anyMatch(point -> (point.x == x && point.y == y))) {
            failedCoordsPoint.add(new Point(x, y));
        }
    }

    public boolean checkValidity(int[][] sudoku) {
        // failedCoords.clear();
        boolean valid = new Boolean(true);
        // Grid for storing already found values
        // int[][] sortedGrid = new int[sudoku.length+1][sudoku.length+1];
        int[][] sortedGrid = new int[sudoku.length][sudoku.length];
        // for(int i = sortedGrid.length-1; i >= 0; i--){
        for (int i = sortedGrid.length - 1; i >= 0; i--) {
            for (int j = 0; j < sortedGrid.length; j++) {
                sortedGrid[i][j] = 0;
            }
        }

        valid = checkRowsDuplicate(sudoku, sortedGrid);
        sortedGrid = new int[sudoku.length][sudoku.length];

        valid = checkColsDuplicate(sudoku, sortedGrid);
        sortedGrid = new int[sudoku.length][sudoku.length];

        // for (int i = sortedGrid.length - 1; i >= 0; i--) {
        // for (int j = 0; j < sortedGrid.length; j++) {
        // sortedGrid[i][j] = 0;
        // }
        // }

        for (int l = 0; l < k * k; l++) {

            for (int i = 0; i < n; i++) {

                for (int j = 0; j < n; j++) {// l/k benytter sig af hvordan java runder op. det er n hvor mange felter
                                             // den skal rygge, og den skal rygge det hver gang l har bev�get sig k
                                             // felter.
                    int cur = sudoku[(i + n * (l / k))][(j + n * l) % (k * n)];
                    if (cur != 0) {
                        sortedGrid[l][cur - 1] = sortedGrid[l][cur - 1] + 1;
                    }
                }
            }
        }
        for (int l = 0; l < k * k; l++) {

            for (int i = 0; i < n; i++) {

                for (int j = 0; j < n; j++) {// l/k benytter sig af hvordan java runder op. det er n hvor mange felter
                                             // den skal rygge, og den skal rygge det hver gang l har bev�get sig k
                                             // felter.
                    int cur = sudoku[(i + n * (l / k))][(j + n * l) % (k * n)];
                    if (cur != 0) {
                        if (sortedGrid[l][cur - 1] > 1) {
                            valid = false;
                            addPoint((i + n * (l / k)), ((j + n * l) % (k * n)));

                            // failedCoords.add(view.sudokuBoard
                            // .getCellFromCoord((i + n * (l / k)), (j + n * l) % (k * n)));

                        }
                    }
                }
            }
        }
        // if (print) {
        // for (int i = 0; i < failedCoords.size(); i++) {
        // // System.out.print(view.getCellCoordinate(failedCoords.get(i))[0] + "," +
        // // view.getCellCoordinate(failedCoords.get(i))[1] + " ");
        // }
        // }
        // if (changeColours) {
        // for (int i = 0; i < failedCoords.size(); i++) {
        // failedCoords.get(i).conflict();
        // }
        // }
        return valid;
    }

    public ArrayList<ArrayList<Point>> findLinearConflicts(int[][] sudoku, int n, int k) {
        ArrayList<ArrayList<Point>> allConflicts = new ArrayList<>();

        for (int i = 0; i < sudoku.length; i++) {
            ArrayList<Point>[] numberRangeRow = new ArrayList[n * k];
            ArrayList<Point>[] numberRangeCol = new ArrayList[n * k];
            ArrayList<Point>[] numberRangeSquare = new ArrayList[n * n];

            for (int q = 0; q < n * k; q++) {
                numberRangeRow[q] = new ArrayList<Point>();
                numberRangeCol[q] = new ArrayList<Point>();
                numberRangeSquare[q] = new ArrayList<Point>();

            }

            // Run through vertical and horizontal
            for (int j = 0; j < sudoku.length; j++) {
                if ((sudoku[i][j]) > 0) {
                    numberRangeRow[sudoku[i][j] - 1].add(new Point(j, i));
                }
                if ((sudoku[j][i]) > 0) {
                    numberRangeCol[(sudoku[j][i] - 1)].add(new Point(j, i));
                }
            }

            for (int q = 0; q < numberRangeRow.length; q++) {
                if (numberRangeRow[q].size() > 1) {
                    allConflicts.add(numberRangeRow[q]);
                }
                if (numberRangeCol[q].size() > 1) {
                    allConflicts.add(numberRangeCol[q]);
                }
                if (numberRangeSquare[q].size() > 1) {
                    allConflicts.add(numberRangeSquare[q]);
                }

            }

            // Run through square
            for (int s1 = 0; s1 < k; s1++) {
                for (int s2 = 0; s2 < k; s2++) {

                    for (int c1 = 0; c1 < n; c1++) {
                        for (int c2 = 0; c2 < n; c2++) {
                            int axis0 = s1 * 3 + c1;
                            int axis1 = s2 * 3 + c2;

                            if (sudoku[axis0][axis1] > 0) {
                                numberRangeSquare[(sudoku[axis0][axis1] - 1)].add(new Point(axis0, axis1));
                            }

                        }
                    }
                }

            }
        }
        System.out.println("STOP DEBUG");
        return null;
    }

    private boolean checkColsDuplicate(int[][] sudoku, int[][] sortedGrid) {
        boolean valid = true;
        for (int j = 0; j < sudoku.length; j++) {
            for (int i = 0; i < sudoku.length; i++) {
                int cur = (sudoku[i][j]);
                if (cur != 0) {
                    if (sortedGrid[j][cur - 1] == 0) {
                        sortedGrid[j][cur - 1] = 1;
                    } else {
                        valid = false;
                        Point failedCoord = new Point(i, j);
                        failedCoordsPoint.add(failedCoord);
                        for (int o = 0; o < i; o++) {
                            // Stream does not allow to use o and j directly.
                            if (sudoku[o][j] == cur) {
                                addPoint(o, j);
                                // if (!failedCoordsPoint.stream()
                                // .anyMatch(point -> (point.x == incO && point.y == incJ))) {
                                // failedCoordsPoint.add(new Point(o, j));
                                // }
                            }
                        }
                    }
                }
            }
        }
        return valid;
    }

    private boolean checkRowsDuplicate(int[][] sudoku, int[][] sortedGrid) {
        boolean valid = true;
        for (int i = 0; i < sudoku.length; i++) {
            for (int j = 0; j < sudoku.length; j++) {
                int cur = (sudoku[i][j]);
                if (cur != 0) {
                    if (sortedGrid[i][cur - 1] == 0) {
                        sortedGrid[i][cur - 1] = 1;
                    } else {
                        valid = false;
                        Point failedCoord = new Point(i, j);
                        failedCoordsPoint.add(failedCoord);

                        for (int o = 0; o < j; o++) {

                            // Stream does not allow to use o and j directly.
                            int incO = o;
                            int incI = i;

                            if (sudoku[i][o] == cur) {
                                // if (!failedCoordsPoint.stream()
                                // .anyMatch(point -> (point.x == incI && point.y == incO))) {
                                // failedCoordsPoint.add(new Point(i, o));
                                // }
                                addPoint(i, o);

                            }
                        }
                    }
                }
            }
        }
        return valid;
    }
}
