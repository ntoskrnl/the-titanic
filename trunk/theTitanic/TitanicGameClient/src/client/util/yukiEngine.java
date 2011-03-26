/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client.util;
import client.util.event.ImpactEvent;
import titanic.basic.*;

/**
 *
 * @author AntiZerg
 */

/* Невероятно продвинутая модель физики. */
public class yukiEngine implements PhysicalEngine {

    public void compute() {
        testWallCollisions(); //Проверяем столкновение шаров со стенками.
        testBallsCollisions(); //Проверяем столкновение шаров друг с другом.
        moveBalls(); //Двигаем шары.
        convert2Global();//Конвентируем шары в глобальный формат.
    }

    /* Конструктор физического движка. */
    public yukiEngine(Game game){
        /* В качестве параметра передаётся экземпляр игры. */

        dT = (float) 0.25; // Время дискретизации.
        balls = game.getGameScene().getBalls(); // Получаем все шары.
        bQuant = balls.length; // Число шаров.

  //      impList = new int[bQuant];
        conList = new int[bQuant];
        for(int i=0; i<bQuant; ++i){
  //          impList[i] = 0;
            conList[i] = 0;
        }

        // Получаем положение шаров.
        bPos = new float [bQuant][3];
        for(int i=0; i<bQuant; ++i){
            Vector3D a = balls[i].getCoordinates();
            bPos[i][0] = a.getX();
            bPos[i][1] = a.getY();
            bPos[i][2] = a.getZ();
        }

        // Получаем скорости шаров.
        bVel = new float [bQuant][3];
        for(int i=0; i<bQuant; ++i){
            Vector3D a = balls[i].getSpeed();
            bVel[i][0] = a.getX();
            bVel[i][1] = a.getY();
            bVel[i][2] = a.getZ();
        }

        // Получаем размеры стола.
        width = game.getGameScene().getBounds().getX();
        height = game.getGameScene().getBounds().getY();

        // Получаем радиус шариков.
        r = balls[0].getRadius();
    }

    private Ball[] balls; // Массив шаров для обработки.
//    private int impList[]; // Массив столкновений шаров.
    private int conList[]; // Массив состояний шаров.
    private int bQuant; // Число шаров.
    private float dT; // Добавочное время.
    private float bPos[][]; // Массив координат шаров.
    private float bVel[][]; // Массив скоростей шаров.
    /* Ширина и высота стола. */
    private double width;
    private double height;
    private double r; // Радиус шаров.

    /* Процедура, проверяющая столкновение шаров друг с другом. */
    private void testBallsCollisions(){
        for( int i=0; i<bQuant; ++i)
            for( int q=i+1; q<bQuant; ++q){
                //Расстояние между шарами.
                if (
                   ((bPos[i][0]-bPos[q][0])*(bPos[i][0]-bPos[q][0])
                   +(bPos[i][1]-bPos[q][1])*(bPos[i][1]-bPos[q][1])
                   +(bPos[i][2]-bPos[q][2])*(bPos[i][2]-bPos[q][2]))
                        < (4*r*r)
                   ){

//                   if ( (impList[i]!=q+1) && (impList[q]!=i+1) ){
//                       impList[i] = q+1;
//                       impList[q] = i+1;
                       impact( i , q );
                   }
//
//                } else
//                   if ( (impList[i]==q+1) && (impList[q]==i+1) ){
//                       impList[i] = 0;
//                       impList[q] = 0;
//                   }
            }
    }

    /* Процедура, реагирующая на столкновение со стенками. */
    private void testWallCollisions(){
        for( int i=0; i<bQuant; ++i){
            if ( ( abs(bPos[i][0])>(width/2-r)) && ((bPos[i][0]*bVel[i][0])>0) )
                    bVel[i][0] *= (-1);
            if ( ( abs(bPos[i][1])>(height/2-r) ) && ((bPos[i][1]*bVel[i][1])>0) )
                    bVel[i][1] *= (-1);
        }
/*
        for( int i=0; i<bQuant; ++i){
            if (bPos[i][0]>(width/2-r)){
                if (impList[i]!=-1)
                    bVel[i][0] *= (-1);
            } else if (impList[i]==-1)
                impList[i] = 0;

            if (bPos[i][0]<((-1)*width/2+r)){
                if (impList[i]!=-2)
                    bVel[i][0] *= (-1);
            } else if (impList[i]==-2)
                impList[i] = 0;

            if (bPos[i][1]>(height/2-r)){
                if (impList[i]!=-3)
                    bVel[i][1] *= (-1);
            } else if (impList[i]==-3)
                impList[i] = 0;

            if (bPos[i][1]<((-1)*height/2+r)){
                if (impList[i]!=-4)
                    bVel[i][1] *= (-1);
            } else if (impList[i]==-4)
                impList[i] = 0;
        }
 */
    }

    /* Процедура, перемещающая шары. */
    private void moveBalls(){
            /* Добавляем к координатам шаров скорость шаров, помноженную на dT. */
            for( int i=0; i<bQuant; ++i )
                for( int q=0; q<3; ++q)
                    bPos[i][q] += bVel[i][q]*dT;
    }

    /* Процедура связи с внешним миром. */
    private void convert2Global(){
        for(int i=0; i<bQuant; ++i){
            Vector3D a = new Vector3D( bPos[i][0] , bPos[i][1] , bPos[i][2] );
            balls[i].setCoordinates(a);
        }
        for(int i=0; i<bQuant; ++i){
            Vector3D a = new Vector3D( bVel[i][0] , bVel[i][1] , bPos[i][2]);
            balls[i].setSpeed(a);
        }
    }

    /* Обработчик столкновений. */
    private void impact(int b1, int b2){
        //(x,y,z) - координаты вектора, соединяющего середины шаров.
        float x = bPos[b1][0] - bPos[b2][0];
        float y = bPos[b1][1] - bPos[b2][1];
        float z = bPos[b1][2] - bPos[b2][2];

        // Расстояние между центрами.
        float ro = (float) Math.sqrt(x*x+y*y+z*z);

        // Нормировка вектора (x,y,z)
        x /= ro; y /= ro; z /= ro;

        // v1 и v2 проекции скоростей шаров на вышенайденный вектор.
        float v1 = x*bVel[b1][0] + y*bVel[b1][1] + z*bVel[b1][2];
        float v2 = x*bVel[b2][0] + y*bVel[b2][1] + z*bVel[b2][2];

        float p = v2-v1;

        //Получаем скорости после столкновения.
        if (p>0){
            bVel[b1][0] += p*x; bVel[b2][0] -= p*x;
            bVel[b1][1] += p*y; bVel[b2][1] -= p*y;
            bVel[b1][2] += p*z; bVel[b2][2] -= p*z;
        }
    }

    /* Модуль. */
    private float abs(float a){
        if (a<0) return (-1)*a;
        return a;
    }

    /* Сигнум. */
    private float sgn(float a){
        if (a<0) return -1;
        if (a>0) return 1;
        return 0;
    }
}
