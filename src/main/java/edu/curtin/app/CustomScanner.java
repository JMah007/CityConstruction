package main.java.edu.curtin.app;
import java.util.Scanner;

public class CustomScanner{

    private static final Scanner SCANNER = new Scanner(System.in);

    private CustomScanner() { // private constructor to prevent instantiation of this class
        
    }


    /**
    *
    * This method returns a Scanner object to be used
    *
    */
    public static Scanner getScanner() {
        return SCANNER;
    }


    /**
    *
    * This method closes a Scanner object
    *
    */
    public static void closeScanner() {
        if (SCANNER != null) {
            SCANNER.close();
        }
    }
}
