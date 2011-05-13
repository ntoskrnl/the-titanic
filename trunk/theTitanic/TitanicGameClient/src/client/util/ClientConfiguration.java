package client.util;

import java.util.prefs.Preferences;

/**
 * Preferences
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

    public static String lookAndFeel = "Nimbus";

    private static Preferences prefs = Preferences.userRoot().node("TitanicGameClient");;
    
    public static void loadPreferences(){
        System.out.println("Loading preferences...");
        serverHost = prefs.get("server_host", DEFAULT_SERVER);
        serverPort = prefs.getInt("server_port",DEFAULT_PORT);
        lastLogin = prefs.get("last_login", "");
        lookAndFeel = prefs.get("look", lookAndFeel);
    }

    public static void savePreferences(){
       System.out.println("Saving preferences...");
       prefs.putInt("server_port", serverPort);
       prefs.put("server_host", serverHost);
       prefs.put("last_login", lastLogin);
       prefs.put("look", lookAndFeel);
    }
}
