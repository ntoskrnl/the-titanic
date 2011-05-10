package util;

/**
 *
 * @author danon
 */
public class Player {
    private int score;
    private int id;
    private int status;
    private boolean hit;
    private int hBall;
    private float hAngle;
    private float hPower;
    private int lastHits;

    public Player(int id) {
        this.id = id;
        status = 0;
        score = 0;
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        if(score>this.score)
            lastHits+=score-this.score;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public synchronized void setHit(int ball, float power, float angle) {
        hit = true;
        hBall = ball;
        hPower = power;
        hAngle = angle;
    }
    
    public synchronized boolean hasHit(){
        return hit;
    }
   
    public Integer getHitBall(){
        if(!hit) return null;
        return hBall;
    }
    public Float getHitPower(){
        if(!hit) return null;
        return hPower;
    }
    public Float getHitAngle(){
        if(!hit) return null;
        return hAngle;
    }
    
    public synchronized void unsetHit(){
        hit = false;
    }

    public int lastHits() {
        return lastHits;
    }

    void resetLastHits() {
        lastHits = 0;
    }
}
