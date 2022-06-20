package sudoku.Model.Validity;

import java.util.ArrayList;
import java.awt.Point;

public interface ValidityInterface {

    public abstract ArrayList<Point> getUniqueConflictPoints(int[][] sudoku);

    public abstract boolean checkValidity(int[][] sudoku);

}
