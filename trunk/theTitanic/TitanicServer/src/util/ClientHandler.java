package util;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.UUID;


/**
 * Client-Server Collaboration
 * @author danon [Anton Danshin]
 */
public class ClientHandler implements Comparable<ClientHandler>{
    @SuppressWarnings("CallToThreadStartDuringObjectConstruction")

    private Socket socket = null;
    private static int _id=0; // id counter
    private int id; // internal user id (for server only)
    private ConnectionContainer container = null;
    private User user;

    private BufferedReader br;
    private PrintWriter pw;

    public ClientHandler(ConnectionContainer container, Socket s) throws IOException{
        socket = s;
        id=_id++;
        this.container = container;
        user = new User(this);

        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pw = new PrintWriter(socket.getOutputStream());

        new Thread(new ClientHandlerRunnable(this)).start();
    }

    public void disconnect(){
        container.remove(this);
        String sql = "DELETE FROM online_users WHERE user_id = "+user.getId();
        System.out.println(sql);
        Main.usersDB.doUpdate(sql);
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

    public User getUser(){
        return user;
    }

    public BufferedReader getSocketReader(){
        return br;
    }

    public PrintWriter getSocketWriter(){
        return pw;
    }

    public int compareTo(ClientHandler o) {
        if(this.getId()>o.getId()) return 1;
        if(this.getId()<o.getId()) return -1;
        return 0;
    }
    
}

