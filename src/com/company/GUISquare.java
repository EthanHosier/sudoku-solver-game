package com.company;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class GUISquare extends JTextField {

    //stores the integer being held and displayed by the text field.
    private int number;

    //stores the amount of ms the thread sleeps after updating the text field with new number
    private int sleepMs;

    public GUISquare(int number, Color color, int sleepMs){


        this.sleepMs = sleepMs;
        this.number = number;

        //sets the GuiSquare to display the number, if its value is not 0
        if (number != 0) {
            this.setText(Integer.toString(this.number));
            this.setEnabled(false);

        //if the number is 0, displays " " instead of 0
        }else{
            this.setText(" ");
        }
        //setting format of text in JTextField
        this.setFont(new Font("Serif Sans", Font.BOLD,40));
        this.setHorizontalAlignment(CENTER);
        this.setBackground(color);

        //adds border to increase distinctiveness of each square
        this.setBorder(new EtchedBorder(1));
        this.setDisabledTextColor(Color.BLACK);
    }

    //method to set the number being stored by the GUISquare
    public void setNumber(int number) {
        this.number = number;

        //calls updateGUINum method with parameter number to update the textField to show the new number.
        updateGUINum(number);

    }

    //getter for number
    public int getNumber(){
        return this.number;
    }

    //method to update number displayed by textField on sudoku board
    private void updateGUINum(int number){

        //prints number to screen if 1-9 (used to prevent both 0 and 10 being displayed on the screen -
        // 10 would be displayed momentarily before SudokuMaster.solve makes the number 0 again otherwise.
        if (number > 0 && number < 10){
            this.setText(Integer.toString(number));
        }else{
            this.setText("");
        }


        //makes thread sleep sleepMs ms long. Makes animation of numbers more visual, instead of just updating all the numbers seemingly at once.
        try {
            Thread.sleep(sleepMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }





}
