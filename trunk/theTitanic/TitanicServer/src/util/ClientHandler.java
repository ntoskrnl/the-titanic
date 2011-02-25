package util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

    public ClientHandlerRunnable(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        socket = clientHandler.getSocket();
        try{
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (Exception ex) {
            clientHandler.disconnect();
        }
    }

    public boolean authorize(){
        return true;
    }

    public void run(){
        String s;
        if(!authorize()){
            clientHandler.disconnect("Authorization problem.");
            return;
        }
        while(true){
            try{
                s = br.readLine();
            } catch (Exception ex){ s=null; }
            if(s==null) {
                System.out.println("Client has disconected from server.");
                break;
            }
            if(s.equals("exit")){
                System.out.println("Client sent terminal command.");
                try{
                    socket.close();
                } catch(Exception ex){}
                break;
            }
            System.out.println(s);
        }
        clientHandler.disconnect();
    }
}
