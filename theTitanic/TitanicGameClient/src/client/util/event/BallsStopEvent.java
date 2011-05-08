package client.util.event;

import client.util.SimpleGame;
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
            // here we should decide who plays next
            if(sg.iPlayNext()){
                sg.changeStatus(Game.S_BALL_SELECT);
                sg.setIPlayNext(false);
            }
            else {
                sg.changeStatus(Game.S_WAIT_RIVAL);
                sg.setIPlayNext(true);
            }
        }
    }

    @Override
    public String toString(){
        return getClass().getName()+"{type=\""+getType()+"\";}";
    }

}
