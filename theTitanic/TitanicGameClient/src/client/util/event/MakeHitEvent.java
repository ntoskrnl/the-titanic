package client.util.event;

import client.util.SoundPlayer;
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
        Game g = (Game)getSource();
        g.getBilliardKey().setPower((float)strength);
        
    }

    public void execute() {
        SoundPlayer.play(getClass().getResource("/client/res/event/click.wav"), 
                (float)Math.pow(Math.abs(strength), 1/5.0));
        Game g = (Game)getSource();
        if(g.getBilliardKey().getBall()==null) {
            g.changeStatus(Game.S_BALL_SELECT);
            return;
        }
        if(g.getBilliardKey().getBall().isActive() == false){
            g.changeStatus(Game.S_BALL_SELECT);
            return;
        }
        g.getBilliardKey().makeHit();
        g.makeHit(g.getBilliardKey().getBall(), g.getBilliardKey().getPower(), 
                    g.getBilliardKey().getAngle() + (float)Math.PI/2);
    }

    @Override
    public String toString(){
        return getClass().getName()+"{type=\""+getType()+"\"; strength="+strength+";}";
    }

}
