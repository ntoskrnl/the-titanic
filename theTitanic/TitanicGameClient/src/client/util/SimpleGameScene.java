package client.util;

import java.awt.Container;
import titanic.basic.Ball;
import titanic.basic.GameScene;
import titanic.basic.Vector3D;

/**
 * Simple implementation of class GameScene
 * @author danon
 */
class SimpleGameScene extends GameScene {
    private Container renderingArea;
    private Ball[] balls;
    private Vector3D bounds;

    public SimpleGameScene(Container c, Ball[] b){
        renderingArea = c;
        balls = b;
        bounds = new Vector3D((float)c.getWidth(), (float)c.getHeight());
    }

    @Override
    public Ball[] getBalls() {
        return balls;
    }

    @Override
    public Vector3D getBounds() {
        return bounds;
    }

}
