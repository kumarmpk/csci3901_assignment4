//Stores the information of the grouped cells

public class Group {

    //name of the group
    private String name;

    //result of the group
    private String result;

    //operator of the group
    private String operator;

    /*
    setResult method
    gets the result of the group as input
    sets the input in the result of this object
     */
    public void setResult(String result){
        this.result = result;
    }

    /*
    getResult method
    returns the result of the group
     */
    public String getResult(){
        return this.result;
    }

    /*
    setOperator method
    gets the operator of the group as input
    sets the input in the operator of this object
    */
    public void setOperator(String operator){
        this.operator = operator;
    }

    /*
    getOperator method
    returns the operator of the group
    */
    public String getOperator(){
        return this.operator;
    }

    /*
    setName method
    gets the name of the group as input
    sets the input in the name of this object
    */
    public void setName(String name){
        this.name = name;
    }

    /*
    getName method
    returns the name of the group
    */
    public String getName(){
        return this.name;
    }

}