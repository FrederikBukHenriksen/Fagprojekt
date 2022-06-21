package sudoku.Model;

import java.util.ArrayList;

public class Stack {
    public ArrayList<stackObj> sudokuStack;
    public ArrayList<stackObj> redoStack;
    int[][] sudoku;

    /*
     * Author: Rasmus
     * Function: Constructor for the Stack class, creates a undoStack and a
     * redoStack, and takes in the Sudoku
     * Inputs: The sudoku board we are working with
     * Outputs: None
     */
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

        // Getter methods
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

    // Push for undoStack
    /*
     * Author: Rasmus
     * Function: Adds the newest change in form of a stackObj to the undoStack
     * Inputs: The stackObject to be added to the stack
     * Outputs: None
     */
    public void pushStack(stackObj x) {
        sudokuStack.add(x);
        // moves++;
    }

    // Push for redo-stack
    /*
     * Author: Rasmus
     * Function: Adds the newest change in form of a stackObj to the redoStack
     * Inputs: The stackObject to be added to the stack
     * Outputs: None
     */
    public void pushRedoStack(stackObj x) {
        redoStack.add(x);
        // redoes++;
    }

    // new pop method
    /*
     * Author: Rasmus
     * Function: Removes the most recently added stackObj from the undoStack, sets
     * the relevant sudoku cell to what it was before the change, and returns the
     * stackObj
     * Inputs: None
     * Outputs: The popped stackObj
     */
    public stackObj popStack() {
        stackObj temp = sudokuStack.get(sudokuStack.size() - 1);
        sudoku[temp.getX()][temp.getY()] = temp.prevVal;
        sudokuStack.remove(sudokuStack.size() - 1);
        return temp;
    }

    // Pop for redo-stack
    /*
     * Author: Rasmus
     * Function: Removes the most recently added stackObj from the redoStack, sets
     * the relevant sudoku cell to what it was before the corresponding undo-action,
     * and returns the stackObj
     * Inputs: None
     * Outputs: The popped stackObj
     */
    public stackObj popRedoStack() {
        stackObj temp = redoStack.get(redoStack.size() - 1);
        sudoku[temp.getX()][temp.getY()] = temp.newVal;
        redoStack.remove(redoStack.size() - 1);
        return temp;
    }

    // Method for clearing the redo stack
    /*
     * Author: Rasmus
     * Function: Removes all elements from the redoStack
     * Inputs: None
     * Outputs: None
     */
    public void clearRedoStack() {
        redoStack.clear();
        // redoes = 0;
    }

    // Returns the size of the stack
    public int getStackSize() {
        return sudokuStack.size();
    }

    // CreateStackObject method
    /*
     * Author: Rasmus
     * Function: Method for creating new stackObjects, to be called from other files
     * Inputs: The coordinates for the cell as x and y, the value before the change
     * as oldVal, and the value after the change as newVal
     * Outputs: the newly created stackObject
     */
    public stackObj createStackObj(int x, int y, int oldVal, int newVal) {
        return new stackObj(x, y, oldVal, newVal);
    }

    // Method for returning coords of the last change on the sudokuStack
    /*
     * Author: Rasmus
     * Function: Gets the coordinates of the most recent change of the sudokuBoard
     * Inputs: None
     * Outputs: an int array containing the x-coordinate at index 0, and the
     * y-coordinate at index 1
     */
    public int[] getStackCoords() {
        int[] result = new int[2];
        stackObj temp = sudokuStack.get(sudokuStack.size() - 1);
        result[0] = temp.getX();
        result[1] = temp.getY();
        return result;
    }

    // Method for returning coords of the last change on the redoStack
    /*
     * Author: Rasmus
     * Function: Gets the coordinates of the most recently added element of the redo
     * Stack
     * Inputs: None
     * Outputs: an int array containing the x-coordinate at index 0, and the
     * y-coordinate at index 1
     */
    public int[] getRedoStackCoords() {
        int[] result = new int[2];
        stackObj temp = redoStack.get(redoStack.size() - 1);
        result[0] = temp.getX();
        result[1] = temp.getY();
        return result;
    }

}
