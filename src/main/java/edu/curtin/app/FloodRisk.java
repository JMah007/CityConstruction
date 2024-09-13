package edu.curtin.app;

public class FloodRisk extends ZoningRules {
    private double riskPercentage = 0; // NOPMD

    public FloodRisk(GridSquare oldGridSquare, double newRiskPercentage) {
        super(oldGridSquare);
        riskPercentage = newRiskPercentage;
    }


    /**
    *
    * This method extends ZoningRules for decorator pattern
    * @return a String showing description ",flood-risk=<riskValue>"
    *
    */
    @Override
    public String getFormat(){
        return super.getFormat() + ",flood-risk=" + riskPercentage;
    }

}
