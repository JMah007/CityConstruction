package edu.curtin.app;

public class Swampy implements GridSquare{

  /**
    *
    * This method implements GridSquare as a concrete decorator
    * @return a String showing description "swampy"
    *
    */
    @Override
    public String getFormat(){
      return "swampy";
    }
}