package titanic.basic;

/**
 * This interface extends Ball with its physical parameters.
 * It is used by PhysicalEngine
 * @author danon
 */
public interface PhysicalBall {

    /**
     * Radius of the ball
     * @return radius
     */
    public float getRadius();

    /**
     * Sets new radius for a ball
     * @param r New radius
     */
    public void setRadius(float r);

    /**
     * Set new coordinates of the ball to a.
     * @param a Radius vector (new coordinates)
     */


    /**
     * Mass of the ball in standard units
     * @return Mass of the ball
     */
    public float getMass();

    /**
     * Sets mass of the ball to new value m.
     * @param m New mass in standart units
     */
    public void setMass(float m);

    /**
     * Returns vector of the speed.
     * @return Vector of the speed.
     */
    public Vector2D getSpeed();

    /**
     * Sets new speed.
     * @param v New speed.
     */
    public void setSpeed(Vector2D v);


    /**
     * Set new coordinates of the ball to a.
     * @param a Radius vector (new coordinates)
     */
    public void setCoordinates(Vector2D a);

    /**
     * Coordinates of the ball (radius-vector, 2D)
     * @return Current coordinates of the ball (Vector2D)
     */
    public Vector2D getCoordinates();
}
