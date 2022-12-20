package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

//frame which opens when program start, offering user selection of sudoku sizes and difficulties.
public class StartupFrame extends JFrame implements ActionListener {

    private ImageIcon icon;

    //stores size of sudoku (3 for 3x3, 2 for 2x2 etc)
    private int size;

    //stores difficulty of sudoku ("Easy","Medium" or "Hard")
    private String difficulty;


    private JOptionPane sizeOptionPane;
    private JOptionPane difficultyOptionPane;

    //array storing all available sizes, added to sizeOptionPane
    private String[] sudokuSizes = {"2x2", "3x3"};

    //array storing the available difficulties, added to difficultyOptionPane
    private String[] difficulties = {"Easy", "Medium", "Hard"};

    private StartupFrameEnterButton enterButton;

    private JComboBox sizesComboBox;
    private JComboBox difficultiesComboBox;

    //constructor method
    public StartupFrame(int frameWidth, int frameHeight){

        //sets frame location to the centre of the screen.
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((dimension.width - frameWidth) / 2, (dimension.height - frameHeight) / 2);



        //loads iconbluesudoku.png as an ImageIcon object from resources folder.
        java.net.URL imageURL = Main.class.getClassLoader().getResource("iconbluesudoku.png");

        Image image = null;

        try {
            image = ImageIO.read(imageURL);

        }catch (Exception e){
            System.out.println("Error loading image");
        }

        ImageIcon icon = new ImageIcon(image);

        //sets app icon as 'icon'
        this.setIconImage(icon.getImage());

        //sets title of frame
        this.setTitle("ESudoku");

        //setting dimensions etc of StartupFrame
        this.setSize(frameWidth,frameHeight);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);


        //adding instance of StartupFrameText to correct position for both Sudoku Size and Sudoku Difficulty combo box
        this.add(new StartupFrameText("Select Sudoku Size:", 20, frameHeight / 7));
        this.add(new StartupFrameText("Select Sudoku Difficulty:", 20,frameHeight / 3));

        //creating sizesComboBox as instance of JComboBox class, holding the String values of sudokuSizes
        sizesComboBox = new JComboBox(sudokuSizes);

        //starts displaying element of index 1 by default
        sizesComboBox.setSelectedIndex(1);
        sizesComboBox.setBounds(190, frameHeight / 7, 70,30);


        //creating difficultiesComboBox as instance of JComboBox class, holding the String values of difficulties
        difficultiesComboBox = new JComboBox(difficulties);
        difficultiesComboBox.setSelectedIndex(1);
        difficultiesComboBox.setBounds(190, frameHeight / 3, 70,30);

        //adding both comboBox objects to frame
        this.add(sizesComboBox);
        this.add(difficultiesComboBox);

        //initializing instance of StartupFrameEnterButton class (to be used at bottom right of frame)
        enterButton = new StartupFrameEnterButton(frameWidth,frameHeight);
        enterButton.addActionListener(this);

        //add enterButton to frame
        this.add(enterButton);

        //makes frame visible
        this.setVisible(true);

    }

    //ActionListener events
    @Override
    public void actionPerformed(ActionEvent e) {

        int size;
        String difficulty;
        ArrayList<Square> templateSudokuArrayList;
        ArrayList<Square> solvedSudokuArrayList;

        //if enter button pressed, contents of if statement are run.
        if (e.getSource() == enterButton){

            //gets size of sudoku requested by the use from sizesComboBox
            size = Character.getNumericValue( sizesComboBox.getSelectedItem().toString().charAt(0));

            // System.out.println(size);

            difficulty = difficultiesComboBox.getSelectedItem().toString();

            //System.out.println(difficulty);

            //creating new instance of sudokuMaker class, with parameters given of size and difficulty
            SudokuMaker sudokuMaker = new SudokuMaker(size,difficulty);

            //templateSudokuArrayList assinged the value of the getter method from sudokuMaker - an arrayList of Square objects of 0s and completed numbers,
            templateSudokuArrayList = sudokuMaker.getFinalTemplate();

            //SudokuMaster.printSudoku(templateSudokuArrayList,size);
            solvedSudokuArrayList = sudokuMaker.getCompletedSudokuArrayList();

            //SudokuMaster.printSudoku(solvedSudokuArrayList,size);

            //opens 'MainGameFrame'
            new MainGameFrame(size,templateSudokuArrayList,solvedSudokuArrayList,difficulty);

            //closes frame
            this.dispose();
        }



    }
}
