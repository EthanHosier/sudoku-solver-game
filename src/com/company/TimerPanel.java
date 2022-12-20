package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//Panel which contains timer, used in sidePanel which ticks up as player plays game
public class TimerPanel extends JPanel {
    private JLabel timerLabel;
    private int secondsPassed;
    private Timer myTimer;
    private String mins;
    private String seconds;
    private String zeroToAddSeconds;
    private String zeroToAddMins;

    //constructor method
    public TimerPanel(int height) {

        secondsPassed = 0;
        timerLabel = new JLabel();

        //creating new instance of Timer class, 1000ms is delayed each repeat, starts from action listener
        myTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //by default, extra zero slot for seconds and mins is set as empty string
                zeroToAddSeconds = "";
                zeroToAddMins = "";

                //every time repeats, seconds passed increases by 1
                secondsPassed ++;

                //stores number of minutes passed, calculated from number of seconds
                mins = Integer.toString((int)Math.floor(secondsPassed/60));

                //stores number of seconds passed, once the seconds from the minutes have been subtracted.
                seconds = Integer.toString(secondsPassed - (Integer.parseInt(mins) * 60));

                //if seconds is < 10, zeroToAddSeconds set to "0", so extra 0 is added when timer is printed, to make up for absence of extra digit.
                if (Integer.parseInt(seconds) < 10){
                    zeroToAddSeconds = "0";
                }

                //if mins < 10, zeroToAddMins set to "0" - make up for extra lost position.
                if (Integer.parseInt(mins) < 10) {
                    zeroToAddMins = "0";
                }

                //sets text of timerLabel to correct seconds passed, formatted with extra 0s to make up for extra digit spaces.
                timerLabel.setText(zeroToAddMins + mins + " : " + zeroToAddSeconds + seconds);

            }
        });

        this.setBounds(25,height / 30,100,50);
        timerLabel.setSize(new Dimension(200,200));
        timerLabel.setFont(new Font("Sarif Sans", Font.BOLD, 25));

        this.add(timerLabel);


    }

    //method to start timer
    public void startTimer(){
        myTimer.start();
    }

    //method to stop timer
    public void stopTimer(){
        myTimer.stop();
    }




}
