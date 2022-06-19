package sudoku.Model.Validity;

import java.util.ArrayList;
import java.awt.Point;

public abstract class ValidityExtend implements ValidityInterface {

    // int[][] sudoku;

    public abstract ArrayList<Point> getUniqueConflictPoints(int[][] sudoku);

    public abstract boolean checkValidity(int[][] sudoku);

}
