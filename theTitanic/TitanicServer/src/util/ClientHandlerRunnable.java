/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author danon
 */
public class ClientHandlerRunnable implements Runnable {
    private Socket socket;
    private ClientHandler clientHandler;
    private BufferedReader br;
    private PrintWriter pw;

    public ClientHandlerRunnable(ClientHandler clientHandler) {
        this.clientHandler = clientHandler;
        socket = clientHandler.getSocket();
        pw = clientHandler.getSocketWriter();
        br = clientHandler.getSocketReader();
    }


    public void run(){
        String s;
        while(true){
            try{
                s = br.readLine();
            } catch (Exception ex){ s=null; }
            if(s==null) {
                System.out.println("Client has disconected from server.");
                break;
            }
            Main.cmd.processCommand(s, clientHandler.getUser());
        }
        clientHandler.disconnect();
    }

}