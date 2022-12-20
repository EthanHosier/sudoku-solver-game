package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//class used to solve sudoku
public class SudokuMaker {

    //an ArrayList of the start position of the gridArrayList
    private ArrayList<Square> gridArrayListTemplate;

    private ArrayList<Square> completedSudokuArrayList;

    private ArrayList<Square> finalTemplate;

    private int sudokuSize;

    //constructor method
    public SudokuMaker(int size, String difficulty) {

        sudokuSize = size;
        gridArrayListTemplate = new ArrayList<>();
        completedSudokuArrayList = new ArrayList<>();

        boolean valid = false;

        //sets completedSudokuArrayList
        do {

            completedSudokuArrayList.clear();
            gridArrayListTemplate.clear();

            //adds size^4 Square objects to gridArrayListTemplate, all with value 0
            for (int i = 0; i < Math.pow(size, 4); i++) {
                gridArrayListTemplate.add(new Square(0));
            }

            //sets the gridArrayList to a potential set of values.
            setPotentialGridArrayList();

            //adss all the Square objects within gridArrayListTemplate to completedSudokuArrayList
            for (Square s: gridArrayListTemplate){

                completedSudokuArrayList.add(new Square(s.getNumber()));

            }
            //attempts to solve sudoku
            //valid recieves boolean value of whether sudoku could be solved or not.
            valid = SudokuMaster.solve(completedSudokuArrayList, size);

        }while (valid == false);  //loops until valid == true (sudoku has been solved)

        //makes sudoku with applicable difficulty - adding extra numbers for easier difficulties.
        makeSudokuTemplateWithDifficulty(difficulty);



    }


    private void setPotentialGridArrayList() {
        int numsAdded = 0;
        int numToSet;
        int indexToChange;
        Random rand = new Random();

        //adds size^2 nums to gridArrayList in valid locations
        while (numsAdded < Math.pow(sudokuSize, 3)) {  // keeps adding numbers until size^3 numbers added

            //chooses random number from all applicable numbers for that sudoku size (9 for 3x3, 4 for 2x2 etc.)
            numToSet = rand.nextInt((int) Math.pow(sudokuSize, 2)) + 1;

            //randomly chooses number from applicable range (all squares within sudoku) to set to numToSet
            indexToChange = rand.nextInt((int) Math.pow(sudokuSize, 4));


            //checks if number is able to go in desired position (at indexToChange) and sets that number there if it can
            if (SudokuMaster.checkIfCanChangeTo(gridArrayListTemplate.get(indexToChange), numToSet, gridArrayListTemplate, sudokuSize) && gridArrayListTemplate.get(indexToChange).getNumber() == 0) {

                gridArrayListTemplate.get(indexToChange).setNumber(numToSet);
                numsAdded += 1;
            }


        }


    }

    //method to return most simplified version of the sudoku which is STILL UNIQUE
    private ArrayList<Square> getMostSimplifiedUniqueSudoku() {

        Random rand = new Random();
        int indexToChange;


        ArrayList<Square> uniqueSudokuSimple = new ArrayList<>();

        //stores what the sudoku was 'last step' so can be referred to when new sudoku is no longer unique
        ArrayList<Square> oneAheadUniqueSudokuSimple = new ArrayList<>();

        int numTakenAway;
        boolean unique;
        int numSolutions = 0;

        Square squareOn;

        //loops through all squares withing completedSudokuArrayList, adding them to uniqueSudokuSimple.
        for (Square s : completedSudokuArrayList) {

            uniqueSudokuSimple.add(new Square(s.getNumber()));

        }

        do { // keeps looping and attempting to create simplified sudoku until valid number of numbers removed - (sudoku size ^ 4) * 0.61
            unique = true;

            oneAheadUniqueSudokuSimple.clear();
            //adding all the objects to uniqueSudokuSimple - new square objects, but same number value within them as in same index of completed sudoku arrayList;
            for (Square s : completedSudokuArrayList) {

                oneAheadUniqueSudokuSimple.add(new Square(s.getNumber()));

            }


            numTakenAway = 0;
            while (unique == true) {

                //gets new square which doesnt have value 0
                do {
                    squareOn = oneAheadUniqueSudokuSimple.get(rand.nextInt((int)Math.pow(sudokuSize,4)));
                } while (squareOn.getNumber() == 0);

                squareOn.setNumber(0);

                //gets num solutions for this sudoku
                numSolutions = SudokuMaster.getNumSodokuSolutions(oneAheadUniqueSudokuSimple, sudokuSize);

                //if number of solutions isn't one (sudoku isnt unique), unique set to false.
                if (numSolutions != 1) {
                    unique = false;
                }else{

                    //adds one to numTakenAway
                    numTakenAway += 1;


                    for (Square s : oneAheadUniqueSudokuSimple) {

                        uniqueSudokuSimple.get(oneAheadUniqueSudokuSimple.indexOf(s)).setNumber(s.getNumber());

                    }


                }



            }

        } while (numTakenAway < ((int)Math.pow(sudokuSize,4) * 0.61));

       //returns this simplified sudoku
        return uniqueSudokuSimple;
    }

    //method to add correct numbers to sudoku template depending on difficulty specified.
    private void makeSudokuTemplateWithDifficulty(String difficulty){

        //initializing ArrayList which stores final template (with difficulty numbers added)
       finalTemplate = getMostSimplifiedUniqueSudoku();


       //indexesBlank List stores the indexes of the objects within finalTemplate which have 0 as their value
        List<Integer> indexesBlank = new ArrayList<>();

        //loops through all Squares withing final Template, adding one to indexesBlank each time a Square object value is 0.
        for (int i = 0; i < finalTemplate.size(); i ++) {

            if (finalTemplate.get(i).getNumber() == 0) {
                indexesBlank.add(i);
            }

        }

            //creating new Random object
            Random rand = new Random();

            int multiplier = 0;

            if(difficulty == "Easy"){
                multiplier = 2;
            }else if(difficulty == "Medium"){
                multiplier = 1;
            }

            //shuffles indexesBlank, so that the order of indexes which have value 0 are now random
            Collections.shuffle(indexesBlank);

            for(int i = 0; i < (int)(indexesBlank.size() * multiplier) / 3; i ++){

                finalTemplate.get(indexesBlank.get(i)).setNumber(completedSudokuArrayList.get(indexesBlank.get(i)).getNumber());

            }
    }

    //getter for finalTemplate (arrayList<Square>)
    public ArrayList<Square> getFinalTemplate(){

        return finalTemplate;

    }

    //getter for completedSudokuArrayList (ArrayList<Square>)
    public ArrayList<Square> getCompletedSudokuArrayList() {
        return completedSudokuArrayList;
    }



}
