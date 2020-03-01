import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class MainClass {





    // Retrieve data to the end of the line as an argument for a method call
    // Include two special kinds of arguments:
    //   "null" asks us to return no string
    //   "empty" asks us to return an empty string
    private static String getEndingString(Scanner userInput ) {
        String userArgument = null;

        userArgument = userInput.next();
        userArgument = userArgument.trim();

        // Include a "hack" to provide null and empty strings for testing
        if (userArgument.equalsIgnoreCase("empty")) {
            userArgument = "";
        } else if (userArgument.equalsIgnoreCase("null")) {
            userArgument = null;
        } else if(userArgument.equalsIgnoreCase("\"\"")){
            userArgument = "";
        }

        return userArgument;
    }


    // Main program to process user commands.
    // This method is not robust.  When it asks for a command, it expects all arguments to be there.

    public static void main(String[] args) {
        // Command options

        String load = "load";
        String ready = "ready";
        String solve = "solve";
        String print = "print";
        String choices = "choices";
        String quit = "quit";

        // Define variables to manage user input

        String userCommand = "";
        Scanner userInput = new Scanner( System.in );

        // Define the recommender that we will be testing.

        Mathdoku mathdoku = new Mathdoku();

        // Define variables to catch the return values of the methods

        boolean booleanOutcome;
        String stringOutcome;
        int integerOutcome;

        // Let the user know how to use this interface

        System.out.println("Please find below the commands available:");
        System.out.println("  " + load + " <stream of data>");
        System.out.println("  " + ready );
        System.out.println("  " + solve);
        System.out.println("  " + print);
        System.out.println("  " + choices);

        // Process the user input until they provide the command "quit"
        try {
            do {
                // Find out what the user wants to do
                userCommand = userInput.next();

                /* Do what the user asked for. */

                if (userCommand.equalsIgnoreCase(load)) {

                    // Call the method
                    booleanOutcome = mathdoku.loadPuzzle(getBufferedStream());
                    System.out.println(userCommand + " outcome " + booleanOutcome);
                }
                else if (userCommand.equalsIgnoreCase(ready)) {

                    //call the method
                    booleanOutcome = mathdoku.readyToSolve();
                    System.out.println(userCommand + " outcome " + booleanOutcome);
                }
                else if (userCommand.equalsIgnoreCase(solve)){
                    //call the method
                    booleanOutcome = mathdoku.solve();
                    System.out.println(userCommand + " outcome " + booleanOutcome);
                }
                else if(userCommand.equalsIgnoreCase(print)){
                    //call the method
                    stringOutcome = mathdoku.print();
                    System.out.println(userCommand + " outcome " + stringOutcome);
                }
                else if(userCommand.equalsIgnoreCase(choices)){
                    //call the method
                    integerOutcome = mathdoku.choices();
                    System.out.println(userCommand + " outcome "+integerOutcome);
                }
                else if (userCommand.equalsIgnoreCase(quit)) {
                    System.out.println(userCommand);
                } else {
                    System.out.println("Bad command: " + userCommand);
                }
            } while (!userCommand.equalsIgnoreCase("quit"));
        }
        catch (Exception e){
            System.out.println("Program faced an unexpected exception.");
        }

        // The user is done so close the stream of user input before ending.
        userInput.close();
    }

    /*
    getBufferedStream method
    gets the full file path converts into the buffered stream of data
    returns the stream
     */
    private static BufferedReader getBufferedStream(){
        BufferedReader bufferedReader = null;

        try{
            File file = new File("C:\\Users\\prath\\IdeaProjects\\Assignment4\\manoharan\\input4.txt");
            FileReader fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);

        } catch (Exception e){
            System.out.println("System faced unexpected exception in converting the file into stream.");
        }

        return bufferedReader;
    }


}
