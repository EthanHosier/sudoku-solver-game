package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SudokuBoardPanel extends JPanel {

    private ArrayList<Square> sudokuTemplateArrayList;
    private ArrayList<GUISquare> guiSquareArrayList;

    //stores ms GUISquare thread is to sleep after the number is reset.
    private int sleepMs;

    //primary color
    private Color color1 = new Color(0xDEE3EF);

    //accent color
    private Color color2 = new Color(0x44E5E5);

    //constructor
    public SudokuBoardPanel(int sudokuSize, ArrayList<Square> sudokuTemplate, String difficulty){

        //calculating what sleep time should be

        if (sudokuSize == 3 && difficulty == "Medium"){
            sleepMs = 20;
        }else if(sudokuSize == 3 && difficulty == "Hard"){
            sleepMs = 1;
        }else{
            sleepMs = 30;
        }


        Color color;

        this.sudokuTemplateArrayList = sudokuTemplate;
        guiSquareArrayList = new ArrayList<>();

        this.setLayout(new GridLayout((int)Math.pow(sudokuSize,2),(int)Math.pow(sudokuSize,2), 1,1));


        //loops through all squares withing sudokuTemplate, setting correct colour of background of corresponding GUISquare,
        // and adding GUISquare to guiSquareArrayList
        for(Square s: sudokuTemplate){

            color = color1;
            //get colour of square:

            //if 2x2 sudoku, color of GUISquares within big square 1 and 2 (indexing start at 0) to be set to color 2
            if (sudokuSize == 2){

                if((SudokuMaster.getSquareIn(s,sudokuTemplate,sudokuSize) == 1) || (SudokuMaster.getSquareIn(s,sudokuTemplate,sudokuSize) == 2)){
                    color = color2;
                }

                //if odd size of sudoku (3x3 etc.) color of GUISquares within even big Square numbers (indexing start at 0) to be set to color2
            }else if(sudokuSize % 2 != 0){

                if(SudokuMaster.getSquareIn(s,sudokuTemplate,sudokuSize) % 2 == 0){
                    color = color2;
                }

            }

            //adds new GUI square object to guiSquareArrayList with correct details given by parameters.
            guiSquareArrayList.add(new GUISquare(s.getNumber(), color,sleepMs));
        }

        //adds each guiSquare to the panel
        for(GUISquare g: guiSquareArrayList){
            this.add(g);
        }



    }

    //getter for guiSquareArrayList
    public ArrayList<GUISquare> getGuiSquareArrayList(){
        return guiSquareArrayList;
    }


}
