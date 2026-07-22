import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import Utils.MyPrint;

public class GradebookApp {

    public static void main(String[] args) {
        GradebookManager manager = new GradebookManager(); // this is basically the brain that holds all the students
        Scanner scanner = new Scanner(System.in); // so we can actually read what the user types
        boolean running = true; // keeps the loop going until they wanna quit

        while (running) {
            printMenu();
            System.out.print("Enter choice: ");

            if (!scanner.hasNextInt()) {
                // they typed something random like letters instead of a number
                MyPrint.print();
                MyPrint.print("Invalid input. Please enter a number from 1 to 8.");
                scanner.next(); // gotta clear it out or it loops forever lol
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine(); // eats the leftover enter key so next input doesnt get messed up

            // just checking which option they picked and calling the right method
            if (choice == 1) {
                addStudent(manager, scanner);
            } else if (choice == 2) {
                addGradeToStudent(manager, scanner);
            } else if (choice == 3) {
                viewAllStudents(manager);
            } else if (choice == 4) {
                viewStudentDetails(manager, scanner);
            } else if (choice == 5) {
                searchStudent(manager, scanner);
            } else if (choice == 6) {
                loadData(manager, scanner);
            } else if (choice == 7) {
                saveData(manager, scanner);
            } else if (choice == 8) {
                running = false; // this stops the while loop
                MyPrint.print();
                MyPrint.print("Goodbye.");
            } else {
                MyPrint.print();
                MyPrint.print("Invalid menu choice. Please enter a number from 1 to 8.");
            }
        }

        scanner.close(); // close it when were done, good habit i guess
    }

    public static void printMenu() {
        // just prints out the options, nothing fancy here
        MyPrint.print("==== Gradebook Manager ====");
        MyPrint.print("1. Add Student");
        MyPrint.print("2. Add Grade to Student");
        MyPrint.print("3. View All Students");
        MyPrint.print("4. View Student Details");
        MyPrint.print("5. Search Student by ID");
        MyPrint.print("6. Load Data from File");
        MyPrint.print("7. Save Data to File");
        MyPrint.print("8. Exit");
    }

    public static void addStudent(GradebookManager manager, Scanner scanner) {
        System.out.print("Enter student id: ");
        if (!scanner.hasNextInt()) {
            MyPrint.print();
            MyPrint.print("Invalid student id. Student id must be greater than 0.");
            scanner.next();
            return;
        }
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        MyPrint.print();

        if (manager.findById(id) != null) {
            // dont let them add the same id twice, that would be weird
            MyPrint.print("A student with id " + id + " already exists. Student was not added.");
            return;
        }

        try {
            manager.addStudent(new GradebookStudent(id, name));
            MyPrint.print("Student added successfully.");
        } catch (IllegalArgumentException e) {
            // the student class throws this if stuff is invalid so we gotta figure out which one
            if (id <= 0) {
                MyPrint.print("Invalid student id. Student id must be greater than 0.");
            } else {
                MyPrint.print("Invalid student name. Student name cannot be empty.");
            }
        }
    }

    public static void addGradeToStudent(GradebookManager manager, Scanner scanner) {
        if (manager.getStudents().isEmpty()) {
            // cant add a grade if theres literally no students yet
            MyPrint.print();
            MyPrint.print("No students in the gradebook yet. Add a student or load data from a file first.");
            return;
        }

        System.out.print("Enter student id: ");
        if (!scanner.hasNextInt()) {
            MyPrint.print();
            MyPrint.print("Invalid student id.");
            scanner.next();
            return;
        }
        int id = scanner.nextInt();
        scanner.nextLine();

        GradebookStudent student = manager.findById(id);
        if (student == null) {
            MyPrint.print();
            MyPrint.print("No student found with id " + id + ". Grade was not added.");
            return;
        }

        System.out.print("Enter grade title: ");
        String title = scanner.nextLine();

        System.out.print("Enter score: ");
        if (!scanner.hasNextDouble()) {
            MyPrint.print();
            MyPrint.print("Invalid score. Score must be between 0.0 and 100.0.");
            scanner.next();
            return;
        }
        double score = scanner.nextDouble();
        scanner.nextLine();

        MyPrint.print();

        try {
            manager.addGradeToStudent(id, title, score);
            MyPrint.print("Grade added successfully.");
        } catch (IllegalArgumentException e) {
            MyPrint.print("Invalid score. Score must be between 0.0 and 100.0.");
        }
    }

    public static void viewAllStudents(GradebookManager manager) {
        if (manager.getStudents().isEmpty()) {
            MyPrint.print();
            MyPrint.print("No students in the gradebook yet.");
            return;
        }

        MyPrint.print();
        MyPrint.print("All Students:");

        for (GradebookStudent student : manager.getStudents()) {
            double avg = Math.round(student.averageGrade() * 100.0) / 100.0; // rounds it to 2 decimals

            if (student.getGrades().isEmpty()) {
                MyPrint.print(student.getID() + " - " + student.getName()
                        + " - Average: " + avg + " (no grades yet)");
            } else {
                MyPrint.print(student.getID() + " - " + student.getName()
                        + " - Average: " + avg);
            }
        }
    }

    public static void viewStudentDetails(GradebookManager manager, Scanner scanner) {
        if (manager.getStudents().isEmpty()) {
            MyPrint.print();
            MyPrint.print("No students in the gradebook yet. Add a student or load data from a file before viewing details.");
            return;
        }

        System.out.print("Enter student id: ");
        if (!scanner.hasNextInt()) {
            MyPrint.print();
            MyPrint.print("Invalid student id.");
            scanner.next();
            return;
        }
        int id = scanner.nextInt();
        scanner.nextLine();

        GradebookStudent student = manager.findById(id);
        MyPrint.print();

        if (student == null) {
            MyPrint.print("No student found with id " + id + ".");
            return;
        }

        // print out everything about this one student
        MyPrint.print("Student Details:");
        MyPrint.print("ID: " + student.getID());
        MyPrint.print("Name: " + student.getName());
        MyPrint.print();
        MyPrint.print("Grades:");

        if (student.getGrades().isEmpty()) {
            MyPrint.print("No grades yet.");
        } else {
            for (GradeItem grade : student.getGrades()) {
                MyPrint.print(grade);
            }
        }

        double avg = Math.round(student.averageGrade() * 100.0) / 100.0;
        MyPrint.print();
        MyPrint.print("Average: " + avg);
    }

    public static void searchStudent(GradebookManager manager, Scanner scanner) {
        if (manager.getStudents().isEmpty()) {
            MyPrint.print();
            MyPrint.print("No students in the gradebook yet. Add or load data first.");
            return;
        }

        System.out.print("Enter student id to search: ");
        if (!scanner.hasNextInt()) {
            MyPrint.print();
            MyPrint.print("Invalid student id.");
            scanner.next();
            return;
        }
        int id = scanner.nextInt();
        scanner.nextLine();

        GradebookStudent student = manager.findById(id);
        MyPrint.print();

        if (student == null) {
            MyPrint.print("No student found with id " + id + ".");
        } else {
            double avg = Math.round(student.averageGrade() * 100.0) / 100.0;
            MyPrint.print("Found:");
            MyPrint.print(student.getID() + " - " + student.getName());
            MyPrint.print("Average: " + avg);
        }
    }

    public static void loadData(GradebookManager manager, Scanner scanner) {
        System.out.print("Enter file path: ");
        String filename = scanner.nextLine();

        MyPrint.print();

        try {
            int studentsLoaded = manager.loadFromFile(filename);
            MyPrint.print("Data loaded successfully.");
            MyPrint.print("Students loaded: " + studentsLoaded);
        } catch (FileNotFoundException e) {
            // file path was probably wrong or doesnt exist
            MyPrint.print("Could not find file: " + filename);
            MyPrint.print("Gradebook was not changed.");
        }
    }

    public static void saveData(GradebookManager manager, Scanner scanner) {
        if (manager.getStudents().isEmpty()) {
            MyPrint.print();
            MyPrint.print("No gradebook data to save yet. Add a student or load data from a file before saving.");
            return;
        }

        System.out.print("Enter file path: ");
        String filename = scanner.nextLine();

        MyPrint.print();

        try {

            //saveToFile writes out the entire current roster every time, not just newly added elements
            //it means that save then load always gives back the exact same data
            manager.saveToFile(filename);
            MyPrint.print("Gradebook saved successfully.");
        } catch (IOException e) {
            //this could occur if the folder in the path doesnt exist
            MyPrint.print("Could not save file: " + filename);
        }
    }
}