import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//class is used to load, solve and print the puzzle

public class Mathdoku {

    //size of the puzzle
    private int size = 0;

    //number of groups in the puzzle
    private int noOfGrps = 0;

    //actual final puzzle
    private List<List<Integer>> puzzle = new ArrayList<>();

    //group name of the each cell
    private List<List<Character>> grpNameOfCell = new ArrayList<>();

    //list of groups
    private List<Group> groups = new ArrayList<>();

    /*
    printString method
    gets the input string and prints that to user
     */
    private void printString (String input){
        System.out.println(input);
    }

    /*
    loadPuzzle method
    gets the stream of data as input
    creates a puzzle with the input
     */
    public boolean loadPuzzle(BufferedReader stream) {
        boolean isLoaded = true;
        String space = " ";
        try {
            if (stream != null) {
                String line = null;
                int overAllLineNo = 0;
                int lineNoForSize = 0;
                int lineNoForGrps = 0;
                while ((line = stream.readLine()) != null) {
                    if(line.contains(space)){
                        char[] grpDesc = line.toCharArray();
                        if(grpDesc.length == 5) {
                            Group grp = new Group();
                            grp.setName(grpDesc[0]);
                            grp.setResult(Integer.valueOf(String.valueOf(grpDesc[2])));
                            grp.setOperator(grpDesc[4]);
                            groups.add(grp);
                        } else{
                            printString("Group description is wrong/without enough information in line number "+overAllLineNo);
                            isLoaded = false;
                            break;
                        }
                        lineNoForGrps = lineNoForGrps + 1;
                    } else{
                        char[] grpNames = line.toCharArray();
                        List<Character> cells = new ArrayList<>();
                        for(char grpName : grpNames){
                            cells.add(grpName);
                        }
                        grpNameOfCell.add(lineNoForSize, cells);
                        lineNoForSize = lineNoForSize + 1;
                    }
                    overAllLineNo = overAllLineNo + 1;
                }

                size = lineNoForSize;
                noOfGrps = lineNoForGrps;

            } else {
                printString("Input stream is empty.");
                isLoaded = false;
            }
        } catch (IOException e){
            isLoaded = false;
            printString("Exception in reading the input stream in loadPuzzle method.");
        } catch (Exception e){
            isLoaded = false;
            printString("System faced unexpected exception in loadPuzzle method.");
        }
        return isLoaded;
    }

    /*
    readyToSolve method
    checks the puzzle is valid and solvable
    returns boolean value based on the validations
     */
    public boolean readyToSolve() {
        boolean isReady = false;



        return isReady;
    }

    /*
    solve method
    tries to solve the method
     */
    public boolean solve() {
        boolean isSolved = false;



        return isSolved;
    }

    /*
    print method
    convert the puzzle into a string
    returns the puzzle
     */
    public String print() {
        String finalPuzzle = null;

        return finalPuzzle;
    }

    /*
    choices method
    returns the number of choices/tries took to solve the puzzle
     */
    public int choices( ){
        int numberOfChoices = 0;

        return numberOfChoices;
    }

}
