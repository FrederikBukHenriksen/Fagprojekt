package sudoku.Model;

public class Stack {

    stackObj[] sudokuStack;
    stackObj[] redoStack;
    int[][] sudoku;
    public int moves = 0;
    public int redoes = 0;

    public Stack(int[][] sudoku) {
        sudokuStack = new stackObj[1000];
        redoStack = new stackObj[1000];
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
        sudokuStack[moves] = x;
        moves++;
    }

    // Push for redo-stack
    public void pushRedoStack(stackObj x) {
        redoStack[redoes] = x;
        redoes++;
    }

    // new pop method
    public stackObj popStack() {
        stackObj temp = sudokuStack[moves - 1];
        sudoku[temp.getX()][temp.getY()] = temp.prevVal;
        moves--;
        return temp;
    }

    // Pop for redo-stack
    public stackObj popRedoStack() {
        stackObj temp = redoStack[redoes - 1];
        sudoku[temp.getX()][temp.getY()] = temp.newVal;
        redoes--;
        return temp;
    }

    // Method for clearing the redo stack
    public void clearRedoStack() {
        redoStack = new stackObj[1000];
        redoes = 0;
    }

    // Returns the size of the stack
    public int getStackSize() {
        return moves;
    }

    // CreateStackObject method
    public stackObj createStackObj(int x, int y, int oldVal, int newVal) {
        return new stackObj(x, y, oldVal, newVal);
    }

    // Method for returning coords of the last change on the sudokuStack
    public int[] getStackCoords() {
        int[] result = new int[2];
        stackObj temp = sudokuStack[moves - 1];
        result[0] = temp.getX();
        result[1] = temp.getY();
        return result;
    }

    // Method for returning coords of the last change on the redoStack
    public int[] getRedoStackCoords() {
        int[] result = new int[2];
        stackObj temp = redoStack[redoes - 1];
        result[0] = temp.getX();
        result[1] = temp.getY();
        return result;
    }

}
