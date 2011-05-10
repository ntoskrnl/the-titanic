package util;

import java.util.UUID;

/**
 * Represents server-side game
 * @author danon
 */
public class Game {
    private final String id;
    private Player player1, player2, whoPlays;
    private Ball[] balls;
    public boolean ballsModified = false;
    
    public Game(int p1, int p2) {
        id = UUID.randomUUID().toString();
        player1 = new Player(p1);
        player2 = new Player(p2);
        whoPlays = player1;
        balls = new Ball[16];
        for(int i=0;i<16;i++)
            balls[i]= new Ball(i + " 0 0 0 0 1");
        player1.setStatus(Game.S_BALL_SELECT);
        player2.setStatus(Game.S_WAIT_RIVAL);
    }
    
    public String getID(){
        return id;
    }
    
    public int whoPlays(){
        return whoPlays.getId();
    }
    
    public void setCurrentPlayer(int p){
        if(player1.getId()==p)
            whoPlays = player1;
        else if(player2.getId()==p)
            whoPlays = player2;
    }

    public Player getPlayer1(){
        return player1;
    }
    public Player getPlayer2(){
        return player2;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof Game){
            return ((Game)o).getID().equals(getID());
        }
        return false;
    }
    
    public void resolveTurn(){
        if(whoPlays.equals(player1)){
            if(player1.lastHits()==0){
                whoPlays = player2;
                player2.setStatus(S_BALL_SELECT);
                player1.setStatus(S_WAIT_RIVAL);
            } else {
                player1.setStatus(S_BALL_SELECT);
                player2.setStatus(S_WAIT_RIVAL);
            }
        } else {
            if(player2.lastHits()==0){
                whoPlays = player1;
                player1.setStatus(S_BALL_SELECT);
                player2.setStatus(S_WAIT_RIVAL);
            } else {
                player2.setStatus(S_BALL_SELECT);
                player1.setStatus(S_WAIT_RIVAL);
            }
        }
        player1.resetLastHits();
        player2.resetLastHits();
    }

    Ball[] getBalls() {
        return balls;
    }
        
        /**
     * Waiting for user to make next impact. (no physics)
     */
    public static final int S_BALL_SELECT = 1;
    /**
     * Now the balls are moving (physics is active)
     */
    public static final int S_MOVING = 2;
    /**
     * Animation of the key to simulate a hit.
     */
    public static final int S_MAKE_HIT = 3;
    /**
     * The game is over. It's time for the final verdict.
     */
    public static final int S_FINISH = 4;
    /**
     * Coffee break or something like that (no physics).
     */
    public static final int S_PAUSE = 5;
    /**
     * Wait for rival to make a hit
     */
    public static final int S_WAIT_RIVAL = 6;
    /**
     * Synchronisation
     */
    public static final int S_SYNC = 7;

    public static final int S_NONE = 0;
}
