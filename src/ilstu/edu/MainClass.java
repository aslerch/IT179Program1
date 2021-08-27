package ilstu.edu;

public class MainClass {

    public static void main(String [] args) {

        StudentReport stuRep = new StudentReport("Grades.csv");
        stuRep.readFile();

    }

}
