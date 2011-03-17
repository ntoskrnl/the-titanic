package titanic.basic;

/**
 *
 * @author danon
 */
public abstract class GameScene {

    /**
     * Returns balls which are on the table.
     * @return Array of Ball
     */
    public abstract Ball[] getBalls();

    /**
     * Returns bounds of the table - Vector3D(x, y),
     * where x,y are positive float values.
     * The center of the table is at the origin.
     *
     * <p style="color: red;">Thowgh, I think this method must be reconstructed.</p>
     * @return bounds vector
     */
    public abstract Vector3D getBounds();
}
