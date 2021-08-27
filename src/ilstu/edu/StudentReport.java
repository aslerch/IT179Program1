package ilstu.edu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/** A class that represents a student report */
public class StudentReport {

    private final int INVALID = -1;

    /** Name of the file that we want to read */
    private String fileName;

    /** Grades for all of the students */
    private double [][] grades;

    /** Names of all the students */
    private String [] students;

    /** Names of all the graded items in the file(fileName) */
    private String [] gradedItems;

    /**
     * Instantiates a StudentReport object and also instantiates the fields of grades, students, and gradedItems
     * @param fileName The name of the file that contains the data to be used
     */
    public StudentReport(String fileName) {
        this.fileName = fileName;
        this.grades = new double [100][8];
        this.students = new String [100];
        this.gradedItems = new String [8];
    }

    /**
     * Reads fileName and fills the grades, students, and gradedItems arrays
     */
    public void readFile() {
        File gradesFile = new File(this.fileName);
        fillGradesArray(gradesFile);
        fillStudentsArray(gradesFile);
        fillGradedItemsArray(gradesFile);
    }

    /**
     * fills the gradedItems array
     * @param gradesFile The name of the File object that references fileName
     */
    private void fillGradedItemsArray(File gradesFile) {
        try {
            Scanner fileReader = new Scanner(gradesFile);
            String currentLine = fileReader.nextLine();
            String [] gradesFileHeaders = currentLine.split(",");
            for (int i = 1; i < gradesFileHeaders.length; i++) //skips the "name" header
                this.gradedItems[i - 1] = gradesFileHeaders[i];
        } catch (FileNotFoundException e) {
            System.out.println("file cannot be found");
        }
    }

    /**
     * Fills the grades array
     * @param gradesFile The name of the File object that references fileName
     */
    private void fillGradesArray(File gradesFile) {
        try {
            Scanner fileReader = new Scanner(gradesFile);
            fileReader.nextLine(); //skips the header
            int rowIndex = 0;
            while (fileReader.hasNextLine()) {
                String currentLine = fileReader.nextLine();
                String [] grades = currentLine.split(",");
                for (int i = 1; i < grades.length; i++) {
                    this.grades[rowIndex][i - 1] = Double.parseDouble(grades[i]);
                }
                rowIndex++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("file cannot be found");
        }
    }

    /**
     * Fills the students array
     * @param gradesFile The name of the File object that references fileName
     */
    private void fillStudentsArray(File gradesFile) {
        try {
            Scanner fileReader = new Scanner(gradesFile);
            fileReader.nextLine(); //skips the header
            int studentsIndex = 0;
            while (fileReader.hasNextLine()) {
                String currentLine = fileReader.nextLine();
                String [] info = currentLine.split(",");
                students[studentsIndex] = info[0];
                studentsIndex++;
            }
        } catch (FileNotFoundException e) {
            System.out.println("file cannot be found");
        }
    }

    /**
     * Generates a report card for a specific student and saves it as "(student's name).txt"
     */
    public void writeFile(String name) {
        String reportCardInfo = createReportCardInfo(name);
        String outputFileName = name.replace(" ", "") + ".txt";
        File reportCard = new File(outputFileName);
        try {
            PrintWriter fileWriter = new PrintWriter(reportCard);
            fileWriter.write(reportCardInfo);
            fileWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("cannot write to the file");
        }
    }

    /**
     * Compiles information from various sources to format the info for a report card
     * @param name The name of the student
     * @return Formatted information for a report card
     */
    private String createReportCardInfo(String name) {
        String reportCardInfo = "";
        int indexOfStudent = findIndexOfStudent(name);
        if (indexOfStudent == INVALID)
            reportCardInfo = "No data for the student could be found";
        if (indexOfStudent != INVALID) {
            reportCardInfo = "Name: " + name + "\n" +
                    collectGradesWithAssignmentNameOfStudent(name) +
                    "Total: " + (int)calculateTotalForGradesOfStudent(name) +
                    "\nGrade: " + determineLetterGradeForStudent(name);
        }
        return reportCardInfo;
    }

    /**
     * Finds the index of a student in the students array
     * @param name The name of the student
     * @return The index of the student if the student is in the students array and -1 otherwise
     */
    private int findIndexOfStudent(String name) {
        int index = -1;
        for (int i = 0; i < students.length; i++) {
            if (students[i] != null) {
                if (students[i].equalsIgnoreCase(name))
                    index = i;
            }
        }
        return index;
    }

    /**
     * Collects the grades and assignment names for a student
     * @param name The name of the student
     * @return A String in the format "(name of assignment): (grade)" for all assignments
     */
    private String collectGradesWithAssignmentNameOfStudent(String name) {
        String gradesOfStudent = "";
        int indexOfStudent = findIndexOfStudent(name);
        for (int i = 0; i < grades[indexOfStudent].length; i++) {
            gradesOfStudent += gradedItems[i] + ": " + (int)grades[indexOfStudent][i] + "\n";
        }
        return gradesOfStudent;
    }

    /**
     * Calculates the total score for all grades of a student
     * @param name The name of the student
     * @return The total score
     */
    private double calculateTotalForGradesOfStudent(String name) {
        double total = 0;
        int indexOfStudent = findIndexOfStudent(name);
        for (int i = 0; i < grades[indexOfStudent].length; i++) {
            total += (int)grades[indexOfStudent][i];
        }
        return total;
    }

    /**
     * Determines the letter grade that a student should earn based on their total score
     * @param name The name of the student
     * @return The student's letter grade
     */
    private char determineLetterGradeForStudent(String name) {
        char grade = ' ';
        int total = (int)calculateTotalForGradesOfStudent(name);
        if (total >= 90)
            grade = 'A';
        if (total >= 80 & total <= 89)
            grade = 'B';
        if (total >= 70 & total <= 79)
            grade = 'C';
        if (total >= 60 & total <= 69)
            grade = 'D';
        if (total < 69)
            grade = 'F';
        return grade;
    }

}
