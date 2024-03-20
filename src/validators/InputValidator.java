package src.validators;

import java.util.Locale;
import java.util.Scanner;

public class InputValidator {
    protected static final String INVALID_VALUE_STRING = "Invalid value! Please type max %s characters (number not allowed): ";

    public int checkInputUserValueByQuantityDisired(int qty, Scanner sc) {

        int userInput;
        if (sc.hasNextInt()) {
            userInput = sc.nextInt();
            if(String.valueOf(userInput).length() != qty) {
                userInput = 0;
            }
        } else {
            sc.next();
            userInput = 0;
        }
        while (userInput==0) {
            System.out.printf("Invalid value! Please type %s digits: ", qty);
            if (sc.hasNextInt()) {
                userInput = sc.nextInt();if(String.valueOf(userInput).length() != qty) {
                    userInput = 0;
                }
            } else {
                sc.next();
                userInput = 0;
            }
        }
        return userInput;
    }

    public String checkStringValue(int stringQty, Scanner sc) {
        String userStringInput = sc.nextLine();
        int userInput=1;

        if (userStringInput.trim().length() > 60 || userStringInput.matches("[0-9]+")) {
            userInput=0;
        }
        while (userInput==0) {
            System.out.printf(INVALID_VALUE_STRING, stringQty);
            userStringInput = sc.nextLine();
            if (userStringInput.trim().length() <= 60 && !userStringInput.matches("[0-9]+")) {
                userInput = 1;
            }
        }
        return userStringInput;
    }

    public double checkDoubleUserValue(Scanner sc) {
        int i = 1;
        double userInput = 0.00;

        if (sc.hasNextDouble()) {
            userInput = sc.nextDouble();
        } else {
            sc.next();
            i = 0;
        }
        while (i==0) {
            System.out.println("Invalid value! Please type double value as decimal: ");
            if (sc.hasNextDouble()) {
                userInput = sc.nextDouble();
                i=1;
            } else {
                sc.next();
                i = 0;
            }
        }
        return userInput;
    }
}
