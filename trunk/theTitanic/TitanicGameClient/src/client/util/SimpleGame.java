package client.util;

import java.awt.Container;
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
    private int status = Game.S_NONE;
    private boolean first;
    private int score;
    private boolean iPlayNext = true;
    private UserProfile rival, me;
    private boolean blankCycle;

    /**
     * Constructs new Game instance and sets c as default rendering area.
     * @param c JPanel or other container where to render the scene
     * @param first Is it the first player?
     */
    public SimpleGame(Container c, boolean first, UserProfile rival) {
        Ball[] balls = new Ball[16];
        renderingArea = c;
        this.first = first;
        this.rival = rival;
        
        if(this.rival==null) this.rival = new UserProfile(0);
        this.rival.update();
        
        me = new UserProfile(0);
        me.update();
        
        if(me.equals(this.rival)) blankCycle = true;
        else blankCycle = false;
        
        status = S_NONE;
        
        

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
    private void arrangeBalls(Ball[] balls, Vector3D bounds) {
        Vector3D[] mass = new Vector3D[balls.length];
        float r = 0.045f;
        synchronized (balls) {
            mass[0] = new Vector3D();

            int k = 5, i, x = 5;
            for (i = 1; i < 15; i++) {
                Vector3D a = new Vector3D();
                mass[i] = a;
                float r_ = r - 0.005f;
                if (i == x) {

                    mass[i].setX(mass[i - k].getX() - r_);
                    mass[i].setY((float) (mass[i - k].getY() + 2 * r_ * Math.cos((Math.PI) / 6)));
                    k--;
                    x = x + k;
                } else {
                    mass[i].setX(mass[i - 1].getX() + r_);
                    mass[i].setY((float) (mass[i - 1].getY() + 2 * r_ * Math.cos((Math.PI) / 6)));

                }
            }

            //Random rand = new Random(System.currentTimeMillis());
            for (i = 0; i < 15; i++) {
                if (balls[i] == null) {
                    balls[i] = new Ball();
                }
                balls[i].setCoordinates(mass[i]);
                balls[i].setRadius(r);
                balls[i].setId(i);
            }
            if (balls[15] == null) {
                balls[15] = new Ball(0, -0.5f);
            }
            balls[15].setCoordinates(new Vector3D(0, -0.5f, 0));
            balls[15].setId(15);
            balls[15].setRadius(r);
        }
    }

    /**
     * Starts game thread
     */
    public void start() {
        // Stop all threads
        stop();
        
        // Rearrange balls
        arrangeBalls(game.getGameScene().getBalls(), game.getGameScene().getBounds());
        
        // I play next
        setIPlayNext(!first);
        
        // Setting initial game status
        if(first) changeStatus(S_BALL_SELECT);
        else changeStatus(S_WAIT_RIVAL);
        if(status == S_BALL_SELECT) System.out.println("It's your turn. Please, select a ball and hit");
        else if(status == S_WAIT_RIVAL) System.out.println("Now wait for player 1 to make hit.");
        else {
            System.err.println("Failed to set start status.");
            return;
        }
        
        // Starting rendering thread
        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!thread1.isInterrupted()) {
                        try {
                            graphics.render(game);
                        } catch (Exception ex) {
                        } catch (Error er) {
                        } catch (Throwable th) {
                        }
                        Thread.sleep(25);
                    }
                } catch (InterruptedException ex) {
                }
            }
        });
        thread1.start();
        
        // Starting physical engine thread
        thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!thread2.isInterrupted()) {
                        try {
                            if (status == S_MOVING) {
                                physics.compute();
                            }
                        } catch (Exception ex) {
                        } catch (Error er) {
                        } catch (Throwable th) {
                        }
                        Thread.sleep(30);
                    }
                } catch (InterruptedException ex) {
                }
            }
        });
        thread2.start();
        
        // Starting event dispatcher thread
        thread3 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!thread3.isInterrupted()) {
                        try {
                            while (events.size() != 0) {
                                events.getFirst().execute();
                            }
                        } catch (Exception ex) {
                        } catch (Error er) {
                        } catch (Throwable th) {
                        }
                        Thread.sleep(10);
                    }
                } catch (InterruptedException ex) {
                }
            }
        });
        thread3.start();
    }

    /**
     * Stops game thread
     */
    public void stop() {
        try {
            if (thread1 != null) {
                thread1.interrupt();
            }
            if (thread2 != null) {
                thread2.interrupt();
            }
            if (thread3 != null) {
                thread3.interrupt();
            }
            rearrange = false;
            arrangeBalls(getGameScene().getBalls(), null);

            graphics.render(game);
        } catch (Exception ex) {
            System.err.println("Game.stop: " + ex.getLocalizedMessage());
        }
        changeStatus(S_NONE);
    }

    @Override
    public GameScene getGameScene() {
        return scene;
    }

    @Override
    synchronized public int changeStatus(int newStatus) {
        if (newStatus==status) return status;
        if (newStatus == S_WAIT_RIVAL && blankCycle)
            newStatus = S_BALL_SELECT;
        if(newStatus==S_NONE) return status = S_NONE;
        if(status==S_NONE){
            if(newStatus!=S_BALL_SELECT && newStatus!=S_WAIT_RIVAL)
                return status;
        }
        if(status==S_BALL_SELECT){
            if(newStatus!=S_MOVING && newStatus!=S_WAIT_RIVAL)
                return status;
        }
        if(status==S_MAKE_HIT){
            if(newStatus!=S_MOVING)
                return status;
        }
        if(status==S_MOVING){
            if(newStatus==S_MAKE_HIT)
                return status;
        }
        if(status==S_WAIT_RIVAL){
            if(newStatus!=S_BALL_SELECT && newStatus!=S_FINISH)
                return status;
        }
        return status = newStatus;
    }

    @Override
    synchronized public int getGameStatus() {
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
        if (graphics != null) {
            graphics.dispose();
        }
        events.clear();
        System.gc();
    }

    public boolean isFirst() {
        return first;
    }
    
    public void setScore(int score){
        this.score = score;
    }
    
    public int getScore(){
        return score;
    }
    
    public void setIPlayNext(boolean me){
        iPlayNext = me;
    }
    public boolean iPlayNext(){
        return iPlayNext;
    }
}
