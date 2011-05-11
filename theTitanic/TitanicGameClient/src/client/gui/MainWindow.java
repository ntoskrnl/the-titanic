/*
 * MainWindow.java
 *
 * Created on Mar 13, 2011, 8:28:00 PM
 */

package client.gui;

import client.util.GUIRoutines;
import client.Main;
import client.util.TitanicServer;
import client.util.UserProfile;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

/**
 *
 * @author danon
 */
public class MainWindow extends javax.swing.JFrame {

    /** Creates new form MainWindow */
    public MainWindow() {
        initComponents();
        myProfile = new UserProfile(0);
        myProfile.update();

        checkConnectionTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkConnection();
            }
        });
        userUpdateTimer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updateUserList(e);
            }
        });
        
        trafficCheckTimer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                jLabel1.setText("Traffic: "+TitanicServer.in.readBytesCount()/1024 + " K / "
                        + TitanicServer.out.writtenBytesCount()/1024 + " K");
            }
        });
        
        checkRequestsTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] r = Main.server.commandAndResponse(100, "GET GAME REQUEST", Main.server.secret);
                if(!r[0].equalsIgnoreCase("SUCCESS")) return;
                int id = Integer.parseInt(r[1]);
                acceptRequest(id);
            }
        });
        
        trafficCheckTimer.start();
        userUpdateTimer.start();
        checkConnectionTimer.start();
        checkRequestsTimer.start();
        
        GUIRoutines.toScreenCenter(this);
    }

    private void acceptRequest(int id){
        UserProfile u = new UserProfile(id);
        u.update();
        if(u.equals(myProfile)){
            // Automatically agree to play
            Main.server.commandAndResponse(100, "ACCEPT GAME", u.getProperty("id"), Main.server.secret);
            return;
        }
        new GameRequestWindow(this, u).setVisible(true);
    }
    
    private void showLostConnectionMessage(){
        checkConnectionTimer.stop();
        userUpdateTimer.stop();
        int res = JOptionPane.showConfirmDialog(rootPane, java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("MainWindow.lostConnectionMessageText"),
                java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("MainWindow.lostConnectionMessageTitle"), JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        if(res==JOptionPane.YES_OPTION){
            Main.loginWindow.setVisible(true);
            this.dispose();
            Main.server.disconnect();
        } else {
            userUpdateTimer.start();
            checkConnectionTimer.start();
        }
    }

    private void updateUserList(ActionEvent e){
        DefaultListModel model = (DefaultListModel)jList1.getModel();
        Object sel = jList1.getSelectedValue();
        model.clear();
        try{
            String res[] = Main.server.commandAndResponse(1000, "list users", "online", Main.server.secret);
            if(res[0]==null || !res[0].equals("SUCCESS")){
                showLostConnectionMessage();
            }

            for(int i=1; i<res.length; i++){
                String r[] = Main.server.commandAndResponse(500, "profile by id", res[i], Main.server.secret);
                if(r==null || r[0]==null || !r[0].equals("SUCCESS")) continue;
                UserProfile u = new UserProfile(Integer.parseInt(res[i]));
                u.update();
                if(u.equals(myProfile)) u.setProperty("pub_nickname", u.getProperty("pub_nickname")+" (Me)");
                model.addElement(u);
            }
        } catch (Exception ex){
            System.err.println("user list: "+ex.getLocalizedMessage());
        }
        jList1.setSelectedValue(sel, true);
    }

    private void checkConnection(){
        if(!Main.server.isConnected()){
            showLostConnectionMessage();
        }
    }

    private boolean requestGame(UserProfile rival){
        try{
            UserProfile me = new UserProfile(0);
            me.update();
            if(rival==null) rival=me;
            if(rival.equals(me)){    
                 // Show message that user is trying to play with themselves. =)
//                EventQueue.invokeLater(new Runnable() {
//                    public void run() {
//                        JOptionPane.showMessageDialog(rootPane, "You are about to play with yourself. "
//                                + "The results of the game won't be stored on the server.",
//                        "Titanic GameClient: Info", JOptionPane.INFORMATION_MESSAGE);
//                    }
//                });
                
            }     
            String[] r = Main.server.commandAndResponse(200, "REQUEST GAME", rival.getProperty("id"), Main.server.secret);
            if(r==null) return false;
            if(r.length==0) return false;
            if(!r[0].toUpperCase().equals("SUCCESS")) return false;
            return true;
        } catch(Exception ex){}
        return false;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem8 = new javax.swing.JMenuItem();

        jPopupMenu1.setInvoker(jList1);
        jPopupMenu1.setName("jPopupMenu1"); // NOI18N
        jPopupMenu1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPopupMenu1ComponentShown(evt);
            }
        });

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("client/gui/Bundle"); // NOI18N
        jMenuItem1.setText(bundle.getString("MainWindow.jMenuItem1.text")); // NOI18N
        jMenuItem1.setName("jMenuItem1"); // NOI18N
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem1);

        jMenuItem2.setText(bundle.getString("MainWindow.jMenuItem2.text")); // NOI18N
        jMenuItem2.setName("jMenuItem2"); // NOI18N
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jPopupMenu1.add(jMenuItem2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(bundle.getString("MainWindow.title")); // NOI18N
        setName("Form"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), bundle.getString("MainWindow.players_online"))); // NOI18N
        jPanel1.setName("jPanel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jList1.setModel(new DefaultListModel());
        jList1.setComponentPopupMenu(jPopupMenu1);
        jList1.setName("jList1"); // NOI18N
        jScrollPane1.setViewportView(jList1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton1.setText(bundle.getString("MainWindow.jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText(bundle.getString("MainWindow.jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jMenuBar1.setName("jMenuBar1"); // NOI18N

        jMenu1.setText(bundle.getString("MainWindow.jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        jMenuItem10.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem10.setText(bundle.getString("MainWindow.jMenuItem10.text")); // NOI18N
        jMenuItem10.setName("jMenuItem10"); // NOI18N
        jMenu1.add(jMenuItem10);

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem9.setText(bundle.getString("MainWindow.jMenuItem9.text")); // NOI18N
        jMenuItem9.setName("jMenuItem9"); // NOI18N
        jMenu1.add(jMenuItem9);

        jSeparator2.setName("jSeparator2"); // NOI18N
        jMenu1.add(jSeparator2);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem3.setText(bundle.getString("MainWindow.jMenuItem3.text")); // NOI18N
        jMenuItem3.setName("jMenuItem3"); // NOI18N
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText(bundle.getString("MainWindow.jMenu2.text")); // NOI18N
        jMenu2.setName("jMenu2"); // NOI18N

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_M, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText(bundle.getString("MainWindow.jMenuItem4.text")); // NOI18N
        jMenuItem4.setName("jMenuItem4"); // NOI18N
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText(bundle.getString("MainWindow.jMenuItem5.text")); // NOI18N
        jMenuItem5.setName("jMenuItem5"); // NOI18N
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        jMenu3.setText(bundle.getString("MainWindow.jMenu3.text")); // NOI18N
        jMenu3.setName("jMenu3"); // NOI18N

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem6.setText(bundle.getString("MainWindow.jMenuItem6.text")); // NOI18N
        jMenuItem6.setName("jMenuItem6"); // NOI18N
        jMenu3.add(jMenuItem6);

        jMenuItem7.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem7.setText(bundle.getString("MainWindow.jMenuItem7.text")); // NOI18N
        jMenuItem7.setName("jMenuItem7"); // NOI18N
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jMenu3.add(jSeparator1);

        jMenuItem8.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem8.setText(bundle.getString("MainWindow.jMenuItem8.text")); // NOI18N
        jMenuItem8.setName("jMenuItem8"); // NOI18N
        jMenu3.add(jMenuItem8);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try{
            if(!Main.checkMemory(12*1024*1024))
                throw new OutOfMemoryError("Low available memory");
            UserProfile rival = (UserProfile)jList1.getSelectedValue();
            boolean r = requestGame(rival);
            if(r){
                JFrame f = new WaitingForPlayerWindow(rival, this);
                f.setVisible(true);
            }
            else{
                System.out.println("Your game request was rejected by the game server.");
            }
        } catch (Error ex){
            System.err.println("Start game error: "+ex.getLocalizedMessage());
            JOptionPane.showMessageDialog(rootPane,
                    ex.getLocalizedMessage()+" The application may behave abnormally.",
                    "Titanic GameCilent: Error",
                    JOptionPane.ERROR_MESSAGE);
            System.gc();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try{
            Main.server.disconnect();
        } catch (Exception ex) {}
        Main.loginWindow.setVisible(true);
        checkConnectionTimer.stop();
        userUpdateTimer.stop();
        trafficCheckTimer.stop();
        checkRequestsTimer.stop();
        if(gameWindows!=null){
            try{
                for(GameWindow w : gameWindows)
                    w.dispose();
            } catch (Exception ex) {}
            gameWindows.clear();
            gameWindows = null;
        }
            
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        if(jList1.getSelectedIndex()<0) return;
        try{
            UserProfile u = (UserProfile)jList1.getSelectedValue();
            myProfile.update();
            if(u.equals(myProfile)) new UserProfileEdit(this, myProfile).setVisible(true);
            else new UserProfileView(u).setVisible(true);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jPopupMenu1ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPopupMenu1ComponentShown
        if(jList1.getSelectedIndex()<0){
            jMenuItem1.setEnabled(false);
            jMenuItem2.setEnabled(false);
        } else {
           jMenuItem1.setEnabled(true);
           jMenuItem2.setEnabled(true); 
        }
        
    }//GEN-LAST:event_jPopupMenu1ComponentShown

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        formWindowClosing(null);
        dispose();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        String url = "http://danon-laptop.campus.mipt.ru/TitanicWebSite/index";
        if( !java.awt.Desktop.isDesktopSupported() ) {
            System.err.println( "Desktop is not supported!\nGo to "+url);
            return;
        }
        
        java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
        
        if( !desktop.isSupported( java.awt.Desktop.Action.BROWSE ) ) {
            System.err.println("Desktop doesn't support the browse action.");
            System.err.println("Desktop is not supported!\nGo to "+url);
            return;
        }

        try {
            desktop.browse(new URI(url));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        if(jList1.getSelectedIndex()<0) return;
        try{
            UserProfile u = (UserProfile)jList1.getSelectedValue();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    jButton1ActionPerformed(null);
                }
            });
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        new UserProfileEdit(this, myProfile).setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    // End of variables declaration//GEN-END:variables

    private Timer checkConnectionTimer, userUpdateTimer, trafficCheckTimer, checkRequestsTimer;
    private UserProfile myProfile;
    public ArrayList<GameWindow> gameWindows = new ArrayList<GameWindow>();
}


