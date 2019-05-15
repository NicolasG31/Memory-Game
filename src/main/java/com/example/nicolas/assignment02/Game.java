package com.example.nicolas.assignment02;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class which contains our game data
 */
public class Game {

    /**
     * Enum which sets the current player turn
     */
    public enum Turn {
        PLAYER_ONE,
        PLAYER_TWO
    }

    /**
     * Contains the current turn
     */
    private Turn turn;
    /**
     * Contains the player 1 score
     */
    private int score1;
    /**
     * Contains the player 2 score
     */
    private int score2;
    /**
     * Contains the first flipped card ID
     */
    private int firstCardId;
    /**
     * Array which contains all our 16 cards
     */
    private ArrayList<Card> cards;

    /**
     * Game constructor which initialize variables to their default value.
     */
    Game() {
        turn = Turn.PLAYER_ONE;
        score1 = 0;
        score2 = 0;
        cards = new ArrayList<>();
        cards = fillCards(cards);

        // shuffles the cards in order to have a random order for the game
        Collections.shuffle(cards);
    }

    /**
     * Fills our empty card array with the cards we need for the game (8 pairs)
     * @param cards empty card array
     * @return filled array of cards
     */
    public ArrayList<Card> fillCards(ArrayList<Card> cards) {
        cards.add(new Card(Card.State.DEFAULT, Card.Type.ONE));
        cards.add(new Card(Card.State.DEFAULT, Card.Type.ONE));

        cards.add(new Card(Card.State.DEFAULT, Card.Type.TWO));
        cards.add(new Card(Card.State.DEFAULT, Card.Type.TWO));

        cards.add(new Card(Card.State.DEFAULT, Card.Type.THREE));
        cards.add(new Card(Card.State.DEFAULT, Card.Type.THREE));

        cards.add(new Card(Card.State.DEFAULT, Card.Type.FOUR));
        cards.add(new Card(Card.State.DEFAULT, Card.Type.FOUR));

        cards.add(new Card(Card.State.DEFAULT, Card.Type.FIVE));
        cards.add(new Card(Card.State.DEFAULT, Card.Type.FIVE));

        cards.add(new Card(Card.State.DEFAULT, Card.Type.SIX));
        cards.add(new Card(Card.State.DEFAULT, Card.Type.SIX));

        cards.add(new Card(Card.State.DEFAULT, Card.Type.SEVEN));
        cards.add(new Card(Card.State.DEFAULT, Card.Type.SEVEN));

        cards.add(new Card(Card.State.DEFAULT, Card.Type.EIGHT));
        cards.add(new Card(Card.State.DEFAULT, Card.Type.EIGHT));

        return cards;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public int getScore1() {
        return score1;
    }

    public void setScore1(int score1) {
        this.score1 = score1;
    }

    public int getScore2() {
        return score2;
    }

    public void setScore2(int score2) {
        this.score2 = score2;
    }

    public Card getCard(int i) {
        return cards.get(i);
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCardState(int id, Card.State nState) {
        cards.get(id).setState(nState);
    }

    public int getFirstCardId() {
        return firstCardId;
    }

    public void setFirstCardId(int firstCardId) {
        this.firstCardId = firstCardId;
    }

    /**
     * Verifies if the game is over
     * @return true or false
     */
    public boolean isOver() {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getState() != Card.State.EMPTY)
                return false;
        }
        return true;
    }
}
