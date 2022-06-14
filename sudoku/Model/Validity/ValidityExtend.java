package sudoku.Model.Validity;

import java.util.ArrayList;
import java.awt.Point;

public abstract class ValidityExtend implements ValidityInterface {

    int[][] sudoku;

    ValidityExtend validity;

    public void setSudoku(int[][] sudoku) {
        this.setSudoku(sudoku);
    }

    public String getValidityType() {
        return this.getClass().getSimpleName();
    }

    public abstract boolean checkValidity();

    public abstract boolean checkValidity(int[][] sudoku);

}
