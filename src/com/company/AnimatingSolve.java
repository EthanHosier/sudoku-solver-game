package com.company;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

//new thread object so UI can be updated on seperate thread to main program - allows for 'animated' look of numbers as program solves sudoku with backtracking algorithm.
public class AnimatingSolve extends Thread {

    private ActionEvent e;
    private SolveButton solveButton;
    private SudokuBoardPanel sudokuBoardPanel;
    private ArrayList<Square> templateSudokuArrayList;
    private int size;

    //Constructor method for thread.
    public AnimatingSolve(ActionEvent e,
                          SolveButton solveButton,
                          SudokuBoardPanel sudokuBoardPanel,
                          ArrayList<Square> templateSudokuArrayList,
                          int size) {

        //assigning variables
        this.e = e;
        this.solveButton = solveButton;
        this.sudokuBoardPanel = sudokuBoardPanel;
        this.templateSudokuArrayList = templateSudokuArrayList;
        this.size = size;


    }

    @Override
    //method ran when thread started
    public void run() {

        //makes reference of guiSuareArrayList
            ArrayList<GUISquare> guiSquareArrayList = sudokuBoardPanel.getGuiSquareArrayList();

            //disables all GUISquare objects (JTextFields) withing guiSquareArrayList (which are on the board)
            for(GUISquare g : guiSquareArrayList){
                g.setEnabled(false);
            }

            //runs overloaded solve method with extra parameter guiSquareArrayList.
            try {
                SudokuMaster.solve(this.templateSudokuArrayList, size, guiSquareArrayList); // need t
            } catch (InterruptedException i) {
                i.printStackTrace();
            }

            //prints solved sudoku to console.
            //SudokuMaster.printSudoku(this.templateSudokuArrayList, size);



    }
}