package com.company;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class ClueButton extends JButton {

    //constructor method
    public ClueButton(int height){

        //setting information + dimensions of button
        this.setText("Clue");
        this.setSize(200,300);
        this.setFocusable(false);

        this.setBorder(new EtchedBorder());

        this.setFont(new Font("Serif Sans", Font.BOLD,25));
        this.setBounds(25,(height/8),100,50);

    }

}


