package ilstu.edu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MainClass {

    private static Scanner keyboard = new Scanner(System.in);
    private static final String WELCOME_BANNER = """
            *****************************************
            Welcome to the School Statistics Database
            *****************************************""";
    private static final int PROCESS_FILE = 1, PRINT_STUDENTS = 2, GENERATE_REPORT_CARD = 3, DISPLAY_STATISTICS = 4, EXIT = 5;
    private static StudentReport studentReport = new StudentReport();

    public static void main(String [] args) {

        System.out.println(WELCOME_BANNER);
        int menuSelection = -1; //arbitrary initial value for menuSelection
        while ( menuSelection != EXIT) {
            displayMenu();
            try {
                menuSelection = askForIntBetween(1, 5); //menu selections have the bounds of 1 and 5
                if (menuSelection == PROCESS_FILE) {
                    studentReport = new StudentReport(askForStringWithinLength("Please enter the file name: ", 100));
                    studentReport.readFile();
                    System.out.println("*****************************************");
                }
                if (menuSelection == PRINT_STUDENTS) {
                    if (studentReport.isProcessed) {
                        studentReport.printStudents();
                    }
                    if (studentReport.isProcessed == false)
                        System.out.println("ERROR: file has not yet been processed");
                    System.out.println("*****************************************");
                }
                if (menuSelection == GENERATE_REPORT_CARD) {
                    if (studentReport.isProcessed) {
                        String studentName = askForStringWithinLength("Please enter the name of a student: ", 100);
                        studentReport.writeFile(studentName);
                    }
                    if (studentReport.isProcessed == false)
                        System.out.println("ERROR: file has not yet been processed");
                    System.out.println("*****************************************");

                }
                if (menuSelection == DISPLAY_STATISTICS) {
                    if (studentReport.isProcessed)
                        studentReport.displayStatistics();
                    if (studentReport.isProcessed == false)
                        System.out.println("ERROR: file has not yet been processed");
                    System.out.println("*****************************************");
                }
                if (menuSelection == EXIT)
                    System.out.println("Thank you for using the school statistics database");
            } catch (InputMismatchException e) {
                keyboard.nextLine();
                System.out.println("ERROR: Input not an integer");
            }
        }
    }

    /**
     * Displays the menu
     */
    private static void displayMenu() {
        System.out.println("""
                1. Process a file
                2. Print a list of all students
                3. Generate a report card
                4. Display statistics
                5. Exit""");
    }

    /**
     * Asks for an int between or equal to lower and upper bound values
     * @param num1 The lower bound
     * @param num2 The upper bound
     * @return A number between or equal to the upper bound and lower bound
     */
    private static int askForIntBetween(int num1, int num2) {
        int selection = -9999;
        boolean isInt = false;
        boolean isBetweenNum1AndNum2 = false;
        while (isInt == false || isBetweenNum1AndNum2 == false) {
            isInt = false;
            isBetweenNum1AndNum2 = false;
            try {
                System.out.print("Your selection (" + num1 + " - " + num2 + "): ");
                selection = keyboard.nextInt();
                if (selection >= num1 & selection <= num2)
                    isBetweenNum1AndNum2 = true;
                if (selection < num1 || selection > num2)
                    System.out.println("selection outside of acceptable range");
                isInt = true;
            } catch (InputMismatchException e) {
                keyboard.nextLine();
                System.out.println("Incorrect input: not an integer");
            }
        }
        return selection;
    }

    /**
     * Asks for a String that is within a certain length
     * @param informationRequestMessage The request message
     * @param lengthUpperBound The upper bound for length
     * @return A String within a length determined by the upper bound
     */
    private static String askForStringWithinLength(String informationRequestMessage, int lengthUpperBound) {
        keyboard.nextLine();
        boolean isWithinCorrectLength = false;
        String output = "";
        while (isWithinCorrectLength == false) {
            System.out.print(informationRequestMessage);
            output = keyboard.nextLine();
            if (output.length() <= lengthUpperBound)
                isWithinCorrectLength = true;
            if (output.length() > lengthUpperBound)
                System.out.println("input not of valid length " + "(" + lengthUpperBound + ")");
        }
        return output;
    }

}
