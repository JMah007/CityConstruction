package edu.curtin.app;

public class HeightLimit extends ZoningRules {
    private int heightLimit;

    public HeightLimit(GridSquare oldGridSquare, int newHeightLimit) {
        super(oldGridSquare);
        this.heightLimit = newHeightLimit;
    }


    /**
    *
    * This method extends ZoningRules for decorator pattern
    * @return a String showing description ",height-limit=<heightValue>"
    *
    */
    @Override
    public String getFormat(){
        return super.getFormat() + ",height-limit=" + heightLimit;
    }


}