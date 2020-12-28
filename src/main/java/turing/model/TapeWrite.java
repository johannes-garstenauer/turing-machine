package turing.model;

/**
 * Simulates the functions of the output and working tapes.
 */
public class TapeWrite extends Tape {

    /**
     * Public constructor for TapeWrite class.
     */
    public TapeWrite() {
        super();
    }

    /**
     * Replaces character in content list at current heads position
     *
     * @param c Character which is inserted into tape.
     */
    public void write(Character c) {
        content.remove(getHeadPos());
        content.add(getHeadPos(), c);
    }
}
