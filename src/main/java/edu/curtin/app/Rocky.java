package edu.curtin.app;

public class Rocky implements GridSquare{

    /**
    *
    * This method implements GridSquare as a concrete decorator 
    * @return a String showing description "rocky"
    *
    */
    @Override
    public String getFormat(){
        return "rocky";
    }
    
}