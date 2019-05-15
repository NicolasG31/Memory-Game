package com.example.nicolas.assignment02;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Our main activity class, which sets what happens when we start the app
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Method called when the main activity is started.
     * Sets the globals variables needed for the UI.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sets the global UI objects to the activity
        Globals.player1 = findViewById(R.id.player1label);
        Globals.player2 = findViewById(R.id.player2label);
        Globals.infoText = findViewById(R.id.infoText);
        Globals.resetButton = findViewById(R.id.resetButton);

        // Sets the text to display considering the game data
        Globals.player1.setText(getResources().getString(R.string.player1, Globals.game.getScore1()));
        Globals.player2.setText(getResources().getString(R.string.player2, Globals.game.getScore2()));
        if (Globals.game.isOver()) {
            if (Globals.game.getScore1() > Globals.game.getScore2())
                Globals.infoText.setText(getResources().getString(R.string.winInfo, 1));
            else if (Globals.game.getScore2() > Globals.game.getScore1())
                Globals.infoText.setText(getResources().getString(R.string.winInfo, 2));
            else
                Globals.infoText.setText(getResources().getString(R.string.drawInfo));
        }
        else {
            if (Globals.game.getTurn() == Game.Turn.PLAYER_ONE)
                Globals.infoText.setText(getResources().getString(R.string.turnInfo, 1));
            else
                Globals.infoText.setText(getResources().getString(R.string.turnInfo, 2));
        }
    }

    /**
     * Method which resets the activity when we click on the 'reset' button.
     * Initializes a new game so the data is erased.
     * Used to restart the game.
     * @param view
     */
    public void resetClick(View view) {
        Globals.game = new Game();
        this.recreate();
    }
}
