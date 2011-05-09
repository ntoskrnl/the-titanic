package client.util.event;

import java.security.InvalidParameterException;
import javax.sound.sampled.DataLine.*;
import titanic.basic.Game;

/**
 *
 * @author danon
 */
public class MakeHitEvent extends GameEvent {
    private double strength;
    private boolean mine;

    public MakeHitEvent(Game src, double str) {
        super(src, GameEvent.EVENT_IMPACT);
        strength = str;
        if(str==0) {
            src.changeStatus(Game.S_BALL_SELECT);
            throw new InvalidParameterException("Hit with zero power???");
        }
    }

    public void execute() {
        Game g = (Game)getSource();
        if(g.getGameStatus()!=Game.S_MAKE_HIT){
            System.err.println("It is not allowed to make hit now.");
            return;
        }
            g.getBilliardKey().setPower((float)strength);
            g.getBilliardKey().makeHit();
            g.makeHit(g.getBilliardKey().getBall(), g.getBilliardKey().getPower(), 
                        g.getBilliardKey().getAngle() + (float)Math.PI/2);
        g.changeStatus(Game.S_MOVING);
    }

    @Override
    public String toString(){
        return getClass().getName()+"{type=\""+getType()+"\"; strength="+strength+";}";
    }

}
