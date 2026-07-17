import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class GradebookManager {

   private ArrayList<GradebookStudent> students;

  public GradebookManager() {
  students = new ArrayList<>();
    }

    public boolean addStudent(GradebookStudent student) {
    if (student == null) {
       return false;
    }

       if (findById(student.getID()) != null) {
       return false;
        }

    students.add(student);
         return true;
          }

    public GradebookStudent findById(int id) {
        for (GradebookStudent student : students) {
                  if (student.getID() == id) {
      return student;
       }
        }

        return null;
    }

    public boolean addGradeToStudent(int id, String title, double score) {
     GradebookStudent student = findById(id);

  if (student == null) {
  return false;
   }

   student.addGrade(new GradeItem(title, score));
   return true;
    }

    public ArrayList<GradebookStudent> getStudents() {
    return new ArrayList<>(students);
    }
   public void loadFromFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
           String line = scanner.nextLine().trim();
      if (line.isEmpty()) {
       continue; // skip blank lines, nothing to do with those
         }

 String[] parts = line.split(",");

        try {
         if (parts[0].equals("STUDENT")) {
        int id = Integer.parseInt(parts[1].trim());
         String name = parts[2].trim();
    
         addStudent(new GradebookStudent(id, name));
     
      } else if (parts[0].equals("GRADE")) {              
         int id = Integer.parseInt(parts[1].trim());
       String title = parts[2].trim();
        double score = Double.parseDouble(parts[3].trim());
        addGradeToStudent(id, title, score);
         } else {
            System.out.println("skipping line, don't recognize it: " + line);
            }
            } catch (Exception e) {
                System.out.println("couldn't parse this line, skipping: " + line);
            }
        }

        scanner.close();
    }

    public void saveToFile(String filename) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(filename));

        for (GradebookStudent student : students) {
            writer.println("STUDENT," + student.getID() + "," + student.getName());

        for (GradeItem grade : student.getGrades()) {
             writer.println("GRADE," + student.getID() + "," + grade.getTitle() + "," + grade.getScore());
            }
        }

        writer.close();
    } 
   }

