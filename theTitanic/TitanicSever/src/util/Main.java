package util;

import gui.ServerConfigWindow;
import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * TheTitanic Server Side Main Class
 * @author DANON [Anton Danshin]
 */
public class Main {
    
    private static ServerConfigWindow configWindow = null;
    private static Thread mainServerThread = null;
    /**
     * Program entry point
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                configWindow = new ServerConfigWindow();
                configWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                configWindow.setVisible(true);
            }
        });
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

    /**
     * Default port for incoming connections
     */
    static final int DEFAULT_PORT = 10000;
    
    public void run(){
        startServer();
    }

    public static void startServer(){
        System.out.println("Starting the Titanic game server...");
        connections = new ConnectionContainer();
        try{
            server = new ServerSocket(DEFAULT_PORT);
            System.out.println("Waiting for incoming connections...");
            while(true){
                if(Thread.currentThread().isInterrupted()) break;
                Socket newClient = server.accept();
                if(Thread.currentThread().isInterrupted()) break;
                connections.add(newClient);
                System.out.println(System.currentTimeMillis()+"\tNew client has just connected to the server!");
            }
        }catch(Exception ex){
            //Error
            System.out.println("Error!");
            System.err.println(ex);
        }
        try {
            server.close();
        } catch (IOException ex) {
            Logger.getLogger(MainServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
