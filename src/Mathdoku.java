import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

//class is used to load, solve and print the puzzle

public class Mathdoku {

    //size of the puzzle
    private int size = 0;

    //number of groups in the puzzle
    private int noOfGrps = 0;

    //number of times a value has been stored in a cell to solve the puzzle
    private int choices = 0;

    //actual final puzzle
    private List<List<Integer>> puzzle = new ArrayList<>();

    //group name of the each cell
    private List<List<Character>> grpNameOfCell = new ArrayList<>();

    //list of groups
    private List<Group> groups = new ArrayList<>();

    //list of group names
    private Set<String> grpNames = new HashSet<>();

    //map contains group name as key and the list of cells in the group as value
    private Map<String, List<Cell>> grpCellsMap = new HashMap<>();

    /*
    printString method
    gets the input string and prints that to user
     */
    private void printString (String input){
        System.out.println(input);
    }

    /*
    clearAllDetails method
    all existing data will be cleared in this method
    called in load method to load new details
     */
    private void clearAllDetails(){
        if(grpNames != null && !grpNames.isEmpty()){
            grpNames.clear();
        }
        if(groups != null && !groups.isEmpty()){
            groups.clear();
        }
        if(grpCellsMap != null && !grpCellsMap.isEmpty()){
            grpCellsMap.clear();
        }
        if(grpNameOfCell != null && !grpNameOfCell.isEmpty()){
            grpNameOfCell.clear();
        }
        size = 0;
        noOfGrps = 0;
        choices = 0;
    }

    /*
    getStringArray method
    gets one line of text as input
    remove unwanted spaces and return array of strings
     */
    private String[] getStringArray(String line){
        String[] arr = line.split(" ");
        String[] returnArr = new String[arr.length];
        int i = 0;
        for(String grpDtl : arr){
            grpDtl = grpDtl.trim();
            if(!grpDtl.isEmpty()){
                returnArr[i] = grpDtl;
                i = i + 1;
            }
        }
        return returnArr;
    }

    /*
    loadPuzzle method
    gets the stream of data as input
    creates a puzzle with the input
     */
    public boolean loadPuzzle(BufferedReader stream) {
        boolean isLoaded = true;
        String space = " ";
        clearAllDetails();
        try {
            //null check
            if (stream != null) {
                String line = null;
                int overAllLineNo = 0;
                int lineNoForSize = 0;
                int lineNoForGrps = 0;

                //check line is empty
                while ((line = stream.readLine()) != null) {

                    //removing space at front and end
                    line = line.trim();

                    if(!line.isEmpty()) {
                        //condition to check whether the line belongs to grid or group description
                        if (line.contains(space)) {
                            String[] grpDetails = getStringArray(line);

                            //add the line only if it has all three information of group
                            //ignore the line in case if any detail is missing
                            if (grpDetails.length >= 3) {
                                Group grp = new Group();
                                grp.setName(grpDetails[0]);     //name of group
                                grpNames.add(grpDetails[0]);    //adding in group list
                                grp.setResult(grpDetails[1]);      //group result
                                grp.setOperator(grpDetails[2]);     //group operator
                                groups.add(grp);
                            }
                            lineNoForGrps = lineNoForGrps + 1;      //to find the number of groups
                        } else {
                            char[] grpNames = line.toCharArray();
                            List<Character> grid = new ArrayList<>();
                            for (int i = 0; i < grpNames.length; i++) {
                                //adding the group names in the row
                                grid.add(grpNames[i]);
                                if (grpCellsMap.containsKey(String.valueOf(grpNames[i]))) {
                                    Cell cell = new Cell(lineNoForSize, i);

                                    //set the group name as key and list of cells as value
                                    List<Cell> grpCells = grpCellsMap.get(String.valueOf(grpNames[i]));
                                    grpCells.add(cell);
                                    grpCellsMap.put(String.valueOf(grpNames[i]), grpCells);
                                } else {
                                    Cell cell = new Cell(lineNoForSize, i);
                                    List<Cell> grpCells = new ArrayList<>();
                                    grpCells.add(cell);

                                    //set the group name as key and list of cells as value
                                    grpCellsMap.put(String.valueOf(grpNames[i]), grpCells);
                                }
                            }
                            //add the row to the list
                            grpNameOfCell.add(lineNoForSize, grid);
                            lineNoForSize = lineNoForSize + 1;
                        }
                        overAllLineNo = overAllLineNo + 1;
                    }
                }

                size = lineNoForSize;
                noOfGrps = lineNoForGrps;

            } else {
                printString("Input stream is empty.");
                isLoaded = false;
            }
        } catch (IOException e){
            //catch IO exception
            isLoaded = false;
            printString("Exception in reading the input stream in loadPuzzle method.");
        }
        catch (NumberFormatException e){
            isLoaded = false;
            printString("One of the group descriptions have non integer result expectation. Kindly correct it in input.");
        }
        catch (Exception e){
            //catch all exception
            isLoaded = false;
            printString("System faced unexpected exception in loadPuzzle method.");
        }
        return isLoaded;
    }

    /*
    checkRowColSize method
    checks whether the input has n*n group name grid
    return boolean value
     */
    private boolean checkRowColSize(){
        boolean validSize = true;

        if(grpNameOfCell != null && !grpNameOfCell.isEmpty()){
            int puzzleSize = grpNameOfCell.size();
            for(int rowNo = 0; rowNo < puzzleSize ; rowNo++){
                List<Character> rowWise = grpNameOfCell.get(rowNo);

                //checks the puzzle row size with column size in each row
                if(puzzleSize != rowWise.size()){
                    validSize = false;
                    return validSize;
                }
            }
        } else {
            validSize = false;
        }

        return validSize;
    }

    /*
    readyToSolve method
    checks the puzzle is valid and solvable
    returns boolean value based on the validations
     */
    private boolean checkGrpListWithGrid(){
        boolean isGrpExists = true;

        if(grpNameOfCell != null && !grpNameOfCell.isEmpty() &&
                grpNames != null && !grpNames.isEmpty()) {
            for (List<Character> row : grpNameOfCell) {
                for (Character cell : row) {
                    if (!grpNames.contains(String.valueOf(cell))) {
                        isGrpExists = false;
                    }
                }
            }
        } else {
            isGrpExists = false;
        }
        return isGrpExists;
    }

    private boolean checkGrpResult(){
        boolean isValidGrpResult = true;

        try{
            if(groups != null && !groups.isEmpty()){
                for(Group grp : groups){
                    int result = Integer.valueOf(grp.getResult());
                    if(result <= 0){
                        isValidGrpResult = false;
                        return isValidGrpResult;
                    }
                }
            }
        } catch (Exception e){
            isValidGrpResult = false;
        }

        return isValidGrpResult;
    }

    public boolean readyToSolve() {
        boolean isReady = false;
        List<List<Integer>> testPuzzle = setZeroInAllCell();
        try {

            //check whether load method is called with valid values
            if(testPuzzle != null && !testPuzzle.isEmpty()){
                isReady = true;
            } else {
                isReady = false;
                printString("Please call load method to load the data or call the load method with valid inputs.");
                return isReady;
            }

            //checks whether the group name grid in input is n*n
            if(checkRowColSize()){
                isReady = true;
            } else {
                isReady = false;
                printString("Puzzle is invalid. It is not in n*n structure.");
                return isReady;
            }

            //check whether all the group names in grid are in description as well
            if (checkGrpListWithGrid()) {
                isReady = true;
            } else {
                isReady = false;
                printString("One of the groups provided in grid is not defined.");
                return isReady;
            }

            if(checkGrpResult()){
                isReady = true;
            } else{
                isReady = false;
                printString("One of the groups does not valid group result.");
                return isReady;
            }

            //checks whether all groups have valid operators
            if (checkGrpOpr()) {
                isReady = true;
            } else {
                isReady = false;
                printString("One of the groups does not have valid operator.");
                return isReady;
            }



            //for cells with equals operator check in row and column for duplicate
            if (validPuzzle(testPuzzle)) {
                isReady = true;
            } else {
                isReady = false;
                printString("Puzzle is invalid.");
                return isReady;
            }
        } catch (Exception e){
            //catches all exceptions of this method
            isReady = false;
            printString("System faced unexpected exception in readyToSolve method.");
        }

        return isReady;
    }

    /*
    setZeroInAllCell method
    creates puzzle with 0 in all cells
     */
    private List<List<Integer>> setZeroInAllCell(){
        List<List<Integer>> allZeroPuzzle = null;
        if(grpNameOfCell != null && !grpNameOfCell.isEmpty()) {
            allZeroPuzzle = new ArrayList<>();
            for (List<Character> rowWiseGrpName : grpNameOfCell) {
                List<Integer> rowWiseCellValue = new ArrayList<>();
                for (Character c : rowWiseGrpName) {
                    rowWiseCellValue.add(0);
                }
                allZeroPuzzle.add(rowWiseCellValue);
            }
        }
        return allZeroPuzzle;
    }

    /*
    validPuzzle method
    gets the puzzle as input
    checks duplicate value for equals operator groups
    returns boolean value
     */
    private boolean validPuzzle(List<List<Integer>> validPuzzle){
        boolean isValid = true;
        if(groups != null && !groups.isEmpty() && grpCellsMap != null
                && !grpCellsMap.isEmpty()) {
            for (Group grp : groups) {
                if (grp.getOperator().equals("=")) {
                    List<Cell> cells = grpCellsMap.get(grp.getName());
                    int grpResult = Integer.valueOf(grp.getResult());

                    //check whether equals operator group has more than one cell
                    if (cells == null || cells.isEmpty() || cells.size() != 1) {
                        isValid = false;
                        printString("equals to operator has invalid cells.");
                        return isValid;
                    } else {
                        //add the value in a temperory puzzle if it is valid
                        for (Cell cell : cells) {
                            if (validPuzzle.isEmpty() || validPuzzle.get(cell.getRowNumber()) == null || validPuzzle.get(cell.getRowNumber()).isEmpty()) {
                                List<Integer> rowWise = new ArrayList<>();
                                rowWise.add(cell.getColumnNumber(), grpResult);
                                validPuzzle.add(cell.getRowNumber(), rowWise);
                            }

                            //check for duplication in row
                            if (!checkInRow(validPuzzle.get(cell.getRowNumber()), grpResult)) {
                                isValid = false;
                                return isValid;
                            }

                            //check for duplication in column
                            if (!checkInColumn(cell, validPuzzle, grpResult)) {
                                isValid = false;
                                return isValid;
                            }
                            List<Integer> rowWise = validPuzzle.get(cell.getRowNumber());
                            rowWise.add(cell.getColumnNumber(), grpResult);
                            validPuzzle.add(cell.getRowNumber(), rowWise);
                        }
                    }
                }
                else{
                    List<Cell> cells = grpCellsMap.get(grp.getName());

                    //check whether operator has enough operands
                    if (cells == null || cells.isEmpty() || cells.size() <= 1) {
                        isValid = false;
                        printString("Operands provided for one of the operations is invalid.");
                        return isValid;
                    }
                }
            }
        } else{
            isValid = false;
        }
        return isValid;
    }

    /*
    checkInColumn method
    get cell position, puzzle and value as inputs
    checks value is already in same column
    returns boolean value
     */
    private boolean checkInColumn(Cell cell, List<List<Integer>> locPuzzle, int value){
        boolean validColValue = true;

        //loop the grid rowwise
        for(List<Integer> rowWise : locPuzzle){

            //loop each column
            for(int columnNo = 0; columnNo < rowWise.size() ; columnNo++){

                //check the column number
                if(columnNo == cell.getColumnNumber()){

                    //check if it matches with value
                    if(rowWise.get(columnNo) == value){
                        validColValue = false;
                        return validColValue;
                    }
                }
            }
        }

        return validColValue;
    }

    /*
    checkInRow method
    gets the values of the row and value as inputs
    checks the value is in the row
    return boolean value
     */
    private boolean checkInRow(List<Integer> rowWise, int value){
        boolean validRowValue = true;

        //loop all values in the row
        for(int cell : rowWise){

            //check whether its equal to value
            if(cell == value){
                validRowValue = false;
                return validRowValue;
            }
        }

        return validRowValue;
    }

    /*
    checkGrpOpr method
    checks whether the groups have valid operators
    returns the boolean value
     */
    private boolean checkGrpOpr(){
        boolean isGrpOprValid = true;

        List<String> validOperators = new ArrayList<>();
        validOperators.add("+");
        validOperators.add("-");
        validOperators.add("–");
        validOperators.add("*");
        validOperators.add("/");
        validOperators.add("=");

        if(groups != null && !groups.isEmpty()) {
            for (Group grp : groups) {

                //compares the operators of the group from input description with the valid operators
                if (!validOperators.contains(String.valueOf(grp.getOperator()))) {
                    isGrpOprValid = false;
                }
            }
        } else {
            isGrpOprValid = false;
        }
        return isGrpOprValid;
    }

    /*
    solve method
    solves the methdoku if its solvable
    returns boolean value
     */
    public boolean solve() {
        boolean isSolved = false;
        try {

            //check whether the puzzle has a 0 valued cell
            Cell nullCell = nullCheck();
            if(puzzle != null && !puzzle.isEmpty()) {
                if (nullCell != null) {

                    //loop all the valid values
                    for (int value = 1; value <= size; value++) {

                        //check for row duplication
                        if (checkInRow(puzzle.get(nullCell.getRowNumber()), value)) {

                            //check for column duplication
                            if (checkInColumn(nullCell, puzzle, value)) {

                                //check whether the value is valid for the group
                                if (checkInGrp(nullCell, value)) {
                                    List<Integer> rowWise = puzzle.get(nullCell.getRowNumber());

                                    //set the value in the cell
                                    rowWise.set(nullCell.getColumnNumber(), value);
                                    System.out.println("set value" + "\n" +print());
                                    //incrementing the number of choices made
                                    choices++;

                                    //make a recursive call till we set value in all cells
                                    if (solve()) {
                                        isSolved = true;
                                        return isSolved;
                                    }

                                    //if the value is not a fit - set zero in the cell
                                    List<Integer> rowWise2 = puzzle.get(nullCell.getRowNumber());
                                    rowWise2.set(nullCell.getColumnNumber(), 0);
                                    System.out.println("set zero" + "\n" +print());
                                }
                            }
                        }
                    }
                    isSolved = false;
                } else {
                    isSolved = true;
                }
            }
            else {
                isSolved = false;
                printString("Please call load method to load the data or call the load method with valid inputs.");
            }
        } catch (Exception e){
            //catches all exceptions of the method
            isSolved = false;
            printString("System faced unexpected exception in solve method. Call ready method to know the specific error.");
        }
        return isSolved;
    }

    /*
    checkInGrp method
    gets the cell position and value as inputs
    checks whether the value is fit for the group
    returns boolean value
     */
    private boolean checkInGrp(Cell cell, int value){
        boolean validGrpValue = false;
        String grpName = null;
        Group group = null;
        boolean zeroPresence = false;
        boolean firstZeroFlag = false;

        List<Character> rowWiseGrpNames = grpNameOfCell.get(cell.getRowNumber());

        //gets the group name of the cell from input grid
        grpName = String.valueOf(rowWiseGrpNames.get(cell.getColumnNumber()));

        for(Group grp : groups){

            //gets the group from the group description list
            if(grp.getName().equals(grpName)){
                group = grp;
                break;
            }
        }
        if(group != null) {
            int groupResult = Integer.valueOf(group.getResult());

            //gets the cells list for the group
            List<Cell> grpCells = grpCellsMap.get(grpName);

            //check the operation is addition
            if (group.getOperator().equals("+")) {

                //set the value into grpResult
                int grpResult = value;

                //add all the values of the group
                for (Cell grpCell : grpCells) {
                    List<Integer> rowWiseCellValues = puzzle.get(grpCell.getRowNumber());
                    int cellValue = rowWiseCellValues.get(grpCell.getColumnNumber());
                    grpResult = grpResult + cellValue;
                }

                //check the end value is less than or equal to group result provided in input
                if (grpResult <= groupResult) {
                    validGrpValue = true;
                    return validGrpValue;
                }
            }

            //check the operation is subraction
            if (group.getOperator().equals("-") || group.getOperator().equals("–")) {
                int grpSize = grpCells.size();
                int grpArr[] = new int[grpSize];

                //form an array with the values of the group
                for (int grpCellNo = 0; grpCellNo < grpSize; grpCellNo++) {
                    Cell grpCell = grpCells.get(grpCellNo);
                    List<Integer> rowWiseCellValues = puzzle.get(grpCell.getRowNumber());
                    int cellValue = rowWiseCellValues.get(grpCell.getColumnNumber());

                    //check the value is not zero or not the first zero of group
                    if (cellValue != 0 || firstZeroFlag) {
                        grpArr[grpCellNo] = cellValue;
                    }
                    //check the value is zero and first zero of group
                    else if (cellValue == 0 && !firstZeroFlag) {
                        firstZeroFlag = true;
                        grpArr[grpCellNo] = value;
                    }
                }

                //sorting the array
                Arrays.sort(grpArr);
                int max = 0;

                //loop the elements of the group
                //if zero is present set the flag
                //if no zero subtract all values from the max value of the group
                for (int i = 0; i < grpSize - 1; i++) {
                    if (grpArr[i] == 0) {
                        zeroPresence = true;
                        break;
                    }
                    max = grpArr[grpSize - 1] - grpArr[i];
                }

                //check the end value with group result or zero presence
                if (max == groupResult || zeroPresence) {
                    validGrpValue = true;
                    return validGrpValue;
                }

            }

            //check the operation is multiplication
            if (group.getOperator().equals("*")) {
                int grpSize = grpCells.size();
                int grpArr[] = new int[grpSize];

                //form an array with group values
                for (int grpCellNo = 0; grpCellNo < grpSize; grpCellNo++) {
                    Cell grpCell = grpCells.get(grpCellNo);
                    List<Integer> rowWiseCellValues = puzzle.get(grpCell.getRowNumber());
                    int cellValue = rowWiseCellValues.get(grpCell.getColumnNumber());

                    //check value is not zero or not first zero value of the group
                    if (cellValue != 0 || firstZeroFlag) {
                        grpArr[grpCellNo] = cellValue;
                    }
                    //check value is zero and first zero of group
                    else if (cellValue == 0 && !firstZeroFlag) {
                        firstZeroFlag = true;
                        grpArr[grpCellNo] = value;
                    }
                }

                int max = 1;

                //loop all the values of the group and multiply
                //if zero is present, set the flag value and break the loop
                //if no zero value, multiply all the values with each other
                for (int i = 0; i < grpSize; i++) {
                    if (grpArr[i] == 0) {
                        zeroPresence = true;
                        break;
                    }
                    max = max * grpArr[i];
                }

                //check the end value with result of group or presence of zero
                if (max == groupResult || zeroPresence) {
                    validGrpValue = true;
                    return validGrpValue;
                }
            }

            //check the operation is division
            if (group.getOperator().equals("/")) {
                int grpSize = grpCells.size();
                int grpArr[] = new int[grpSize];

                //form an array with group values
                for (int grpCellNo = 0; grpCellNo < grpSize; grpCellNo++) {
                    Cell grpCell = grpCells.get(grpCellNo);
                    List<Integer> rowWiseCellValues = puzzle.get(grpCell.getRowNumber());
                    int cellValue = rowWiseCellValues.get(grpCell.getColumnNumber());

                    //check value is not zero or not first zero of the group
                    if (cellValue != 0 || firstZeroFlag) {
                        grpArr[grpCellNo] = cellValue;
                    }
                    //check value is zero and first zero of the group
                    else if (cellValue == 0 && !firstZeroFlag) {
                        firstZeroFlag = true;
                        grpArr[grpCellNo] = value;
                    }
                }

                //sorting the values of the group
                Arrays.sort(grpArr);
                int max = 0;

                //loop all the values of the group
                //if zero is present, set the flag value and break the loop
                for (int i = 0; i < grpSize - 1; i++) {
                    if (grpArr[i] == 0) {
                        zeroPresence = true;
                        break;
                    }
                    max = grpArr[grpSize - 1] / grpArr[i];
                }

                //check the end value with the group result of zero presence
                if (max == groupResult || zeroPresence) {
                    validGrpValue = true;
                    return validGrpValue;
                }
            }

            //check the operation is equal to
            if (group.getOperator().equals("=")) {

                //compare the value with group result
                if (value == groupResult) {
                    validGrpValue = true;
                    return validGrpValue;
                }
            }
        }
        return validGrpValue;
    }

    /*
    nullCheck method
    check whether the puzzle has 0 valued cell
    return the cell position
     */
    private Cell nullCheck() {
        if (puzzle == null || puzzle.isEmpty()) {
            puzzle = setZeroInAllCell();
        }
        if (puzzle != null && !puzzle.isEmpty()){
            for (int rowNo = 0; rowNo < puzzle.size(); rowNo++) {
                List<Integer> rowWise = puzzle.get(rowNo);
                for (int columnNo = 0; columnNo < rowWise.size(); columnNo++) {
                    int cellValue = rowWise.get(columnNo);
                    if (cellValue == 0) {
                        return new Cell(rowNo, columnNo);
                    }
                }
            }
        }
        return null;
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
        try {
            if (puzzle != null && !puzzle.isEmpty()) {
                for (int i = 0; i < puzzle.size(); i++) {
                    List<Integer> rowsOfNos = puzzle.get(i);
                    for (int j = 0; j < rowsOfNos.size(); j++) {
                        int cell = rowsOfNos.get(j);

                        //if the value is not null, add the value
                        if (cell != 0) {
                            finalPuzzle = finalPuzzle.concat(String.valueOf(cell));
                        }
                        //if the value is null, add the group name of the cell
                        else {
                            List<Character> rowsOfGrpName = grpNameOfCell.get(i);
                            Character grpName = rowsOfGrpName.get(j);
                            finalPuzzle = finalPuzzle.concat(String.valueOf(grpName));
                        }
                    }
                    finalPuzzle = finalPuzzle.concat(newLine);
                }
            }
            //if the puzzle is empty, yet to solve, print the group name of the cell
            else if (grpNameOfCell != null && !grpNameOfCell.isEmpty()) {
                printString("Please solve the puzzle to print the output. Please find below the input.");
                for (int i = 0; i < grpNameOfCell.size(); i++) {
                    List<Character> rowsOfGrpName = grpNameOfCell.get(i);
                    for (int j = 0; j < rowsOfGrpName.size(); j++) {
                        Character grpName = rowsOfGrpName.get(j);
                        finalPuzzle = finalPuzzle.concat(String.valueOf(grpName));
                    }
                }
            }
            //in case load method is not called
            else {
                printString("Please load data to print.");
            }
        } catch (Exception e){
            //catches all the exceptions of the method
            finalPuzzle = null;
            printString("System faced unexpected exception in print method.");
        }
        return finalPuzzle;
    }

    /*
    choices method
    returns the number of choices/tries took to solve the puzzle
     */
    public int choices( ){
        if(choices == 0) {
            printString("Please call the solve method first to print the number of attempts made to solve.");
        }
        return choices;
    }

}
