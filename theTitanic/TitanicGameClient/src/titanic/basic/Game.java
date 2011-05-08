package titanic.basic;

/**
 * This abstract class represents the Game.
 * @author danon
 */
public abstract class Game {
    /**
     * Returns game scene
     * @return Game scene of null.
     */
    public abstract GameScene getGameScene();

    /**
     * Changes current status of the game.
     * @param gameStatus New game status.
     * @return final game status.
     */
    public abstract int changeStatus(int gameStatus);

    /**
     * Returns current status as <code>int</code>.
     */
    public abstract int getGameStatus();

    public abstract EventPipeLine getEventPipeLine();
    public abstract GraphicalEngine getGraphicalEngine();
    public abstract PhysicalEngine getPhysicalEngine();

    /**
     * Returns a billiard key object.
     */
    public abstract BilliardKey getBilliardKey();

    /**
     * Dispose all objects
     */
    public abstract void dispose();
    
    public abstract boolean isFirst();
    
    public abstract boolean iPlayNext();
    public abstract void setIPlayNext(boolean me);
    
    public abstract void setScore(int score);
    public abstract int getScore();
    
    public abstract void makeHit(Ball b);

    /**
     * Waiting for user to make next impact. (no physics)
     */
    public static final int S_BALL_SELECT = 1;
    /**
     * Now the balls are moving (physics is active)
     */
    public static final int S_MOVING = 2;
    /**
     * Animation of the key to simulate a hit.
     */
    public static final int S_MAKE_HIT = 3;
    /**
     * The game is over. It's time for the final verdict.
     */
    public static final int S_FINISH = 4;
    /**
     * Coffee break or something like that (no physics).
     */
    public static final int S_PAUSE = 5;
    /**
     * Wait for rival to make a hit
     */
    public static final int S_WAIT_RIVAL = 6;

    public static final int S_NONE = 0;
}
