package util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Date;

/**
 *
 * @author danon
 */
public class MainServerThread implements Runnable {
    /**
     * Contains all currently active connections
     */
    public static ConnectionContainer connections;
    
    public static RequestContainer requests;
    
    public static  GameContainer games;
    /**
     * ServerSocket to accept incoming connections
     */
    static ServerSocket server;

    static String db_file;

    public void run(){
        startServer();
    }

    public static void startServer(){
        javax.swing.Timer commitTimer = new javax.swing.Timer(10000, new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    try{
                        if(Main.usersDB.modified())
                            Main.usersDB.reconnect();
                    } catch(Exception ex){
                        Main.logs.warning("commitTimer: "+ex.getMessage());
                    }
                }
            });

        Main.logs.info("Starting the Titanic game server on port "+ServerConfiguration.serverPort+"...");

        if(ServerConfiguration.proxyType==2||ServerConfiguration.proxyType==1){
            System.setProperty("socksProxyHost",ServerConfiguration.proxyHost);
            System.setProperty("socksProxyPort",""+ServerConfiguration.proxyPort);
            Main.logs.info("Using proxy server: "+ServerConfiguration.proxyHost+":"+ServerConfiguration.proxyPort);
        }

        requests = new RequestContainer();
        games = new GameContainer();
        
        try{
            Main.logs.info("Connecting to databases...");

            Main.usersDB = new DataBaseAccess(ServerConfiguration.user_db_file);
            Main.usersDB.doUpdate("DELETE FROM online_users WHERE 1=1");

            if(!Main.usersDB.isConnected()){
                throw new Exception("Cannot connect to Sqlite database: "+ServerConfiguration.user_db_file);
            }
            
            commitTimer.start();

            connections = new ConnectionContainer();

            server = new ServerSocket(ServerConfiguration.serverPort);
            
            server.setSoTimeout(1000);

            Main.cmd = new CommandInterpreter();

            Main.logs.info("Waiting for incoming connections...");
            while(true){
                Socket newClient = null;
                try{
                    newClient = server.accept();
                } catch(SocketTimeoutException ex){
                   if(Thread.currentThread().isInterrupted()) break;
                   else continue;
                }
                
                String ip = newClient.getInetAddress().getHostAddress();
                connections.add(newClient);
                String now = new Date().toString();
                Main.logs.info(now+"\tNew client "+ip+" has just connected to the server!");
            }
        }catch(Exception ex){
            //Error
            Main.logs.warning(ex.getMessage());
        }

        try {
            if(server!=null) server.close();
            Main.usersDB.doUpdate("DELETE FROM online_users WHERE 1=1");
            Main.usersDB.close();
        } catch (IOException ex) {
            Main.logs.warning(ex.getMessage());
        }
        Main.logs.info("Server stopped.");
        commitTimer.stop();
    }

}

