package client.util.event;

import client.Main;
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
        SimpleGame sg = (SimpleGame)src;
    }

    public void execute() {
        SimpleGame sg = (SimpleGame)getSource();
        sg.changeStatus(Game.S_SYNC);
        Main.server.commandAndResponse(100, "GAME BALLS STOP", sg.gameID, Main.server.secret);
        System.out.println("Balls stop");
    }

    @Override
    public String toString(){
        return getClass().getName()+"{type=\""+getType()+"\";}";
    }

}
