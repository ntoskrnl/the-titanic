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
    static ConnectionContainer connections;
    /**
     * ServerSocket to accept incoming connections
     */
    static ServerSocket server;

    public void run(){
        startServer();
    }

    public static void startServer(){
        Main.logs.info("Starting the Titanic game server on port "+ServerConfiguration.serverPort+"...");

        if(ServerConfiguration.proxyType==2||ServerConfiguration.proxyType==1){
            System.setProperty("socksProxyHost",ServerConfiguration.proxyHost);
            System.setProperty("socksProxyPort",""+ServerConfiguration.proxyPort);
            Main.logs.info("Using proxy server: "+ServerConfiguration.proxyHost+":"+ServerConfiguration.proxyPort);
        }

        try{
            Main.logs.info("Connecting to databases...");

            Main.usersDB = new DataBaseAccess("titanic_users.db");
            Main.usersDB.doUpdate("DELETE FROM online_users WHERE 1=1");
            
            javax.swing.Timer commitTimer = new javax.swing.Timer(10000, new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    try{
                        Main.usersDB.reconnect();
                        Main.logs.info("DB: reconnect");
                    } catch(Exception ex){
                        Main.logs.warning("commitTimer: "+ex.getMessage());
                    }
                }
            });
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
    }

}

