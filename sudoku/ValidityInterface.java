package sudoku;

import java.util.ArrayList;
import java.awt.Point;

public interface ValidityInterface {

    public boolean checkValidity();

    public ArrayList<Point> getUniqueConflictPoints();

}
