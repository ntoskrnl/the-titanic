/*
 * GameWindow.java
 *
 * Created on Mar 13, 2011, 10:57:49 PM
 */
package client.gui;

import client.Main;
import client.util.GUIRoutines;
import client.util.SimpleGame;
import client.util.UserProfile;
import java.awt.AWTEvent;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Timer;
import javax.swing.JOptionPane;
import titanic.basic.Game;

/**
 * Window with GameScene and several control buttons.
 * @author danon
 */
public class GameWindow extends javax.swing.JFrame {

    /** Creates new form GameWindow */
    public GameWindow(UserProfile rvl, boolean first) {
        initComponents();
        try {
            if (!Main.checkMemory(10 * 1024 * 1024)) {
                JOptionPane.showMessageDialog(rootPane, "Too few free memory! Game may bevave abnormally.",
                        "Titanic GameClient: Warning", JOptionPane.WARNING_MESSAGE);
            } else if (!Main.checkMemory(8 * 1024 * 1024)) {
                throw new ExceptionInInitializerError("Out of memory.");
            }

            setTitle(getTitle() + " [You vs. " + rvl.getProperty("pub_nickname") + "]");
            initGame();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, "Failed to create a game instance!\nIt is trongly recommended to close all games and relogin. If problem repeats, restart the application.",
                    "Titanic GameClient: Error", JOptionPane.ERROR_MESSAGE);
            closeWindowLater();
        }
        if (splash != null) {
            splash.setVisible(false);
        }
        GUIRoutines.toScreenCenter(this);

        Toolkit.getDefaultToolkit().addAWTEventListener(new GameAWTEventListener(game),
                AWTEvent.KEY_EVENT_MASK | AWTEvent.WINDOW_EVENT_MASK | AWTEvent.ACTION_EVENT_MASK);
    }

    public final void initGame() {
        game = new SimpleGame(gameScenePanel);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        gamePanel = new javax.swing.JPanel();
        gameScenePanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("client/gui/Bundle"); // NOI18N
        setTitle(bundle.getString("GameWindow.title")); // NOI18N
        setAlwaysOnTop(true);
        setName("Form"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        buttonPanel.setName("buttonPanel"); // NOI18N
        buttonPanel.setPreferredSize(new java.awt.Dimension(180, 326));

        jButton1.setText(bundle.getString("GameWindow.jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText(bundle.getString("GameWindow.jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addContainerGap(355, Short.MAX_VALUE))
        );

        getContentPane().add(buttonPanel, java.awt.BorderLayout.LINE_END);

        gamePanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        gamePanel.setName("gamePanel"); // NOI18N
        gamePanel.setLayout(new java.awt.BorderLayout());

        gameScenePanel.setBackground(new java.awt.Color(0, 0, 0));
        gameScenePanel.setName("gameScenePanel"); // NOI18N

        javax.swing.GroupLayout gameScenePanelLayout = new javax.swing.GroupLayout(gameScenePanel);
        gameScenePanel.setLayout(gameScenePanelLayout);
        gameScenePanelLayout.setHorizontalGroup(
            gameScenePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 416, Short.MAX_VALUE)
        );
        gameScenePanelLayout.setVerticalGroup(
            gameScenePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 409, Short.MAX_VALUE)
        );

        gamePanel.add(gameScenePanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(gamePanel, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (game != null) {
            game.start();
        }
        gameScenePanel.requestFocus();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (game != null) {
            game.stop();
        }
        gameScenePanel.requestFocus();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (game != null) {
            game.dispose();
            System.gc();
        }
    }//GEN-LAST:event_formWindowClosing

    private void closeWindowLater() {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                setVisible(false);
                dispose();
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel gamePanel;
    private javax.swing.JPanel gameScenePanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    // End of variables declaration//GEN-END:variables
    private SimpleGame game;
    private Timer timer;
    private UserProfile rival;
    private boolean blankCycle = true;
    public static javax.swing.JDialog splash;
}


class GameAWTEventListener implements AWTEventListener {

    public GameAWTEventListener(Game g) {
        game = g; 
    }

    
    
    @Override
    public void eventDispatched(AWTEvent event) {
        // KeyEvents
        if(event instanceof KeyEvent)
            processKeyEvent((KeyEvent)event);
        // Window Events
        if(event instanceof WindowEvent)
            processWindowEvent((WindowEvent)event);
        
    }
    
    private void processKeyEvent(KeyEvent evt){
        game.getGraphicalEngine().processKeyEvent(evt);
    }
    
    private void processWindowEvent(WindowEvent evt){
        
    }
    
    private Game game;
}