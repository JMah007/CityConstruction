package edu.curtin.app;
import java.util.*;
import main.java.edu.curtin.app.CustomScanner;

public class Uniform implements Structure{


    /**
    *
    * This method implements Structure class where it gets a number of floors from the user
    * @return an integer randomly generated
    *
    */
    @Override
    public int getNumFloors() {
        int numFloors = 0;
        boolean valid = false;

        Scanner kb = CustomScanner.getScanner(); // NOPMD

        System.out.println("\nEnter number of floors");
        while(!valid){
            try{
            numFloors = kb.nextInt();
            if (numFloors < 1) { 
                    System.out.println("\nError, please enter a number greater than or equal to 1");
            } else {
                valid = true;
            }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number greater or equal to 0");
                kb.next(); // Discard the invalid input
                }
            }
        return numFloors;
    }
            

    /**
    *
    * This method implements Structure class where it gets a foundation from the user 
    * @return a String randomly generated either "slabs" or "stilts"
    *
    */
    @Override
    public String getFoundation() throws InputMismatchException{
        int option = 0;
        boolean valid = false;
        String foundation = null;

        Scanner kb = CustomScanner.getScanner(); // NOPMD

        System.out.println("\nChoose a foundation either 1 or 2");
        System.out.println(" 1. Slab\n2. Stilts");
        while(!valid){
            try{
                option = kb.nextInt();
                if (option != 1 && option != 2) { // NOPMD
                    System.out.println("\nError, please enter either option 1 or 2");
                } else{
                    valid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 2 inclusive.");
                kb.next(); // Discard the invalid input
                }
        }

        switch (option) {
            case 1:
                foundation = "slab";
                break;
            case 2: 
                foundation = "stilts";
                break;
            default: // no need implementation
        }
                
        return foundation;
    }
    

    /**
    *
    * This method implements Structure class where it gets material from user 
    * @return a String randomly generated either "wood", "stone", "bricks" or "concrete"
    *
    */
    @Override
    public String getMaterial() throws InputMismatchException{
        int option = 0;
        boolean valid = false;
        String material = null;

        Scanner kb = CustomScanner.getScanner(); // NOPMD

        System.out.println("\nChoose a option from 1 to 4");
        System.out.println("1. Wood\n2. Stone\n3. Brick\n4. Concrete");

        while(!valid){    
            try{              
                option = kb.nextInt();
                if (option < 1 || option > 4) { 
                System.out.println("\nError, please enter an option from 1 to 4");
                } else{
                    valid = true;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 4 inclusive.");
                kb.next(); // Discard the invalid input
            }
        }
    
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
            default: // no need implementation
        }

        return material;
    }
}