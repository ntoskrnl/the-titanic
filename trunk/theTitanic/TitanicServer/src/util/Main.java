package util;

import gui.ServerConfigWindow;
import java.net.*;
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

    static ConnectionContainer connections;
    /**
     * ServerSocket to accept incoming connections
     */
    static ServerSocket server;

    public static  DataBaseAccess usersDB = null;
    public static Log logs = null;
    public static CommandInterpreter cmd = null;


    /**
     * Program entry point
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        logs = new Log();
        logs.info("Starting game server");
        
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
            Main.logs.warning("Could not find "+l);
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
