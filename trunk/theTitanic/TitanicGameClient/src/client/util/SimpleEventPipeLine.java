package client.util;

import client.util.event.GameEvent;
import java.util.LinkedList;
import java.util.Queue;
import titanic.basic.EventPipeLine;

/**
 * This class is a simple implementation of the interface EventPipeLine.
 * It is used to collect and process the events during the game.
 * @author Danon
 */
public class SimpleEventPipeLine implements EventPipeLine {

    private final Queue<GameEvent> events;

    public SimpleEventPipeLine() {
        events = new LinkedList<GameEvent>();
    }


    public void add(GameEvent e) {
        //System.out.println("Event: "+e);
        events.add(e);
    }

    public void exec() {
        synchronized(events){
            for(GameEvent event: events){
                event.execute();
            }
        }
    }

    public void clear() {
        synchronized(events){
            events.clear();
        }
    }

    public GameEvent[] getEvents() {
        synchronized(events){
            return (GameEvent[])events.toArray();
        }
    }

    public GameEvent getFirst() {
            return events.poll();
    }

    public int size() {
        return events.size();
    }

}