package edu.curtin.app;

public class Random implements Structure{

    /**
    *
    * This method implements Structure class where it gets a random number of floors
    * @return an integer randomly generated
    *
    */
    @Override
    public int getNumFloors() {
        return (int)(Math.random() * 300 - 1) + 1;
    }


    /**
    *
    * This method implements Structure class where it gets a random foundation 
    * @return a String randomly generated either "slabs" or "stilts"
    *
    */
    @Override
    public String getFoundation() {
        int option;
        String foundation = null;

        option = (int)(Math.random() * 2-1) + 1;

        switch (option) {
            case 1:
                foundation = "slab";
                break;
            case 2: 
                foundation = "stilts";
                break;
            default: // no implementation needed
        }
        return foundation;
    }


    /**
    *
    * This method implements Structure class where it gets a random material 
    * @return a String randomly generated either "wood", "stone", "bricks" or "concrete"
    *
    */
    @Override
    public String getMaterial() {
        int option;
        String material = null;

        option = (int)(Math.random() * 4-1) + 1;

        switch (option) {
            case 1:
                material = "wood";
                break;
            case 2: 
                material = "stone";
                break;
            case 3:
                material = "brick";
                break;
            case 4:
                material = "concrete";
                break;
            default: // no implementation needed
        }
        return material;
    }
}