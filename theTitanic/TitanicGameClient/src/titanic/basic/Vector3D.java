package titanic.basic;

/**
 * This class represents a 3-Dimensional vector
 * and provides basic operations of vector algebra.
 * @author danon
 */
public class Vector3D {

    private float x, y, z;

    public Vector3D() {
        x=0; y=0; z=0;
    }

    public Vector3D(float x, float y){
        this.x = x;
        this.y = y;
        this.z = 0;
    }

    public Vector3D(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(Vector3D v){
        x = v.getX();
        y = v.getY();
        z = v.getZ();
    }

    /**
     * @return the x
     */
    public float getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public float getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * @return the x
     */
    public float getZ() {
        return z;
    }

    /**
     * @param x the x to set
     */
    public void setZ(float z) {
        this.z = z;
    }

    /**
     * Multiply current vector by a scalar float value c.
     * @return The result of multiplication.
     */
    public Vector3D multiply(float c){
        return new Vector3D(getX()*c, getY()*c, getZ()*c);
    }

    /**
     * Scalar multiplication of this vector and <code>v</code>
     * @return The result of the multiplication.
     */
    public float multiply(Vector3D v){
        return v.getX()*getX() + v.getY()*getY() + v.getZ()*getZ();
    }

    /**
     * Summ of specified vector v and this vector.
     * @return Result of the summ
     */
    public Vector3D add(Vector3D v){
        return new Vector3D(this.getX()+v.getX(), this.getY()+v.getY(), this.getZ()+v.getZ());
    }

    /**
     * Norm of the vector (abs(this))
     * @return norm af the vector
     */
    public double getNorm(){
        return Math.sqrt(this.multiply(this));
    }

    @Override
    public String toString(){
        String s = this.getClass().getName();
        s+="{x="+getX()+"; y="+getY()+"; z=" +getZ()+";}";
        return s;
    }
    
}
