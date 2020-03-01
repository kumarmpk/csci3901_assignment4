import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

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

    private Map<Character, List<Cell>> grpCellsMap = new HashMap<>();

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
                            grpNames.add(grpDetails[0]);
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
                        List<Character> grid = new ArrayList<>();
                        for(int i = 0; i < grpNames.length; i++){
                            grid.add(grpNames[i]);
                            if(grpCellsMap.containsKey(grpNames[i])){
                                Cell cell = new Cell(lineNoForSize, i);
                                List<Cell> grpCells = grpCellsMap.get(grpNames[i]);
                                grpCells.add(cell);
                                grpCellsMap.put(grpNames[i], grpCells);
                            }
                            else{
                                Cell cell = new Cell(lineNoForSize, i);
                                List<Cell> grpCells = new ArrayList<>();
                                grpCells.add(cell);
                                grpCellsMap.put(grpNames[i], grpCells);
                            }
                        }
                        grpNameOfCell.add(lineNoForSize, grid);
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
        List<List<Integer>> testPuzzle = setZeroInAllCell();

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

        if(validPuzzle(testPuzzle)){
            isReady = true;
        } else {
            isReady = false;
            printString("Puzzle is invalid.");
            return isReady;
        }


        return isReady;
    }

    private List<List<Integer>> setZeroInAllCell(){
        List<List<Integer>> allZeroPuzzle = new ArrayList<>();
        for(List<Character> rowWiseGrpName : grpNameOfCell){
            List<Integer> rowWiseCellValue = new ArrayList<>();
            for(Character c : rowWiseGrpName){
                rowWiseCellValue.add(0);
            }
            allZeroPuzzle.add(rowWiseCellValue);
        }
        return allZeroPuzzle;
    }

    private boolean validPuzzle(List<List<Integer>> validPuzzle){
        boolean isValid = true;

        for(Group grp : groups){
            if(grp.getOperator() == '='){
                List<Cell> cells = grpCellsMap.get(grp.getName());
                if(cells == null || cells.isEmpty() || cells.size() != 1){
                    isValid = false;
                    return isValid;
                } else {
                    for(Cell cell : cells){
                        if(validPuzzle.isEmpty() || validPuzzle.get(cell.getRowNumber()) == null || validPuzzle.get(cell.getRowNumber()).isEmpty() ) {
                            List<Integer> rowWise = new ArrayList<>();
                            rowWise.add(cell.getColumnNumber(), grp.getResult());
                            validPuzzle.add(cell.getRowNumber(), rowWise);
                        }

                        if(!checkInRow(validPuzzle.get(cell.getRowNumber()), grp.getResult())){
                            isValid = false;
                            return isValid;
                        }
                        if(!checkInColumn(cell, validPuzzle, grp.getResult())){
                            isValid = false;
                            return isValid;
                        }
                        List<Integer> rowWise = validPuzzle.get(cell.getRowNumber());
                        rowWise.add(cell.getColumnNumber(), grp.getResult());
                        validPuzzle.add(cell.getRowNumber(), rowWise);
                    }
                }
            }
        }

        return isValid;
    }


    private boolean checkInColumn(Cell cell, List<List<Integer>> locPuzzle, int value){
        boolean validColValue = true;

        for(List<Integer> rowWise : locPuzzle){
            for(int columnNo = 0; columnNo < rowWise.size() ; columnNo++){
                if(columnNo == cell.getColumnNumber()){
                    if(rowWise.get(columnNo) == value){
                        validColValue = false;
                        return validColValue;
                    }
                }
            }
        }

        return validColValue;
    }

    private boolean checkInRow(List<Integer> rowWise, int value){
        boolean validRowValue = true;

        for(int cell : rowWise){
            if(cell == value){
                validRowValue = false;
                return validRowValue;
            }
        }

        return validRowValue;
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
        validOperators.add('–');
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
        try {
            Cell nullCell = nullCheck();
            if (nullCell != null) {
                for (int value = 1; value <= size; value++) {
                    if (checkInRow(puzzle.get(nullCell.getRowNumber()), value)) {
                        if (checkInColumn(nullCell, puzzle, value)) {
                            if (checkInGrp(nullCell, value)) {
                                List<Integer> rowWise = puzzle.get(nullCell.getRowNumber());
                                rowWise.set(nullCell.getColumnNumber(), value);
                                System.out.println("set value" + "\n" +print());
                                if (solve()) {
                                    isSolved = true;
                                    return isSolved;
                                }
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
        } catch (Exception e){
            e.printStackTrace();
        }
        return isSolved;
    }

    private boolean checkInGrp(Cell cell, int value){
        boolean validGrpValue = false;
        Character grpName = null;
        Group group = null;
        boolean zeroPresence = false;
        boolean firstZeroFlag = false;

        List<Character> rowWiseGrpNames = grpNameOfCell.get(cell.getRowNumber());
        grpName = rowWiseGrpNames.get(cell.getColumnNumber());

        for(Group grp : groups){
            if(grp.getName() == grpName){
                group = grp;
                break;
            }
        }

        List<Cell> grpCells = grpCellsMap.get(grpName);

        if(group.getOperator() == '+'){
            int grpResult = value;
            for(Cell grpCell : grpCells){
                List<Integer> rowWiseCellValues = puzzle.get(grpCell.getRowNumber());
                int cellValue = rowWiseCellValues.get(grpCell.getColumnNumber());
                grpResult = grpResult + cellValue;
            }
            if(grpResult <= group.getResult()){
                validGrpValue = true;
                return validGrpValue;
            }
        }
        if(group.getOperator() == '-' || group.getOperator() == '–'){
            int grpSize = grpCells.size();
            int grpArr[] = new int[grpSize];
            for(int grpCellNo = 0; grpCellNo < grpSize; grpCellNo++){
                Cell grpCell = grpCells.get(grpCellNo);
                List<Integer> rowWiseCellValues = puzzle.get(grpCell.getRowNumber());
                int cellValue = rowWiseCellValues.get(grpCell.getColumnNumber());
                if(cellValue != 0 || firstZeroFlag) {
                    grpArr[grpCellNo] = cellValue;
                } else if(cellValue == 0 && !firstZeroFlag){
                    firstZeroFlag = true;
                    grpArr[grpCellNo] = value;
                }
            }

            Arrays.sort(grpArr);
            int max = 0;

            for(int i = 0; i < grpSize-1; i++){
                if(grpArr[i] == 0){
                    zeroPresence = true;
                    break;
                }
                max = grpArr[grpSize-1] - grpArr[i];
            }

            if(max == group.getResult() || zeroPresence){
                validGrpValue = true;
                return validGrpValue;
            }

        }
        if(group.getOperator() == '*'){
            int grpResult = value;
            for(Cell grpCell : grpCells){
                List<Integer> rowWiseCellValues = puzzle.get(grpCell.getRowNumber());
                int cellValue = rowWiseCellValues.get(grpCell.getColumnNumber());
                grpResult = grpResult * cellValue;
            }
            if(grpResult <= group.getResult()){
                validGrpValue = true;
                return validGrpValue;
            }
        }
        if(group.getOperator() == '/'){
            int grpSize = grpCells.size();
            int grpArr[] = new int[grpSize];
            for(int grpCellNo = 0; grpCellNo < grpSize; grpCellNo++){
                Cell grpCell = grpCells.get(grpCellNo);
                List<Integer> rowWiseCellValues = puzzle.get(grpCell.getRowNumber());
                int cellValue = rowWiseCellValues.get(grpCell.getColumnNumber());
                /*if(cellValue == 0){
                    validGrpValue = true;
                    return validGrpValue;
                }
                grpArr[grpCellNo] = cellValue;*/
                if(cellValue != 0 || firstZeroFlag) {
                    grpArr[grpCellNo] = cellValue;
                } else if(cellValue == 0 && !firstZeroFlag){
                    firstZeroFlag = true;
                    grpArr[grpCellNo] = value;
                }
            }

            Arrays.sort(grpArr);
            //int max = Math.max(grpArr[grpSize], value);
            int max = 0;

            for(int i = 0; i < grpSize-1; i++){
                if(grpArr[i] == 0){
                    zeroPresence = true;
                    break;
                }
                max = grpArr[grpSize-1] / grpArr[i];
            }

            if(max == group.getResult() || zeroPresence){
                validGrpValue = true;
                return validGrpValue;
            }
        }
        if(group.getOperator() == '='){
            if(value == group.getResult()){
                validGrpValue = true;
                return validGrpValue;
            }
        }

        return validGrpValue;
    }

    private Cell nullCheck(){
        if(puzzle == null || puzzle.isEmpty()){
            puzzle = setZeroInAllCell();
        }
        for(int rowNo = 0; rowNo < puzzle.size() ; rowNo++){
            List<Integer> rowWise = puzzle.get(rowNo);
            for(int columnNo = 0; columnNo < rowWise.size(); columnNo++){
                int cellValue = rowWise.get(columnNo);
                if(cellValue ==0){
                    return new Cell(rowNo, columnNo);
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
