package ballsimpact;
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

        /* Потом напишу, для чего этот массив. */
        impDet = new boolean [BQ+4][BQ+4];
//        for(int i=0; i<BQ+4; ++i)
//            for(int j=0; j<BQ+4; ++j)
//                impDet[i][j] = false;
    }

    Game game;// Экземпляр игры.
    Ball[] balls; // Получаем все шары.
    int BQ; // Число шаров.
    boolean impDet[][];
    double r2;
    
    /* Обработчик столкновений. */
    private void impact(Ball a, Ball b){
        Vector2D va = a.getSpeed();
        Vector2D vb = b.getSpeed();

        // Вектор, соединяющий середины векторов.
        Vector2D c = new Vector2D();
            c.setX( (float) ( va.getX() - vb.getX() ) );
            c.setY( (float) ( va.getY() - vb.getY() ) );

        // Вектор, ему ортогональный.
        Vector2D d = new Vector2D();
            d.setX( (float) c.getY() );
            d.setY( (float) (-1) * c.getX() );

        Vector2D a2 = new Vector2D();
            a2 = ( va.multiply(va.multiply(c)) ).add( vb.multiply(vb.multiply(d)) );

        Vector2D b2 = new Vector2D();
            b2 = ( vb.multiply(vb.multiply(c)) ).add( va.multiply(va.multiply(d)) );

         // Присваевыем новые скорости.
            a.setSpeed( a2 );
            b.setSpeed( b2 );
    }

        /* Единственный паблик-метод: его задача по заданному
         расположению шаров и их скоростей вычислить их местонахождение
         через квант времени. */
    public void compute() {
        /* Здесь неплохо бы разместить различные проверки на допустимость,
         но я не представляю себе какие. */

        System.out.println("Physics iteration.");
        double Quant = 1; // Заданный квант времени.
        double Delta = 0.5; // Добавочное рассчётное время.
        double t = 0; // Текущее время.

        /* Все шары одного радиуса, пока. */
        r2 = balls[0].getRadius()*balls[0].getRadius();
        /* Коэффицент трения. */
        double f = 0.0;
        /* Ширина и высота стола. */
        double width = game.getGameScene().getBounds().getX();
        double height = game.getGameScene().getBounds().getY();

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
                if (y>height/2){
                    System.out.println("Impact!");
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
            
}


