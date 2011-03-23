package util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        System.out.println("Starting the Titanic game server on port "+ServerConfiguration.serverPort+"...");

        if(ServerConfiguration.proxyType==2||ServerConfiguration.proxyType==1){
            System.setProperty("socksProxyHost",ServerConfiguration.proxyHost);
            System.setProperty("socksProxyPort",""+ServerConfiguration.proxyPort);
            System.out.println("Using proxy server: "+ServerConfiguration.proxyHost+":"+ServerConfiguration.proxyPort);
        }

        try{
            System.out.println("Connecting to databases...");

            Main.usersDB = new DataBaseAccess("titanic_users.db");

            connections = new ConnectionContainer();

            server = new ServerSocket(ServerConfiguration.serverPort);
            server.setSoTimeout(1000);

            Main.cmd = new ServerCommandProcessor();

            System.out.println("Waiting for incoming connections...");
            while(true){
                Socket newClient = null;
                try{
                    newClient = server.accept();
                } catch(SocketTimeoutException ex){
                   if(Thread.currentThread().isInterrupted()) break;
                   else continue;
                }

                connections.add(newClient);
                System.out.println(System.currentTimeMillis()+"\tNew client has just connected to the server!");
            }
        }catch(Exception ex){
            //Error
            System.err.println("Error!");
            System.err.println(ex);
        }

        try {
            server.close();
            Main.usersDB.doUpdate("DELETE FROM online_users WHERE 1=1");
            Main.usersDB.close();
        } catch (IOException ex) {
            Logger.getLogger(MainServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Server stopped.");
    }

}

