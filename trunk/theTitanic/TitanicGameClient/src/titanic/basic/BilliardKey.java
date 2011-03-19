package titanic.basic;

/**
 * Interface represents the billiard key.
 * @author Danon
 */
public interface BilliardKey {
    public Ball getBall();
    public float getAngle();
    public float getPower();

    public void setBall(Ball b);
    public void setAngle(float a);
    public void setPower(float p);
}
