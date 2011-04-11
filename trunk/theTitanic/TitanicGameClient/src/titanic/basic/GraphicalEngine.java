package titanic.basic;

import java.awt.Container;

/**
 * Interface for Graphical engine.
 * @author danon
 */
public interface GraphicalEngine {

    /**
     * Sets the area where to render the game scene (where to add Canvas3D)
     * @param area AWT Container to paint on (e.g. JPanel instance).
     */
    public void setRenderingArea(Container area);

    /**
     * Renders the game scene.
     * @param game The instance of the game to render.
     */
    public void render(Game game);

    /**
     * Releases all Java3D objects to free memory.
     * Do not call any methods after calling this one.
     */
    public void dispose();
}
