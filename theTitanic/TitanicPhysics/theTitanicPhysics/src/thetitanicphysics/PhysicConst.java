package thetitanicphysics;

/**
 *
 * @author AntiZerg
 */
/* Класс для хранения физических констант. */
public class PhysicConst {
    public final static int QBALL = 16; // Число шариков.
    public final static double BRADIUS = 5; // Радиус шарика.
    public final static double TRCOEFFICENT = 0.5; // Ускорение трения.

    /* Массив шариков. */
    public PhysicalBall pBalls [] = new PhysicalBall[ QBALL ];
}
