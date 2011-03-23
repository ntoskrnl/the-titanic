package client.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.ArrayList;

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
    private boolean connected = false;
    public String secret = null;

    public TitanicServer() {
        host = "danon-laptop.campus.mipt.ru";
        port = 10000;
        connect();
    }

    public final void connect(){
        try{
            socket = new Socket(host, port);
            socket.setSoTimeout(3000);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            pw = new PrintWriter(socket.getOutputStream());
        } catch (Exception ex){
            System.err.println("CONNECT: "+ex.getLocalizedMessage());
            connected = false;
        }
    }

    public boolean isConnected(){
        if(socket==null) return false;
        return connected && socket.isConnected();
    }

    /**
     * Authentication method.
     * @return TRUE if authentication performed, and FALSE otherwise.
     */
    public boolean authorize(String login, String password){
        status = "Authentication";
        if(socket==null||!socket.isConnected()){
            System.err.println("AUTH: Not connected.");
            disconnect();
            return false;
        }
        try{
            command("authorize",login, Crypto.md5(password));
            String[] res = getResponse();
            if(!res[0].equals("SUCCESS")) {
                disconnect();
                return false;
            }
            secret = res[1];
            if(secret!=null) secret = secret.trim();
        } catch (Exception ex){
            status="WAITING";
            System.err.println("AUTHOSIZE: "+ex.getLocalizedMessage());
            disconnect();
            return false;
        }
        connected = true;
        return true;
    }

    public void command(String command, String... args){
        String pstatus = status;
        String cmd = command.trim().toUpperCase();
        status = cmd;
        pw.println(cmd);
        for(String arg : args){
            if(arg!=null) pw.println(arg.trim());
            else pw.println();
        }
        pw.flush();
        status = pstatus;
    }

    public String[] getResponse(){
        ArrayList<String> res = new ArrayList<String>();
        try{
            res.add(br.readLine());
            if(res.get(0)==null || !res.get(0).equals("SUCCESS")) 
                return (String[])res.toArray();
        
            String line;
            while(!(line=br.readLine()).equals(""))
                res.add(line);

        } catch(Exception ex){
            System.err.println("getResponse: Connection problem or strange server response.");
            if(!res.isEmpty()) System.err.println(res.get(0));
        }

        if(res.isEmpty())
            res.add("FAIL");

        String[] r = new String[res.size()];
        res.toArray(r);
        return r;
    }

    public void disconnect(){
        try{
            if(pw!=null){
                pw.println("exit");
                pw.close();
            }
            if(br!=null) br.close();
            if(socket!=null)
                socket.close();
            socket = null;
            pw = null;
            br = null;
        } catch (Exception ex) {}
        connected = false;
    }

}
