package thetitanicphysics;

/**
 *
 * @author AntiZerg
 */

/* Простенький класс для работы с векторами. */
public class Vec2d {
    
    public Vec2d() { x = 0; y = 0; }
    public Vec2d( double X, double Y ){ x = X; y = Y; }
    
    /* (x,y) координаты векторов. */
    public double x;
    public double y;
    
    /* Скалярное умножение. */
    public double scalMl( Vec2d a, Vec2d b ){
        return a.x * b.x + a.y * a.y;
    }

    /* Сумма двух векторов. */
    public Vec2d add( Vec2d a, Vec2d b ){
        Vec2d c = new Vec2d( a.x + b.x, a.y + b.y);
        return c;
    }
    
    /*  Умножение вектора на число. */
    public Vec2d Mul( double a, Vec2d b ){
        Vec2d c = new Vec2d( a * b.x, a * b.y);
        return c;
    }
    public Vec2d Mul( Vec2d a, double b ){
        Vec2d c = new Vec2d( a.x * b , a.y * b);
        return c;
    }
}
