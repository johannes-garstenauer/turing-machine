package turing.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This is the implementation of a deterministic turing machine.
 */
public class DTM implements TuringMachine {

    private final State[] states;
    private final State beginState;
    private final Tape input;

    // Contains output tape at first position.
    private final TapeWrite[] workingTapes;

    private final int numberOfTapes;

    /**
     * Public constructor for the DTM class.
     *
     * @param numberOfStates Amount of states in machine.
     * @param numberOfTapes  Amount of working tapes in machine.
     * @param startStateId   ID of the state in which the machine starts
     *                       computing input words.
     * @param stopStateIds   IDs of states in which the machine rejects an
     *                       input word.
     * @param acceptStateIds IDs of states in which the machine accepts an input
     *                       word.
     */
    public DTM(int numberOfStates, int numberOfTapes, int startStateId,
               Set<Integer> stopStateIds, Set<Integer> acceptStateIds) {

        this.numberOfTapes = numberOfTapes;

        states = new State[numberOfStates];
        for (int i = 0; i < numberOfStates; i++) {
            states[i] = new State(i, StateTypes.NORMAL);
            if (stopStateIds.contains(i)) {
                states[i] = new State(i, StateTypes.HOLDING);
            }
            if (acceptStateIds.contains(i)) {
                states[i] = new State(i, StateTypes.ACCEPTING);
            }
        }

        beginState = states[startStateId];
        input = new Tape();

        workingTapes = new TapeWrite[numberOfTapes + 1];
        for (int i = 0; i < numberOfTapes + 1; i++) {
            workingTapes[i] = new TapeWrite();
        }
    }

    /**
     * @inheritDoc
     */
    @Override
    public void addCommand(int sourceState, char inputTapeChar,
                           char[] tapeChars, int targetState,
                           Direction inputTapeHeadMove, char[] newTapeChars,
                           Direction[] tapeHeadMoves) {

        states[sourceState].addCommand(new Command(sourceState, inputTapeChar,
                tapeChars, targetState, inputTapeHeadMove, newTapeChars,
                tapeHeadMoves));
    }

    /**
     * @inheritDoc
     */
    @Override
    public String simulate(String input) {
        check(input);

        return workingTapes[0].toString();
    }

    /**
     * @inheritDoc
     */
    @Override
    public boolean check(String input) {
        State temp = beginState;

        insertStringToInputTape(input);

        while (!(temp.getStateType().equals(StateTypes.HOLDING))
                && !(temp.getStateType().equals(StateTypes.ACCEPTING))) {

            boolean commandFound = false;

            for (Command cmd : temp.getCommandList()) {
                if (isValidCommand(temp, cmd)) {
                    commandFound = true;
                    temp = runCommand(cmd);
                    break;
                }
            }

            /*  If no suitable command was found the input must be rejected by
             the turing machine  */
            if (!commandFound) {
                return false;
            }

        }
        return temp.getStateType() == StateTypes.ACCEPTING;
    }

    /**
     * @inheritDoc
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (State state : states) {
            stringBuilder.append(state);
        }

        return stringBuilder.toString();
    }

    /**
     * Compares if a given Character is a legal value to be used in a truing
     * program
     *
     * @param c The Character to be checked.
     * @return Boolean contains result of assessment.
     */
    public static boolean isValidTapeChar(Character c) {
        if (c > LAST_CHAR) {
            return c == BLANK_CHAR;
        } else {
            return c >= FIRST_CHAR;
        }
    }

    /**
     * Sets the content of the {@code input} to a given string
     *
     * @param str The String to be inserted in the input tape.
     */
    private void insertStringToInputTape(String str) {
        if ("".equals(str)) {
            return;
        }

        List<Character> temp = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            temp.add(i, str.charAt(i));
        }
        input.setContent(temp);
    }

    /**
     * Executes a given command by moving and writing on required tapes.
     *
     * @param cmd The command to be executed.
     * @return The target state of the command.
     */
    private State runCommand(Command cmd) {
        input.move(cmd.getInputTapeHeadMove());

        for (int i = 0; i < workingTapes.length; i++) {
            workingTapes[i].write(cmd.getNewTapeChars()[i]);
            workingTapes[i].move(cmd.getTapeHeadMoves()[i]);
        }

        return states[cmd.getTargetState()];
    }

    /**
     * Checks if a given command can be legally executed in the turing
     * machines current configuration.
     *
     * @param currentState The current state of the turing machine.
     * @param command      The command to be checked.
     * @return Boolean contains result of assessment.
     */
    private boolean isValidCommand(State currentState, Command command) {
        char[] workingTapesChars = new char[numberOfTapes + 1];

        for (int i = 0; i < workingTapesChars.length; i++) {
            workingTapesChars[i] = workingTapes[i].read();
        }

        /* Dummy command containing left relevant information about turing
        machine to be compared with given command */
        Command searchDummy = new Command(currentState.getStateID(),
                input.read(), workingTapesChars, command.getTargetState(),
                command.getInputTapeHeadMove(), command.getNewTapeChars(),
                command.getTapeHeadMoves());

        return searchDummy.equals(command);
    }
}
