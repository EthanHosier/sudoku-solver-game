package com.company;

import java.util.ArrayList;


//Acts as overseer to program, performs sodoku based algorithms / calculations
abstract class SudokuMaster {

    //method checks position of square given, within the arrayList provided
    private static int getSquaresRow(Square square, ArrayList<Square> gridArrayList, int size) {

        int pos = gridArrayList.indexOf(square);

        return (int) Math.floor(pos / Math.pow(size, 2));

    }

    //method checks if square can be placed in the row given with the specific value given
    private static boolean numValidInRow(Square square, int valToSet, ArrayList<Square> gridArrayList, int size) {

        //gets row to test
        int rowOn = getSquaresRow(square, gridArrayList, size);

        //loops through every number within that row, checking if valToSet already in that row
        for (int i = ((int) Math.pow(size, 2)) * rowOn; i < (((int) Math.pow(size, 2)) * rowOn) + ((int) Math.pow(size, 2)); i++) {

            if ((gridArrayList.get(i).getNumber() == valToSet) && (gridArrayList.get(i) != square)) {

                return false;

            }

        }

        return true;

    }

    //returns postion in row of square object - indexing start at 0
    private static int getPosInRow(Square square, ArrayList<Square> gridArrayList, int size) {

        return gridArrayList.indexOf(square) % ((int) Math.pow(size, 2));
    }


    //returns true if valToSet can be placed in the column the square object is in
    private static boolean numValidInColumn(Square square, int valToSet, ArrayList<Square> gridArrayList, int size) {

        //gets position in row of that square.
        int posInRow = getPosInRow(square, gridArrayList, size);

        //loops through all square objects in the specified column, and checks if they have the same value as valToSet
        for (int i = posInRow; i < ((int) Math.pow(size, 4)); i += ((int) Math.pow(size, 2))) {

            if ((gridArrayList.get(i).getNumber() == valToSet) && (gridArrayList.get(i) != square)) {

                return false;

            }

        }

        return true;


    }

    //method returns index of the start of the big square
    private static int getStartPosBigSquare(Square square, ArrayList<Square> gridArrayList, int size) {

        return ((int) Math.pow(size, 3)) * (int) Math.floor(getSquaresRow(square, gridArrayList, size) / size) + size * (int) Math.floor(getPosInRow(square, gridArrayList, size) / size);
    }


    //method returns square number the square object is in

    public static int getSquareIn(Square square, ArrayList<Square> gridArrayList, int size){

        return (int) Math.floor(getSquaresRow(square,gridArrayList,size) / size) * size + (int)Math.floor(getPosInRow(square,gridArrayList,size)/size);

    }


    //returns if valToSet can be put in the same large square as square object - returns false if that value already in the square.
    private static boolean numValidInBigSquare(Square square, int valToSet, ArrayList<Square> gridArrayList, int size) {

        //loop starts at calculated start position, ends at this position + size
        for (int i = getStartPosBigSquare(square, gridArrayList, size); i < getStartPosBigSquare(square, gridArrayList, size) + size; i++) {

            //loop checks through all (size) columns within this row (in the big square)
            for (int j = 0; j < size; j++) {

                if ((gridArrayList.get(i + j * ((int) Math.pow(size, 2))).getNumber() == valToSet) && (gridArrayList.get(i + j * ((int) Math.pow(size, 2))) != square)) {
                    return false;
                }

            }

        }

        return true;
    }

    public static boolean checkIfCanChangeTo(Square square, int valToSet, ArrayList<Square> gridArrayList, int size) {

        if (numValidInColumn(square, valToSet, gridArrayList, size) && numValidInRow(square, valToSet, gridArrayList, size) && numValidInBigSquare(square, valToSet, gridArrayList, size)) {
            return true;
        }
        return false;


    }

    //returns square object which is the first possible changeable one.
    public static Square getFirstChangeableSquare(ArrayList<Square> gridArrayList) {

       for(Square s: gridArrayList){
            s.updateChangeable(); // updates whether instance of square class is changeable
        }

        for (Square s : gridArrayList) {
            if (s.isChangeable() == true) {
                return s;
            }

        }
        return null;
    }

    public static void printSudoku(ArrayList<Square> gridArrayList, int size) {

        int squareOn = 0;

        for (int i = 0; i < ((int) Math.pow(size, 2)); i++) {
            System.out.println();
            for (int j = 0; j < ((int) Math.pow(size, 2)); j++) {
                System.out.print(gridArrayList.get(squareOn).getNumber());
                squareOn += 1;
            }

        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////



    public static boolean solve (ArrayList<Square> templateGrid, int size) {

        // declaring variables
        boolean forwards = true;
        boolean solved = false;
        boolean keepIterating;

        Square squareOn;
        Square firstChangeableSquare = getFirstChangeableSquare(templateGrid);

        int gridPosOn = templateGrid.indexOf(firstChangeableSquare);
        boolean carryOn = true;

        while ((firstChangeableSquare.getNumber() < ((int) Math.pow(size, 2))) && (carryOn== true)) {

            //iterating statement set to true by default.
            keepIterating = true;

            //declaring temporary variable to represent square being changed
            squareOn = templateGrid.get(gridPosOn);

            //value increases depending on whether can be placed as that square's value in that position


            if (squareOn.isChangeable()) {


                //loop keeps adding one to squareOn.number until conditions met
                while (keepIterating == true) {

                    //adds one to the squareOn object
                    squareOn.addOne();



                    //checks if current value of squareOn > 9 (invalid) and changes it to zero if it is.
                    if (squareOn.getNumber() > ((int) Math.pow(size, 2))) {
                        forwards = false;
                        squareOn.setNumber(0);
                        keepIterating = false;
                    } else {

                        //checks if the current value of squareOn is valid for the grid position the object is set in.
                        if (checkIfCanChangeTo(squareOn, squareOn.getNumber(),templateGrid,size)) {

                            keepIterating = false;

                            forwards = true;

                        }

                    }

                }

            }


            //if searcher is 'allowed' to move on to next number, goes to next grid position.
            if (forwards == true) {
                gridPosOn += 1;
            } else {      // if searcher must go back one, goes to previous position.
                gridPosOn -= 1;
            }

            //if the current grid position > 80, sudoku must have been solved.
            if (gridPosOn > ((int) Math.pow(size, 4)) - 1) {
                carryOn = false;
                solved = true;
            }

            if(gridPosOn < 0){
                carryOn = false;
                solved = false;
            }

        }

        //UN- COMMENT THIS IF WANT TO CHECK SOLUTION ============
       // printSudoku(solvedGrid,size);
       // System.out.println(solved);


        if(solved == true){
            return true;
        }else{
            return false;
        }


    }

    //overwridden method for when passed with ArrayList of GUI Squares as extra parameter - USED WHEN SOLVE BUTTON PRESSED ON GUI
    public static boolean solve (ArrayList<Square> templateGrid, int size, ArrayList<GUISquare> GUISquareArrayList) throws InterruptedException {

        // declaring variables
        boolean forwards = true;
        boolean solved = false;
        boolean keepIterating;

        Square squareOn;
        Square firstChangeableSquare = getFirstChangeableSquare(templateGrid);

        int gridPosOn = templateGrid.indexOf(firstChangeableSquare);
        boolean carryOn = true;

        while ((firstChangeableSquare.getNumber() < ((int) Math.pow(size, 2))) && (carryOn== true)) {

            //iterating statement set to true by default.
            keepIterating = true;

            //declaring temporary variable to represent square being changed
            squareOn = templateGrid.get(gridPosOn);

            //value increases depending on whether can be placed as that square's value in that position


            if (squareOn.isChangeable()) {


                //loop keeps adding one to squareOn.number until conditions met
                while (keepIterating == true) {

                    //adds one to the squareOn object
                    squareOn.addOne();
                    GUISquareArrayList.get(templateGrid.indexOf(squareOn)).setNumber(squareOn.getNumber());

                    //checks if current value of squareOn > 9 (invalid) and changes it to zero if it is.
                    if (squareOn.getNumber() > ((int) Math.pow(size, 2))) {
                        forwards = false;
                        squareOn.setNumber(0);
                        GUISquareArrayList.get(templateGrid.indexOf(squareOn)).setNumber(squareOn.getNumber());


                        keepIterating = false;
                    } else {

                        //checks if the current value of squareOn is valid for the grid position the object is set in.
                        if (checkIfCanChangeTo(squareOn, squareOn.getNumber(),templateGrid,size)) {

                            keepIterating = false;

                            forwards = true;

                        }

                    }

                }

            }


            //if searcher is 'allowed' to move on to next number, goes to next grid position.
            if (forwards == true) {
                gridPosOn += 1;
            } else {      // if searcher must go back one, goes to previous position.
                gridPosOn -= 1;
            }

            //if the current grid position > 80, sudoku must have been solved.
            if (gridPosOn > ((int) Math.pow(size, 4)) - 1) {
                carryOn = false;
                solved = true;
            }

            if(gridPosOn < 0){
                carryOn = false;
                solved = false;
            }

        }

        //UN- COMMENT THIS IF WANT TO CHECK SOLUTION ============
        // printSudoku(solvedGrid,size);
        // System.out.println(solved);


        if(solved == true){
            return true;
        }else{
            return false;
        }


    }

    //method to return number of solutions (int) to the given sudoku
    public static int getNumSodokuSolutions(ArrayList<Square> templateGrid, int size) {

        int numSolutions = 0;
        ArrayList<Square> solvedGrid = new ArrayList<>();

        //adds each Square object within templateGrid to solvedGrid.
        for(Square s: templateGrid){

            solvedGrid.add(new Square(s.getNumber()));

        }

        // declaring variables
        boolean forwards = true;
        boolean keepIterating;

        Square squareOn;
        Square firstChangeableSquare = getFirstChangeableSquare(solvedGrid);

        int gridPosOn = solvedGrid.indexOf(firstChangeableSquare);
        boolean carryOn = true;

        while ((firstChangeableSquare.getNumber() < ((int) Math.pow(size, 2))) && (carryOn== true)) {

            //iterating statement set to true by default.
            keepIterating = true;

            //declaring temporary variable to represent square being changed
            squareOn = solvedGrid.get(gridPosOn);

            //value increases depending on whether can be placed as that square's value in that position


            if (squareOn.isChangeable()) {


                //loop keeps adding one to squareOn.number until conditions met
                while (keepIterating == true) {

                    //adds one to the squareOn object
                    squareOn.addOne();


                    //checks if current value of squareOn > 9 (invalid) and changes it to zero if it is.
                    if (squareOn.getNumber() > ((int) Math.pow(size, 2))) {
                        forwards = false;
                        squareOn.setNumber(0);

                        keepIterating = false;
                    } else {

                        //checks if the current value of squareOn is valid for the grid position the object is set in.
                        if (checkIfCanChangeTo(squareOn, squareOn.getNumber(),solvedGrid,size)) {

                            keepIterating = false;

                            forwards = true;

                        }

                    }

                }

            }


            //if searcher is 'allowed' to move on to next number, goes to next grid position.
            if (forwards == true) {
                gridPosOn += 1;
            } else {      // if searcher must go back one, goes to previous position.
                gridPosOn -= 1;
            }

            //if the current grid position > 80, sudoku must have been solved.
            if (gridPosOn > ((int) Math.pow(size, 4)) - 1) {


                numSolutions += 1;

                gridPosOn = solvedGrid.indexOf(getLastChangeableSquare(solvedGrid));

            }

            if(gridPosOn < 0){
                carryOn = false;

            }

        }


        return numSolutions;


    }

    //returns square object which is the last possible changeable one.
    public static Square getLastChangeableSquare(ArrayList<Square> gridArrayList) {

        for (int i = gridArrayList.size() - 1; i >= 0; i --) {

            if (gridArrayList.get(i).isChangeable()) {
                return gridArrayList.get(i);
            }

        }
        return null;
    }

}



