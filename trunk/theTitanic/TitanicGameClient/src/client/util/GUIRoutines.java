package client.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

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
    
}
