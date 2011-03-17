package client.util;

import java.awt.Color;
import java.awt.Container;
import java.util.Random;
import javax.media.j3d.Canvas3D;
import titanic.basic.Ball;
import titanic.basic.Game;
import titanic.basic.GameScene;
import titanic.basic.GraphicalEngine;
import titanic.basic.PhysicalEngine;
import titanic.basic.Vector3D;

/**
 * Simple implementation of class Game
 * @author danon
 */
public class SimpleGame extends Game {
    private GameScene scene;
    private PhysicalEngine physics;
    private GraphicalEngine graphics;
    private Thread thread1, thread2;
    private Game game;

    private boolean rearrange = false;

    /**
     * Constructs new Game instance and sets c as default rendering area.
     * @param c JPanel or other container where to render the scene
     */
    public SimpleGame(Container c){
        Ball[] balls = new Ball[16];
        scene = new SimpleGameScene(c, balls);
        arrangeBalls(balls, scene.getBounds());

        physics = new SimplePhysics(this);
        
        graphics = new Graphics3D(this);
        graphics.setRenderingArea(c);

        game = this;

        //graphics.render(this);
    }


    /**
     * Arrange balls.
     * @param balls The array with null elements
     * @param bounds Table bounds Vector3D
     */
    private void arrangeBalls(Ball[] balls, Vector3D bounds){
        synchronized(balls){
            Random rand = new Random(System.currentTimeMillis());
            float R = 12;
            for(int i=0;i<balls.length;i++){
                balls[i] = new Ball();
                balls[i].setCoordinates(new Vector3D(R+rand.nextFloat()*(bounds.getX()-R) - bounds.getX()/2.0f,
                        R+rand.nextFloat()*(bounds.getY()-R) - bounds.getY()/2.0f));
                balls[i].setColor(Color.BLACK);
                balls[i].getSpeed().setY(10 - rand.nextFloat()*30);
                balls[i].getSpeed().setX(10 - rand.nextFloat()*30);
                balls[i].setRadius(R-1);
                System.out.println(balls[i]);
            }
        }
    }

    /**
     * Starts game thread
     */
    public void start(){
        stop();
        if(!rearrange) rearrange = true;
        else arrangeBalls(game.getGameScene().getBalls(), game.getGameScene().getBounds());
        thread1 = new Thread(new Runnable() {
            public void run() {
                try {
                    while(!thread1.isInterrupted()){
                        graphics.render(game);
                        Thread.currentThread().sleep(25);
                    }
                } catch (InterruptedException ex){}
            }
        });
        thread2 = new Thread(new Runnable() {
            public void run() {
                try {
                    while(!thread1.isInterrupted()){
                        physics.compute();
                        Thread.currentThread().sleep(15);
                    }
                } catch (InterruptedException ex){}
            }
        });
        thread1.start();
        thread2.start();
    }

    /**
     * Stops game thread
     */
    public void stop(){
        if(thread1!=null)
            thread1.interrupt();
        if(thread2!=null)
            thread2.interrupt();
    }

    @Override
    public GameScene getGameScene() {
        return scene;
    }

    @Override
    public int changeStatus(int gameStatus) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public GraphicalEngine getGraphicalEngine() {
        return graphics;
    }

    @Override
    public PhysicalEngine getPhysicalEngine() {
        return physics;
    }


}

/**
 * Simple implementation of class GameScene
 * @author danon
 */
class SimpleGameScene extends GameScene {
    private Container renderingArea;
    private Ball[] balls;
    private Vector3D bounds;
    
    public SimpleGameScene(Container c, Ball[] b){
        renderingArea = c;
        balls = b;
        bounds = new Vector3D((float)c.getWidth(), (float)c.getHeight());
    }

    @Override
    public Ball[] getBalls() {
        return balls;
    }

    @Override
    public Vector3D getBounds() {
        return bounds;
    }
    
}
