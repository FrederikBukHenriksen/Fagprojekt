package sudoku.Model.Validity;

import java.util.ArrayList;
import java.awt.Point;

public interface ValidityInterface {
	/*
	 * Author: Frederik
	 * Function: Interface, the rest of the code uses to get Validity
	 */
    public abstract ArrayList<Point> getUniqueConflictPoints(int[][] sudoku);

    public abstract boolean checkValidity(int[][] sudoku);

}
