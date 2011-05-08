package client.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.UIManager;

/**
 * Class provides some functions for GUI as static methods.
 * @author danon
 */
public class GUIRoutines {

    /**
     * Moves a window to the center of the screen
     * @param window a window to move
     */
    public static void toScreenCenter(Component window){
        if(window==null) return;
        
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    
        // Determine the new location of the window
        int w = window.getSize().width;
        int h = window.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;

        // Move the window
        window.setLocation(x, y);
    }
    
    /**
     * Tries to apply a look and feel that is specified as name. 
     * (GTK+, Windows, Metal, ...)
     * @param name Alias for look and feel.
     * @return true if look and feel was applied successfully.
     */
    public static boolean tryLookAndFeel(String name){
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (name.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                return true;
                }
            }
        } catch (Exception ex){}
        return false;
    }
    
    
}
