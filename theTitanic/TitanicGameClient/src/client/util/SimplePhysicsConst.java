package client.util;;

/**
 *
 * @author AntiZerg
 */
/* Постоянные физического движка: все в одном месте, чтобы
                править было легче. */
public class SimplePhysicsConst {
<<<<<<< .mine
   static int rej = 10; /*Коэффицент режекции. Очень опасен в настройке и имеет
        смысл только в первой модели физики.*/
   static double N = 0.0; // Коэффицент трения.
=======
   static double N = 0.07; // Коэффицент трения.
>>>>>>> .r69
   static int PhysicsModel = 1; //Модель физики: 1 или 2.
   static int CorectionEnable = 1; //Использование коррекции.
   static int BallGetDown = 0; //Падение шаров в лузу.
   static int CorrectionPenalty = 10;
}
