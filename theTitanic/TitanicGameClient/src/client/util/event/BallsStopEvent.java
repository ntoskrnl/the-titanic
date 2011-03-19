package client.util.event;

import client.util.SimpleGame;
import client.util.SoundPlayer;
import javax.sound.sampled.DataLine.*;
import titanic.basic.Game;

/**
 *
 * @author danon
 */
public class BallsStopEvent extends GameEvent {

    public BallsStopEvent(Game src) {
        super(src, GameEvent.EVENT_BALLS_STOP);
    }

    public void execute() {
        Game g = (Game)getSource();
        if(g instanceof SimpleGame){
            SimpleGame sg = (SimpleGame)g;
            sg.stop();
        }
    }

    @Override
    public String toString(){
        return getClass().getName()+"{type=\""+getType()+"\";}";
    }

}
