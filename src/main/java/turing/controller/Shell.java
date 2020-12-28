package turing.controller;

import turing.TuringMachineFactory;
import turing.model.TuringMachine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.regex.Pattern;

/**
 * Shell to interact with a turing machine.
 */
public final class Shell {
    private static final String PROMPT = "dtm> ";
    private static final Pattern WHITESPACE_SPLIT = Pattern.compile("\\s+");
    private static String filePath;

    /**
     * Private constructor for Shell class.
     */
    private Shell() {
    }

    /**
     * Reads from the run window and triggers commands.
     *
     * @param args Program arguments are not used.
     * @throws IOException Possibly caused by readLine().
     */
    public static void main(String[] args) throws IOException {
        TuringMachine turingMachine = null;
        boolean quit = false;
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(System.in));

        while (!quit) {
            System.out.print(PROMPT);
            String input = bufferedReader.readLine();

            if (input == null) {
                break;
            }

            String[] tokenParts = WHITESPACE_SPLIT.split(input);
            if ((tokenParts.length == 0) || tokenParts[0].isEmpty()) {
                printError("There is no command");
            } else {
                switch (tokenParts[0].toLowerCase().charAt(0)) {
                case 'i':
                    turingMachine = executeInput(tokenParts);
                    break;
                case 'h':
                    printHelp();
                    break;
                case 'c':
                    if (isNullMachine(turingMachine)) {

                        // Breaks if the turing machine is uninitialized
                        break;
                    }
                    executeCheck(turingMachine, tokenParts);

                    turingMachine = constructTuringMachine(filePath);

                        /* Constructs the same turing machine again in order to
                        reset tape contents */
                    break;
                case 'r':
                    if (isNullMachine(turingMachine)) {
                        break;
                    }
                    executeRun(turingMachine, tokenParts);

                    /* Constructs the same turing machine again in order to
                        reset tape contents */
                    turingMachine = constructTuringMachine(filePath);
                    break;
                case 'q':
                    quit = true;
                    break;
                case 'p':
                    if (isNullMachine(turingMachine)) {

                        // Breaks if the turing machine is uninitialized
                        break;
                    }
                    executePrint(turingMachine);
                    break;
                default:
                    printError("Command unknown.");
                    System.out.println("Type \"help\" for further hints.");
                    break;
                }
            }
        }
    }

    /**
     * Prints out the commands making up the turing machines {@code
     * turingMachine} program.
     *
     * @param turingMachine Turing machine program to be printed out.
     */
    private static void executePrint(TuringMachine turingMachine) {
        System.out.println(turingMachine.toString());
    }

    /**
     * Creates a new turing machine from the path {@code filePath} in the
     * command arguments.
     *
     * @param tokenParts Arguments of the input command.
     * @return The newly constructed turing machine.
     * @throws IOException May be thrown by method constructTuringMachine().
     */
    private static TuringMachine executeInput(String[] tokenParts)
            throws IOException {
        if (hasCorrectAmountArguments(tokenParts, 2)) {
            filePath = parsePath(tokenParts);
            return constructTuringMachine(filePath);
        }
        return null;
    }

    /**
     * Creates a new turing machine using the class {@code
     * TuringMachineFactory}.
     *
     * @param path File path of program to be created.
     * @return Constructed turing machine.
     * @throws IOException May be thrown by method {@code loadFromFile()}.
     */
    private static TuringMachine constructTuringMachine(String path)
            throws IOException {
        try {
            return TuringMachineFactory.loadFromFile(Paths.get(path).toFile());
        } catch (ParseException | FileNotFoundException exception) {
            printError("Caught: " + exception);
            return null;
        }
    }

    /**
     * Parses information about file path into a string.
     *
     * @param tokenParts Command arguments.
     * @return String value of file path.
     */
    private static String parsePath(String[] tokenParts) {
        try {
            return tokenParts[1];
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsexc) {
            printError("Not enough arguments for this command!");
            return null;
        }
    }

    /**
     * Determines whether a given String is accepted by the turing machine.
     *
     * @param turingMachine The machine on which the check is executed.
     * @param tokenParts    String array of arguments containing the String word
     *                      to be assessed.
     */
    private static void executeCheck(TuringMachine turingMachine,
                                     String[] tokenParts) {
        boolean result = false;
        if (tokenParts.length == 1) {

            // No word is interpreted as empty input string.
            result = turingMachine.check("");
        } else if (tokenParts.length == 2) {
            result = turingMachine.check(tokenParts[1]);
        } else {

            // Any other amount of arguments than 1 or 2 triggers an error.
            printError("The amount of arguments is incorrect.");
        }

        if (result) {
            System.out.println("accept");
        } else {
            System.out.println("reject");
        }
    }

    /**
     * Simulates the turing machine on a given input word. Prints out the
     * content of the output tape afterwards.
     *
     * @param turingMachine The turing machine to be executed.
     * @param tokenParts    String array of arguments containing input word.
     */
    private static void executeRun(TuringMachine turingMachine,
                                   String[] tokenParts) {
        if (tokenParts.length == 1) {

            // No word is interpreted as empty input string.
            System.out.println(turingMachine.simulate(""));
        } else if (tokenParts.length == 2) {
            System.out.println(turingMachine.simulate(tokenParts[1]));
        } else {

            // Any other amount of arguments than 1 or 2 triggers an error.
            printError("The amount of arguments is incorrect.");
        }
    }

    /**
     * Determines whether the command has the required amount of arguments.
     *
     * @param tokenParts String array of all arguments.
     * @param amount     The required amount of arguments.
     * @return {@code true} if the expected amount of arguments is met.
     */
    private static boolean hasCorrectAmountArguments(String[] tokenParts,
                                                     int amount) {
        if (tokenParts.length != amount) {
            printError("The amount of arguments is incorrect.");
        }
        return tokenParts.length == amount;
    }

    /**
     * Prints out error message.
     *
     * @param message Error description.
     */
    private static void printError(String message) {
        System.err.println("Error! " + message);
    }

    private static boolean isNullMachine(TuringMachine turingMachine) {
        if (turingMachine == null) {
            printError("This deterministic turing machine has not been "
                    + "instantiated. Please use 'INPUT <path>' to do so.");
            return true;
        }
        return false;
    }

    /**
     * Prints a help message containing information about available commands.
     */
    private static void printHelp() {
        System.out.println("This program can simulate a functioning "
                + "deterministic turing machine.\n"
                + "Following commands are available:\n");
        System.out.println("INPUT <path>: Initiates the turing machine from "
                + "given file.");
        System.out.println("RUN <word> : Prints content of the output "
                + "tape after computing the input word.");
        System.out.println("CHECK <word> : Returns whether the given "
                + "word is accepted by the machine.");
        System.out.println("PRINT: Prints out the commands contained in the "
                + "turing machine.");
        System.out.println("HELP : Prints out his help message.");
        System.out.println("QUIT : Terminates this program.\n");
    }
}
