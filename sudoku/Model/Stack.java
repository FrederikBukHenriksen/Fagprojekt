package sudoku.Model;

import java.util.ArrayList;

public class Stack {
    public ArrayList<stackObj> sudokuStack;
    public ArrayList<stackObj> redoStack;
    int[][] sudoku;
    // public int moves = 0;
    // public int redoes = 0;

    public Stack(int[][] sudoku) {
        sudokuStack = new ArrayList<stackObj>();
        redoStack = new ArrayList<stackObj>();
        this.sudoku = sudoku;
    }

    class stackObj {
        int xCoord = 0;
        int yCoord = 0;
        int prevVal = 0;
        int newVal = 0;

        public stackObj(int a, int b, int c, int d) {
            xCoord = a;
            yCoord = b;
            prevVal = c;
            newVal = d;
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
    public void pushStack(stackObj x) {
        sudokuStack.add(x);
        // moves++;
    }

    // Push for redo-stack
    public void pushRedoStack(stackObj x) {
        redoStack.add(x);
        // redoes++;
    }

    // new pop method
    public stackObj popStack() {
        stackObj temp = sudokuStack.get(sudokuStack.size() - 1);
        sudoku[temp.getX()][temp.getY()] = temp.prevVal;
        // moves--;
        sudokuStack.remove(sudokuStack.size() - 1);
        return temp;
    }

    // Pop for redo-stack
    public stackObj popRedoStack() {
        stackObj temp = redoStack.get(redoStack.size() - 1);
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
    public int getStackSize() {
        return sudokuStack.size();
    }

    // CreateStackObject method
    public stackObj createStackObj(int x, int y, int oldVal, int newVal) {
        return new stackObj(x, y, oldVal, newVal);
    }

    // Method for returning coords of the last change on the sudokuStack
    public int[] getStackCoords() {
        int[] result = new int[2];
        stackObj temp = sudokuStack.get(sudokuStack.size() - 1);
        result[0] = temp.getX();
        result[1] = temp.getY();
        return result;
    }

    // Method for returning coords of the last change on the redoStack
    public int[] getRedoStackCoords() {
        int[] result = new int[2];
        stackObj temp = redoStack.get(redoStack.size() - 1);
        result[0] = temp.getX();
        result[1] = temp.getY();
        return result;
    }

}
