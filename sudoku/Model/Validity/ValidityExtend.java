package sudoku.Model.Validity;

import java.util.ArrayList;
import java.awt.Point;

public abstract class ValidityExtend implements ValidityInterface {

    int[][] sudoku;

    ValidityExtend validity;

    public ValidityExtend() {
    }

    public void setSudoku(int[][] sudoku) {
        this.setSudoku(sudoku);
    }

    public String getValidity() {
        return this.getClass().getSimpleName();
    }

}
