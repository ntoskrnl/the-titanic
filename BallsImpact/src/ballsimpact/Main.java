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
import titanic.basic.*;

/**
 * 2D Balls Collision Test (using SimplePhysics by Arkthik)
 * @author Danon
 */
public class Main {

    private static BallPanel ballPanel = null;
    private static Thread collision = null;
    private static JButton startButton = null;
    private static boolean suspended = true;
    private static JLabel statusLabel = null;
    private static Game game = null;
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
        ballPanel.setBorder(new LineBorder(Color.BLACK, 1));
        content.add(ballPanel, BorderLayout.CENTER);
        myFrame.pack();
        myFrame.setSize(600, 500);
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

class SimpleGame extends Game {
    private GameScene gameScene = null;
    public SimpleGame(Ball[] balls, Vector2D bounds) {
        gameScene = new SimpleGameScene(balls, bounds);
    }



    @Override
    public GameScene getGameScene() {
        return gameScene;
    }

    @Override
    public int changeStatus(int gameStatus) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}

class SimpleGameScene extends GameScene {
    private Ball[] balls;
    private Vector2D bounds;

    public SimpleGameScene(Ball[] balls, Vector2D bounds) {
        this.balls = balls;
        this.bounds = bounds;
    }



    @Override
    public Ball[] getBalls() {
        return balls;
    }

    @Override
    public Vector2D getBounds() {
        return bounds;
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
        float R = 11;
        for(int i=0;i<count;i++){
            balls[i] = new Ball();
            balls[i].setCoordinates(new Vector2D(R+rand.nextFloat()*(getWidth()-R) - getWidth()/2.0f,
                    R+rand.nextFloat()*(getHeight()-R) - getHeight()/2.0f));
            balls[i].setColor(Color.BLACK);
            balls[i].getSpeed().setY(10 - rand.nextFloat()*20);
            balls[i].getSpeed().setX(10 - rand.nextFloat()*20);
            balls[i].setRadius(R-1);
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
            r = balls[i].getRadius();
            x = balls[i].getCoordinates().getX();
            y = balls[i].getCoordinates().getY();
            Ellipse2D p = new Ellipse2D.Double(x-r + getWidth()/2.0f,
                   0.5f*getHeight() - (y-r),
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


class CollisionThread implements Runnable {
    private Ball[] balls = null;
    private PhysicalEngine physics = null;
    private BallPanel ballPanel = null;
    private Game game = null;

    public CollisionThread(BallPanel b) {
        ballPanel = b;
        if(b==null) return;
        balls = b.getBalls();
        game = new SimpleGame(balls, new Vector2D(ballPanel.getWidth()/2f, ballPanel.getHeight()));
        physics = new SimplePhysics(game);
    }

    public void run(){
        if(balls==null) return;
        if(ballPanel==null) return;
        if(physics==null) return;
        System.out.println("Thread started!");
        Thread thread = Thread.currentThread();
        System.out.println("Collision started!");
        while(!thread.isInterrupted()){
            physics.compute();
            ballPanel.repaint();
            try{
                thread.sleep(34); // ~ 25-30 fps
            }catch(InterruptedException ex){ }
        }
    }
}
