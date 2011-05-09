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
            balls[i]= new Ball(i + " 0 0 0 0 true");
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

    Ball[] getBalls() {
        return balls;
    }
}
