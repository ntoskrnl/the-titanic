import java.net.*;
import java.sql.Time;
import java.util.Date;

/**
 * TheTitanic Server Side Main Class
 * @author DANON [Anton Danshin]
 */
public class Main {

    /**
     * Program entry point
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ConnectionContainer connections = new ConnectionContainer();
        try{
            ServerSocket server = new ServerSocket(10000);
            while(true){
                Socket newClient = server.accept();
                connections.add(newClient);
                System.out.println(System.currentTimeMillis()+"\tNew client has just connected to the server!");
            }
        }catch(Exception ex){
            System.err.println(ex);
        }
    }

}
