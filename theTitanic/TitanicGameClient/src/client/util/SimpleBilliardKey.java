package client.util;

import titanic.basic.Ball;
import titanic.basic.BilliardKey;

/**
 * Represents billiard key with its position and power of the hit.
 * @author Danon
 */
public class SimpleBilliardKey implements BilliardKey {
    private Ball ball;
    private float angle;
    private float power;

    public SimpleBilliardKey() {
        ball = null;
        angle = 0;
        power = 0;
    }

    public Ball getBall() {
        return ball;
    }

    public float getAngle() {
        return angle;
    }

    public float getPower() {
        return power;
    }

    public void setBall(Ball b) {
        ball = b;
    }

    public void setAngle(float a) {
        angle = a;
    }

    public void setPower(float p) {
        power = p;
    }

}
