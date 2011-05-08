package client.util.event;

import javax.sound.sampled.DataLine.*;
import titanic.basic.Game;

/**
 *
 * @author danon
 */
public class MakeHitEvent extends GameEvent {
    private double strength;

    public MakeHitEvent(Game src, double str) {
        super(src, GameEvent.EVENT_IMPACT);
        strength = str;
    }

    public void execute() {
        Game g = (Game)getSource();
        
        g.getBilliardKey().setPower((float)strength);
        g.getBilliardKey().makeHit();
        g.makeHit();
        g.changeStatus(Game.S_MOVING);
    }

    @Override
    public String toString(){
        return getClass().getName()+"{type=\""+getType()+"\"; strength="+strength+";}";
    }

}
