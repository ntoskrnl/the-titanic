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
        T = 0;//Устанавливаем текущее время.
        dTnow = dT;
        importFromGlobal();//Получаем глобальные шары.
        while(T<dT){
            setDiskretTime();//Получаем время дискретизации.
            testWallCollisions(); //Проверяем столкновение шаров со стенками.
            testBallsCollisions(); //Проверяем столкновение шаров друг с другом.
            moveBalls(); //Двигаем шары.
            //Стоит добавить сюда тест на падение в лузу.
            T += dTnow;//Добавляем время дискретизации.
        }
        precious();//Учитываем трение.
        export2Global();//Конвентируем шары в глобальный формат.
    }

    /* Конструктор физического движка. */
    public yukiEngine(Game game){
        /* В качестве параметра передаётся экземпляр игры. */

        dT = (float) 0.0025; // Время дискретизации. Дефолтовское.
        dTnow = 0; // Время дискретизации. Текущее.

        balls = game.getGameScene().getBalls(); // Получаем все шары.
        bQuant = balls.length; // Число шаров.

  //      impList = new int[bQuant];
        conList = new int[bQuant];
        for(int i=0; i<bQuant; ++i){
  //          impList[i] = 0;
            conList[i] = 0;
        }

        // Получаем размеры стола.
        width = game.getGameScene().getBounds().getX();
        height = game.getGameScene().getBounds().getY();

        // Получаем радиус шариков.
        r = balls[0].getRadius();

        // Установка радиуса лузы.
        ultraPocketR = (float) (5*r);
        realPocketR = (float) (5*r);

        // Расстановка самих луз.
        posPocket = new double[6][2];
        posPocket[0][0] = width; posPocket[0][1] = height;
        posPocket[1][0] = width; posPocket[1][1] = 0;
        posPocket[2][0] = width; posPocket[2][1] = (-1)*height;
        posPocket[3][0] = (-1)*width; posPocket[3][1] = height;
        posPocket[4][0] = (-1)*width; posPocket[4][1] = 0;
        posPocket[5][0] = (-1)*width; posPocket[5][1] = (-1)*height;
    }

    private Ball[] balls; // Массив шаров для обработки.
//    private int impList[]; // Массив столкновений шаров.
    private int conList[]; // Массив состояний шаров.
                           //-1 - неактивен
                           // 0 - обычное состояние
                           // 1 - может попасть в лузу
                           // 2 - падает в лузу
    private int bQuant; // Число шаров.
    private float dT; // Добавочное время.
    private float dTnow;
    private float T; // Текущее время.
    private float bPos[][]; // Массив координат шаров.
    private float bVel[][]; // Массив скоростей шаров.
    /* Ширина и высота стола. */
    private double width;
    private double height;
    private double r; // Радиус шаров.

    private float ultraPocketR;// Эффективный радиус лузы.
    private float realPocketR;// Реальный радиус лузы.
    private double posPocket[][];// Положение луз.

    /* Процедура, проверяющая столкновение шаров друг с другом. */
    private void testBallsCollisions(){
        for( int i=0; i<bQuant; ++i)
            for( int q=i+1; q<bQuant; ++q){
                if ( (conList[i]>=0)&&(conList[q]>=0) )
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
            if (conList[i]==0){//Проверка на валидность.
                if ( ( abs(bPos[i][0])>(width/2-r)) && ((bPos[i][0]*bVel[i][0])>0)
                        && !isInPocked( bPos[i][0], bPos[i][1]) )
                        bVel[i][0] *= (-1);
                if ( ( abs(bPos[i][1])>(height/2-r) ) && ((bPos[i][1]*bVel[i][1])>0)
                        && !isInPocked( bPos[i][0], bPos[i][1]) )
                        bVel[i][1] *= (-1);
            }
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
                if (conList[i]>=0)
                    for( int q=0; q<3; ++q)
                        bPos[i][q] += bVel[i][q]*dTnow;
    }

    /* Процедуры связи с внешним миром. */
    private void importFromGlobal(){
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
    }
    private void export2Global(){
        for(int i=0; i<bQuant; ++i){
            Vector3D a = new Vector3D( bPos[i][0] , bPos[i][1], bPos[i][2] );
            balls[i].setCoordinates(a);
        }
        for(int i=0; i<bQuant; ++i){
            Vector3D a = new Vector3D( bVel[i][0] , bVel[i][1], bVel[i][2] );
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

    /* Максимум. */
    private double max(double a, double b){
        if (a>b) return a;
        return b;
    }

    /* Минимум. */
    private double min(double a, double b){
        if (a<b) return a;
        return b;
    }

    /* Процедура, дающая разрешение на падение в лузу. */
    private void selectPocketCandidates(){
        for( int i=0; i<bQuant; ++i)
            if (conList[i]==0)
               for( int q=0; q<6; ++q){
                   if (
                           ((posPocket[q][0]-bPos[i][0])*(posPocket[q][0]-bPos[i][0])+
                            (posPocket[q][1]-bPos[i][1])*(posPocket[q][1]-bPos[i][1]))
                            < ultraPocketR*ultraPocketR
                      )
                   conList[i] = 1;
                }
    }

    /* Процедура, забирающая разрешение на попадание в лузу. */
    private void killPocketCandidates(){
        for( int i=0; i<bQuant; ++i)
            if (conList[i]==1)
                conList[i] = 0;
    }

    /* Процедура, проверяющая попадание шаров в лузу. */
    private void testPocketHit(){
        for( int i=0; i<bQuant; ++i)
            if (conList[i]>0)
               for( int q=0; q<6; ++q){
                   if (
                           ((posPocket[q][0]-bPos[i][0])*(posPocket[q][0]-bPos[i][0])+
                           (posPocket[q][1]-bPos[i][1])*(posPocket[q][1]-bPos[i][1]))
                            < realPocketR*realPocketR
                      )
                   conList[i] = -1;
                }
    }

    /* Учитываем трение. */
    private void precious(){
        float Nt = (float) 1.5; //Коэфицент трения.
        for( int i=0; i<bQuant; ++i){
            float speed = (float) Math.sqrt((float)(bVel[i][0]*bVel[i][0]+bVel[i][1]*bVel[i][1]));
            if (speed<Nt){
                bVel[i][0] = 0;
                bVel[i][1] = 0;
            } else {
                bVel[i][0] -= bVel[i][0]/speed * Nt*dT*10;
                bVel[i][1] -= bVel[i][1]/speed * Nt*dT*10;
            }
        }
    }

   /* Процедура, определяющая время дискретизации. */
    private void setDiskretTime(){
        /*
         Время дискретизации определяется из максимальной скорости
         шарика. В случае если она равна нулю: посылается сообщение
         конвееру о том, что шарики остановились.
         */
        double max = 0;
        for( int i=0; i<bQuant; ++i){
            double tek = 0;
            for( int q=0; q<3; ++q)
                tek += bVel[i][q]*bVel[i][q];
//            tek = bVel[i][0]*bVel[i][0]+bVel[i][1]*bVel[i][1]+
//                    bVel[i][2]*bVel[i][2];
            if (tek>max)
                max = tek;
        }
        max = Math.sqrt(max); //Здесь находится максимальная скорость шарика.

        if (max==0){
            /*
             Здесь необходимо расположить посылку сообщения о том, что все
             шарики остановились. Если Антону вообще такое сообщение от физики
             нужно.
             */
            dTnow = dT;
        } else {
            double p = r/(4*max);
            dTnow = (float) min(min(p,dT),abs(dT - T) );
        }
    }

    /* Проверка на столкновение с лузами. */
    private boolean isInPocked(double x, double y){
        /* Условие на x. */
        if (abs((abs((float)((width-realPocketR)/2))-abs((float)x)))<r)
            return true;

        /* Условие на y. */
        if (abs((abs((float)((height-realPocketR)/2))-abs((float)y)))<r)
            return true;

        return false;
    }
}
