package turing.model;

/**
 * Enumeration for types of states in order to determine the behaviour of the
 * turing machine.
 */
public enum StateTypes {

    /**
     * Once the turing machine reaches a state with this type it will stop
     * executing any new commands. Furthermore it indicates that the word
     * checked was rejected by the turing program.
     */
    HOLDING,

    /**
     * This is the default statetype.
     */
    NORMAL,

    /**
     * Once the turing machine reaches a state with this type it will stop
     * executing any new commands. Furthermore it indicates that the word
     * checked was accepted by the turing program.
     */
    ACCEPTING
}
