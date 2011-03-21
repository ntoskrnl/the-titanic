package util;

import gui.ServerConfigWindow;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.UIManager;
import java.util.prefs.Preferences;

/**
 * TheTitanic Server Side Main Class
 * @author DANON [Anton Danshin]
 */
public class Main {

    private static ServerConfigWindow configWindow = null;
    private static Thread mainServerThread = null;
    private static Preferences userPreferences = null;

    static ConnectionContainer connections;
    /**
     * ServerSocket to accept incoming connections
     */
    static ServerSocket server;

    public static DataBaseAccess usersDB = null;
    public static DataBaseAccess serviceDB = null;


    /**
     * Program entry point
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ServerConfiguration.loadPreferences();
        applyLookAndFeel(ServerConfiguration.lookAndFeel);

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                configWindow = new ServerConfigWindow();
                configWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                configWindow.setVisible(true);
            }
        });
    }

    public static void applyLookAndFeel(String l){
        try {
            UIManager.setLookAndFeel(l);
        } catch (Exception ex){
            System.err.println("Could not find "+l);
        }
    }

    public static void startServer(){
        mainServerThread = new Thread(new MainServerThread());
        mainServerThread.start();
    }

    public static Thread getMainServerThread(){
        return mainServerThread;
    }

}

class MainServerThread implements Runnable {
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
            Main.usersDB.close();
        } catch (IOException ex) {
            Logger.getLogger(MainServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Server stopped.");
    }

}