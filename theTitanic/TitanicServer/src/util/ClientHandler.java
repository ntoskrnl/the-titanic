package util;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


/**
 * Client-Server Collaboration
 * @author danon [Anton Danshin]
 */
public class ClientHandler implements Comparable<ClientHandler>{
    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")

    private Socket socket = null;
    private static int _id=0;
    private int id;
    private ConnectionContainer container = null;

    public ClientHandler(ConnectionContainer container, Socket s) {
        socket = s;
        id=_id++;
        this.container = container;
        new Thread(new ClientHandlerRunnable(this)).start();
    }

    public void disconnect(){
        container.remove(this);
        System.out.println(container.getCount()+" clients are still here!");
    }

    public void disconnect(String msg){
        disconnect();
        System.out.println(msg);
    }

    public Socket getSocket(){
        return socket;
    }

    public int getId(){
        return id;
    }

    public int compareTo(ClientHandler o) {
        if(this.getId()>o.getId()) return 1;
        if(this.getId()<o.getId()) return -1;
        return 0;
    }
    
}

class ClientHandlerRunnable implements Runnable {
    private Socket socket;
    private ClientHandler clientHandler;
    private BufferedReader br;
    private PrintWriter pw;

    public ClientHandlerRunnable(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        socket = clientHandler.getSocket();
        try{
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream());
        } catch (Exception ex) {
            clientHandler.disconnect();
        }
    }

    public boolean authorize(String login, String password){
        return true;
    }

    public void run(){
        String s;
        while(true){
            try{
                s = br.readLine();
            } catch (Exception ex){ s=null; }
            if(s==null) {
                System.out.println("Client has disconected from server.");
                break;
            }
            processCommand(s);
        }
        clientHandler.disconnect();
    }

    private void processCommand(String command){
        if(command==null) return;
        boolean result = false;
        String cmd = command.toLowerCase().trim();
        try{
            // Connection stop
            if(cmd.equals("exit")){
                System.out.println("Client sent terminal command.");
                try{
                    socket.close();
                    return;
                } catch(Exception ex){}
            }
            // Authentication
            if(cmd.equals("authorize")){
                String login = br.readLine();
                String pwd = br.readLine();
                if(authorize(login, pwd)){
                    pw.println("SUCCESS");
                    pw.println("secret");
                    pw.println();
                    result = true;
                }
            }

            // User List Request (secret required)
            if(cmd.equals("list users")){
                String which = br.readLine();
                String secret = br.readLine();
                if(which.trim().toLowerCase().equals("online")){
                    pw.println("SUCCESS");
                    pw.println("user1");
                    pw.println("user2");
                    pw.println("user3");
                    pw.println();
                    result = true;
                }
            }

            if(!result) pw.println("FAIL");

            // send buffered data to the client
            pw.flush();
        } catch(Exception ex) {
            System.err.println("COMMAND: "+ex.getLocalizedMessage());
        }
    }
}
