package com.company;

public class Square {

    private int number;   // stores the number taken by the square
    private boolean changeable;   // stores whether the square value is set at the start, or can be changed.

    public Square(int number){

       // this.setText(Integer.toString(number));
        this.number = number;

        if (number == 0) {
            this.changeable = true;
        } else {
            this.changeable = false;
        }

    }

    // setter for this.number
    public void setNumber(int number) {
        this.number = number;
        //updateTextfieldNumber();
    }

    // getter for this.number
    public int getNumber() {
        return number;
    }

    // setter for this.changeable
    public void setChangeable(boolean changeable) {
        this.changeable = changeable;
    }

    // getter for this.changeable
    public boolean isChangeable(){
        return changeable;
    }

    //method to update whether the square is now changeable or not.
    public void updateChangeable(){
        //if the number of the square isn't 0, changeable is false
        if (this.number != 0){
            changeable = false;
        } else{
            //if number of square is 0, square is changeable, so can still be changed.
            changeable = true;
        }
    }

    //method to add one to value of square
    public void addOne(){
        number +=1;
    }




}
