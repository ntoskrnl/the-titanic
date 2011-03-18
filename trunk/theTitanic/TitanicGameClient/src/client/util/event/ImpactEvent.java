package client.util.event;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
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
        // play sound of impact (use sun.audio.AudioPlayer)
        try{
            AudioStream as = new AudioStream(getClass().getResourceAsStream("/client/res/event/click.wav"));
            AudioPlayer.player.start(as);
        } catch(Exception ex){
            System.err.println(getClass().getName()+": Failed to play audio file.\n"+ex.getLocalizedMessage());
        }
    }

    @Override
    public String toString(){
        return getClass().getName()+"{type=\""+getType()+"\"; speed="+speed+";}";
    }

}
