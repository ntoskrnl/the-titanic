package client.util;

import client.Main;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;
import javax.swing.Timer;
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
    private Thread thread1, thread2, thread3, thread4;
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
    private String gameID;
    private int rivalStatus;
    private Timer timer;
    /**
     * Constructs new Game instance and sets c as default rendering area.
     * @param c JPanel or other container where to render the scene
     * @param first Is it the first player?
     */
    public SimpleGame(Container c, boolean first, UserProfile rvl) {
        Ball[] balls = new Ball[16];
        renderingArea = c;
        this.first = first;
        this.rival = rvl;
        
        if(this.rival==null) this.rival = new UserProfile(0);
        this.rival.update();
        
        me = new UserProfile(0);
        me.update();

        if(me.equals(this.rival)) blankCycle = true;
        else blankCycle = false;
        
        requestGameID();
        if(!blankCycle && gameID==null)
            throw new UnsupportedOperationException("Unable to get GemeID. Is this operation supported?");

        scene = new SimpleGameScene(c, balls);
        key = new SimpleBilliardKey();
        key.changeBall(balls[15]);

        arrangeBalls(balls, scene.getBounds());

        events = new SimpleEventPipeLine();

        physics = new yukiEngine(this);

        graphics = new Graphics3D(this);
        graphics.setRenderingArea(c);

        
        // Starting server synchronization thread
        thread4 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!thread4.isInterrupted()) {
                        Thread.sleep(250);
                        if(game.getGameStatus()==S_NONE || game.getGameStatus()==S_PAUSE)
                            continue;
                        if(game.getGameStatus()==S_WAIT_RIVAL){
                            requestRivalsHit();
                            requestRivalsStatus();
                        }
                        
                    }
                } catch (InterruptedException ex) {
                }
            }
        });
        
        
       
        game = this;
        //graphics.render(this);
        
        thread4.start();
        
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(getGameStatus()==Game.S_WAIT_RIVAL)
                    syncBalls();
                else sendBalls();
            }
        });
        timer.start();
        start();
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
    public final void start() {
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
                            if (status == S_MOVING || rivalStatus == S_MOVING) {
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
            if (thread4 != null) {
                thread4.interrupt();
            }
            rearrange = false;
        } catch (Exception ex) {
            System.err.println("Game.stop: " + ex.getLocalizedMessage());
        }
        changeStatus(S_FINISH);
        if(!blankCycle)
            Main.server.commandAndResponse(100, "GAME FINISH", gameID, Main.server.secret);
        timer.stop();
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
            if(newStatus!=S_MAKE_HIT && newStatus!=S_WAIT_RIVAL)
                return status;
        }
        if(status==S_MAKE_HIT){
            if(newStatus!=S_MOVING||newStatus!=S_BALL_SELECT)
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
        status = newStatus;
        sendMyStatus();
        return status;
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
        thread4.interrupt();
        renderingArea.removeAll();
        if (graphics != null) {
            graphics.dispose();
        }
        events.clear();
        System.runFinalization();
        System.gc();
    }

    @Override
    public boolean isFirst() {
        return first;
    }
    
    @Override
    public void setScore(int score){
        this.score = score;
    }
    
    @Override
    public int getScore(){
        return score;
    }
    
    @Override
    public void setIPlayNext(boolean me){
        iPlayNext = me;
    }
    @Override
    public boolean iPlayNext(){
        return iPlayNext;
    }
    
    private void requestRivalsStatus(){
        if(blankCycle) return;
        String[] r = Main.server.commandAndResponse(100,"GAME GET STATUS", gameID, Main.server.secret);
        if(!r[0].equalsIgnoreCase("success")){
            System.err.println("Failed to request rival's status!");
            return;
        }
        int s = Integer.parseInt(r[1]);
        rivalStatus = s;
        if(s!=S_WAIT_RIVAL && s!=S_NONE){
            status = S_WAIT_RIVAL;
            sendMyStatus();
        } else if(status==S_WAIT_RIVAL && s==S_WAIT_RIVAL){
            status = S_BALL_SELECT;
            sendMyStatus();
        }
        
    }
    
    private void sendMyStatus(){
        if(blankCycle) return;
        String[] r = Main.server.commandAndResponse(100, "GAME SET STATUS", gameID, 
                getGameStatus()+"", Main.server.secret);
        if(!r[0].equalsIgnoreCase("success")){
            System.err.println("Failed to send game status!");
            return;
        }
    }

    @Override
    public void makeHit(Ball b, float speed, float angle) {
        status = S_MOVING;
        sendMyStatus();
        if(blankCycle) return;
        String[] r = Main.server.commandAndResponse(100, "GAME MAKE HIT", gameID,
                b.getId()+"", speed+"", angle+"", Main.server.secret);
        if(!r[0].equalsIgnoreCase("success")){
            return;
        }
        
    }
    
    private void requestGameID(){
        if(blankCycle) return;
        String[] r = Main.server.commandAndResponse(100, "GAME GET ID", 
                me.getId()+"", rival.getId()+"", Main.server.secret);
        if(!r[0].equalsIgnoreCase("success")){
            return;
        }
        gameID = r[1].trim();
    }
    
    private void requestRivalsHit(){
        if(blankCycle) return;
        String[] r = Main.server.commandAndResponse(100,"GAME GET HIT", gameID, Main.server.secret);
        if(!r[0].equalsIgnoreCase("success")){
            //System.err.println("Failed to request game hit!");
            return;
        }
        try{
            int b = Integer.parseInt(r[1]);
            float s = Float.parseFloat(r[2]);
            float a = Float.parseFloat(r[3]);
            System.out.println("Rival's hit: ball = "+r[1]+"; power = "+r[2]+"; angle = "+r[3]);
            getGameScene().getBalls()[b].setSpeed(new Vector3D(s*(float)Math.cos(a)*20, 
                    s*(float)Math.sin(a)*20));
            rivalStatus = S_MOVING;
        } catch (Exception ex){}
    }
    
    public void sendBalls(){
        if(blankCycle) return;
        if(status!=S_BALL_SELECT && status!=S_MOVING && status!=S_WAIT_RIVAL) 
            return;
        try{
            Ball[] balls = getGameScene().getBalls();
            String[] a = new String[balls.length+2];
            a[0] = gameID;
            a[1] = Main.server.secret;
            synchronized(balls){       
                for(int i=0;i<balls.length;i++){
                    String t = i + " " + balls[i].getCoordinates().getX()+" " +
                            " " + balls[i].getCoordinates().getY() + " "
                            + balls[i].getSpeed().getX() + " " + balls[i].getSpeed().getY()
                            + " " + balls[i].isActive();
                    a[i+2] = t;
                }
            }
            String[] r = Main.server.commandAndResponse(500,"GAME SEND BALLS", a);
            if(!r[0].equalsIgnoreCase("success")){
                System.err.println("Failed to send balls!");
                return;
            }
        } catch (Exception ex){}
    }
    
    public void syncBalls(){
        if(blankCycle) return;
        if(rivalStatus!=S_WAIT_RIVAL||rivalStatus!=S_MOVING||rivalStatus!=S_BALL_SELECT) 
            return;
        String[] r = Main.server.commandAndResponse(300,"GAME SYNC BALLS", gameID, Main.server.secret);
        if(!r[0].equalsIgnoreCase("success")){
            System.err.println("Failed to sync!");
            return;
        }
        try{
            Ball[] balls = getGameScene().getBalls();
            synchronized(balls){
                for(int i=0;i<16;i++){
                    StringTokenizer stk = new StringTokenizer(r[i+1]);
                    int id = Integer.parseInt(stk.nextToken());
                    float x = Float.parseFloat(stk.nextToken());
                    float y = Float.parseFloat(stk.nextToken());
                    float vx = Float.parseFloat(stk.nextToken());
                    float vy = Float.parseFloat(stk.nextToken());
                    boolean active = Boolean.parseBoolean(stk.nextToken());
                    balls[id].setCoordinates(new Vector3D(x, y));
                    balls[id].setSpeed(new Vector3D(vx, vy));
                    balls[id].setActive(active);   
                }
            }
            
        } catch (Exception ex){}
    }
    
    public int getRivalStatus(){
        requestRivalsStatus();
        return rivalStatus;
    }
}
