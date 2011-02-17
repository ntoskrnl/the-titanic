
import java.net.Socket;
import java.util.ArrayList;
/**
 * Container for active connections (Sockets)
 * @author danon
 */
public class ConnectionContainer {
    private ArrayList<ClientHandler> con;
    public ConnectionContainer() {
        con = new ArrayList<ClientHandler>();
    }
    public void add(Socket s){
        con.add(new ClientHandler(s));
    }
}
