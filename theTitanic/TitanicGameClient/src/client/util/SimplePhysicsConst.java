package client.util;;

/**
 *
 * @author AntiZerg
 */
/* Постоянные физического движка: все в одном месте, чтобы
                править было легче. */
public class SimplePhysicsConst {
   static double N = 0.03; // Коэффицент трения.
   static int PhysicsModel = 1; //Модель физики: 1 или 2.
   static int CorectionEnable = 1; //Использование коррекции.
   static int CorrectionPenalty = 10;
}
