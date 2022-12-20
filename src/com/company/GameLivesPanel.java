package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameLivesPanel extends JPanel {

    private ArrayList<JLabel> xLabels;
    private ImageIcon image;

    public GameLivesPanel(int width, int height){

        xLabels = new ArrayList<>();

        //gets directory of project



        //loads 1200px-Red_xblur.svg.png as an icon from resources folder.
        java.net.URL imageURL = Main.class.getClassLoader().getResource("1200px-Red_xblur.svg.png");

        Image image = null;

        try {
            image = ImageIO.read(imageURL);

        }catch (Exception e){
            System.out.println("Error loading image");
        }

        ImageIcon icon = new ImageIcon(image);

        this.setLayout(new GridLayout(4,1));


        //creating each red X label, and adding it to xLabels arrayList.
        for(int i =0; i < 3; i ++) {

        JLabel tempLabel = new JLabel();

        tempLabel.setIcon(icon);
        tempLabel.setHorizontalAlignment(SwingConstants.CENTER);
        tempLabel.setPreferredSize(new Dimension(60, 60));
        xLabels.add(tempLabel);

        }


    }

    //method to add the correct red X to the panel, depending on hpw many lives left
    public void addX(int lives){

        this.add(xLabels.get(lives));

        //updating window to show new red crosses.
        this.revalidate();
        this.repaint();

    }

//method to add 'OUT OF LIVES!' message to the panel
    public void addOutOfLivesMessage(){

        //creating new instance of JLabel
        JLabel outOfLivesLabel = new JLabel();

        //setting text to 'OUT OF LIVES'
        outOfLivesLabel.setText("OUT OF LIVES!");

        outOfLivesLabel.setFont(new Font("Sarif Sans", Font.BOLD,20));
        outOfLivesLabel.setForeground(Color.red);

        //adding label to panel
        this.add(outOfLivesLabel);

        //updating panel to show message
        this.revalidate();
        this.repaint();

    }

    //method to add 'CONGRATULATIONS!' message to panel.
    public void addCongratulationsMessage(){

        //making new instance of JLabel;
        JLabel congratulationsLabel = new JLabel();
        congratulationsLabel.setText("CONGRATULATIONS!");
        congratulationsLabel.setFont(new Font("Sarif Sans", Font.BOLD,20));
        congratulationsLabel.setForeground(new Color(0x4FE756));

        //adding label to panel
        this.add(congratulationsLabel);

        //updating panel to show congratulations message.
        this.revalidate();
        this.repaint();

    }


}
