package turing.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class wraps the parameters of the state in which a turing machine can
 * be found.
 */
public class State {

    private final int stateID;
    private StateTypes stateType;
    private final List<Command> commands = new ArrayList<>();

    /**
     * Public constructor for state class.
     *
     * @param stateID   ID number assigned to this state.
     * @param stateType Type assigned to this state.
     */
    public State(int stateID, StateTypes stateType) {
        this.stateID = stateID;
        this.stateType = stateType;
    }

    /**
     * Adds a command to {@code commands}.
     *
     * @param command The command to be added.
     */
    public void addCommand(Command command) {
        commands.add(command);
    }

    /**
     * Returns the list of commands {@code commands}.
     *
     * @return List containing commands having this state as a source state.
     */
    public List<Command> getCommandList() {
        return commands;
    }

    /**
     * Returns the commands {@code commands} theoretically executable in this
     * state in lexicographical order.
     *
     * @return String containing each command in a separate line.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        Collections.sort(commands);

        for (Command command : commands) {
            stringBuilder.append(command.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Returns of what type this state is.
     *
     * @return Type of the state.
     */
    public StateTypes getStateType() {
        return stateType;
    }

    /**
     * Set state type {@code stateType} of this state.
     *
     * @param stateType The state type to be assigned to this state.
     */
    public void setStateType(StateTypes stateType) {
        this.stateType = stateType;
    }

    /**
     * @return State identification number {@code stateID}.
     */
    public int getStateID() {
        return stateID;
    }
}
