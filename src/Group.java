//Stores the information of the grouped cells

import java.util.ArrayList;
import java.util.List;

public class Group{

    //name of the group
    private Character name;

    //result of the group
    private int result;

    //operator of the group
    private Character operator;

    /*
    setResult method
    gets the result of the group as input
    sets the input in the result of this object
     */
    public void setResult(int result){
        this.result = result;
    }

    /*
    getResult method
    returns the result of the group
     */
    public int getResult(){
        return this.result;
    }

    /*
    setOperator method
    gets the operator of the group as input
    sets the input in the operator of this object
    */
    public void setOperator(Character operator){
        this.operator = operator;
    }

    /*
    getOperator method
    returns the operator of the group
    */
    public Character getOperator(){
        return this.operator;
    }

    /*
    setName method
    gets the name of the group as input
    sets the input in the name of this object
    */
    public void setName(Character name){
        this.name = name;
    }

    /*
    getName method
    returns the name of the group
    */
    public Character getName(){
        return this.name;
    }

}