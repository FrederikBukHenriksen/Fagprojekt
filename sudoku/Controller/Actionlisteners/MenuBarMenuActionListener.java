package sudoku.Controller.Actionlisteners;
import sudoku.SudokuController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractButton;

public class MenuBarMenuActionListener implements ActionListener{

    SudokuController sudokuController;

    public MenuBarMenuActionListener(SudokuController sudokuController){
        this.sudokuController = sudokuController;
    }

    public void actionPerformed(ActionEvent e){
        switch (((AbstractButton) e.getSource()).getActionCommand().toLowerCase()) {
            case "solve sudoku": 
                if(sudokuController.model.crooks.getUniqueness()){
                    for(int i = 0; i < sudokuController.model.getN() * sudokuController.model.getK(); i++){
                        for(int j = 0; j < sudokuController.model.getN() * sudokuController.model.getK(); j++){
                            if(sudokuController.view.getCellFromCoord(i, j).enabled){
                                sudokuController.model.setSudokuCell(i, j, sudokuController.model.crooks.getSolvedSudoku()[i][j]);
                            }
                        }
                    }
                    sudokuController.view.updateBoard(sudokuController.model.getSudoku());
                    sudokuController.updateColours();
                }
                else{
                    sudokuController.model.crooks.solver();
                    if(sudokuController.model.crooks.getSolvedSudoku()[0][0] != 0){
                        for(int i = 0; i < sudokuController.model.getN() * sudokuController.model.getK(); i++){
                            for(int j = 0; j < sudokuController.model.getN() * sudokuController.model.getK(); j++){
                                if(sudokuController.view.getCellFromCoord(i, j).enabled){
                                    sudokuController.model.setSudokuCell(i, j, sudokuController.model.crooks.getSolvedSudoku()[i][j]);
                                }
                            }
                        }
                        sudokuController.updateColours();
                    }
                }
        }
    }
}

