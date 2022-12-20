package com.company;

import javax.swing.*;
import java.awt.*;

//enter button within StartupFrame
public class StartupFrameEnterButton extends JButton {

    //constructor
    public StartupFrameEnterButton(int frameWidth, int frameHeight){

        //setting dimensions and details etc of button.
        this.setText("Enter");
        this.setMargin(new Insets(0,0,0,0));
        this.setHorizontalTextPosition(SwingConstants.CENTER);
        this.setVerticalTextPosition(SwingConstants.CENTER);
        this.setFocusPainted(false);

        this.setFont(new Font("Sans Serif", Font.BOLD, 17));

        //creating bounds of enter button
        this.setBounds(frameWidth - 3* frameWidth / 10, frameHeight - 2* frameHeight / 5, 60,30);

    }

}
