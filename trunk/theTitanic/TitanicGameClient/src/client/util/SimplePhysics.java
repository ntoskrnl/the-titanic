package client.util;
import client.util.event.ImpactEvent;
import titanic.basic.*;
/**
 *
 * @author AntiZerg
 */
/* Простейшая модель физики: будет работать эта - добавлю ( перепишу :-( )
        третье измерение и вращение шариков.    */
public class SimplePhysics implements PhysicalEngine {
    /* Конструктор физики. */
    public SimplePhysics(Game game1){
        game = game1;

        /* Как же Java отличается от C++! Наверно это удобно когда привыкнешь.*/
        balls = game.getGameScene().getBalls(); // Получаем все шары.
        BQ = balls.length; // Число шаров.

        uy = 0;

        /* Потом напишу, для чего этот массив. */
        impDet = new boolean [BQ+4][BQ+4];
        reject = 0;

        impLst = new int [BQ];
        for(int i=0; i<BQ; ++i)
           impLst[i] = 0;

        f = SimplePhysicsConst.N; //Получаем значение коэффицента трения.
    }

    Game game;// Экземпляр игры.
    Ball[] balls; // Получаем все шары.
    int BQ; // Число шаров.
    int reject; // Время до режекции.
    boolean impDet[][];
    double r1;
    double r2;
    int uy;
    double f; // Коэффицент трения.
    int impLst[];

    /* Режектор. */
    private void clean(){
        for(int i=0; i<BQ+4; ++i)
            for(int j=0; j<BQ+4; ++j)
                impDet[i][j] = false;
    }

    /* Обработчик столкновений. */
    private void impact(Ball a, Ball b){

<<<<<<< .mine
=======
        game.getEventPipeLine().add(new ImpactEvent(game, a.getSpeed().add(b.getSpeed()).getNorm()));
>>>>>>> .r69
        uy++;

        Vector3D pa = a.getCoordinates();
        Vector3D pb = b.getCoordinates();

        Vector3D va = a.getSpeed();
        Vector3D vb = b.getSpeed();

        // Вектор, соединяющий середины векторов.
        Vector3D c = new Vector3D();
            c.setX( (float) ( pa.getX() - pb.getX() ) );
            c.setY( (float) ( pa.getY() - pb.getY() ) );

        // Создаём событие: шары столкнулись.
        Vector3D pt = a.getSpeed().add(b.getSpeed());
        game.getEventPipeLine().add(new ImpactEvent( pt.multiply(c) ));

        // Расстояние между шарами.
        float ab = c.getX()*c.getX() + c.getY()*c.getY();

        // Вектор, ему ортогональный.
        Vector3D d = new Vector3D();
            d.setX( (float) c.getY() );
            d.setY( (float) (-1) * c.getX() );

        Vector3D a2 = new Vector3D();
            a2 = ( c.multiply(vb.multiply(c)) ).add( d.multiply(va.multiply(d)) )
                   .multiply( (float) (1/(ab)) );

        Vector3D b2 = new Vector3D();
            b2 = ( c.multiply(va.multiply(c)) ).add( d.multiply(vb.multiply(d)) )
                   .multiply( (float) (1/(ab)) );

         // Присваеваем новые скорости.
            a.setSpeed( a2 );
            b.setSpeed( b2 );
    }

        /* Единственный паблик-метод: его задача по заданному
         расположению шаров и их скоростей вычислить их местонахождение
         через квант времени. */
    public void compute() {
     synchronized(game.getGameScene().getBalls()){



        /* Здесь неплохо бы разместить различные проверки на допустимость,
         но я не представляю себе какие. */

//        System.out.println("Physics iteration.");
        double Quant = 1; // Заданный квант времени.
        double Delta = 0.5; // Добавочное рассчётное время.
        double t = 0; // Текущее время.

        /* Все шары одного радиуса, пока. */
        r1 = balls[0].getRadius();
        r2 = balls[0].getRadius()*balls[0].getRadius();
        /* Ширина и высота стола. */
        double width = game.getGameScene().getBounds().getX();
        double height = game.getGameScene().getBounds().getY();

        if (SimplePhysicsConst.PhysicsModel==1){

<<<<<<< .mine
        if (((++reject)%SimplePhysicsConst.rej)==0) clean();
=======
        if (((++reject)%10)==0) clean();
>>>>>>> .r69
            /* Проверяем, не столкнулся ли какой шар со стенкой. */
            /* Очень важно вставить сюда проверку на валидность! */
            for( int i=0; i<BQ; ++i ){
                Vector3D coord = balls[i].getCoordinates();
                double x = coord.getX();
                double y = coord.getY();

                Vector3D velo = balls[i].getSpeed();
                double vx = velo.getX();
                double vy = velo.getY();

                /* Столкновение с верхней стенкой. */
                if (y>height/2){
                    if (impDet[i][BQ] == false){
                        impDet[i][BQ] = true;
                        vy = (-1)*vy;
                    }
                } else impDet[i][BQ] = false;

                /* Столкновение с нижней стенкой. */
                if (y<( (-1)*height/2) ){
                    if (impDet[i][BQ+1] == false){
                        impDet[i][BQ+1] = true;
                        vy = (-1)*vy;
                    }
                } else impDet[i][BQ+1] = false;

                /* Столкновение с правой стенкой. */
                if (x>width/2){
                    if (impDet[i][BQ+2] == false){
                        impDet[i][BQ+2] = true;
                        vx = (-1)*vx;
                    }
                } else impDet[i][BQ+2] = false;

                /* Столкновение с левой стенкой. */
                if (x< ( (-1) * width/2 ) ){
                    if (impDet[i][BQ+3] == false){
                        impDet[i][BQ+3] = true;
                        vx = (-1)*vx;
                    }
                } else impDet[i][BQ+3] = false;

                velo.setX((float)vx);
                velo.setY((float)vy);

                balls[i].setSpeed(velo);
            }

            /* Проверяем, не столкнулись ли шары друг с другом. */
            for( int i=0; i<BQ; ++i ){
                for( int j = i+1; j<BQ; ++j ){
                    /* Получаем радиус векторы шаров. */
                    Vector3D a = balls[i].getCoordinates();
                    Vector3D b = balls[j].getCoordinates();

                    Vector3D c = a.add(b.multiply((float)-1));

                    float x = a.getX() - b.getX();
                    float y = a.getY() - b.getY();
                    /* Проверяем шары на столкновение. */
                    if //( (c.getX()*c.getX() + c.getY()*c.getY())< r2 ){

                        //  (((a.getX()-b.getX())*(a.getX()-b.getX()))+
                        //  ((a.getY()-b.getY())*(a.getY()-b.getY()))<r2){
                            ( ((x*x)+(y*y))<(4*r2)) {
                        /* Шары столкнулись. */
                        if (!impDet[i][j]){
                            impDet[i][j] = true;
                            /* Шары не столкнулись, так что impact! */
                            impact(balls[i],balls[j]);
                        }
                    }
                    else
                    {
                        /* Шары не столкнулись. */
                        impDet[i][j] = false;
                    }
                }
            }

        } else
        if (SimplePhysicsConst.PhysicsModel==2){
            for( int i=0; i<BQ; ++i ){
                Vector3D coord = balls[i].getCoordinates();
                double x = coord.getX();
                double y = coord.getY();

                Vector3D velo = balls[i].getSpeed();
                double vx = velo.getX();
                double vy = velo.getY();

                /* Столкновение с верхней стенкой. */
                if (y>height/2){
                    if (impLst[i]==0){
                        impLst[i] = -1;
                        vy = (-1)*vy;
                    }
                } else if (impLst[i] == -1);
                    impLst[i] = 0;

                /* Столкновение с нижней стенкой. */
                if (y<( (-1)*height/2) ){
                    if (impLst[i]==0){
                        impLst[i] = -2;
                        vy = (-1)*vy;
                    }
                } else if (impLst[i] == -2);
                    impLst[i] = 0;

                /* Столкновение с правой стенкой. */
                if (x>width/2){
                    if (impLst[i]==0){
                        impLst[i] = -3;
                        vx = (-1)*vx;
                    }
                }  else if (impLst[i] == -3);
                    impLst[i] = 0;

                /* Столкновение с левой стенкой. */
                if (x< (-1)*width/2){
                    if (impLst[i]==0){
                        impLst[i] = -4;
                        vx = (-1)*vx;
                    }
                } else if (impLst[i] == -4);
                    impLst[i] = 0;

                velo.setX((float)vx);
                velo.setY((float)vy);

                // Присваеваем новое значение скорости.
                balls[i].setSpeed(velo);
            }

            for( int i=0; i<BQ; ++i ){
                for( int j = i+1; j<BQ; ++j ){
                    /* Получаем радиус векторы шаров. */
                    Vector3D a = balls[i].getCoordinates();
                    Vector3D b = balls[j].getCoordinates();
                    Vector3D c = a.add(b.multiply((float)-1));

                    /* Проверяем шары на столкновение. */
                    if ( (c.getX()*c.getX() + c.getY()*c.getY())< r2 ){
                        /* Шары столкнулись. */
                        if ((impLst[i]==0)&&(impLst[i]==0)){
                            impLst[i] = i*BQ + j;
                            impLst[j] = j*BQ + i;
                            /* Шары не столкнулись, так что impact! */
                            impact(balls[i],balls[j]);
                        }
                    }
                    else
                    {
                        /* Шары не столкнулись. */
                        if ( (impLst[i]==(i*BQ + j))&&(impLst[j] == (j*BQ + i)) ){
                            impLst[i] = 0;
                            impLst[j] = 0;
                        }
                    }
                }
            }
        }

            /* Двигаем все шары.  */
            for( int i=0; i<BQ; ++i ){
                /* Разврат, или это я что-то не так делаю?
                 Добавляем к координатам каждого шара скорость, умноженную
                 на время Delta.
                 */
                balls[i].setCoordinates( balls[i].getCoordinates().add( balls[i].getSpeed().multiply((float)Delta) )  );

                /* Уменьшаем скорость шаров.  */
                Vector3D v = balls[i].getSpeed();
                double x = v.getX();
                double y = v.getY();
                /* Этот кусок кода тоже должен быть не здесь. */
                double len = Math.sqrt(x*x+y*y);
                if (len<f){
                    v.setX(0);
                    v.setY(0);
                } else {
                    x -= x/len * f;
                    v.setX((float)x);
                    y -= y/len * f;
                    v.setY((float)y);
                }
                balls[i].setSpeed(v);
                }

            if(SimplePhysicsConst.CorectionEnable==1){
                for(int i=0; i<BQ; ++i){
                    Vector3D coord = balls[i].getCoordinates();
                    double x = coord.getX();
                    double y = coord.getY();

                    if (x>(width/2+SimplePhysicsConst.CorrectionPenalty))
                        x = width/2;
                    if (x<((-1)*width/2-SimplePhysicsConst.CorrectionPenalty))
                        x = (-1)*width/2;
                    if (y>(height/2+SimplePhysicsConst.CorrectionPenalty))
                        y = width/2;
                    if (y<((-1)*height/2-SimplePhysicsConst.CorrectionPenalty))
                        y = (-1)*height/2;
                    balls[i].setCoordinates(new Vector3D((float)x,(float)y));
                }
            }

            //Детекция падения в лузу.
            if(SimplePhysicsConst.BallGetDown==1){
                
            }
        }
    }

}


