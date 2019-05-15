package com.example.nicolas.assignment02;

/**
 * Class which represents a card
 */
public class Card {

    /**
     * Enum which defines the current state of the card
     */
    public enum State {
        EMPTY,
        DEFAULT,
        FLIPPED
    }

    /**
     * Enum which defines the type of the card
     */
    public enum Type {
        ONE,
        TWO,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT
    }

    /**
     * Contains the card state
     */
    private State state;
    /**
     * Contains the card type
     */
    private Type type;

    /**
     * Card constructor
     * @param state state
     * @param type type
     */
    Card(State state, Type type) {
        this.state = state;
        this.type = type;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
