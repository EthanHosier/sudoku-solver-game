package com.company;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(int width, int height){

        //setting mainFrame dimensions / details
        this.setLayout(new BorderLayout());
        this.setSize(width,height);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
