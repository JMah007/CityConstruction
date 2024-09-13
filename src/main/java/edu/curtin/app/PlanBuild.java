package edu.curtin.app;
import java.util.*;
import main.java.edu.curtin.app.CustomScanner;
import java.util.logging.Logger;

public class PlanBuild{
    private static final Logger logger = Logger.getLogger(PlanBuild.class.getName());


    /**
    *
    * This method prompts user for the location of a specific grid square, in terms of the row and column indexes (non-negative integers),
    * a number of floors (for the structure), a positive integer, foundation type: either "slab" or "stilts" or construction 
    * material: "wood", "stone", "brick" or "concrete". Then it reports back to user if structure can be built and if not then why and if so then how much will it cost.
    * @parem "grid" a List of Objects containing data about a square for a structure to be built on
    * @parem "gridDimensions" a integer array where [0] should contain height of grid ad [1] contain width 
    *
    */
    public static void buildStructure(List<Object> grid, int[] gridDimensions){
        float cost;
        int gridSquare, numFloors;
        String foundation = null, material = null; // NOPMD
        String gridDescription = null;
        String structureDescription = null; // NOPMD
        GridSquare square;


        //Get structure information from user by calling methods from Uniform class
        Structure approach = new Uniform(); 
        gridSquare = getSquare(gridDimensions);
        System.out.println("\nCoordinates selected correspond to square " + gridSquare); 
        numFloors = approach.getNumFloors();
        foundation = approach.getFoundation();
        material = approach.getMaterial();

        structureDescription = (numFloors + "," + foundation + "," + material); // Structure description always has 3 parts to it
        logger.info("Successfully got structure info");

        //Search for square specified by user in the grid and save the description of that grid
        square = (GridSquare) grid.get(gridSquare);
        if(square instanceof GridSquare){ 
            gridDescription = square.getFormat();
            logger.info("Successfully found grid square specified");
        }

        if(checkCanBuild(gridDescription, structureDescription)){
            System.out.println("\n\n\nStructure can be built on square " + gridSquare);
            cost = calcCost(gridDescription, structureDescription);
            System.out.println("Cost of structure: $" + cost);
        }  
        else{
            System.out.println("Structure cannot be built on square " + gridSquare);
        }
        logger.info("Checked if structure can be built");
    }


    /**
    *
    * This method attempts to build structures on all grid squares according to 1 of the 3 approach styles (1 Uniform, 2 Random or 3 Central).
    * For Uniform, a single structure is tested to see if it can be built on all squares of the grid. For Random a randomly generated structure is made for each square to be tested on. 
    * For Central a new structure based on the squares distance from the centre of the grid is made for each square.
    * @parem "grid" a List of Objects containing data about a square for a structure to be built on
    * @parem "chosen approach" an integer representing type of apporach (1 Uniform, 2 Random or 3 Central).
    * @param "gridDimensions" a integer array where [0] should contain height of grid ad [1] contain width 
    *
    */
    public static void buildCity(List<Object> grid, int chosenApproach, int[] gridDimensions){
        int numFloors;
        String foundation = null, material = null; // NOPMD
        float totalCost = 0;
        String gridDescription = null;
        String structureDescription = null; // NOPMD
        Structure approach;
        int idx = 0; // For case 3 representing index of square in grid for distance calc
        double distance; // For case 3 
        String[][] builtCity = new String[gridDimensions[0]][gridDimensions[1]];
        int xCounter = 0, yCounter = 0; // Used for initialising "builtCity"
        int numBuilt = 0;

        switch (chosenApproach) {
            case 1:
                approach = new Uniform(); // uses Uniform class methods
                
                numFloors = approach.getNumFloors();
                foundation = approach.getFoundation();
                material = approach.getMaterial();

                structureDescription = (numFloors + "," + foundation + "," + material); // Structure description always has 3 parts to it

                for (Object square: grid) { // Check if structure can be built on every square where the same structure is used for every square
                    if(square instanceof GridSquare){ 
                        gridDescription = ((GridSquare) square).getFormat(); // Uses decorator strategy 
                    }

                    if(xCounter == gridDimensions[1]){ // if row is filled
                        xCounter = 0; // Move to first column
                        yCounter++; // Move to next row
                    }

                    if(checkCanBuild(gridDescription, structureDescription)){
                        totalCost = totalCost + calcCost(gridDescription, structureDescription);
                        
                        builtCity[yCounter][xCounter] = "X"; // Mark coordinate where a structure can be built
                        xCounter++;
                        numBuilt++;
                        System.out.println("Can build structure\n");
                    }  
                    else{
                        builtCity[yCounter][xCounter] = " "; // If structure cant be built
                        xCounter++;
                        System.out.println("Cannot build structure\n");
                    }
                }
                logger.info("Checked if structure can be built using Uniform approach");
                break;

            case 2:
                approach = new Random();

                for (Object square: grid) { // Check if structure can be built on every square and reset structure for every square
                    numFloors = approach.getNumFloors();
                    foundation = approach.getFoundation();
                    material = approach.getMaterial();

                    structureDescription = (numFloors + "," + foundation + "," + material); // Structure description always has 3 parts to it

                    gridDescription = ((GridSquare) square).getFormat(); // Uses decorator strategy 

                    if(xCounter == gridDimensions[1]){
                        xCounter = 0; // Move to first column
                        yCounter++; // Move to next row
                    }
                
                    if(checkCanBuild(gridDescription, structureDescription)){
                        totalCost = totalCost + calcCost(gridDescription, structureDescription);
                        
                        builtCity[yCounter][xCounter] = "X"; // If structure can be built
                        xCounter++;
                        numBuilt++;
                        System.out.println("Can build structure\n");
                    }  
                    else{
                        builtCity[yCounter][xCounter] = " "; // if structure cant be built
                        xCounter++;
                        System.out.println("Cannot build structure\n");
                    }
                }
                logger.info("Checked if structure can be built using Random approach");
                break;

            default:
                approach = new Central();
                for (Object square: grid) {

                    // Initialise distance field in Central class
                    distance = calcDistance(idx, gridDimensions);
                    ((Central) approach).setDistance(distance);
                    
                    numFloors = approach.getNumFloors();
                    foundation = approach.getFoundation();
                    material = approach.getMaterial();

                    structureDescription = (numFloors + "," + foundation + "," + material); // Structure description always has 3 parts to it

                    gridDescription = ((GridSquare) square).getFormat(); // Uses decorator strategy 
                    
                    if(xCounter == gridDimensions[1]){
                        xCounter = 0; // Move to first column
                        yCounter++; // Move to next row
                    }

                    if(checkCanBuild(gridDescription, structureDescription)){
                        totalCost = totalCost + calcCost(gridDescription, structureDescription);
                        
                        builtCity[yCounter][xCounter] = "X"; // if structure can be built
                        xCounter++;
                        numBuilt++;
                        System.out.println("Can build structure\n");
                    }  
                    else{
                        builtCity[yCounter][xCounter] = " "; // if structure cant be built
                        xCounter++;
                        System.out.println("Cannot build structure\n");
                    }
                    idx++; // update for calculation of distance for next square
                }
                logger.info("Checked if structure can be built using Central approach");
                break;     
        }
                
                // Displays all results
                System.out.println("\n\nNumber of structures built: " + numBuilt);
                System.out.println("Total cost of building the city: $" + totalCost);    

                // Displays the grid of city built
                System.out.println("\nThe layout of the " + gridDimensions[1] + " by " + gridDimensions[0] + " city");
                for(int i=0; i<gridDimensions[0]; i++){ // Print each row
                    for(int j=0; j<gridDimensions[1]; j++){ // print each column of the row 
                        System.out.print(builtCity[i][j]);
                    }
                    System.out.println();
                }
    }
         

    /**
    *
    * This method compares the description of a structure to the description of a square in a grid to see if any square zoning rules are violated by the structure.
    * @parem "gridDescription" a String with description of structure. Must be in the form "<numberOfFloors>, <foundation>, <material>" 
    * @parem "structureDescription" a String with description of structure. 
    * @return a boolean value where true means the structure can be built and false otherwise
    */
    public static boolean checkCanBuild(String gridDescription, String structureDescription){
        boolean canBuild = true;
        String[] structureDescriptionParts = null; // NOPMD
        String[] gridDescriptionParts = null; // NOPMD
        String[] gridDescriptionRuleField = null; // NOPMD
        int i;

        structureDescriptionParts = structureDescription.strip().split(","); // Split structure description into seperate structural requirements: [0] number of floors, [1] foundation, [2] material 
        gridDescriptionParts = gridDescription.strip().split(","); // Split description into seperate zoning rules

        // Check if structural requirements break any zoning rules of the specific grid square
        for(i=0; i<gridDescriptionParts.length; i++){ // Seperate values from its zoning rule
            gridDescriptionRuleField = gridDescriptionParts[i].strip().split("=");

            switch (gridDescriptionRuleField[0]){ // [0] will have the zoning rule type and [1] will have the value if relevant
                case "swampy":
                    if (structureDescriptionParts[1].equals("slab")) {
                        canBuild = false;
                        System.out.println("A slab foundation cannot be built in a swamp!");
                    }
                    if (structureDescriptionParts[1].equals("wood")) {
                        canBuild = false;
                        System.out.println("A wooden structure cannot be built in a swamp!");
                    }
                    break;
                case "heritage":
                    if (!(structureDescriptionParts[2].equals(gridDescriptionRuleField[1]))) {
                        canBuild = false;
                        System.out.println("Construction material is different from the heritage rule");
                    }
                    break;
                case "height-limit":
                    if (Integer.parseInt(structureDescriptionParts[0]) > Integer.parseInt(gridDescriptionRuleField[1])) {
                        canBuild = false;
                        System.out.println("Structure has more floors than specified limit");
                    }
                    break;
                case "flood-risk":
                    if (Integer.parseInt(structureDescriptionParts[0]) < 2) {
                        canBuild = false;
                        System.out.println("Structure must have at least 2 floors");
                    }
                    break;
                default: // no implementation needed
                
            }
        }
        return canBuild;
    }


    /**
    *
    * This method calculates the cost of building a structure on a square 
    * @parem "gridDescription" a String with description of structure. Must be in the form "<numberOfFloors>, <foundation>, <material>" 
    * @parem "structureDescription" a String with description of structure. 
    * @return a float value representing cost 
    *
    */
    public static float calcCost(String gridDescription, String structureDescription){
        float cost = 0;
        String[] structureDescriptionParts = null; // NOPMD
        String[] gridDescriptionParts = null; // NOPMD
        String[] gridDescriptionRuleField = null; // NOPMD
        int i;

        structureDescriptionParts = structureDescription.strip().split(","); // Split structure description into seperate structural requirements: [0] number of floors, [1] foundation, [2] material 
        gridDescriptionParts = gridDescription.strip().split(","); // Split description into seperate zoning rules

        // Calculate standard costs of building structure according to levels and material 
        switch (structureDescriptionParts[2]) {
            case "wood":
                cost = cost + (10000 * Integer.parseInt(structureDescriptionParts[0]));
                break;
            case "stone":
                cost = cost + (50000 * Integer.parseInt(structureDescriptionParts[0]));
                break;
            case "brick":
                cost = cost + (30000 * Integer.parseInt(structureDescriptionParts[0]));
                break;
            case "concrete":
                cost = cost + (20000 * Integer.parseInt(structureDescriptionParts[0]));
                break;
            default: // no implemntation needed
        }
        
        // Calculate extra costs for building on swampy or rocky ground 
        switch (gridDescriptionParts[0]) {
            case "swamp":
                cost = cost + (20000 * Integer.parseInt(structureDescriptionParts[0]));
                break;
            case "rocky": 
                 cost = cost + 50000;
                break;
            default: // no implementation needed
        }

        // Calculate extra costs for building on land with contamination or flood risk 
        for(i=0; i<gridDescriptionParts.length; i++){ // Seperate values from its zoning rule
            gridDescriptionRuleField = gridDescriptionParts[i].strip().split("=");

            switch (gridDescriptionRuleField[0]) {
                case "contamination":
                    cost =  cost * (float)1.5;
                    break;
                case "flood-risk":
                    cost = cost * (1 + (Float.parseFloat(gridDescriptionRuleField[1]) / 50));
                    break;
                default: // no implementation needed
            }
        }
        return cost;
    }


    /**
    *
    * This method calculates a position of a square in a grid represented as a List according to x and y coordinates given
    * @param "gridDimensions" a integer array where [0] should contain height of grid ad [1] contain width 
    * @return an integer representing position of square in List
    */
    public static int getSquare(int[] gridDimensions) {
        int x = 0, y = 0, squareIdx; // NOPMD

        System.out.println("\nChoose a location to to build on by giving x and y coordinates where x is less than " + gridDimensions[1] + 
        " and y is less than " + gridDimensions[0]);

            x = getXValue(gridDimensions[1]);
            y = getYValue(gridDimensions[0]);
        
        squareIdx = ((y-1) * gridDimensions[1]) + (x-1); // x and y represent the actual number of columns and rows so -1 gives logical array position
        return squareIdx;
    }


    /**
    *
    * This method prompts user for a integer representing x coordinate of a position in the grid
    * @parem "xConstraint" is the x max dimension of the grid
    * @return returns an integer x value
    */
    public static int getXValue(int xConstraint){
        int x = 0;
        boolean valid = false;

        Scanner kb = CustomScanner.getScanner(); // NOPMD

        while(!valid){
            try{
                System.out.println("Enter x value: ");
                x = kb.nextInt();
                if(x < 1 || x > xConstraint){
                    System.out.println("Error, please choose a number between 0 and " + xConstraint);
                }
                else{
                    valid = true;
                }
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter a number between 1 and " + xConstraint + " inclusive.");
                kb.next(); // Discard the invalid input
                }
        }
        return x;
    }


    /**
    *
    * This method prompts user for a integer representing y coordinate of a position in the grid
    * @parem "yConstraint" is the y max dimension of the grid
    * @return returns an integer y value
    */
    public static int getYValue(int yConstraint){
        int y = 0;
        boolean valid = false;

        Scanner kb = CustomScanner.getScanner(); // NOPMD
        
        while(!valid){
            try{
                System.out.println("Enter y value: ");
                y = kb.nextInt();
                if(y < 1 || y > yConstraint){
                    System.out.println("Error, please choose a number between 0 and " + yConstraint);
                }
                else{
                    valid = true;
                }
            }
            catch(InputMismatchException e){
                System.out.println("Invalid input. Please enter a number between 1 and " + yConstraint + " inclusive.");
                kb.next(); // Discard the invalid input
            }
        }
        return y;
    }


    /**
    *
    * This method calculates the distance (in squares) a square in the grid is from the grids centre
    * @parem "idx" representing the square in the grid the calculation is being done for
    * @param "gridDimensions" a integer array where [0] should contain height of grid ad [1] contain width 
    * @return a double representing the distance
    *
    */
    public static double calcDistance(int idx, int[] gridDimensions){
        double distance = 0; // NOPMD
        int x, y;

        x =  idx % gridDimensions[1];
        y =  idx / gridDimensions[1];
        distance = Math.sqrt(Math.pow((y - ((gridDimensions[0] - 1) / 2.0)), 2) + Math.pow((x - ((gridDimensions[1] - 1) / 2.0)), 2));
        System.out.println("Distance square is from centre of grid is " + distance);
        return distance;
    }
}