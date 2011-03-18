package titanic.basic;

import client.util.event.*;

/**
 * The interface for the event queue.
 * @author danon
 */
public interface EventPipeLine {
    /**
     * Adds new event to the pipeline (queue)
     * @param e GameEvent instance
     */
    public void add(GameEvent e);

    /**
     * Calls all event processors and removes them from the queue
     */
    public void exec();

    /**
     * Removes all events. (ignore the events)
     */
    public void clear();

    /**
     * First event from the queue
     * @return GameEvent instance or null if empty
     */
    public GameEvent getFirst();

    /**
     * This method can be usefull if you want to know which events are there in the queue.
     * @return all events as GameEvent[] array
     */
    public GameEvent[] getEvents();

    /**
     * Number of events in the queue
     */
    public int size();
    
}
