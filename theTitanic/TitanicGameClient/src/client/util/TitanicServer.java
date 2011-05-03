package client.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
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
    
    public static MeasuringInputStream in;
    public static MeasuringOutputStream out;
    
    public static final int DEFAULT_TIME_OUT = 10000;

    public TitanicServer() {
        host = "danon-laptop.campus.mipt.ru";
        port = 10000;
        connect();
    }

    public synchronized final void connect(){
        try{
            socket = new Socket(host, port);
            socket.setTcpNoDelay(true);
            socket.setSoTimeout(DEFAULT_TIME_OUT);
            
            in = new MeasuringInputStream(socket.getInputStream());
            out = new MeasuringOutputStream(socket.getOutputStream());
            br = new BufferedReader(new InputStreamReader(in));
            pw = new PrintWriter(out);
            connected = true;
        } catch (Exception ex){
            System.err.println("CONNECT: "+ex.getLocalizedMessage());
            connected = false;
        }
    }

    public synchronized boolean isConnected(){
        if(socket==null) return false;
        return connected && socket.isConnected();
    }

    /**
     * Authentication method.
     * @return TRUE if authentication performed, and FALSE otherwise.
     */
    public synchronized boolean authorize(String login, String password){
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

    private void command(String command, String... args){
        String pstatus = status;
        String cmd = command.trim().toUpperCase();
        status = cmd;
        pw.println(cmd);
        for(String arg : args){
            if(arg!=null) pw.println(arg.trim());
            else pw.println();
        }
        pw.flush();
        System.out.println(cmd);
        status = pstatus;
    }

    private String[] getResponse(){
        ArrayList<String> res = new ArrayList<String>();
        try{
            String line = br.readLine();
            if(line==null) line = "FAIL";
            res.add(line);
           
            if(!res.get(0).equals("SUCCESS")) {
                String[] r = new String[res.size()];
                res.toArray(r);
                return r;
            }
            
            while(!(line=br.readLine()).equals(""))
                res.add(line);

        } catch(Exception ex){
            System.err.println("getResponse: Connection problem or strange server response."+ex.getLocalizedMessage());
            if(!res.isEmpty()) System.err.println(res.get(0));
        }

        if(res.isEmpty())
            res.add("FAIL");

        String[] r = new String[res.size()];
        res.toArray(r);
        return r;
    }
    
    public synchronized String[] commandAndResponse(String command, String... args){
        command(command, args);
        String[] res = getResponse();
        if(res==null){
            res = new String[1];
            res[0]="FAIL";
        }
        return res;
    }
    
    public synchronized String[] commandAndResponse(int timeLimit, String command, String... args){
        int pt = DEFAULT_TIME_OUT;
        try{
            pt = socket.getSoTimeout();
            socket.setSoTimeout(timeLimit);
        } catch (Exception ex){}
        String[] res = null;
        try{
            command(command, args);
            res = getResponse();
            socket.setSoTimeout(pt);
        } catch (Exception ex){}
        if(res==null){
                res = new String[1];
                res[0]="FAIL";
        }
        return res;
    }

    public synchronized void disconnect(){
        try{
            if(br!=null) br.close();
            try{
                if(pw!=null){
                    pw.println("exit");
                    pw.close();
                }
            } catch(Exception e) {}
            if(socket!=null)
                socket.close();
            
        } catch (Exception ex) {}
        connected = false;
        socket = null;
        pw = null;
        br = null;
    }

}
