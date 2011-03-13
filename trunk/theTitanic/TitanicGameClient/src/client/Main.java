package client;

import client.gui.ClientLoginWindow;
import client.util.TitanicServer;
import javax.swing.UIManager;

/**
 * Client application for theTitanicServer
 * @author danon
 */
public class Main {

    public static TitanicServer server = null;

    public static void main(String[] args) {
        System.out.println("");
        try{
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch(Exception ex){
            System.err.println("Failed to apply LookAndFeel theme.");
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientLoginWindow().setVisible(true);
            }
        }
        );
    }

}
