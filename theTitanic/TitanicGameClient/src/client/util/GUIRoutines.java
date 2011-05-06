package client.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.UIManager;

/**
 *
 * @author danon
 */
public class GUIRoutines {

    public static void toScreenCenter(Component window){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    
        // Determine the new location of the window
        int w = window.getSize().width;
        int h = window.getSize().height;
        int x = (dim.width-w)/2;
        int y = (dim.height-h)/2;

        // Move the window
        window.setLocation(x, y);
    }
    
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
