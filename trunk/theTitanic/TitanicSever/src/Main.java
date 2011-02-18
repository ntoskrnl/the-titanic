import java.net.*;

/**
 * TheTitanic Server Side Main Class
 * @author DANON [Anton Danshin]
 */
public class Main {
    /**
     * Contains all currently active connections
     */
    static ConnectionContainer connections;
    /**
     * ServerSocket to accept incoming connections
     */
    static ServerSocket server;

    /**
     * Default port for incoming connections
     */
    static final int DEFAULT_PORT = 10000;

    /**
     * Program entry point
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        connections = new ConnectionContainer();
        try{
            server = new ServerSocket(DEFAULT_PORT);
            while(true){
                Socket newClient = server.accept();
                connections.add(newClient);
                System.out.println(System.currentTimeMillis()+"\tNew client has just connected to the server!");
            }
        }catch(Exception ex){
            //Error
            System.out.println("Error!");
            System.err.println(ex);
        }
    }

}
