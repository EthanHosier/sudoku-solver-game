package com.company;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class MainGameFrame implements ActionListener{

    ArrayList<Square> templateSudokuArrayList;
    ArrayList<Square> solvedSudokuArrayList;
    int size;

    JFrame mainFrame;
    ButtonPanel buttonPanel;
    SudokuBoardPanel sudokuBoardPanel;
GameLivesPanel gameLivesPanel;

    private ImageIcon icon;


    TimerPanel timerPanel;

    ArrayList<GUISquare> guiSquareArrayList;

    int lives = 3;

    ClueButton clueButton;
    SolveButton solveButton;

    int height = 800;
    int width = 900;

    public MainGameFrame(int size, ArrayList<Square> templateSudokuArrayList, ArrayList<Square> solvedSudokuArrayList, String difficulty){


        //loads iconbluesudoku.png as an ImageIcon object from resources folder.
        java.net.URL imageURL = Main.class.getClassLoader().getResource("iconbluesudoku.png");

        Image image = null;

        try {
            image = ImageIO.read(imageURL);

        }catch (Exception e){
            System.out.println("Error loading image");
        }

        ImageIcon icon = new ImageIcon(image);





        this.templateSudokuArrayList = templateSudokuArrayList;
        this.solvedSudokuArrayList = solvedSudokuArrayList;
        this.size = size;

        //initializing mainFrame as instance of JFrame
        MainFrame mainFrame = new MainFrame(width,height);

        //makes mainFrame appear at slightly above the centre of the screen.
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        mainFrame.setLocation((dimension.width - width) / 2, (dimension.height - height) / 2 - 20);

        mainFrame.setIconImage(icon.getImage());

        mainFrame.setTitle("ESudoku");

        //setting solvedButton information
        solveButton = new SolveButton(height);
        solveButton.addActionListener(this);



        //setting clearButton information
        clueButton = new ClueButton(height);
        clueButton.addActionListener(this);

        //creating instance of timerPanel
        timerPanel = new TimerPanel(height);

        buttonPanel = new ButtonPanel();
        buttonPanel.add(timerPanel);
        buttonPanel.add(clueButton);
        buttonPanel.add(solveButton);

        //instantiating side panel object
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(2,1));

        sidePanel.setSize(200,height);

        sidePanel.add(buttonPanel);

        gameLivesPanel = new GameLivesPanel(width,height);

        sidePanel.add(gameLivesPanel);


        this.sudokuBoardPanel = new SudokuBoardPanel(size,templateSudokuArrayList,difficulty);

        guiSquareArrayList = sudokuBoardPanel.getGuiSquareArrayList();

        for(GUISquare g: guiSquareArrayList){

            g.addActionListener(this);

        }


        JPanel spacing = new JPanel();
        spacing.setPreferredSize(new Dimension(100,100));

        //mainFrame.add(spacing,BorderLayout.NORTH);
        mainFrame.add(sudokuBoardPanel,BorderLayout.CENTER);
        //mainFrame.add(gameLivesPanel,BorderLayout.SOUTH);
        mainFrame.add(sidePanel,BorderLayout.EAST);
        //mainFrame.add(spacing,BorderLayout.WEST);

        //starts timer
        timerPanel.startTimer();
        mainFrame.setVisible(true);
        Thread.currentThread().setName("UI Thread");
       // System.out.println(Thread.currentThread().getName());

    }


    //method to check if sudoku completed
    public void checkIfCompleted(){

        //completed set to true by default
        boolean completed = true;

        //loops through all Square objects within templateSudokuArrayList
        for (Square s :templateSudokuArrayList){

            //if any numbers are wrong, completed set to false
            if(s.getNumber() != solvedSudokuArrayList.get(templateSudokuArrayList.indexOf(s)).getNumber()){
                completed = false;
            }

        }

        if (completed == true){

            //stops timer
            timerPanel.stopTimer();

            //adds 'CONGRATULATIONS!' message to gameLivesPanel
            gameLivesPanel.addCongratulationsMessage();

            //disables clueButton.
            clueButton.setEnabled(false);

        }



    }

    public void lifeCheck() {

        //if user still has lives left, adds new corresponding square to gameLivesPanel
        if (lives >= 0) {
            gameLivesPanel.addX(lives);


           // System.out.println("You now have" + lives + "lives");
        } else {

            //code here runs if user has no lives now left.

            //stops timer
            timerPanel.stopTimer();

            //adds Out of Lives! message to gameLivesPanel
            gameLivesPanel.addOutOfLivesMessage();

            clueButton.setEnabled(false);

            //sets all GUISquares as disabled
            for (GUISquare gSquare : guiSquareArrayList) {
                gSquare.setEnabled(false);
            }

        }
    }


    //action listener methods
    @Override
    public void actionPerformed(ActionEvent e) {

      //if solve button pressed, code within if statement executed
      if (e.getSource() == solveButton){

          //disables solveButton, so can't be accidentally / deliberately pressed more than once.
          solveButton.setEnabled(false);

          //stops timer
          timerPanel.stopTimer();

          AnimatingSolve thread = new AnimatingSolve(e,solveButton,sudokuBoardPanel,templateSudokuArrayList,size);
          clueButton.setEnabled(false);
          thread.start();

      }

      //if clue button pressed, code within if statement executed.
        if(e.getSource() == clueButton){

            //new arrayList created, to contain all squares which can be assigned a 'clue value' (have value 0 at the moment)
            ArrayList<Square> availableSquares = new ArrayList<>();

            Random rand = new Random();
            Square chosenSquare;
            int index;

            //loops through all Square objects in templateSudokuArrayList, adding to availableSquares arrayList if value is 0
            for (Square s: templateSudokuArrayList){
                if(s.getNumber() == 0){
                    availableSquares.add(s);
                }
            }

            //creates index for random pick of Square objects within templateSudokuArrayList
            // - try, catch statement used as otherwise error thrown when only one object in array
            try {
                index = rand.nextInt(availableSquares.size() - 1);
            }catch(Exception f){
                index = rand.nextInt(availableSquares.size());
            }

            //chosenSquare used as reference for square chosen to be given clue value
            chosenSquare = availableSquares.get(index);

            //sets chosenSquare with correct square
            chosenSquare.setNumber(solvedSudokuArrayList.get(templateSudokuArrayList.indexOf(chosenSquare)).getNumber());

            //updates the corresponding GUISquare object to display correct new number
            guiSquareArrayList.get(templateSudokuArrayList.indexOf(chosenSquare)).setNumber(chosenSquare.getNumber());

            //sets corresponding GUISquare object to unchangeable.
            guiSquareArrayList.get(templateSudokuArrayList.indexOf(chosenSquare)).setEnabled(false);

            //decreases lives by -1
            lives --;

            //calls method to check no. lives
            lifeCheck();
        }


        //for if number entered into jtextfield
       String numEnteredAsString;

        //loops through all GUISquare objects within guiSquareArrayList, and if that object is cause of actionEvent, is updated accordingly
        for(GUISquare g: guiSquareArrayList){
            if(e.getSource() == g){

                //gets rid of spaces before/after from user input
                numEnteredAsString = g.getText().trim();

                //if correct number added, updated in different arrayLists + on gui.
                if (numEnteredAsString.equals(Integer.toString( solvedSudokuArrayList.get(guiSquareArrayList.indexOf(g)).getNumber()))){

                    g.setNumber(Integer.parseInt(numEnteredAsString));

                    //textField now set to unchangeable
                    g.setEnabled(false);
                    templateSudokuArrayList.get(guiSquareArrayList.indexOf(g)).setNumber(g.getNumber());
                    checkIfCompleted();
                }else{
                    //if not correct number added, textField reset again to value 0 (now empty)
                    g.setNumber(0);

                    //lives number decreases by 1
                    lives -= 1;

                    //calls method to check user's no. lives.
                    lifeCheck();




                }

            }



        }

    }
}
