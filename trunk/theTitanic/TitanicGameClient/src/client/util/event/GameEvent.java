package client.util.event;

import titanic.basic.Game;

/**
 * GameEvent abstract class. All game events must extend this class.
 * @author danon
 */
public abstract class GameEvent {
    private String type = null;
    private Game source;

    /**
     * Constructs new GameEvent instance with specified event type
     * @param g Game instance (source of the event)
     * @param type Event type
     */
    public GameEvent(Game src, String type){
        this.type = type;
        source = src;
    }

    /**
     * Returns type of the event
     * @return string alias of event type
     */
    public String getType(){
        return type;
    }

    @Override
    public String toString(){
        return getClass().getName()+"{type=\""+getType()+"\"}";
    }

    /**
     * Executes the event (plays sound / changes game status)
     */
    abstract public void execute();

    public static final String EVENT_IMPACT = "impact";
    public static final String EVENT_WHOLE = "whole";
    public static final String EVENT_BALLS_STOP = "balls_stop";
    public static final String EVENT_GAME_END = "game_end";

}
