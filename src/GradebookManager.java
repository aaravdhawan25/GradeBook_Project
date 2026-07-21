import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class GradebookManager {

   private ArrayList<GradebookStudent> students; // this is where every student lives

   public GradebookManager(){
    students = new ArrayList<>();
   }

    public boolean addStudent(GradebookStudent student) {
        if (student == null) {
            return false; // cant add nothing lol
        }

        if (findById(student.getID()) != null) {
            return false; // id already taken, no dupes allowed
        }

        students.add(student);
        return true;
    }

    public GradebookStudent findById(int id) {
        // just loops through everyone until it finds the matching id
        for (GradebookStudent student : students) {
            if (student.getID() == id) {
                return student;
            }
        }

        return null; // didnt find em
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
        return new ArrayList<>(students); // giving back a copy so nobody messes with the real list
    }

   public int loadFromFile(String filename) throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);
        int studentsLoadedIn = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue; // skip blank lines, nothing to do with those
            }

        String[] parts = line.split(","); // the file is comma separated so this splits it up

        try {
            if (parts[0].equals("STUDENT")) {
            // first word tells us if this line is a student or a grade
            int id = Integer.parseInt(parts[1].trim());
            String name = parts[2].trim();
    
            addStudent(new GradebookStudent(id, name));
            studentsLoadedIn++;

            } else if (parts[0].equals("GRADE")) {              
                int id = Integer.parseInt(parts[1].trim());
                String title = parts[2].trim();
                double score = Double.parseDouble(parts[3].trim());
                addGradeToStudent(id, title, score);
            } else {
                System.out.println("skipping line, don't recognize it: " + line);
            }
        } catch (Exception e) {
                // line was probably formatted wrong, just skip it instead of crashing everything
                System.out.println("couldn't parse this line, skipping: " + line);
            }
        }

        scanner.close();
        return studentsLoadedIn;
    }

    public void saveToFile(String filename) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(filename));

        // writes every student out, then all their grades right under them
        for (GradebookStudent student : students) {
            writer.println("STUDENT," + student.getID() + "," + student.getName());

        for (GradeItem grade : student.getGrades()) {
             writer.println("GRADE," + student.getID() + "," + grade.getTitle() + "," + grade.getScore());
            }
        }

        writer.close();
    } 
    public ArrayList<GradebookStudent> searchByName(String search) {
        ArrayList<GradebookStudent> matches = new ArrayList<>();
        if(search == null) {
            return matches;
        }
        String lowerSearch = search.toLowerCase(); // lowercase so it doesnt matter how they type it
   for(GradebookStudent student : students) {
            if(student.getName().toLowerCase().contains(lowerSearch)) {
           matches.add(student);
            }
        }
        return matches;
    }

    public void sortByName() {
        // this is just selection sort, finds the smallest name each round and swaps it to the front
        for (int i = 0; i < students.size() - 1; i++) {
            int lowest = i;
        for(int j = i + 1; j < students.size(); j++) {
          if(students.get(j).getName().compareToIgnoreCase(students.get(lowest).getName()) < 0) {
               lowest = j;
          }
         }
              GradebookStudent temp = students.get(i);
          students.set(i, students.get(lowest));
         students.set(lowest, temp);
        }
    }

    public void sortByAverage() {
        // same idea as sortByName but finds the highest average instead
        for (int i = 0; i < students.size() - 1; i++) {
        int highest = i;
       for (int j = i + 1; j < students.size(); j++) {
         if (students.get(j).averageGrade() > students.get(highest).averageGrade()) {
           highest = j;
                }
            }
  GradebookStudent temp = students.get(i);
  students.set(i, students.get(highest));
     students.set(highest, temp);
            }
        }
    }