package client.util.event;

import titanic.basic.Ball;
import titanic.basic.Game;

/**
 * This class represents the event that happens when a ball comes to the pocket.
 * @author danon
 */
public class BallToPocketEvent extends GameEvent{
    private Ball ball;
    private int pocket;
    
    /**
     * Constructs new instance of event with specified ball and pocket
     * @param src Source of the event (Game)
     * @param b What ball came into the pocket?
     * @param pocket Pocket number {0,1,2,3,4,5}
     */
    public BallToPocketEvent(Game src, Ball b, int pocket) 
            throws IllegalArgumentException {
        super(src, GameEvent.EVENT_WHOLE);
        
        if(pocket>5 || pocket<0) 
            throw new IllegalArgumentException("Invalid pocket number " + pocket);
        
        if(ball==null) 
            throw new IllegalArgumentException("Invalid ball (null)");
        
        ball = b;
        this.pocket = pocket;
    }
    
    /**
     * Returns the ball which this event happened to.
     */
    public Ball getBall(){
        return ball;
    }
    
    public int getPocket(){
        return pocket;
    }
    
    @Override
    public void execute() {
        System.out.println(this);
    }
    
}
