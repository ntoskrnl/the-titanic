package util;

import java.util.ArrayList;

/**
 * Synchronized container for games
 * @author danon
 */
public class GameContainer {
    ArrayList<Game> games;
    
    public GameContainer() {
        games = new ArrayList<Game>();
    }
    
    public Game get(int id1, int id2){
        for(Game g : games){
            int p1 = g.getPlayer1().getId();
            int p2 = g.getPlayer2().getId();
            if(id1==p1 && p2==id2) return g;
            if(id2==p1 && p2==id1) return g;
        }
        return null;
    }
    
    public Game get(String id){
        for(Game g : games){
            if(g.getID().equals(id)) return g;
        }
        return null;
    }
    
    public boolean add(int id1, int id2){
        if(get(id1, id2)==null){
            games.add(new Game(id1, id2));
            return true;
        }
        return false;   
    }
    
    public void remove(Game g){
        games.remove(g);
    }

    public void removeFor(int id) {
        ArrayList<Game> d = new ArrayList<Game>();
        for(Game g : games){
            if(g.getPlayer1().getId()==id || g.getPlayer2().getId()==id){
                d.add(g);
            }
        }
        games.removeAll(d);
        d.clear();
    }
}
