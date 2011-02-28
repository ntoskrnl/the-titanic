package titanic.basic;

/**
 * This abstract class represents the Game.
 * @author danon
 */
public abstract class Game {
    /**
     * Retruns game scene
     * @return Game scene of null.
     */
    public abstract GameScene getGameScene();

    /**
     * Chenges current status of the game.
     * @param gameStatus New game status.
     * @return final game status.
     */
    public abstract int changeStatus(int gameStatus);


    /**
     * Waiting for user to make next impact. (no physics)
     */
    public static final int STATUS_WAIT_COMMAND = 0;
    /**
     * Now the balls are moving (physics is active)
     */
    public static final int STATUS_MOVE = 1;
    /**
     * The game is over. It's time for the final verdict.
     */
    public static final int STATUS_FINISH = 2;
    /**
     * Cofee break or something like that (no physics).
     */
    public static final int STATUS_BREAK = 3;
}
