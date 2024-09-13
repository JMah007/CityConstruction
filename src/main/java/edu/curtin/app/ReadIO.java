package edu.curtin.app;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;


public class ReadIO{
    private static final Logger logger = Logger.getLogger(ReadIO.class.getName());


    /**
    *
    * This method reads the dimensions of a file where the dimensions should be specified on the first like as "<height,width>". The method then reads the rest of the 
    * file to see if there is sufficient lines that matches dimnsions of the grid. Exception FileNotFoundException and IOException is thrown if errors with I/O processes. 
    * NumberFormatException is thrown if the number of lines dont match the dimensions and IllegalArgumentException is thrown if the first line doesnt contain valid dimensions
    * @parem "fileName" a string representing file to open and read
    * @return a integer array where [0] has height of grid and [1] has width
    *
    */
    public static int[] readFileGridDimension(String fileName) throws FileNotFoundException, IOException, NumberFormatException, IllegalArgumentException{
        
        String line;
        int[] dimensions = new int[2];  
        int lineCount = 0;

        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
  
        line = br.readLine();

        String[] parts = line.strip().split(",");
        dimensions[0] = Integer.parseInt(parts[0]); // height
        dimensions[1] = Integer.parseInt(parts[1]); // width

        // check number of lines match dimensions given
        while((line = br.readLine()) != null){ // NOPMD
            lineCount++;
        }
        if(lineCount != (dimensions[0] * dimensions[1])){
            logger.severe("File format is incorrect");
            throw new IllegalArgumentException(" number of lines dont match dimensions");
            
        }

        }
        catch(NumberFormatException e){
            logger.severe("File format is incorrect");
            throw new NumberFormatException(" first line doesnt contain valid dimensions"); // NOPMD
        }

        return dimensions;
    } 

    
    /**
    *
    * This method reads the lines containing square data of the grid. Exception FileNotFoundException and IOException is thrown if errors with I/O processes. IllegalArgumentException 
    * and NumberFormatException is thrown if a line has incorrect data format. 
    * @parem "fileName" a string representing file to open and read
    * @parem "grid" an empty List 
    * @return List now containing grid data for each square
    *
    */
    public static List<Object> readFileSquares(String fileName, List<Object> grid) throws FileNotFoundException, IOException, IllegalArgumentException, NumberFormatException{
        String line;
        int lineNum = 1;

        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
        
        line = br.readLine(); // Ignore first line // NOPMD
        line = br.readLine(); // Start reading from second line 

        while(line != null){
            line = line.strip();
            grid.add(extractLineData(line));
            line = br.readLine();
            lineNum++;
        }
              
        }
        catch(NumberFormatException e){
            logger.severe("File format is incorrect");
            throw new NumberFormatException("square on line " + lineNum + e.getMessage()); // NOPMD
        }
        catch(IllegalArgumentException e){
            logger.severe("File format is incorrect");
            throw new IllegalArgumentException("square on line " + lineNum + e.getMessage()); // NOPMD
        }
        
        return grid;
    }



    /**
    *
    * This method processes a line of data to ensure format and values are valid. If line is valid then a GridSquare Object is created and wrapped in decorator ZoningRule Objects
    * IllegalArgumentException and NumberFormatException is thrown if a line has incorrect data format. 
    * @parem "line" a String with description of square read from file
    * @return a GridSquare object either wrapped or not wrapped in ZoningRule objects. This represents line of data with square description
    *
    */
    public static GridSquare extractLineData(String line) throws IllegalArgumentException, NumberFormatException{
        int i;
        String[] fields = null; // NOPMD
        GridSquare square = null;
        String[] parts = null; // NOPMD

        parts = line.strip().split(",");
        
        // This first part of the line specifies the type of terrain
        switch(parts[0]){
            case "flat":
            square = new Flat();
            break;

            case "swampy":
            square = new Swampy();
            break;

            case "rocky":
            square = new Rocky();
            break;

            default:
            if(parts[0].equals("")){
                logger.severe("File format is incorrect");
                throw new IllegalArgumentException(" is missing data");
            }
            else{
                logger.severe("File format is incorrect");
                throw new IllegalArgumentException(" has invalid terrain");
            }
        }

        
        //Reads the rest of the line stating the zoning rules
        for(i=1; i<parts.length; i++){
            fields = parts[i].strip().split("=");
   
            switch (fields[0]) {
                case "heritage":
                    if(fields.length != 2){ // NOPMD
                        logger.severe("File format is incorrect");
                        throw new IllegalArgumentException(" heritage field has incorrect format");
                    }
                    else{ 
                        if((!fields[1].equals("wood")) && (!fields[1].equals("stone")) && (!fields[1].equals("brick"))){ // if heritage is neither wood or brick or stone
                            logger.severe("File format is incorrect");
                            throw new IllegalArgumentException(" heritage value is not valid");
                        }
                        square = new Heritage(square, fields[1]);
                    }
                    break;
            
                case "flood-risk":
                    if(fields.length != 2){ // NOPMD
                        logger.severe("File format is incorrect");
                        throw new IllegalArgumentException(" flood-risk field has incorrect format");
                    }
                    else{
                        try {
                            Double.parseDouble(fields[1]);  // Attempt to parse the value as a double
                        } catch (NumberFormatException e) {
                            logger.severe("File format is incorrect");
                            throw new NumberFormatException(" flood-risk value is not valid"); // NOPMD
                        }
                        if(Double.parseDouble(fields[1]) > 100){
                            logger.severe("File format is incorrect");
                            throw new NumberFormatException(" flood-risk value is not valid"); // NOPMD
                        }
                        
                        square = new FloodRisk(square, Math.round(Double.parseDouble(fields[1]) * 10) / 10.0);
                    }
                    break;

                case "height-limit":
                    if(fields.length != 2){ // NOPMD
                        logger.severe("File format is incorrect");
                        throw new IllegalArgumentException(" height-limit field has incorrect format");
                    }
                    else{
                        if(!fields[1].matches("\\d+") || Integer.parseInt(fields[1]) == 0 ){
                            logger.severe("File format is incorrect");
                            throw new NumberFormatException(" height-limit value is not correct");
                        }
                        square = new HeightLimit(square, Integer.parseInt(fields[1]));
                    }
                    break;

                case "contamination":
                    if(fields.length != 1){ // NOPMD
                        logger.severe("File format is incorrect");
                        throw new IllegalArgumentException(" contamination field has incorrect format");
                    }
                    else{
                        square = new Contamination(square);
                    }
                    break;

                default: 
                    if(!fields[0].equals("")){ // if a zoning rule not recognised is read
                        logger.severe("File format is incorrect");
                        throw new IllegalArgumentException(" invalid zoning rule given"); 
                    }
            }
    }
        return square;
    }
}