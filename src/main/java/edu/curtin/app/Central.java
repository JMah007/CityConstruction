package edu.curtin.app;

public class Central implements Structure{

    private double distance = 0;

    public void setDistance(double newDistance){
        this.distance = newDistance;
    }


    /**
    *
    * This method gets the number of floors for a structure based on a distance value set. It overrides the method in Structure class
    * @return an integer representing number of floors
    *
    */
    @Override
    public int getNumFloors() {
        int numFloors;

        numFloors = (int) Math.round(1 + (20 / (this.distance + 1)));

        return numFloors;
    }

    /**
    *
    * This method gets the foundation for a structure. It overrides the method in Structure class
    * @return always returns the string "slab"
    *
    */
    @Override
    public String getFoundation() {
        return "slab";
    }


    /**
    *
    * This method gets the material for a structure based on the distance value set. It overrides the method in Structure class
    * @return returns String value "concrete" if distance is smaller than 2, "brick" if distance is larger than 2 but smaller than 4, "stone" if distance
    * is larger than 4 but smaller than 6 or "wood" if neither of these
    *
    */
    @Override
    public String getMaterial() {
        String material;
        
        if (this.distance <= 2) {
            material = "concrete";
        } else 
        if (2 < this.distance && this.distance <= 4){
            material = "brick";
        } else 
        if (4 < this.distance && this.distance <= 6){
            material = "stone";
        }
        else{
            material = "wood";
        }

        return material;
    }
}