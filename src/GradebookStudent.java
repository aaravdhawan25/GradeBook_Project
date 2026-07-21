import java.util.ArrayList;

public class GradebookStudent {
    
    private int id;
    private String name;

    ArrayList<GradeItem> grades;

    public GradebookStudent(int id, String name){

        if(id <= 0){
            throw new IllegalArgumentException("ID is not valid"); // no negative or zero ids allowed
        }

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name must exist");
        }



        this.id = id;
        this.name = name;
        grades = new ArrayList<>(); // starts off with no grades, empty list
    }

    public String getName(){
        return name;
    }

    public int getID(){
        return id;
    }

    public void setName(String name){
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name must exist");
        }
        this.name = name;
    }

    public void addGrade(GradeItem grade){
        if(grade == null){
            throw new IllegalArgumentException("Grade must have a value");
        }

        grades.add(grade);
    }

    public ArrayList<GradeItem> getGrades(){
        return new ArrayList<>(grades); // returns a copy so outside code cant mess with the real list
    }

    public double averageGrade(){
        if(grades.isEmpty()) {
            return 0.0; // no grades yet so just say 0
        }
     double total = 0;

     for(GradeItem grade : grades) {
         total += grade.getScore(); // adding up every score
        }

        return total / grades.size(); // then dividing to get the average
     }
   
     public boolean removeGrade(int index) {
        if(index < 0 || index >= grades.size()){
        return false; // out of bounds, cant remove something that doesnt exist
        }else grades.remove(index) ;
        return true;
     }
     public boolean editGrade(int index, String newTitle, double newScore) {
        if(index < 0 || index >= grades.size()){
            return false;
        }
     
     GradeItem grade = grades.get(index); // grab the grade so we can change it
     grade.setTitle(newTitle);
     grade.setScore(newScore);
     return true;
    }
   
}