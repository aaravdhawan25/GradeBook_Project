public class GradeItem {
    
    private String title;
    private double score;

    public GradeItem(String title, double score){

        //this if statement ensures hat the title cannot be blank
        if(title == null || title.isEmpty()){
            throw new IllegalArgumentException("Title must have a value");
        }
     
        //scores only make sense between 0 and 100, so anything outside the range gets rejected
        if(score < 0.0 || score > 100){
            throw new IllegalArgumentException("Score must be in the range of 0 to 100");
        }


        this.score = score;
        this.title = title;
    }

    //getter for title
    public String getTitle(){
        return title;
    }
    //getter for score
    public double getScore(){
        return score;
    }

    
    public void setTitle(String title){
        // same check as the constructor, but lets change a grade item title after its been created
        if(title == null || title.isEmpty()){
            throw new IllegalArgumentException("Title must have a value");
        }

        else this.title  = title;
    }

    public void setScore(double score){
        //same check but changes score after it has been created
        if(score < 0.0 || score > 100){
            throw new IllegalArgumentException("Score must be in the range of 0 to 100");
        }

        else this.score = score;


    }
    //this is what prints when I do system.out.println(someGradeItem)
    // basically a format, instead of showing hashmap or a memory address
    public String toString(){
        return title + ": " + score + "%";
    }



}
