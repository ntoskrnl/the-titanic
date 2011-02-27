package ballsimpact;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 *
 * @author Danon
 */
public class Main {

    private static BallPanel ballPanel = null;
    private static Thread collision = null;
    private static JButton startButton = null;
    private static boolean suspended = true;
    private static JLabel statusLabel = null;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame myFrame = new JFrame("Collision Balls: physics test");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container content = myFrame.getContentPane();
        content.setLayout(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                startCollision();
            }
        });
        buttonPanel.add(startButton);
        statusLabel = new JLabel("<html><strong>Status:</strong> Not started</html>");
        buttonPanel.add(statusLabel);

        content.add(buttonPanel, BorderLayout.SOUTH);
        ballPanel = new BallPanel();
        ballPanel.setBorder(new LineBorder(Color.BLACK, 2));
        content.add(ballPanel, BorderLayout.CENTER);
        myFrame.pack();
        myFrame.setSize(500, 400);
        myFrame.setResizable(false);
        myFrame.setVisible(true);
    }

    public static void startCollision(){
        if(ballPanel==null) return;
        if(collision==null||suspended){
            ballPanel.addBalls(20);
            collision = new Thread(new CollisionThread(ballPanel));
            collision.start();
            startButton.setText("Stop");
            statusLabel.setText("<html><strong>Status:</strong> Running</html>");
            suspended=false;
        } else {
            collision.stop();
            suspended=true;
            statusLabel.setText("<html><strong>Status:</strong> Stopped</html>");
            startButton.setText("Start");
        }

    }

}


class BallPanel extends JPanel {

    private Ball[] balls = null;

    public BallPanel() {
        setBackground(Color.BLACK);
    }



    public void addBalls(int count){
        balls = new Ball[count];
        Random rand = new Random(System.currentTimeMillis());
        double R = 11;
        for(int i=0;i<count;i++){
            balls[i] = new Ball(R+rand.nextDouble()*(getWidth()-R), R+rand.nextDouble()*(getHeight()-R));
            balls[i].setColor(Color.BLACK);
            balls[i].setVY(10 - rand.nextDouble()*20);
            balls[i].setVX(10 - rand.nextDouble()*20);
            balls[i].setR(R-1);
            System.out.println(balls[i]);
        }
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(getBalls()==null) return;
        Graphics2D g2 = (Graphics2D)g;
        for(int i=0;i<getBalls().length;i++){
            double r, x, y;
            r = balls[i].getR();
            x = balls[i].getX();
            y = balls[i].getY();
            Ellipse2D p = new Ellipse2D.Double(x-r,
                   getHeight() - (y-r),
                    r*2, r*2);
            g2.setColor(balls[i].getColor());
            g2.fill(p);
            g2.setColor(Color.WHITE);
            g2.draw(p);
        }
    }

    synchronized public Ball[] getBalls() {
        return balls;
    }
    
}


class Ball{
    private double x, y, vx, vy, r;
    private Color color;

    public Ball() {
        x=y=vx=vy=0d;
        r=5.0d;
        color = Color.BLACK;
    }

    public Ball(double x, double y){
        this.x = x; this.y = y;
        vx=vy=0;
        r=5.0d;
        color = Color.BLACK;
    }

    @Override
    public String toString(){
        return "Ball{x="+x+"; y="+y+"; Vx="+vx+"; Vy="+vy+"; color="+color+"}";
    }

    synchronized public double getX() {
        return x;
    }

    synchronized public double getY() {
        return y;
    }

    synchronized public double getVX() {
        return vx;
    }

    synchronized public double getVY() {
        return vy;
    }

    synchronized public double getR() {
        return r;
    }

    synchronized public Color getColor() {
        return color;
    }

    synchronized public void setX(double x) {
        this.x = x;
    }


    synchronized public void setY(double y) {
        this.y = y;
    }

    synchronized public void setVX(double vx) {
        this.vx = vx;
    }

    synchronized public void setVY(double vy) {
        this.vy = vy;
    }

    public void setR(double r) {
        this.r = r;
    }

    synchronized public void setColor(Color color) {
        this.color = color;
    }

}

class CollisionThread implements Runnable {
    private Ball[] balls = null;
    private Physics physics = null;
    private BallPanel ballPanel = null;

    public CollisionThread(BallPanel b) {
        ballPanel = b;
        if(b==null) return;
        balls = b.getBalls();
        physics = new Physics(ballPanel.getWidth(), ballPanel.getHeight());
    }

    public void run(){
        if(balls==null) return;
        if(ballPanel==null) return;
        if(physics==null) return;
        Thread thread = Thread.currentThread();
        System.out.println("Collision started!");
        while(!thread.isInterrupted()){
            physics.compute(balls);
            ballPanel.repaint();
            try{
                thread.sleep(40);
            }catch(InterruptedException ex){ }
        }
    }
}

class Physics {
    private double width = 0, height =0;

    public Physics(double w, double h) {
        width = w;
        height = h;
    }

    // Warning! This code does not seem to be thread-safe!!!
    void compute(Ball[] balls){
        for(int i=0;i<balls.length-1;i++){
            double x1 = balls[i].getX(), y1=balls[i].getY(),
                    vx1=balls[i].getVX(), vy1=balls[i].getVY(),
                    r1 = balls[i].getR();
            for(int j=i+1;j<balls.length;j++){
                double x2 = balls[j].getX(), y2=balls[j].getY(),
                    vx2=balls[j].getVX(), vy2=balls[j].getVY(),
                    r2=balls[j].getR();
                double d = (x1-x2)*(x1-x2)+(y1-y2)*(y1-y2);
                if(d<0.01&&d>(r1+r2)*(r1+r2)) continue;
                impact(balls[i], balls[j]);
            }
        }
        for(int i=0;i<balls.length;i++){
            double x = balls[i].getX(), y=balls[i].getY(),
                    vx=balls[i].getVX(), vy=balls[i].getVY();
            x+=vx; y+=vy;
            if(y<0&&vy<0) vy=-vy;
            if(y>height&&vy>0) vy=-vy;
            if(x<0&&vx<0) vx=-vx;
            if(x>width&&vx>0) vx=-vx;
            balls[i].setVX(vx);
            balls[i].setVY(vy);
            balls[i].setX(x);
            balls[i].setY(y);
            if(Math.abs(vx)>0.001)
                vx-=vx/Math.abs(vx)*0.01;
            if(Math.abs(vy)>0.001)
                vy-=vy/Math.abs(vy)*0.01;
            if(Math.abs(vx)<0.01) vx = 0;
            if(Math.abs(vy)<0.01) vy = 0;
            balls[i].setVX(vx);
            balls[i].setVY(vy);
            
        }
    }

     // Warning! This code is not tested and optimized!
    // Warning! This method ignores weight of the balls
    public void impact(Ball a, Ball b){
        // TODO Implement and test impact(Ball, Ball) method
        // The distance detween centers of the balls a and b
        // can be less than summ of their radii.

        // Algorithm by A. Gilmullin
        // Вектор (cx,cy), соединяющий середины шаров.
        double cx = b.getX()-a.getX(),
               cy = b.getY()-a.getY();
        // Вектор (dx,dy), ему ортогональный.
        double dx = -cy, dy = cx;

        // Square of the distance
        double dist = (a.getX()-b.getX())*(a.getX()-b.getX())+(a.getY()-b.getY())*(a.getY()-b.getY());
        if(Math.sqrt(dist)>a.getR()+b.getR()) return;

        double v1x = 1/dist*(cx*(b.getVX()*cx + b.getVY()*cy)
                    + dx*(dx*a.getVX() + dy*a.getVY()));
        double v1y = 1/dist*(cy*(b.getVX()*cx + b.getVY()*cy)
                    + dy*(dx*a.getVX() + dy*a.getVY()));
        a.setVX(v1x);
        a.setVY(v1y);

        double v2x = 1/dist*(cx*(a.getVX()*cx + a.getVY()*cy)
                    + dx*(dx*b.getVX() + dy*b.getVY()));
        double v2y = 1/dist*(cy*(a.getVX()*cx + a.getVY()*cy)
                    + dy*(dx*b.getVX() + dy*b.getVY()));
        b.setVX(v2x);
        b.setVY(v2y);

        // Now we have to correct coordinates (to avoid multiple multiple interchange)
        double mx = cx/2 + a.getX(),
               my = cy/2 + a.getY();
        cx*=(a.getR()+b.getR()+0.6)/Math.sqrt(dist);
        cy*=(a.getR()+b.getR()+0.6)/Math.sqrt(dist);

        a.setX(mx-cx/2); a.setY(my-cy/2);
        b.setX(mx+cx/2); b.setY(my+cy/2);


    }
}