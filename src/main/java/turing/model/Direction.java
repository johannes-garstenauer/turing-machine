package turing.model;

/**
 * Enumeration of directions for headPos movements.
 */
public enum Direction {

    /**
     * This indicates a head movement to the left on the tape.
     */
    MOVE_BACK("-1"),

    /**
     * This indicates no head movement on the tape.
     */
    STAY("0"),

    /**
     * This indicates a head movement to the right on the tape.
     */
    MOVE_FORWARD("+1");

    private final String number;

    /**
     * Constructor for the enumeration Direction. For private use in {@code
     * Direction} class only.
     *
     * @param number The number that correlates with a direction/movement.
     */
    Direction(String number) {
        this.number = number;
    }

    /**
     * Getter for the String value assigned to direction.
     *
     * @return The String value of the direction.
     */
    public String getNumber() {
        return number;
    }
}