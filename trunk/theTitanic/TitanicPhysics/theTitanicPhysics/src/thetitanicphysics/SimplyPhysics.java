package thetitanicphysics;

/**
 *
 * @author Dante
 */

public class SimplyPhysics {
    public SimplyPhysics(){        
    }
           /* Удар между a и b шариком. */
    private void impact(PhysicalBall a, PhysicalBall b){
           /* Здесь всё просто: закон сохранения импульса и аналит:-) */

        // Вектор соединяющий середины шаров.
        Vec2d c = new Vec2d( a.x - b.x, a.y - b.y);
        
        // Вектор ему ортогональный.
        Vec2d d = new Vec2d( -c.y, c.x);

        // Важный момент: поскольку шары столкнулись, расстояние между ними
        // равно 2*BRADIUS!
    }

}
