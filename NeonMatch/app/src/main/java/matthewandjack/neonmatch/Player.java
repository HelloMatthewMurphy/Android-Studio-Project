package matthewandjack.neonmatch;

/**
 * Created by user on 26/04/2017.
 */

public class Player {
    private int id;
    private int score;
    private String name;
    public Player() {

    }
    public Player(int id, int Score, String name) {
        this.id = id;
        this.score = score;
        this.name = name;
    }
    public void setId(int rank){
        this.id = rank;
    }
    public void setScore(int score){
        this.score = score;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getId(){
        return id;
    }
    public int getScore(){
        return score;
    }
    public String getName(){
        return name;
    }


}

