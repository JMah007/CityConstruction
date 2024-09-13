package edu.curtin.app;

abstract class ZoningRules implements GridSquare{
    private GridSquare gridSquare;

    public ZoningRules(GridSquare oldGridSquare){
        this.gridSquare = oldGridSquare;

    }

    /**
    *
    * This method implements GridSquare as a component decorator
    * @return a String showing description of a square
    *
    */
    @Override
    public String getFormat(){
        return gridSquare.getFormat();
    }
}