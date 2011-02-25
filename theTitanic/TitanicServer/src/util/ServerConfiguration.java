package util;

import java.util.prefs.Preferences;
import javax.swing.UIManager;

/**
 * Serves server configuration in static variables.
 * @author danon
 */
public class ServerConfiguration {
    static final int DEFAULT_PORT = 10000;
    static final int DEFAULT_PROXY_PORT = 8081;
    static final String DEFAULT_PROXY_HOST = "";

    public static int serverPort = DEFAULT_PORT;

    public static int proxyType = 0;
    public static String proxyHost = "";
    public static int proxyPort = DEFAULT_PROXY_PORT;

    public static String lookAndFeel = UIManager.getCrossPlatformLookAndFeelClassName();

    public static Preferences userPreferences = null;

    public static void savePreferences() {
        System.out.println("Saving preferences...");

        userPreferences = Preferences.userRoot().node("theTitanic").node("server");

        userPreferences.putInt("connection.proxy_port", proxyPort);
        userPreferences.putInt("connection.proxy_type", proxyType);
        userPreferences.putInt("connection.server_port", serverPort);
        userPreferences.putByteArray("connection.proxy_host", proxyHost.getBytes());
        userPreferences.putByteArray("ui.look_and_feel", lookAndFeel.getBytes());
    }

    public static void loadPreferences(){
        System.out.println("Loading user preferences...");

        userPreferences = Preferences.userRoot().node("theTitanic").node("server");

        ServerConfiguration.serverPort = userPreferences.getInt("connection.server_port", DEFAULT_PORT);
        ServerConfiguration.proxyType = userPreferences.getInt("connection.proxy_type", 0);
        ServerConfiguration.proxyHost = new String(userPreferences.getByteArray("connection.proxy_host", DEFAULT_PROXY_HOST.getBytes()));
        ServerConfiguration.proxyPort = userPreferences.getInt("connection.proxy_port", DEFAULT_PROXY_PORT);
        ServerConfiguration.lookAndFeel = new String(userPreferences.getByteArray("ui.look_and_feel", UIManager.getCrossPlatformLookAndFeelClassName().getBytes()));
    }
}
