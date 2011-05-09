package client.util.event;

import client.util.SimpleGame;
import javax.sound.sampled.DataLine.*;
import titanic.basic.Game;

/**
 *
 * @author danon
 */
public class BallsStopEvent extends GameEvent {
    boolean mine;
    
    public BallsStopEvent(Game src) {
        super(src, GameEvent.EVENT_BALLS_STOP);
        SimpleGame sg = (SimpleGame)src;
        mine = (src.getGameStatus()==Game.S_MOVING);
    }

    public void execute() {
        Game g = (Game)getSource();
        if(g instanceof SimpleGame){
            SimpleGame sg = (SimpleGame)g;
            // here we should decide who plays next
            if(!mine){
                sg.syncBalls();
                sg.changeStatus(Game.S_BALL_SELECT);
            }
            else {
                sg.sendBalls();
                sg.changeStatus(Game.S_WAIT_RIVAL);
            }
        }
    }

    @Override
    public String toString(){
        return getClass().getName()+"{type=\""+getType()+"\";}";
    }

}
