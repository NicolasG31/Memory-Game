package com.example.nicolas.assignment02;

import android.widget.Button;
import android.widget.TextView;

/**
 * Class which contains global variables associated to our UI
 * They are needed because the activity is restarted when we change the orientation
 */
public class Globals {

    /**
     * Textview which displays Player 1 score
     */
    static public TextView player1;
    /**
     * Textview which displays Player 2 score
     */
    static public TextView player2;
    /**
     * Textview which displays informations about the current turn or displays who won the game
     */
    static public TextView infoText;
    /**
     * Button which allows us to reset the activity
     */
    static public Button resetButton;
    /**
     * Game variable which contains the current state of the game
     */
    static public Game game = new Game();
}
