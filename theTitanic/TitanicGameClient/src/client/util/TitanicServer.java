package client.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * This class is used for communication with Server
 * @author danon
 */
public class TitanicServer {
    public String status = "none";
    public String host;
    public int port;

    public static BufferedReader br;
    public static PrintWriter pw;
    private Socket socket;

    public TitanicServer() {
        host = "danon-laptop.campus.mipt.ru";
        port = 10000;
        try{
            socket = new Socket(host, port);
            socket.setSoTimeout(3000);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream());
        } catch (Exception ex){
            System.err.println("CONNECT: "+ex.getLocalizedMessage());
        }
    }

    

    /**
     * Authentication method.
     * @return TRUE if authentication performed, and FALSE otherwise.
     */
    public boolean authorize(String login, String password){
        status = "Authentication";
        if(socket==null||!socket.isConnected()){
            System.err.println("AUTH: Not connected.");
            return false;
        }
        try{
           command("authorize",login, password);
           String res = br.readLine(); 
           if(res==null||!res.equals("SUCCESS")) return false;
           String secret = br.readLine();
           br.readLine();
        } catch (Exception ex){
            status="WAITING";
            System.err.println("AUTHOSIZE: "+ex.getLocalizedMessage());
            return false;
        }
        return true;
    }

    public void command(String command, String... args){
        String pstatus = status;
        String cmd = command.trim().toUpperCase();
        status = cmd;
        pw.println(cmd);
        for(String arg : args){
            pw.println(arg.trim());
        }
        pw.flush();
        status = pstatus;
    }

}
