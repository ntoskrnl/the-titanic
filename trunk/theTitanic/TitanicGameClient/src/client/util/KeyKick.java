package client.util;

/**
 *
 * @author 7
 */
import client.util.event.MakeHitEvent;
import javax.media.j3d.*;
import javax.vecmath.*;
import titanic.basic.Game;

public class KeyKick extends Thread {

    private Game game;
    private TransformGroup tr;
    private double A;
    private Transform3D trans;
    private double time = 0;
    private float Str = 0;

    public KeyKick(TransformGroup tr, double A, float STR, Game game) {
        this.game = game;
        this.tr = tr;
        this.A = A;
        this.Str = STR;
        trans = new Transform3D();

    }

    @Override
    public void run() {
        game.changeStatus(Game.S_MAKE_HIT);

        while ((time - Math.PI) < 0 && !Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep((int) (8 * (1.2 - Str)));
                time += 0.01;

                if (time - Math.PI / 2 < 0) {
                    trans.setTranslation(new Vector3d(0.0, -0.5 * A * Math.sin((time)), 0));
                } else {
                    trans.setTranslation(new Vector3d(0.0, -0.5 * A * Math.sin((time)), 0));
                }


                tr.setTransform(trans);

            } catch (Exception ex) {
                System.err.print(ex);
                return;
            }
        }
        
        game.getEventPipeLine().add(new MakeHitEvent(game, Str));
    }
}
