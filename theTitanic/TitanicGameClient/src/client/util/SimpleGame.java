package client.util;

import java.awt.Container;
import java.util.Random;
import titanic.basic.Ball;
import titanic.basic.BilliardKey;
import titanic.basic.EventPipeLine;
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
    private Thread thread1, thread2, thread3;
    private Game game;
    private EventPipeLine events;
    private BilliardKey key;
    private Container renderingArea;
    private boolean rearrange = false;
    int status = Game.S_NONE;

    /**
     * Constructs new Game instance and sets c as default rendering area.
     * @param c JPanel or other container where to render the scene
     */
    public SimpleGame(Container c){
        Ball[] balls = new Ball[16];
        renderingArea = c;
        scene = new SimpleGameScene(c, balls);
        key = new SimpleBilliardKey();
        key.changeBall(balls[15]);
        
        arrangeBalls(balls, scene.getBounds());

        events = new SimpleEventPipeLine();

        physics = new yukiEngine(this);
        
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
        Vector3D[] mass = new Vector3D[balls.length];
        float r = 0.045f;
        synchronized(balls){
            mass[0] = new Vector3D();
    
            int k = 5,i,x=5;
            for(i=1; i<15; i++){
                Vector3D a = new Vector3D();
                mass[i] = a;
                float r_=r-0.005f;
                if(i == x) {

                    mass[i].setX(mass[i - k].getX() - r_);
                    mass[i].setY((float) (mass[i - k].getY() + 2 * r_ * Math.cos((Math.PI) / 6)));
                    k--;
                    x = x + k;
                } else{
                    mass[i].setX(mass[i-1].getX() + r_);
                    mass[i].setY((float)(mass[i-1].getY() +2 * r_ * Math.cos((Math.PI) / 6)));

                }
            }

            //Random rand = new Random(System.currentTimeMillis());
            for(i=0;i<15;i++){
                if(balls[i]==null)
                    balls[i] = new Ball();
                balls[i].setCoordinates(mass[i]);
                balls[i].setRadius(r);
                balls[i].setId(i);
            }
            if(balls[15]==null)
                balls[15] = new Ball(0, -0.5f);
            balls[15].setCoordinates(new Vector3D(0, -0.5f, 0));
            balls[15].setId(15);
            balls[15].setRadius(r);
        }
        Ball cur = key.getBall();
        if(cur==null) return;
        float angle = key.getAngle() + (float)Math.PI/2;   
        cur.setSpeed(new Vector3D(key.getPower()*(float)Math.cos(angle) *20, 
                    key.getPower()*(float)Math.sin(angle)*20));
    }

    /**
     * Starts game thread
     */
    public void start(){
        stop();
        changeStatus(Game.S_MOVING);
        if(!rearrange) 
            rearrange = true;
        else 
            arrangeBalls(game.getGameScene().getBalls(), game.getGameScene().getBounds());
        thread1 = new Thread(new Runnable() {
            public void run() {
                try {
                    while(!thread1.isInterrupted()){
                       try{
                            graphics.render(game);
                         } catch(Exception ex){}
                        catch (Error er) {}
                        catch (Throwable th) {}
                        Thread.currentThread().sleep(25);
                    }
                } catch (InterruptedException ex){}
            }
        });
        thread2 = new Thread(new Runnable() {
            public void run() {
                try {
                    while(!thread2.isInterrupted()){
                        try{
                            physics.compute();
                        } catch(Exception ex){}
                        catch (Error er) {}
                        catch (Throwable th) {}
                        Thread.currentThread().sleep(30);
                    }
                } catch (InterruptedException ex){}
            }
        });
        thread3 = new Thread(new Runnable() {
            public void run() {
                try{
                    while(!thread3.isInterrupted()){
                        try{
                            while(events.size()!=0) 
                                events.getFirst().execute();
                         } catch(Exception ex){}
                        catch (Error er) {}
                        catch (Throwable th) {}
                        Thread.currentThread().sleep(10);
                    }
                } catch(InterruptedException ex){}
            }
        });
        thread3.start();
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
        if(thread3!=null)
            thread3.interrupt();
        rearrange = false;
        arrangeBalls(getGameScene().getBalls(), null);
        graphics.render(game);
    }

    @Override
    public GameScene getGameScene() {
        return scene;
    }

    @Override
    synchronized public int changeStatus(int gameStatus) {
        return status = gameStatus;
    }

    @Override
    synchronized public int getGameStatus(){
        return status;
    }

    @Override
    public GraphicalEngine getGraphicalEngine() {
        return graphics;
    }

    @Override
    public PhysicalEngine getPhysicalEngine() {
        return physics;
    }

    @Override
    public EventPipeLine getEventPipeLine() {
        return events;
    }

    @Override
    public BilliardKey getBilliardKey() {
        return key;
    }

    @Override
    public void dispose() {
        stop();
        renderingArea.removeAll();
        if(graphics!=null)
            graphics.dispose();
        events.clear();
        System.gc();
    }


}


