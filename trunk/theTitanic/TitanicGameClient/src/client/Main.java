package client;

import client.gui.ClientLoginWindow;
import client.util.GUIRoutines;
import client.util.TitanicServer;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
        
        // Applying look and feel
        String look = "Nimbus";
        boolean l = GUIRoutines.tryLookAndFeel(look);
        if(!l) l = GUIRoutines.tryLookAndFeel("Nimbus");
        if(!l) l = GUIRoutines.tryLookAndFeel("Windows");
        if(!l) l = GUIRoutines.tryLookAndFeel("GTK+");
        if(!l) GUIRoutines.tryLookAndFeel("Metal");
        
        // Starting gc thread
        new Thread(new autoGC()).start();
        
        // Create and show login window
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
        if (heapMaxSize+2*1024*1024<minMemorySize) {
            System.err.println(minMemorySize/1024/1024+" MB of memory is required, but current max heap size is "+heapMaxSize/1024/1024+"MB.");
            JOptionPane.showMessageDialog(null, minMemorySize/1024/1024+" MB of memory is required, but current max heap size is "+
                    heapMaxSize/1024/1024+"MB.", "Titanic GameClient: Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        System.out.println("ok");
        return true;
    }

    /**
     * Finds out is there enough memory to run.
     * @param limit Memory limit in bytes
     * @return true if there is >= <code>limit</code> of memory available, otherwise - false.
     */
    public static boolean checkMemory(long limit){
        System.gc();
        long memAvailable = Runtime.getRuntime().maxMemory()-Runtime.getRuntime().totalMemory()+Runtime.getRuntime().freeMemory();
        return limit<=memAvailable;
    }

}

/**
 * Garbage collection thread.
 * @author danon
 */
class autoGC implements Runnable {

    @Override
    public void run() {
        //Mimimum acceptable free memory you think your app needs
        long minRunningMemory = (12*1024*1024);
        long interval = 3000;

        while(!Thread.currentThread().isInterrupted()){
            Runtime runtime = Runtime.getRuntime();
            if(runtime.freeMemory()<minRunningMemory){
                System.out.print("Free memory: "+runtime.freeMemory()/1024/1024+"Mb... ");
                runtime.runFinalization();
                System.gc();
                System.out.println("gc");
                interval = 10000;
            } else {
                System.out.println();
                interval = 3000;
            }
            
            try{
                Thread.sleep(interval);
            } catch(InterruptedException ex){
                break;
            }
        }
    }

}