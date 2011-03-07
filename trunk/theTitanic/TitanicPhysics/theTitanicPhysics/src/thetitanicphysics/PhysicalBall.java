package thetitanicphysics;

/**
 *
 * @author AntiZerg
 */

 /* Класс описывающий шар, точнее его физическую составляющую.
          Никакой инкапсуляции (она не нужна).                 */
public class PhysicalBall{
    
    public PhysicalBall(){ id = 0; x = 0; y = 0;  init(); }
    public PhysicalBall(double a, double b){ id = 0; x = a; y = b; init(); }
    public PhysicalBall(int N, double a, double b){ id = N; x = a; y = b; init(); }

    public int id = 0; // id шарика.
    public boolean isOnBoard = true; // Находится ли шар на столе.
        /*
         * impLst[] решает следующую проблему:
         * в каждом такте цикла SimplyPhysics проверяется расстояние
         * между шарами, если оно меньше 2r (r - радиус шара), происходит
         * impact() между ними (говоря по простому, обмен импульсами). Но
         * на следующем шаге расстояние между ними может оказаться
         * вновь меньше (шары не успевают разойтись) 2r и произойдёт
         * нежелательный обмен импульсами. impLst[] просто массив флагов,
         * решающую эту проблему самым простым методом.
         */
    public boolean impLst[] = new boolean[ PhysicConst.QBALL ];

    /* Часть конструктора: инициализация impLst массива. */
    private void init(){
        for (int i=0; i<PhysicConst.QBALL; ++i)
            impLst[i] = false;
    }

    /* Координаты шарика */
    public double x;
    public double y;

    /* Скорость шарика. */
    public Vec2d v = new Vec2d(0,0);//Начальная скорость (0,0).

    /* Печать шарика. */
    public void print(){
        System.out.println("Physic ball.");
        System.out.printf("id=%d\n",id);
        System.out.printf("(%.1f , %.1f)",x,y);
        System.out.print(" velocity ");
        System.out.printf("(%.1f , %.1f)",v.x,v.y);
        System.out.println();
        for (int i=0; i<PhysicConst.QBALL; ++i)
            System.out.printf("%b ",impLst[i]);
        System.out.println();
    }
}
