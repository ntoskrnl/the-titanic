package util;

/**
 *
 * @author danon
 */
public class GameRequest implements Comparable<GameRequest> {
    private int from, to;
    private Boolean accepted;
    private boolean waiting;
    
    public GameRequest(int from, int to) {
        this.from = from;
        this.to = to;
        accepted = null;
        waiting = false;
    }
    
    public Boolean isAccepted(){
        return accepted;
    }
    
    public void setAccepted(Boolean b){
        accepted = b;
    }
    
    public void deactivate(){
        waiting = true;
    }
    
    public boolean isActive(){
        return waiting;
    }
    public int getTo(){
        return to;
    }
    public int getFrom(){
        return from;
    }

    public int compareTo(GameRequest o) {
        if(o==null) return 1;
        if(o.getFrom()==from && o.getTo()==to) return 0;
        if(o.getFrom()>from) return -1;
        if(o.getFrom()<from) return 1;
        if(o.getTo()>to) return -1;
        if(o.getTo()<to) return 1;
        return 0;
    }
    
    @Override
    public boolean equals(Object o){
        if(o instanceof GameRequest)
            return(compareTo((GameRequest)o)==0);
        else return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 73 * hash + this.from;
        hash = 73 * hash + this.to;
        hash = 73 * hash + (this.accepted != null ? this.accepted.hashCode() : 0);
        hash = 73 * hash + (this.waiting ? 1 : 0);
        return hash;
    }
}
