package ballsimpact;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Danon
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame myFrame = new BallImpactFrame();
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setVisible(true);
    }

}

class BallImpactFrame extends JFrame{
    private JPanel panel;

    public BallImpactFrame() {
        super();
        setSize(500, 300);
        setTitle("Ball Impact Simulation");
        panel = new JPanel();
        getRootPane().add(panel);

        
        start();
    }

    void start(){
        Thread ballsThread = new Thread(new BallsRunnable(panel));
        ballsThread.start();
    }
}

class BallsRunnable implements Runnable{
    private JPanel panel = null;

    public BallsRunnable(JPanel panel) {
        this.panel = panel;
    }

    public void run(){
        if(panel==null) return;
        Graphics2D g = (Graphics2D)panel.getGraphics();
        if(g==null) return;
        while(!Thread.currentThread().isInterrupted()){
            g.setColor(Color.red);
            g.draw(new Ellipse2D.Double(200, 200, 150, 100));
        }
    }
}