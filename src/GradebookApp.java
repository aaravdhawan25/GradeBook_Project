import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

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
                System.out.println();
                System.out.println("Invalid input. Please enter a number from 1 to 8.");
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
                System.out.println();
                System.out.println("Goodbye.");
            } else {
                System.out.println();
                System.out.println("Invalid menu choice. Please enter a number from 1 to 8.");
            }
        }

        scanner.close(); // close it when were done, good habit i guess
    }

    public static void printMenu() {
        // just prints out the options, nothing fancy here
        System.out.println("==== Gradebook Manager ====");
        System.out.println("1. Add Student");
        System.out.println("2. Add Grade to Student");
        System.out.println("3. View All Students");
        System.out.println("4. View Student Details");
        System.out.println("5. Search Student by ID");
        System.out.println("6. Load Data from File");
        System.out.println("7. Save Data to File");
        System.out.println("8. Exit");
    }

    public static void addStudent(GradebookManager manager, Scanner scanner) {
        System.out.print("Enter student id: ");
        if (!scanner.hasNextInt()) {
            System.out.println();
            System.out.println("Invalid student id. Student id must be greater than 0.");
            scanner.next();
            return;
        }
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.println();

        if (manager.findById(id) != null) {
            // dont let them add the same id twice, that would be weird
            System.out.println("A student with id " + id + " already exists. Student was not added.");
            return;
        }

        try {
            manager.addStudent(new GradebookStudent(id, name));
            System.out.println("Student added successfully.");
        } catch (IllegalArgumentException e) {
            // the student class throws this if stuff is invalid so we gotta figure out which one
            if (id <= 0) {
                System.out.println("Invalid student id. Student id must be greater than 0.");
            } else {
                System.out.println("Invalid student name. Student name cannot be empty.");
            }
        }
    }

    public static void addGradeToStudent(GradebookManager manager, Scanner scanner) {
        if (manager.getStudents().isEmpty()) {
            // cant add a grade if theres literally no students yet
            System.out.println();
            System.out.println("No students in the gradebook yet. Add a student or load data from a file first.");
            return;
        }

        System.out.print("Enter student id: ");
        if (!scanner.hasNextInt()) {
            System.out.println();
            System.out.println("Invalid student id.");
            scanner.next();
            return;
        }
        int id = scanner.nextInt();
        scanner.nextLine();

        GradebookStudent student = manager.findById(id);
        if (student == null) {
            System.out.println();
            System.out.println("No student found with id " + id + ". Grade was not added.");
            return;
        }

        System.out.print("Enter grade title: ");
        String title = scanner.nextLine();

        System.out.print("Enter score: ");
        if (!scanner.hasNextDouble()) {
            System.out.println();
            System.out.println("Invalid score. Score must be between 0.0 and 100.0.");
            scanner.next();
            return;
        }
        double score = scanner.nextDouble();
        scanner.nextLine();

        System.out.println();

        try {
            manager.addGradeToStudent(id, title, score);
            System.out.println("Grade added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid score. Score must be between 0.0 and 100.0.");
        }
    }

    public static void viewAllStudents(GradebookManager manager) {
        if (manager.getStudents().isEmpty()) {
            System.out.println();
            System.out.println("No students in the gradebook yet.");
            return;
        }

        System.out.println();
        System.out.println("All Students:");

        for (GradebookStudent student : manager.getStudents()) {
            double avg = Math.round(student.averageGrade() * 100.0) / 100.0; // rounds it to 2 decimals

            if (student.getGrades().isEmpty()) {
                System.out.println(student.getID() + " - " + student.getName()
                        + " - Average: " + avg + " (no grades yet)");
            } else {
                System.out.println(student.getID() + " - " + student.getName()
                        + " - Average: " + avg);
            }
        }
    }

    public static void viewStudentDetails(GradebookManager manager, Scanner scanner) {
        if (manager.getStudents().isEmpty()) {
            System.out.println();
            System.out.println("No students in the gradebook yet. Add a student or load data from a file before viewing details.");
            return;
        }

        System.out.print("Enter student id: ");
        if (!scanner.hasNextInt()) {
            System.out.println();
            System.out.println("Invalid student id.");
            scanner.next();
            return;
        }
        int id = scanner.nextInt();
        scanner.nextLine();

        GradebookStudent student = manager.findById(id);
        System.out.println();

        if (student == null) {
            System.out.println("No student found with id " + id + ".");
            return;
        }

        // print out everything about this one student
        System.out.println("Student Details:");
        System.out.println("ID: " + student.getID());
        System.out.println("Name: " + student.getName());
        System.out.println();
        System.out.println("Grades:");

        if (student.getGrades().isEmpty()) {
            System.out.println("No grades yet.");
        } else {
            for (GradeItem grade : student.getGrades()) {
                System.out.println(grade);
            }
        }

        double avg = Math.round(student.averageGrade() * 100.0) / 100.0;
        System.out.println();
        System.out.println("Average: " + avg);
    }

    public static void searchStudent(GradebookManager manager, Scanner scanner) {
        if (manager.getStudents().isEmpty()) {
            System.out.println();
            System.out.println("No students in the gradebook yet. Add or load data first.");
            return;
        }

        System.out.print("Enter student id to search: ");
        if (!scanner.hasNextInt()) {
            System.out.println();
            System.out.println("Invalid student id.");
            scanner.next();
            return;
        }
        int id = scanner.nextInt();
        scanner.nextLine();

        GradebookStudent student = manager.findById(id);
        System.out.println();

        if (student == null) {
            System.out.println("No student found with id " + id + ".");
        } else {
            double avg = Math.round(student.averageGrade() * 100.0) / 100.0;
            System.out.println("Found:");
            System.out.println(student.getID() + " - " + student.getName());
            System.out.println("Average: " + avg);
        }
    }

    public static void loadData(GradebookManager manager, Scanner scanner) {
        System.out.print("Enter file path: ");
        String filename = scanner.nextLine();

        System.out.println();

        try {
            int studentsLoaded = manager.loadFromFile(filename);
            System.out.println("Data loaded successfully.");
            System.out.println("Students loaded: " + studentsLoaded);
        } catch (FileNotFoundException e) {
            // file path was probably wrong or doesnt exist
            System.out.println("Could not find file: " + filename);
            System.out.println("Gradebook was not changed.");
        }
    }

    public static void saveData(GradebookManager manager, Scanner scanner) {
        if (manager.getStudents().isEmpty()) {
            System.out.println();
            System.out.println("No gradebook data to save yet. Add a student or load data from a file before saving.");
            return;
        }

        System.out.print("Enter file path: ");
        String filename = scanner.nextLine();

        System.out.println();

        try {

            //saveToFile writes out the entire current roster every time, not just newly added elements
            //it means that save then load always gives back the exact same data
            manager.saveToFile(filename);
            System.out.println("Gradebook saved successfully.");
        } catch (IOException e) {
            //this could occur if the folder in the path doesnt exist
            System.out.println("Could not save file: " + filename);
        }
    }
}