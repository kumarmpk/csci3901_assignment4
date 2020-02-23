//class will hold the row number and column number of the values

public class Cell {

    //row numbers of the cells that belongs to the group
    private int rowNumber = 0;

    //column numbers of the cells that belongs to the group
    private int columnNumber = 0;

    Cell(int rowNumber, int columnNumber){
        this.rowNumber = rowNumber;
        this.columnNumber = columnNumber;
    }

    /*
    setRowNumber method
    gets rowNumber as input
    sets the input into this object
     */
    public void setRowNumber(int rowNumber){
        this.rowNumber = rowNumber;
    }

    /*
    getRowNumber method
    returns the rowNumber value of this object
     */
    public int getRowNumber(){
        return this.rowNumber;
    }

    /*
    setColumnNumber method
    gets columnNumber as input
    sets the input into this object
    */
    public void setColumnNumber(int columnNumber){
        this.columnNumber = columnNumber;
    }

    /*
    getColumnNumber method
    returns the columnNumber value of this object
     */
    public int getColumnNumber(){
        return this.columnNumber;
    }


}