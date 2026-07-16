import java.util.ArrayList;


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
}

