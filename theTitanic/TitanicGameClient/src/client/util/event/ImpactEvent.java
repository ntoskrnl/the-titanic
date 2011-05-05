package client.util.event;

import client.util.SoundPlayer;
import javax.sound.sampled.DataLine.*;
import titanic.basic.Game;

/**
 *
 * @author danon
 */
public class ImpactEvent extends GameEvent {
    private double speed;

    public ImpactEvent(Game src, double speed) {
        super(src, GameEvent.EVENT_IMPACT);
        this.speed = speed;
    }

    public void execute() {
        // play sound of impact (use client.util.SoundPlayer.play(URL))
        SoundPlayer.play(getClass().getResource("/client/res/event/click.wav"), 
                (float)Math.pow(Math.abs(speed/20), 1/2.5));
    }

    @Override
    public String toString(){
        return getClass().getName()+"{type=\""+getType()+"\"; speed="+speed+";}";
    }

}
