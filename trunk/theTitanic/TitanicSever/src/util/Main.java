package util;

import gui.ServerConfigWindow;
import java.net.*;
import javax.swing.JFrame;

/**
 * TheTitanic Server Side Main Class
 * @author DANON [Anton Danshin]
 */
public class Main {
    
    private static ServerConfigWindow configWindow = null;
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
        Thread t = new Thread(new MainServerThread());
        t.start();
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
                Socket newClient = server.accept();
                connections.add(newClient);
                System.out.println(System.currentTimeMillis()+"\tNew client has just connected to the server!");
            }
        }catch(Exception ex){
            //Error
            System.out.println("Error!");
            System.err.println(ex);
        }
    }

}
