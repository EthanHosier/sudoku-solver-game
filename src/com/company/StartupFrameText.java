package com.company;

import javax.swing.*;
import java.awt.*;

//object used as text Label for both the 'set size...' and 'set difficulty...' in startupFrame
public class StartupFrameText extends JLabel {

    public StartupFrameText(String text, int x, int y){

        this.setText(text);
        this.setFont(new Font("Sans Serif", Font.BOLD, 13));
        this.setBounds(x,y,text.length() * 13,30);

    }

}

