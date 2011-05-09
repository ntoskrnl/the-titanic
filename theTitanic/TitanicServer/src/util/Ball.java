package util;

import java.util.StringTokenizer;

/**
 * Represents a ball
 * @author danon
 */
public class Ball {
    boolean modified = false;
    public boolean active;
    public int id;
    public float x, y, vx, vy;
    
    public Ball(String s) {
         update(s);
    }
    
    public final void update(String s){
        StringTokenizer stk = new StringTokenizer(s);
         id = Integer.parseInt(stk.nextToken());
         x = Float.parseFloat(stk.nextToken());
         y = Float.parseFloat(stk.nextToken());
         vx = Float.parseFloat(stk.nextToken());
         vy = Float.parseFloat(stk.nextToken());
         active = Boolean.parseBoolean(stk.nextToken());
         modified = true;
    }
   
}
