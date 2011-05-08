package titanic.basic;

/**
 * Interface represents the billiard key.
 * @author Danon
 */
public interface BilliardKey {
    /**
     * Which ball is selected at the moment?
     * @return Ball instance or null if none was selected
     */
    public Ball getBall();
    /**
     * Angle of the billiard key
     * @return angle value in radians
     */
    public float getAngle();

    /**
     * We need to know the power (impulse, F*dt) of the hit.
     * @return Relative value of the power in range (0, 1]
     */
    public float getPower();

    /**
     * Change current ball to hit with.
     * @param b new ball
     */
    public void changeBall(Ball b);

    /**
     * Set new angle of the billiard key.
     * @param a new angle in radians.
     */
    public void setAngle(float a);

    /**
     * Sets relative value of power of the hit
     * @param p float value in range (0,1]
     */
    public void setPower(float p);

    /**
     * Verifies the angle of the key.
     * @return false if angle is invalid (there are intersections with balls), otherwise - true.
     */
    public boolean validAngle(Game game);

    public void makeHit();
}
