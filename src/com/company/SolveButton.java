package com.company;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

//instance is created within ButtonPanel instance.
public class SolveButton extends JButton {

    //setting dimension of button
    public SolveButton(int height) {
        this.setText("Solve");
        this.setSize(200, 300);
        this.setFocusable(false);
        this.setFont(new Font("Serif Sans", Font.BOLD, 25));
        this.setBorder(new EtchedBorder(1));
        this.setBounds(25, (height / 8) * 2, 100, 50);
    }
}