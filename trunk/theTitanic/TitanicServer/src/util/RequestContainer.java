package util;

import java.util.ArrayList;

/**
 * Provides synchronized access to game requests. Thread safe.
 * @author danon
 */
public class RequestContainer {
    private ArrayList<GameRequest> r; 
    public RequestContainer() {
        r = new ArrayList<GameRequest>();
    }
    
    public synchronized boolean add(GameRequest req){
        for(GameRequest w : r){
                if(w.equals(req)) return false;
        }
        r.add(req);
        return true;
    }
    
    public synchronized GameRequest getActive(int from, int to){
        GameRequest w = new GameRequest(from, to);
        for(GameRequest req : r){
            if(!req.isActive())
                if(w.equals(req)) return req;
        }
        return null;
    }
    public synchronized GameRequest get(int from, int to){
        GameRequest w = new GameRequest(from, to);
        for(GameRequest req : r){
                if(w.equals(req)) return req;
        }
        return null;
    }
    
    public synchronized GameRequest getActiveFor(int to){
        for(GameRequest req : r){
            if(!req.isActive())
                if(req.getTo()==to) return req;
        }
        return null;
    }
    
    public synchronized GameRequest getFor(int to){
        for(GameRequest req : r){
                if(req.getTo()==to) return req;
        }
        return null;
    }
    
    public synchronized boolean remove(GameRequest req){
        return r.remove(req);
    }
    
    public synchronized void clear(){
        r.clear();
    }

    public void removeFor(int id) {
        ArrayList<GameRequest> d = new ArrayList<GameRequest>();
        for(GameRequest req : r){
            if(req.getFrom()==id || req.getTo()==id)
                d.add(req);
        }
        r.removeAll(d);
        d.clear();
    }
}
