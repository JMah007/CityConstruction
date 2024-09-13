package edu.curtin.app;

public class Flat implements GridSquare{

    /**
    *
    * This method implements GridSquare as a concrete decorator 
    * @return a String showing description "flat"
    *
    */
    @Override
    public String getFormat(){
        return "flat"; 
    }
}