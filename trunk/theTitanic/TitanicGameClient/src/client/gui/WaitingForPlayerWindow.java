/*
 * WaitingForPlayerWindow.java
 *
 * Created on 02.05.2011, 19:26:28
 */

package client.gui;

import client.Main;
import client.util.UserProfile;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * @author Danon
 */
public class WaitingForPlayerWindow extends javax.swing.JFrame {

    /**
     * Default time limit in seconds to wait for response.
     */
    public static final int TIME_LIMIT = 20;
    
    /** Creates new form WaitingForPlayerWindow */
    public WaitingForPlayerWindow(UserProfile rvl, MainWindow mw) {
        initComponents();
        setLocationRelativeTo(mw);
        
        count = TIME_LIMIT;
        mainWindow = mw;
        rival = rvl;

        if(rival==null){
            rival = new UserProfile(0);
            rival.update();
        }

        jLabel1.setText(java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("request_was_sent")
                + rival.getProperty("pub_nickname") + java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("br_please_wait"));

        // This will provide a count down
        countDownTimer = new Timer(999, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(count>0){
                    count--;
                    jButton1.setText(java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("CANCEL") + " ("+count+")");
                } else {
                    closeMe();
                }
            }
        });
        
        // This will check the response
        responseCheckTimer = new Timer(999, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                responseCheckTimer.stop();
                boolean r = checkRequest();
                Boolean a = getResponse();
                if(a!=null) accepted = a;
                if(r && a!=null && a==true && mainWindow!=null) {
                    closeMe();
                    GameWindow.splash = new GameStartingSplashWindow(mainWindow, rival, true);
                    GameWindow.splash.setVisible(true);
                    return;
                }
                responseCheckTimer.start();
                if(!r || (a!=null && a==false)){
                    closeMe();
                }
            }
        });
        setResizable(false);
        
        responseCheckTimer.start();
        countDownTimer.start();
    }
    
    
    private Boolean getResponse(){
        try{  
            String[] r = Main.server.commandAndResponse(500, "IS GAME ACCEPTED", rival.getProperty("id"), Main.server.secret);
            if(r==null||r.length==0) return null;
            if(!r[0].toUpperCase().equals("SUCCESS")) return null;
            if(r[1].toUpperCase().equals("YES")) return true;
            else if (r[1].toUpperCase().equals("NO")) return false;
            else return null;
        } catch (Exception ex){
            return false;
        }
    }
    
    private boolean checkRequest(){
        UserProfile me = new UserProfile(0);
        me.update();
        String[] r = Main.server.commandAndResponse(500, "IS REQUEST VALID", me.getProperty("id"), rival.getProperty("id"), Main.server.secret);
        if(r[0].equalsIgnoreCase("SUCCESS")) return true;
        return false;
    }
    
    private void cancelRequest(){
        Main.server.commandAndResponse(500, "CANCEL REQUEST", rival.getProperty("id"), Main.server.secret);
    }
    
    private void closeMe(){
        countDownTimer.stop();
        responseCheckTimer.stop();
        dispose();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Waiting for user response...");
        setAlwaysOnTop(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jLabel1.setText("<html>You are about to play with your opponent. <br>Now you need to receive their response.</html>");

        jButton1.setText("Cancel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Reset timer");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cancelRequest();
        closeMe();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        count = TIME_LIMIT;
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        countDownTimer.stop();
        responseCheckTimer.stop();
        UserProfile me = new UserProfile(0);
        me.update();
        if(!accepted)
            cancelRequest();
    }//GEN-LAST:event_formWindowClosed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

    private int count;
    private Timer countDownTimer, responseCheckTimer;
    private MainWindow mainWindow;
    private UserProfile rival;
    private boolean accepted = false;
}
