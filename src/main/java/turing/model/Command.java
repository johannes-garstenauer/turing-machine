package turing.model;

import java.util.Arrays;

/**
 * Class to wrap command parameters.
 */
public class Command implements Comparable<Command> {
    private final int sourceState;
    private final char inputTapeChar;
    private final char[] tapeChars;
    private final int targetState;
    private final Direction inputTapeHeadMove;
    private final char[] newTapeChars;
    private final Direction[] tapeHeadMoves;

    /**
     * Public constructor for the command class.
     *
     * @param sourceState       State in which turing machine needs to be in
     *                          order for this command tobe considered for
     *                          execution.
     * @param inputTapeChar     Char which needs to read from input tape in
     *                          order for this command tobe considered for
     *                          execution.
     * @param tapeChars         Chars which need to be read from output and
     *                          working tapes in order for this command to be
     *                          considered for execution.
     * @param targetState       State of turing machine after execution of this
     *                          command.
     * @param inputTapeHeadMove Movement of input tape head of turing machine
     *                          after execution of this command.
     * @param newTapeChars      Chars written on output and working tapes of
     *                          turing machine after execution of this command.
     * @param tapeHeadMoves     Movements of output and working tapes heads of
     *                          turing machine after execution of this command.
     */
    public Command(int sourceState, char inputTapeChar,
                   char[] tapeChars, int targetState,
                   Direction inputTapeHeadMove, char[] newTapeChars,
                   Direction[] tapeHeadMoves) {
        this.sourceState = sourceState;
        this.inputTapeChar = inputTapeChar;
        this.tapeChars = tapeChars;
        this.targetState = targetState;
        this.inputTapeHeadMove = inputTapeHeadMove;
        this.newTapeChars = newTapeChars;
        this.tapeHeadMoves = tapeHeadMoves;
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (other instanceof Command) {
            Command otherCmd = (Command) other;
            return (sourceState == otherCmd.sourceState)
                    && (inputTapeChar == otherCmd.inputTapeChar)
                    && (Arrays.equals(tapeChars, otherCmd.tapeChars))
                    && (targetState == otherCmd.targetState)
                    && (inputTapeHeadMove.equals(otherCmd.inputTapeHeadMove))
                    && (Arrays.equals(newTapeChars, otherCmd.newTapeChars))
                    && (Arrays.equals(tapeHeadMoves, otherCmd.tapeHeadMoves));
        } else {
            return false;
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public int hashCode() {
        StringBuilder b = new StringBuilder(sourceState + "," + inputTapeChar);
        for (char c : tapeChars) {
            b.append(",").append(c);
        }
        return b.toString().hashCode();
    }

    /**
     * Returns command parameters as a one-line string.
     *
     * @return Command parameters as string.
     */
    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(").append(sourceState).append(", ")
                .append(inputTapeChar);

        for (char tapeChar : tapeChars) {
            stringBuilder.append(", ").append(tapeChar);
        }

        stringBuilder.append(") -> (").append(targetState).append(", ")
                .append(inputTapeHeadMove.getNumber());

        for (int i = 0; i < tapeHeadMoves.length; i++) {
            stringBuilder.append(", ").append(newTapeChars[i]).append(", ")
                    .append(tapeHeadMoves[i].getNumber());
        }

        return stringBuilder.append(")").toString();
    }

    /**
     * Getter for command parameter {@code targetState}.
     *
     * @return State id of target state.
     */
    public int getTargetState() {
        return targetState;
    }

    /**
     * Getter for command parameter {@code inputTapeHeadMove}.
     *
     * @return Input tape movement for head.
     */
    public Direction getInputTapeHeadMove() {
        return inputTapeHeadMove;
    }

    /**
     * Getter for command parameter {@code newTapeChars}
     *
     * @return New tape chars for output and working tapes.
     */
    public char[] getNewTapeChars() {
        return newTapeChars;
    }

    /**
     * Getter for command parameter {@code tapeHeadMoves}.
     *
     * @return New head movements of output and working tapes.
     */
    public Direction[] getTapeHeadMoves() {
        return tapeHeadMoves;
    }

    /**
     * @inheritDoc
     */
    @Override
    public int compareTo(Command command) {

        if (this.sourceState < command.sourceState) {
            return -1;
        } else if (this.sourceState > command.sourceState) {
            return 1;
        }

        if (this.inputTapeChar < command.inputTapeChar) {
            return -1;
        } else if (this.inputTapeChar > command.inputTapeChar) {
            return 1;
        }

        for (int i = 0; i < tapeChars.length; i++) {
            if (this.tapeChars[i] < command.tapeChars[i]) {
                return -1;
            } else if (this.tapeChars[i] > command.tapeChars[i]) {
                return 1;
            }
        }

        return 0;
    }
}
