package titanic.basic;

import java.awt.Color;

/**
 * This class represents a ball with coordinates and radius
 * @author danon
 */
public class Ball implements PhysicalBall, GraphicalBall {
    private float radius;
    private float mass;
    private Vector3D speed;
    private Vector3D coordinates;
    private Color color;
    private int id;
    private boolean selected;
    private static final double EPS = 1e-7;

    public Ball(){
        color = Color.WHITE;
        speed = new Vector3D(0, 0);
        id = 0;
        coordinates = new Vector3D(0, 0);
        mass = 0;
        radius = 0;
        selected = false;
    }

    public Ball(float x, float y){
        color = Color.WHITE;
        speed = new Vector3D(0, 0);
        id = 0;
        coordinates = new Vector3D(x, y);
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

    public Vector3D getSpeed() {
        return speed;
    }

    public void setSpeed(Vector3D v) {
        speed = v;
    }

    public void setCoordinates(Vector3D a) {
        coordinates = a;
    }

    public Vector3D getCoordinates() {
        return coordinates;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color c) {
        color = c;
    }

    @Override
    public String toString(){
        String s = this.getClass().getName();
        s+="{id="+getId()+"; R="+getRadius()+"; (x,y,z)="+getCoordinates()+
                "; velocity="+getSpeed()+"; color="+getColor()+";}";
        return s;
    }

    public boolean isActive(){
        return this.getSpeed().getNorm() > EPS;
    }

    public boolean isSelected(){
        return selected;
    }

    public void setSelected(boolean state){
        selected = state;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }
}
