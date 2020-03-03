//Object of this represents a cell in the puzzle

public class Cell {

    //row number of the cell
    private int rowNumber;

    //column number of the cell
    private int columnNumber;

    Cell(int rowNumber, int columnNumber){
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    /*
    getRowNumber method
    returns the rowNumber value of this object
     */
    public int getRowNumber(){
        return this.rowNumber;
    }

    /*
    getColumnNumber method
    returns the columnNumber value of this object
     */
    public int getColumnNumber(){
        return this.columnNumber;
    }


}