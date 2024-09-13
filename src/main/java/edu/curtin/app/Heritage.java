package edu.curtin.app;

public class Heritage extends ZoningRules {
    private String heritage = null; // NOPMD

    public Heritage(GridSquare oldGridSquare, String newHeritage) {
        super(oldGridSquare);
        this.heritage = newHeritage;
    }

    /**
    *
    * This method extends ZoningRules for decorator pattern
    * @return a String showing description ",heritage=<heritageName>"
    *
    */
    @Override
    public String getFormat(){
        return super.getFormat() + ",heritage=" + heritage;
    }
}