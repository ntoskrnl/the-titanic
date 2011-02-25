/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client.util;

import javax.swing.UIManager;

/**
 *
 * @author Danon
 */
public class ClientConfiguration {

    public static final int DEFAULT_PORT = 10000;
    public static final String DEFAULT_SERVER = "localhost";
    public static final int DEFAULT_PROXY_TYPE = 0;
    public static final int DEFAULT_PROXY_PORT = 8081;
    public static final String DEFAULT_PROXY_HOST = "";

    public static String lastLogin = "";
    public static String serverHost = DEFAULT_SERVER;
    public static int serverPort = DEFAULT_PORT;
    public static int proxyType = DEFAULT_PROXY_TYPE;
    public static String proxyHost = DEFAULT_PROXY_HOST;
    public static int proxyPort = DEFAULT_PROXY_PORT;

    public static String lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();

    public void loadPreferences(){
        // TODO Implement loadPreferences
    }

    public void savePreferences(){
       // TODO Implement savePreferences
    }
}
