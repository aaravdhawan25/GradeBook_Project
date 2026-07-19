import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class GradebookApp {
 public static void main(String[] args) {
        GradebookManager manager = new GradebookManager(); // this holds allr students in memory
        Scanner scanner = new Scanner(System.in);
        boolean running = true; // stays true until they pick 8 thats what ends the while loop

        while (running) {
    System.out.println("==== Gradebook Manager ====");
     System.out.println("1. Add Student");
      System.out.println("2. Add Grade to Student");
    System.out.println("3. View All Students");
    System.out.println("4. View Student Details");
     System.out.println("5. Search Student by ID");
    System.out.println("6. Load Data from File");
     System.out.println("7. Save Data to File");
    System.out.println("8. Exit");
    System.out.print("Enter choice: ");

      // if they type a letter instead of a number this stops it from crashing
      if (!scanner.hasNextInt()) {
         System.out.println();
          System.out.println("Invalid input. Please enter a number from 1 to 8.");
           scanner.next(); // throw away whatever they typed
        continue;
            }
        
    int choice = scanner.nextInt();
    scanner.nextLine(); // clears the leftover newline so nextLine below works right

         if (choice == 1) {

        // add student 
       System.out.print("Enter student id: ");
      if (!scanner.hasNextInt()) {
            System.out.println();
           System.out.println("Invalid student id. Student id must be greater than 0.");
           scanner.next();
            continue;
       }
           int id = scanner.nextInt();
            scanner.nextLine();

         System.out.print("Enter student name: ");
        String name = scanner.nextLine();

        System.out.println();

            if (manager.findById(id) != null) {
           System.out.println("A student with id " + id + " already exists. Student was not added.");
         } else {
            try {
                   manager.addStudent(new GradebookStudent(id, name));
                   System.out.println("Student added successfully.");
            } catch (IllegalArgumentException e) {
             if (id <= 0) {
               System.out.println("Invalid student id. Student id must be greater than 0.");
                 } else {
                System.out.println("Invalid student name. Student name cannot be empty.");
                  }
                    }
                }

            } else if (choice == 2) {

         // add grade to student 
          if (manager.getStudents().isEmpty()) {
          System.out.println();
          System.out.println("No students in the gradebook yet. Add a student or load data from a file first.");       continue; }

        System.out.print("Enter student id: ");
           if (!scanner.hasNextInt()) {
              System.out.println();
              System.out.println("Invalid student id.");
           scanner.next();
           continue;
                }
         int id = scanner.nextInt();
       scanner.nextLine();

         GradebookStudent student = manager.findById(id);
         if (student == null) {
          System.out.println();
            System.out.println("No student found with id " + id + ". Grade was not added.");
            continue;
         }

     System.out.print("Enter grade title: ");
       String title = scanner.nextLine();

    System.out.print("Enter score: ");
        if (!scanner.hasNextDouble()) {
            System.out.println();
           System.out.println("Invalid score. Score must be between 0.0 and 100.0.");
              scanner.next();
              continue;
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

            } else if (choice == 3) {

                // view all students 
             if (manager.getStudents().isEmpty()) {
                   System.out.println();
                  System.out.println("No students in the gradebook yet.");
                    continue;
                }

       System.out.println();
               System.out.println("All Students:");

     for (GradebookStudent student : manager.getStudents()) {
            double avg = Math.round(student.averageGrade() * 100.0) / 100.0; // round to 2 decimals

              if (student.getGrades().isEmpty()) {
              System.out.println(student.getID() + " - " + student.getName()
                    + " - Average: " + avg + " (no grades yet)");
        } else {
          System.out.println(student.getID() + " - " + student.getName()
             + " - Average: " + avg);
                }
              }

            } else if (choice == 4) {

                //  view student details 
        if (manager.getStudents().isEmpty()) {
        System.out.println();
          System.out.println("No students in the gradebook yet. Add a student or load data from a file before viewing details.");
                continue;
                }

         System.out.print("Enter student id: ");
        if (!scanner.hasNextInt()) {
           System.out.println();
          System.out.println("Invalid student id.");
        scanner.next();
        continue;
                }
        int id = scanner.nextInt();
     scanner.nextLine();

       GradebookStudent student = manager.findById(id);
       System.out.println();

      if (student == null) {
           System.out.println("No student found with id " + id + ".");
        continue;
           }

          System.out.println("Student Details:");
           System.out.println("ID: " + student.getID());
         System.out.println("Name: " + student.getName());
         System.out.println();
           System.out.println("Grades:");

             if (student.getGrades().isEmpty()) {
          System.out.println("No grades yet.");
             } else {
             for (GradeItem grade : student.getGrades()) {
                System.out.println(grade); // GradeItems toString gives "title: score%"
               }
                }

       double avg = Math.round(student.averageGrade() * 100.0) / 100.0;
          System.out.println();
          System.out.println("Average: " + avg);

       } else if (choice == 5) {

                // search student by id 
           if (manager.getStudents().isEmpty()) {
           System.out.println();
          System.out.println("No students in the gradebook yet. Add or load data first.");
               continue;
                }

         System.out.print("Enter student id to search: ");
          if (!scanner.hasNextInt()) {
           System.out.println();
               System.out.println("Invalid student id.");
           scanner.next();
                    continue;
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

            } else if (choice == 6) {

                // ---- load data from file ----
                System.out.print("Enter file path: ");
                String filename = scanner.nextLine();

                System.out.println();

                try {
                    int studentsLoaded = manager.loadFromFile(filename);
                    System.out.println("Data loaded successfully.");
                    System.out.println("Students loaded: " + studentsLoaded);
                } catch (FileNotFoundException e) {
                    System.out.println("Could not find file: " + filename);
                    System.out.println("Gradebook was not changed.");
                }

            } else if (choice == 7) {

                // ---- save data to file ----
                if (manager.getStudents().isEmpty()) {
                    System.out.println();
                    System.out.println("No gradebook data to save yet. Add a student or load data from a file before saving.");
                    continue;
                }

                System.out.print("Enter file path: ");
                String filename = scanner.nextLine();

                System.out.println();

                try {
                    manager.saveToFile(filename);
                    System.out.println("Gradebook saved successfully.");
                } catch (IOException e) {
                    System.out.println("Could not save file: " + filename);
                }

            } else if (choice == 8) {

                // exit
                running = false; // this is what breaks the while loop up top
                System.out.println();
                System.out.println("Goodbye.");

            } else {

                // anything not 1 to 8n
                System.out.println();
                System.out.println("Invalid menu choice. Please enter a number from 1 to 8.");
            }
        }

        scanner.close();
    }
}

