package titanic.basic;

import java.awt.Color;

/**
 * This interface describes ball with coordinates and radius
 * @author danon
 */
public class Ball implements PhysicalBall, GraphicalBall {
    private float radius;
    private float mass;
    private Vector2D speed;
    private Vector2D coordinates;
    private Color color;
    private int id;

    public Ball(){
        color = Color.WHITE;
        speed = new Vector2D(0, 0);
        id = 0;
        coordinates = new Vector2D(0, 0);
        mass = 0;
        radius = 0;
    }

    public Ball(float x, float y){
        color = Color.WHITE;
        speed = new Vector2D(0, 0);
        id = 0;
        coordinates = new Vector2D(x, y);
        mass = 0;
        radius = 0;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float r) {
        radius = r;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float m) {
        mass = m;
    }

    public Vector2D getSpeed() {
        return speed;
    }

    public void setSpeed(Vector2D v) {
        speed = v;
    }

    public void setCoordinates(Vector2D a) {
        coordinates = a;
    }

    public Vector2D getCoordinates() {
        return coordinates;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color c) {
        color = c;
    }

    public String toString(){
        return this.getClass().getName();
    }
    
}
