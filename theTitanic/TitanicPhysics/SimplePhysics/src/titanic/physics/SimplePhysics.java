package titanic.physics;

/**
 *
 * @author AntiZerg
 */
/* Простейшая модель физики: будет работать эта - добавлю ( перепишу :-( )
        третье измерение и вращение шариков.    */
public class SimplePhysics implements PhysicalEngine {

    /* Обработчик столкновений. */
    private void impact(Ball a, Ball b){
    }

        /* Единственный паблик-метод: его задача по заданному
         расположению шаров и их скоростей вычислить их местонахождение
         через квант времени. */
    public void compute(Game game) {
        /* Здесь неплохо бы разместить различные проверки на допустимость,
         но я не представляю себе какие. */

        double Quant = 1; // Заданный квант времени.
        double Delta = 0.01; // Добавочное рассчётное время.
        double t = 0; // Текущее время.

        /* Как же Java отличается от C++! Наверно это удобно когда привыкнешь.*/
        Ball balls[] = game.getGameScene().getBalls(); // Получаем все шары.
        int BQ = balls.length; // Число шаров.

        /* Все шары одного радиуса, пока. */
        double r2 = balls[0].getRadius()*balls[0].getRadius();
        /* Коэффицент трения. */
        double f = 0.01;
        /* Ширина и высота стола. */
        double width = 10;
        double height = 5;

        /* Этот массив должен быть не здесь! Будут баги. */
        boolean impDet[][] = new boolean [BQ+4][BQ+4];
        for(int i=0; i<BQ+4; ++i)
            for(int j=0; j<BQ+4; ++j)
                impDet[i][j] = false;

        while(t<Quant){
            /* Проверяем, не столкнулся ли какой шар со стенкой. */
            /* Очень важно вставить сюда проверку на валидность! */
            for( int i=0; i<BQ; ++i ){
                Vector2D coord = balls[i].getCoordinates();
                double x = coord.getX();
                double y = coord.getY();

                Vector2D velo = balls[i].getSpeed();
                double vx = velo.getX();
                double vy = velo.getY();

                /* Столкновение с верхней стенкой. */
                if (y>width/2){
                    if (impDet[i][BQ] == false){
                        impDet[i][BQ] = true;
                        vy = (-1)*vy;
                    }
                } else impDet[i][BQ] = false;

                /* Столкновение с нижней стенкой. */
                if (y< ((-1)*width/2) ){
                    if (impDet[i][BQ+1] == false){
                        impDet[i][BQ+1] = true;
                        vy = (-1)*vy;
                    }
                } else impDet[i][BQ+1] = false;

                /* Столкновение с правой стенкой. */
                if (y>height/2){
                    if (impDet[i][BQ+2] == false){
                        impDet[i][BQ+2] = true;
                        vx = (-1)*vx;
                    }
                } else impDet[i][BQ+2] = false;

                /* Столкновение с левой стенкой. */
                if (y< ((-1)*height/2) ){
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
                    Vector2D a = balls[i].getCoordinates();
                    Vector2D b = balls[i].getCoordinates();
                    
                    Vector2D c = a.add(b.multiply(-1));
                    /* Проверяем шары на столкновение. */
                    if ( (c.getX()*c.getX() + c.getY()*c.getY())< r2 ){
                        /* Шары столкнулись. */
                        if (!impDet[i][j]){
                            impDet[i][j] = true;
                            /* Шары не столкнулись, так что impact! */
                            this.impact(balls[i],balls[j]);
                        }
                    }
                    else
                    {
                        /* Шары не столкнулись. */
                        impDet[i][j] = false;
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
                Vector2D v = balls[i].getSpeed();
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
            }
            
           /* Добавляем прошедшее время. */
            t += Delta;
        }
}


