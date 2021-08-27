package ilstu.edu;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

/** A class that represents a student report */
public class StudentReport {

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
     * Reads fileName and fills the grades, students, and gradedItems array
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

}
