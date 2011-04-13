package client;

import client.gui.ClientLoginWindow;
import client.util.TitanicServer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 * Client application for theTitanicServer
 * @author danon
 */
public class Main {

    public static TitanicServer server = null;
    public static JFrame loginWindow = null;

    public static final long minMemorySize = 32*1024*1024;


    /**
     * Program entry point
     * @param args Command line arguments. Ignored for now.
     */
    public static void main(String[] args) {
        System.out.println("Starting Titanic GameClient version 1.0b");

        if (!checkSystem()) return;

        try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch(Exception ex){
            System.err.println("Failed to apply LookAndFeel theme.");
        }
        
        loginWindow = new ClientLoginWindow();
        loginWindow.setVisible(true);
    }

    /**
     * Check that system is able to run the program.
     * @return true - all is ok, false - cannot run a program.
     */
    public static boolean checkSystem(){
        System.out.print("Checking system...  ");
        // Checking memory
        long heapMaxSize = Runtime.getRuntime().maxMemory();
        if (heapMaxSize<minMemorySize) {
            System.err.println(minMemorySize/1024/1024+" MB of memory is required, but current max heap size is "+heapMaxSize/1024/1024+"MB.");
            JOptionPane.showMessageDialog(null, minMemorySize/1024/1024+" MB of memory is required, but current max heap size is "+
                    heapMaxSize/1024/1024+"MB.", "Titanic GameClient: Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        System.out.println("ok");
        return true;
    }

}
