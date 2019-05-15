package com.example.nicolas.assignment02;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Class which defines my square custom view containing the cards
 */
public class CustomView extends View {
    /**
     * Array which will contain the limits of my board (from 0 to max size)
     */
    private float limits[] = new float[4 + 1];
    /**
     * Defines if the user is allowed to tap on a card
     */
    boolean allowed;
    /**
     * Defines if the game is over
     */
    boolean won;
    /**
     * Int which defines if this is the first or the second tap on the screen
     */
    int touched;
    /**
     * Index of the card tapped during the ACTION_DOWN event when the screen is touched
     */
    int down_cnt;

    /**
     * Constructor that takes in a context
     * @param c
     */
    public CustomView(Context c) {
        super(c);
        init();
    }

    /**
     * Constructor that takes in a context and a list of attributes that were set in XML
     * @param c
     * @param as
     */
    public CustomView(Context c, AttributeSet as) {
        super(c, as);
        init();
    }

    /**
     * Constructor that takes a context, attributes set and also a default style
     * @param c
     * @param as
     * @param default_style
     */
    public CustomView(Context c, AttributeSet as, int default_style) {
        super(c, as, default_style);
        init();
    }

    /**
     * Actions occurred during the construction
     */
    private void init() {
        won = false;
        allowed = true;
        touched = 0;
        Log.d("DEBUG", "Initializes the game");
    }

    /**
     * Method called when the custom view is created or when the orientation is changed
     * It allows us to set the size of our view
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();

        // We want our view to be a square, so we take the smallest size
        // and make it the width and the height of our view
        if (width > height) {
            size = height;
        } else {
            size = width;
        }
        setMeasuredDimension(size, size);

        // Calculates the limits of the cells on the board
        for (int i = 0; i <= 4; i++) {
            limits[i] = i * size / 4;
        }
    }

    /**
     * Method which returns the drawable associated to a card considering its status and type
     * @param card The card we want to draw
     * @return The drawable associated to the card
     */
    public Drawable getCardDrawable(Card card) {
        Card.State state = card.getState();
        // If the card is gone we return a transparent color
        if (state == Card.State.EMPTY)
            return new ColorDrawable(Color.TRANSPARENT);
        // If the card isn't flipped yet we set the default drawable
        else if (state == Card.State.DEFAULT) {
            Drawable d = getResources().getDrawable(R.drawable.def);
            // Calculates the size of the drawable considering the size of the board
            d.setBounds(0, 0, Math.round(limits[4] / 7), Math.round(limits[4] / 5.5f));
            return d;
        }
        // If the card is flipped we set the associated drawable
        else if (state == Card.State.FLIPPED) {
            Drawable d = null;
            switch (card.getType()) {
                case ONE:
                    d = getResources().getDrawable(R.drawable.one);
                    break;
                case TWO:
                    d = getResources().getDrawable(R.drawable.two);
                    break;
                case THREE:
                    d = getResources().getDrawable(R.drawable.three);
                    break;
                case FOUR:
                    d = getResources().getDrawable(R.drawable.four);
                    break;
                case FIVE:
                    d = getResources().getDrawable(R.drawable.five);
                    break;
                case SIX:
                    d = getResources().getDrawable(R.drawable.six);
                    break;
                case SEVEN:
                    d = getResources().getDrawable(R.drawable.seven);
                    break;
                case EIGHT:
                    d = getResources().getDrawable(R.drawable.eight);
                    break;
            }
            d.setBounds(0, 0, 150, 200);
            return d;
        }
        return null;
    }

    /**
     * Public method overridden to draw the contents of the widget
     * Simply draws all the cards at the limits of the board
     * @param canvas
     */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int cnt = 0;
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                // Save the state of the canvas
                canvas.save();
                // Translate to where the card should be drawn,
                // plus some margin calculated with the max size of the board (21.6 and 36 are ratios)
                canvas.translate(limits[x] + (limits[4] / 21.6f), limits[y] + (limits[4] / 36f));
                // Gets the picture to draw and draws it
                getCardDrawable(Globals.game.getCard(cnt)).draw(canvas);
                cnt++;
                // Restores the canvas to its original position
                canvas.restore();
            }
        }
    }

    /**
     * Methods which defines what happens when the user touches the screen
     * Only Down and Up actions interest us here
     * @param event
     * @return
     */
    public boolean onTouchEvent(MotionEvent event) {
        // If the user is not allowed to tap, nothing happens
        if (!allowed)
            return true;
        // If the user puts a finger down on the screen
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            // Gets the x and y coordinates of the touch
            float yPos = event.getY();
            float xPos = event.getX();

            int x = 0;
            int y = 0;
            // Gets the x and y position of the card in the board
            while (xPos > limits[x])
                x++;
            while (yPos > limits[y])
                y++;
            // Calculates the index of the card on which the user put the finger down
            down_cnt = ((y-1) * 4 + (x-1));
            return true;
        }
        // If the user takes off his finger from the screen
        else if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            // Gets the x and y coordinates of the touch
            float yPos = event.getY();
            float xPos = event.getX();

            int x = 0;
            int y = 0;
            // Gets the x and y position of the card in the board
            while (xPos > limits[x])
                x++;
            while (yPos > limits[y])
                y++;
            final int cnt = ((y-1) * 4 + (x-1));
            // Compares with the saved card index, so we know if the same card was
            // touched when the user put his finger down
            if (cnt != down_cnt)
                // If the card is different we do nothing
                return true;

            // Sets the touched card to the state Flipped if the card wasn't flipped already
            if (Globals.game.getCard(cnt).getState() == Card.State.DEFAULT ) {
                touched++;
                Globals.game.setCardState(cnt, Card.State.FLIPPED);
            }

            // Saves the first flipped card
            if (touched == 1) {
                Globals.game.setFirstCardId(cnt);
            }
            else if (touched == 2) {
                // If this is the 2nd card touched then we block the touch
                allowed = false;
                touched = 0;

                if (Globals.game.getTurn() == Game.Turn.PLAYER_ONE) {
                    // If Player 1 turn
                    // Set the next turn to player 2 and updates the UI
                    Globals.game.setTurn(Game.Turn.PLAYER_TWO);
                    Globals.infoText.setText(getResources().getString(R.string.turnInfo, 2));
                    // If the first card flipped is the same as the second
                    if (Globals.game.getCard(Globals.game.getFirstCardId()).getType() == Globals.game.getCard(cnt).getType()) {
                        won = true;
                        // Player 1 wins a point
                        Globals.game.setScore1(Globals.game.getScore1() + 1);
                        Globals.player1.setText(getResources().getString(R.string.player1, Globals.game.getScore1()));
                    }
                }
                else {
                    // Else if Player 2 turn
                    // Set the next turn to player 1 and updates the UI
                    Globals.game.setTurn(Game.Turn.PLAYER_ONE);
                    Globals.infoText.setText(getResources().getString(R.string.turnInfo, 1));
                    // If the first card flipped is the same as the second
                    if (Globals.game.getCard(Globals.game.getFirstCardId()).getType() == Globals.game.getCard(cnt).getType()) {
                        won = true;
                        // Player 2 wins a point
                        Globals.game.setScore2(Globals.game.getScore2() + 1);
                        Globals.player2.setText(getResources().getString(R.string.player2, Globals.game.getScore2()));
                    }
                }

                // Sets a handler that will be waiting 800ms before executing
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (won) {
                            // If the player won the round, we set the concerned cards to EMPTY state
                            // so they are removed from the board
                            Globals.game.setCardState(Globals.game.getFirstCardId(), Card.State.EMPTY);
                            Globals.game.setCardState(cnt, Card.State.EMPTY);
                        }
                        else {
                            // If the player didn't get the two right cards, we flip them again to their default state
                            Globals.game.setCardState(Globals.game.getFirstCardId(), Card.State.DEFAULT);
                            Globals.game.setCardState(cnt, Card.State.DEFAULT);
                        }
                        invalidate();
                        won = false;
                        allowed = true;

                        if (Globals.game.isOver()) {
                            // If all the cards are removed from the board (EMPTY state),
                            // we inform the user who won
                            if (Globals.game.getScore1() > Globals.game.getScore2())
                                Globals.infoText.setText(getResources().getString(R.string.winInfo, 1));
                            else if (Globals.game.getScore2() > Globals.game.getScore1())
                                Globals.infoText.setText(getResources().getString(R.string.winInfo, 2));
                            else
                                Globals.infoText.setText(getResources().getString(R.string.drawInfo));
                        }
                    }
                }, 800);
            }

            // Warns the app to update the view
            invalidate();
            return true;
        }

        return super.onTouchEvent(event);
    }
}
