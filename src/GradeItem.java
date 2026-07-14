public class GradeItem {
    
    private String title;
    private double score;

    public GradeItem(String title, double score){

        if(title == null || title.isEmpty()){
            throw new IllegalArgumentException("Title must have a value");
        }

        if(score < 0.0 || score > 100){
            throw new IllegalArgumentException("Score must be in the range of 0 to 100");
        }


        this.score = score;
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public double getScore(){
        return score;
    }

    public void setTitle(String title){
        if(title == null || title.isEmpty()){
            throw new IllegalArgumentException("Title must have a value");
        }

        else this.title  = title;
    }

    public void setScore(double score){

        if(score < 0.0 || score > 100){
            throw new IllegalArgumentException("Score must be in the range of 0 to 100");
        }

        else this.score = score;


    }

    public String toString(){
        return title + ": " + score + "%";
    }



}
