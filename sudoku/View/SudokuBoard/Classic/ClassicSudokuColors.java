package sudoku.View.SudokuBoard.Classic;

import java.awt.Color;

import sudoku.View.SudokuBoard.Cell;

public class ClassicSudokuColors {

    public Color colorDefaultBackground = Color.white;
    public Color colorDefaultFont = Color.black;
    public Color colorDefaultFontEnabled = new Color(80, 110, 242);
    public Color cellSelected = new Color(161, 205, 240);

    public void colorDefault(Cell cell) {
        if (cell.getEnabled()) {
            cell.setTextColor(colorDefaultFontEnabled);
        } else {
            cell.setTextColor(colorDefaultFont);
        }
        cell.setBackgroundColor(colorDefaultBackground);
    }

    public void colorConflict(Cell cell) {
        if (cell.getEnabled()) {
            cell.setTextColor(new Color(230, 67, 70));
        }
        cell.setBackgroundColor(new Color(240, 192, 193));
    }

    public void colorSimilar(Cell cell) {
        cell.setBackgroundColor(new Color(144, 182, 212));
    }

    public void colorPeer(Cell cell) {
        cell.setBackgroundColor(new Color(199, 219, 235));
    }

    public void colorSquare(Cell cell) {
        cell.setBackgroundColor(new Color(199, 219, 235));
    }
}
