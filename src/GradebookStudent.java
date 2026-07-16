import java.util.ArrayList;

public class GradebookStudent {
    
    private int id;
    private String name;

    ArrayList<GradeItem> grades;

    public GradebookStudent(int id, String name){

        if(id <= 0){
            throw new IllegalArgumentException("ID is not valid");
        }

        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name must exist");
        }



        this.id = id;
        this.name = name;
        grades = new ArrayList<>();
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
        return new ArrayList<>(grades);
    }

    public double averageGrade(){
        if(grades.isEmpty()) {
            return 0.0;
        }
     double total = 0;

     for(GradeItem grade : grades) {
         total += grade.getScore();
        }

        return total / grades.size();
     }
   
    }
   
   
    // public ArrayList<GradeItem> findByID(int id){
    //     ArrayList<GradeItem> byId = new ArrayList<>();
    //     if (id == getID()) {
             
    //     }
    // }

