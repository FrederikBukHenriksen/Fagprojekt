package sudoku.View.SudokuBoard;

import java.util.ArrayList;

import javax.swing.JPanel;
import sudoku.View.SudokuBoard.SudokuInterface;

import sudoku.Controller.Exceptions.CellDoesNotExist;
import sudoku.Controller.Exceptions.NoCellSelected;

public abstract class SudokuExtend extends JPanel implements SudokuInterface {
    protected int[][] sudoku;
    public Cell[][] cells;




}
