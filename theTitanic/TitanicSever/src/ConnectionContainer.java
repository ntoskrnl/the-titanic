import java.net.Socket;
import java.util.TreeSet;

/**
 * Container for active connections (Sockets)
 * @author danon
 */
public class ConnectionContainer {
    /**
     * Contains all active sockets (as a ClientHandler)
     */
    private TreeSet<ClientHandler> con;

    /**
     * Default constructor. Creates empty container.
     */
    public ConnectionContainer() {
        con = new TreeSet<ClientHandler>();
    }

    /**
     * Adds new Socket to the container and starts new thread
     * @param s Socket to add
     */
    public void add(Socket s){
        con.add(new ClientHandler(this, s));
    }

    /**
     * Removes ClientHandler from container
     * @param s ClientHandler to remove
     */
    public void remove(ClientHandler s){
        con.remove(s);
    }

    /**
     * How many elements does has the container got?
     * @return Number of active connections
     */
    public int getCount(){
        return con.size();
    }
}
