package edu.curtin.app;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import main.java.edu.curtin.app.CustomScanner;


public class CityPlanningApp{
    public static void main(String[] args) {

        Map<Integer, Object> approaches = new HashMap<>(); // Contains the classes for the type of approaches for menu option 2
        List<Object> grid = new ArrayList<>();
        int[] gridDimensions = null; // NOPMD
        String fileName = null; // NOPMD
        int menuOption;
        int approach  = 1; // Set default approach to Uniform
        boolean quitProgram = false;
        
        

        if(args.length != 1) { // NOPMD
            System.out.println("Error, no file name given.");
        }
        else
        { // If file name is provided then execute rest of the program
            
            try {
                // Setup Phase
                fileName = args[0];
                gridDimensions= ReadIO.readFileGridDimension(fileName);
                grid = ReadIO.readFileSquares(fileName, grid);   
                initApproaches(approaches);

                // Post setup phase. Loop until user quits program
                System.out.println("\n\nWelcome to City Planning App!");
                while(!quitProgram){
                    dispMenu();
                    menuOption = getMenuOption();
                    switch(menuOption){
                        case 1:
                            PlanBuild.buildStructure(grid, gridDimensions);
                            break;
                        case 2:
                            PlanBuild.buildCity(grid, approach, gridDimensions);
                            break;
                        case 3: 
                            System.out.println("Select an approach below between 1-3");
                            System.out.println("1. Uniform\n" + "2. Random\n" + "3. Central");
                            approach = getApproachOption();
                            break;
                        default:
                            quitProgram = true;
                            System.out.println("\n\nYou quite the program. Bye!!!!\n\n");
                            break;
                    }
                }
            } 
            catch (FileNotFoundException e){
                System.out.println("Error, could not find file called " + fileName);
            }
            catch(IOException e){
                System.out.println("Error, couldnt read file");
            }
            catch(IllegalArgumentException e){
                System.out.println("Error, " + e.getMessage());
            }
            
        }
        CustomScanner.closeScanner(); // Close input stream
    }


    /**
    *
    * This method displays the main menu options of the program to the user
    *
    */
    public static void dispMenu(){
        System.out.println("\n\nChoose an option from the menu below (1-4)\n");
        System.out.println("1. Build Structure\n" + "2. Build City\n" + "3. Configure\n" + "4. Quit");
    }


    /**
    *
    * This method makes the user choose a number between 1 and 4 inclusive. It handles exceptions by itself
    * @return an integer representing a number between 1 and 4 inclusive
    */
    public static int getMenuOption(){
        int option = 0;
        boolean valid = false;

        Scanner kb = CustomScanner.getScanner(); // NOPMD

        while(!valid){
            try {
                option = kb.nextInt();
    
                if (option < 1 || option > 4) { 
                    System.out.println("Error, please choose a number between 1 and 4 inclusive.");
                } else {
                    valid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 4 inclusive.");
                kb.next(); // Discard the invalid input
            }
        }

        return option;
    }


    /**
    *
    * This method gets the user to choose a number between 1 and 3 inclusive. It handles all exceptions itself
    * @return an integer representing a number between 1 and 3 inclusive
    *
    */
    public static int getApproachOption(){
        int approach = 0;
        boolean valid = false;

        Scanner kb = CustomScanner.getScanner(); // NOPMD

        while (!valid) {
            try {
                approach = kb.nextInt();

                if (approach < 1 || approach > 3) {
                    System.out.println("Error, please choose a number between 1 and 3 inclusive.");
                } else {
                    valid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3 inclusive.");
                kb.next(); // Discard the invalid input
                }
        }
        return approach;
    }


    /**
    *
    * This method takes in a Map data structure and initialises it with approach type classes which are subclasses on Structure class
    * @param approaches which is a Map where the key is an integer and value an Object
    *
    */
    public static void initApproaches(Map<Integer, Object> approaches){
        approaches.put(1, new Uniform());
        approaches.put(2, new Random());
        approaches.put(3, new Central());
    }
  
}