package titanic.basic;

/**
 * This interface declares methods for Physical Engine
 * @author danon
 */
public interface PhysicalEngine {
    /**
     * This method computes next status of the game
     * @param game Instance of the Game
     */
    public void compute(Game game);
}
