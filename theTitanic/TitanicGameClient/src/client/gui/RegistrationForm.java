/*
 * RegistrationForm.java
 *
 * Created on Mar 24, 2011, 4:48:15 PM
 */

package client.gui;

import client.Main;
import client.util.Crypto;
import client.util.TitanicServer;
import javax.swing.JOptionPane;

/**
 *
 * @author danon
 */
public class RegistrationForm extends javax.swing.JFrame {

    /** Creates new form RegistrationForm */
    public RegistrationForm() {
        initComponents();
    }

    private boolean checkFields(){
        if(loginTextField.getText().isEmpty()) {
            statusLabel.setText(java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("loginRequired"));
            return false;
        }
        if(passwordTextField.getPassword().length==0) {
            statusLabel.setText(java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("passwordRequired"));
            return false;
        }
        if(retypePwdTextField.getPassword().length==0) {
            statusLabel.setText(java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("retypePassword"));
            return false;
        }
        if(! new String(passwordTextField.getPassword()).equals(new String(retypePwdTextField.getPassword()))) {
            statusLabel.setText(java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("passwordsNotMatch"));
            return false;
        }
        if(firstNameTextField.getText().isEmpty()) {
            statusLabel.setText(java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("enterFirstName"));
            return false;
        }
        if(surnameTextField.getText().isEmpty()) {
            statusLabel.setText(java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("enterSurname"));
            return false;
        }
        if(pubNicknameTextField.getText().isEmpty()) {
            statusLabel.setText(java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("specifyPubNickname"));
            return false;
        }
        statusLabel.setText(java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("OKgreen"));
        return true;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        loginTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        passwordTextField = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        retypePwdTextField = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        firstNameTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        surnameTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        pubNicknameTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        statusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("client/gui/Bundle"); // NOI18N
        setTitle(bundle.getString("RegistrationForm.title")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jPanel1.setName("jPanel1"); // NOI18N

        jButton1.setText(bundle.getString("RegistrationForm.jButton1.text")); // NOI18N
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText(bundle.getString("RegistrationForm.jButton2.text")); // NOI18N
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(101, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addGap(27, 27, 27))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("RegistrationForm.jPanel2.border.title"))); // NOI18N
        jPanel2.setName("jPanel2"); // NOI18N

        loginTextField.setName("loginTextField"); // NOI18N
        loginTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        loginTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                focusChange(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                focusChange(evt);
            }
        });
        loginTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RegistrationForm.this.keyPressed(evt);
            }
        });

        jLabel2.setText(bundle.getString("RegistrationForm.jLabel2.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        passwordTextField.setName("passwordTextField"); // NOI18N
        passwordTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        passwordTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                focusChange(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                focusChange(evt);
            }
        });
        passwordTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RegistrationForm.this.keyPressed(evt);
            }
        });

        jLabel3.setText(bundle.getString("RegistrationForm.jLabel3.text")); // NOI18N
        jLabel3.setName("jLabel3"); // NOI18N

        retypePwdTextField.setName("retypePwdTextField"); // NOI18N
        retypePwdTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        retypePwdTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                focusChange(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                focusChange(evt);
            }
        });
        retypePwdTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RegistrationForm.this.keyPressed(evt);
            }
        });

        jLabel4.setText(bundle.getString("RegistrationForm.jLabel4.text")); // NOI18N
        jLabel4.setName("jLabel4"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(retypePwdTextField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(passwordTextField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(loginTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(loginTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(retypePwdTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap())
        );

        jLabel1.setText(bundle.getString("RegistrationForm.jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("RegistrationForm.jPanel3.border.title"))); // NOI18N
        jPanel3.setName("jPanel3"); // NOI18N

        firstNameTextField.setName("firstNameTextField"); // NOI18N
        firstNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        firstNameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                focusChange(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                focusChange(evt);
            }
        });
        firstNameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RegistrationForm.this.keyPressed(evt);
            }
        });

        jLabel5.setText(bundle.getString("RegistrationForm.jLabel5.text")); // NOI18N
        jLabel5.setName("jLabel5"); // NOI18N

        surnameTextField.setName("surnameTextField"); // NOI18N
        surnameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        surnameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                focusChange(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                focusChange(evt);
            }
        });
        surnameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RegistrationForm.this.keyPressed(evt);
            }
        });

        jLabel6.setText(bundle.getString("RegistrationForm.jLabel6.text")); // NOI18N
        jLabel6.setName("jLabel6"); // NOI18N

        pubNicknameTextField.setName("pubNicknameTextField"); // NOI18N
        pubNicknameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        pubNicknameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                focusChange(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                focusChange(evt);
            }
        });
        pubNicknameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                RegistrationForm.this.keyPressed(evt);
            }
        });

        jLabel7.setText(bundle.getString("RegistrationForm.jLabel7.text")); // NOI18N
        jLabel7.setName("jLabel7"); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(pubNicknameTextField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(surnameTextField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(firstNameTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(surnameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pubNicknameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel8.setText(bundle.getString("RegistrationForm.jLabel8.text")); // NOI18N
        jLabel8.setName("jLabel8"); // NOI18N

        statusLabel.setText(bundle.getString("RegistrationForm.statusLabel.text")); // NOI18N
        statusLabel.setName("statusLabel"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(18, 18, 18)
                .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void focusChange(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_focusChange
        checkFields();
    }//GEN-LAST:event_focusChange

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(!checkFields()) return;
        Main.server = new TitanicServer();
        if(!Main.server.isConnected()){
            statusLabel.setText(java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("connectionProblemHTMLred"));
            return;
        }
        try {
            Main.server.command("register", loginTextField.getText(),
                        Crypto.md5(new String(passwordTextField.getPassword())),
                        firstNameTextField.getText(), surnameTextField.getText(),
                        pubNicknameTextField.getText(), "", "", "", "");
            String r[] = Main.server.getResponse();
            if(r[0].equals("SUCCESS")) {
                JOptionPane.showMessageDialog(rootPane, java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("registrationSuccessfulMessageText"), 
                        java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("registrationSuccessfulMessageTitle"), JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        } catch(Exception e){
            Main.server.disconnect();
            statusLabel.setText(java.util.ResourceBundle.getBundle("client/gui/Bundle").getString("cannotRegisterHTMLred"));
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void keyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyPressed
        checkFields();
    }//GEN-LAST:event_keyPressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField firstNameTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JTextField loginTextField;
    private javax.swing.JPasswordField passwordTextField;
    private javax.swing.JTextField pubNicknameTextField;
    private javax.swing.JPasswordField retypePwdTextField;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JTextField surnameTextField;
    // End of variables declaration//GEN-END:variables

}
