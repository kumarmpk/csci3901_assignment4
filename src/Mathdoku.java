import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private Set<Character> grpNames = new HashSet<>();

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
                        char[] grpDetails = line.toCharArray();
                        if(grpDetails.length == 5) {
                            Group grp = new Group();
                            grp.setName(grpDetails[0]);
                            grpNames.add(grp.getName());
                            grp.setResult(Integer.valueOf(String.valueOf(grpDetails[2])));
                            grp.setOperator(grpDetails[4]);
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

        if(checkGrpOpr()){
            isReady = true;
        } else {
            isReady = false;
            printString("One of the groups does not have valid operator.");
            return isReady;
        }

        if(checkGrpListWithGrid()){
            isReady = true;
        } else {
            isReady = false;
            printString("One of the groups does not have enough information to proceed.");
            return isReady;
        }

        

        return isReady;
    }

    private boolean checkGrpListWithGrid(){
        boolean isGrpExists = true;

        for(List<Character> row : grpNameOfCell){
            for(Character cell : row){
                if(!grpNames.contains(cell)){
                    isGrpExists = false;
                }
            }
        }

        return isGrpExists;
    }

    private boolean checkGrpOpr(){
        boolean isGrpOprValid = true;

        List<Character> validOperators = new ArrayList<>();
        validOperators.add('+');
        validOperators.add('-');
        validOperators.add('*');
        validOperators.add('/');
        validOperators.add('=');

        for(Group grp : groups){
            if(!validOperators.contains(grp.getOperator())){
                isGrpOprValid = false;
            }
        }

        return isGrpOprValid;
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
        String emptyString = "";
        String finalPuzzle = emptyString;
        String newLine = "\n";
        for(List<Integer> rows : puzzle){
            for(Integer cell : rows){
                finalPuzzle = finalPuzzle.concat(String.valueOf(cell));
            }
            finalPuzzle = finalPuzzle.concat(newLine);
        }

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
