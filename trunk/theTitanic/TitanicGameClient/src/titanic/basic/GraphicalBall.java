package titanic.basic;

import java.awt.Color;

/**
 * This interface declares parameters of the ball,
 * which are required by Graphic Engine
 * @author danon
 */
public interface GraphicalBall {
    /**
     * Returns current color of the ball
     * @return Current color
     */
    public Color getColor();

    /**
     * Sets new ball color to <code>c</code>.
     * @param c New color
     */
    public void setColor(Color c);

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
     * Set new coordinates of the ball to a.
     * @param a Radius vector (new coordinates)
     */
    public void setCoordinates(Vector3D a);

    /**
     * Coordinates of the ball (radius-vector, 2D)
     * @return Current coordinates of the ball (Vector3D)
     */
    public Vector3D getCoordinates();

    /**
     * Check wether the ball is selected or not (to hit).
     * @return TRUE if the ball is selected, or FALSE if this ball is not selected
     */
    public boolean isSelected();
    
}
