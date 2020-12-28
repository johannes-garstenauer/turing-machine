package turing.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Simulates functions of the input tape.
 */
public class Tape {

    /**
     * Arraylist containing the chars of this tape.
     */
    protected List<Character> content = new ArrayList<>();
    private int headPos;

    /**
     * Public constructor for Tape class.
     */
    public Tape() {
        this.headPos = 0;
        content.add('~'); // For avoidance of exceptions
    }

    /**
     * Reads the character contained at the current head position {@code
     * headPos}.
     *
     * @return Character from content list.
     */
    public char read() {
        return content.get(headPos);
    }

    /**
     * Changes the heads position {@code headPos}.
     *
     * @param dir Direction in which the head position is moved.
     */
    public void move(Direction dir) {

        switch (dir) {
        case MOVE_BACK:
            headPos--;
            break;
        case MOVE_FORWARD:
            headPos++;
            break;
        default:
            break;
        }

        if (headPos >= content.size()) {
            content.add('~');
        } else if (headPos < 0) {
            content.add(0, '~');
            headPos = 0;
        }
    }

    /**
     * @param content List to with which current content tape is replaced
     *                {@code content}.
     */
    public void setContent(List<Character> content) {
        this.content = content;
    }

    /**
     * @return Index of the current position of the head {@code headPos}.
     */
    public int getHeadPos() {
        return headPos;
    }

    /**
     * Returns String value of tapes content list {@code content}. Leading and
     * trailing blanks are removed.
     *
     * @return A string containing relevant tape content.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        /*
         * A stack in which blanks are placed until it is determined whether
         * they are trailing or contained within relevant tape content.
         */
        List<Character> tempStack = new ArrayList<>();

        /*
         * Once the first relevant character is reached all blanks will be
         * placed on the temporary stack.
         */
        boolean firstCharReached = false;

        for (Character ch : content) {
            if (firstCharReached) {
                if (ch != '~') {
                    for (Character stackCharacter : tempStack) {
                        stringBuilder.append(stackCharacter);
                    }
                    tempStack.clear();
                    stringBuilder.append(ch);
                } else {
                    tempStack.add(ch);
                }
            } else if (ch != '~') {
                firstCharReached = true;
                stringBuilder.append(ch);
            }
        }

        return stringBuilder.toString();
    }
}