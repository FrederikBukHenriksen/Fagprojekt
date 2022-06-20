package sudoku.Model;

import java.util.ArrayList;

public class Stack {
    private ArrayList<StackObj> undoStack;
    private ArrayList<StackObj> redoStack;
    private int[][] sudoku;
    // public int moves = 0;
    // public int redoes = 0;

    public Stack(int[][] sudoku) {
        undoStack = new ArrayList<StackObj>();
        redoStack = new ArrayList<StackObj>();
        this.sudoku = sudoku;
    }

    class StackObj {
        int xCoord = 0;
        int yCoord = 0;
        int prevVal = 0;
        int newVal = 0;

        public StackObj(int xCoord, int yCoord, int prevVal, int newVal) {
            this.xCoord = xCoord;
            this.yCoord = yCoord;
            this.prevVal = prevVal;
            this.newVal = newVal;
        }

        public int getX() {
            return xCoord;
        }

        public int getY() {
            return yCoord;
        }

        public int getPrevVal() {
            return prevVal;
        }

        public int getNewVal() {
            return newVal;
        }
    }

    // Push for new stack
    public void pushStack(StackObj input) {
        undoStack.add(input);
        // moves++;
    }

    // Push for redo-stack
    public void pushRedoStack(StackObj input) {
        redoStack.add(input);
        // redoes++;
    }

    // new pop method
    public StackObj popStack() {
        StackObj temp = undoStack.get(undoStack.size() - 1);
        sudoku[temp.getX()][temp.getY()] = temp.prevVal;
        // moves--;
        undoStack.remove(undoStack.size() - 1);
        return temp;
    }

    // Pop for redo-stack
    public StackObj popRedoStack() {
        StackObj temp = redoStack.get(redoStack.size() - 1);
        sudoku[temp.getX()][temp.getY()] = temp.newVal;
        // redoes--;
        redoStack.remove(redoStack.size() - 1);
        return temp;
    }

    // Method for clearing the redo stack
    public void clearRedoStack() {
        redoStack.clear();
        // redoes = 0;
    }

    // Returns the size of the stack
    public int getUndoStackSize() {
        return undoStack.size();
    }

    public int getRedoStackSize() {
        return redoStack.size();
    }

    // CreateStackObject method
    public StackObj createStackObj(int x, int y, int oldVal, int newVal) {
        return new StackObj(x, y, oldVal, newVal);
    }

    // Method for returning coords of the last change on the sudokuStack
    public int[] getUndoStackCoords() {
        int[] result = new int[2];
        StackObj temp = undoStack.get(undoStack.size() - 1);
        result[0] = temp.getX();
        result[1] = temp.getY();
        return result;
    }

    // Method for returning coords of the last change on the redoStack
    public int[] getRedoStackCoords() {
        int[] result = new int[2];
        StackObj temp = redoStack.get(redoStack.size() - 1);
        result[0] = temp.getX();
        result[1] = temp.getY();
        return result;
    }

}
