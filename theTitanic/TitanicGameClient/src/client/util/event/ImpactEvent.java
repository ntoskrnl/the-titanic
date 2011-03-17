package client.util.event;

/**
 *
 * @author danon
 */
public class ImpactEvent extends GameEvent {
    private double speed;

    public ImpactEvent(double speed) {
        super(GameEvent.EVENT_IMPACT);
        this.speed = speed;
    }

    public void execute() {
        // play sound of impact (use sun.audio.AudioPlayer)
    }

    @Override
    public String toString(){
        return getClass().getName()+"{type=\""+getType()+"\"; speed="+speed+";}";
    }

}
