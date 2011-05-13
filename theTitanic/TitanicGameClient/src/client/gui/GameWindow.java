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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import titanic.basic.Game;

/**
 * Window with GameScene and several control buttons.
 * @author danon
 */
public class GameWindow extends javax.swing.JFrame {

    /** Creates new form GameWindow */
    public GameWindow(UserProfile rvl, boolean f) {
        initComponents();
        first = f;
        rival = rvl;
        try {
            if (!Main.checkMemory(10 * 1024 * 1024)) {
                JOptionPane.showMessageDialog(rootPane, java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("too_few_free_memory"),
                        "Titanic GameClient: Warning", JOptionPane.WARNING_MESSAGE);
            } else if (!Main.checkMemory(8 * 1024 * 1024)) {
                throw new ExceptionInInitializerError(java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("out_of_memory"));
            }

            setTitle(getTitle() + java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("You_vs") + rvl.getProperty("pub_nickname") + "]");
            initGame();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("failed_to_create_game") + ex.getMessage() 
                    + java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("n_recommend_to_close"),
                    "Titanic GameClient: Error", JOptionPane.ERROR_MESSAGE);
            closeWindowLater();
        }
        if (splash != null) {
            splash.setVisible(false);
        }
        
        awtEventListener = new GameAWTEventListener(this, game);
        Toolkit.getDefaultToolkit().addAWTEventListener(awtEventListener,
                AWTEvent.KEY_EVENT_MASK | AWTEvent.WINDOW_EVENT_MASK | AWTEvent.ACTION_EVENT_MASK);
        GUIRoutines.toScreenCenter(this);
        
        timer = new javax.swing.Timer(1000, new ActionListener() {

            private String convertStatus(int s){
                if(s==Game.S_NONE) return java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("not_started");
                if(s==Game.S_FINISH) return java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("finished");
                if(s==Game.S_MAKE_HIT) return java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("making_hit_blue");
                if(s==Game.S_MOVING) return java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("balls_moving");
                if(s==Game.S_PAUSE) return java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("pause");
                if(s==Game.S_WAIT_RIVAL) return java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("waiting_red");
                if(s==Game.S_SYNC) return java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("synch");
                if(s==Game.S_BALL_SELECT) return java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("ball_select_green");
                return "?";
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                if(game.initialized()&&!game.checkValid()){
                    game.changeStatus(Game.S_FINISH);
                    game.stop();
                    JOptionPane.showMessageDialog(rootPane, java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("game_was_finished"), 
                            "Error", JOptionPane.INFORMATION_MESSAGE);
                    game.dispose();
                    return;
                }
                int r = game.getRivalStatus();
                int s = game.getGameStatus();
                jLabel4.setText("<html>"+game.getMyScore()+" "+convertStatus(s)+"</html>");
                jLabel5.setText("<html>"+game.getRivalScore()+" "+convertStatus(r)+"</html>");
                if(game.getMyScore() + game.getRivalScore()>14){
                    if(game.getMyScore()>game.getRivalScore())
                        JOptionPane.showMessageDialog(rootPane, java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("congratulations_win"),
                                java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("you_win")+game.getMyScore()+":"+game.getRivalScore(), JOptionPane.INFORMATION_MESSAGE);
                    else 
                        JOptionPane.showMessageDialog(rootPane, java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("sorry_loose"),
                                java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("you_loose")+game.getMyScore()+":"+game.getRivalScore(), JOptionPane.INFORMATION_MESSAGE);
                    game.changeStatus(Game.S_FINISH);
                    game.dispose();
                    closeWindowLater();
                    return;
                }
                timer.start();
            }
        });
        timer.start();
    }

    public final void initGame() {
        game = new SimpleGame(gameScenePanel, first, rival);
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
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
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

        jLabel1.setText(bundle.getString("GameWindow.jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jButton1.setText(bundle.getString("GameWindow.jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText(bundle.getString("GameWindow.jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        jLabel3.setText(bundle.getString("GameWindow.jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel4.setText(bundle.getString("GameWindow.jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 12));
        jLabel5.setText(bundle.getString("GameWindow.jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        jLabel6.setText(bundle.getString("GameWindow.jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        javax.swing.GroupLayout buttonPanelLayout = new javax.swing.GroupLayout(buttonPanel);
        buttonPanel.setLayout(buttonPanelLayout);
        buttonPanelLayout.setHorizontalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                    .addGroup(buttonPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4))
                    .addGroup(buttonPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel5))
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        buttonPanelLayout.setVerticalGroup(
            buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(buttonPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(buttonPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 129, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
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

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (game != null) {
            game.dispose();
            System.gc();
        }
        timer.stop();
        Toolkit.getDefaultToolkit().removeAWTEventListener(awtEventListener);
    }//GEN-LAST:event_formWindowClosing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        game.dispose();
        game = null;
        timer.stop();
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    // End of variables declaration//GEN-END:variables
    private SimpleGame game;
    private Timer timer;
    private UserProfile rival;
    //private boolean blankCycle = true;
    private boolean first;
    
    public static javax.swing.JDialog splash;
    private AWTEventListener awtEventListener;
}


class GameAWTEventListener implements AWTEventListener {

    public GameAWTEventListener(GameWindow w, Game g) {
        game = g; 
        window = w;
    }

    
    
    @Override
    public void eventDispatched(AWTEvent event) {
        if(game==null || window == null) return;
        if(!window.isActive()) return;
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
    private GameWindow window;
}