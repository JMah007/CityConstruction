package edu.curtin.app;

public class Contamination extends ZoningRules {

    public Contamination(GridSquare oldGridSquare) {
        super(oldGridSquare);
    }

    
    /**
    *
    * This method extends ZoningRules for decorator pattern
    * @return a String showing description ",contamination"
    *
    */
    @Override
    public String getFormat(){
        return super.getFormat() + ",contamination";
    }

}
