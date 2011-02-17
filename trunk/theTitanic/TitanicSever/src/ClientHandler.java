
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author danon
 */
public class ClientHandler {
    Socket socket = null;
    public ClientHandler(Socket s) {
        socket = s;
        new Thread(new ClientHandlerRunnable(s)).start();
    }

}

class ClientHandlerRunnable implements Runnable {
    private Socket socket;
    private InputStream is;
    public ClientHandlerRunnable(Socket s) {
        socket = s;
        try{
            is = s.getInputStream();
        } catch (Exception ex) {  
        }
    }

    public void run(){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String s;
        while(true){
            try{
                s = br.readLine();
            } catch (Exception ex){ s=null; }
            if(s==null) {
                System.out.println("Client has disconected from server.");
                break;
            }
            if(s.equals("exit")){
                System.out.println("Client set terminal command.");
                try{
                    socket.close();
                } catch(Exception ex){}
                break;
            }
            System.out.println(s);
        }
    }
}
