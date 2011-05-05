/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client.util;

/**
 *
 * @author 7
 */

import javax.media.j3d.*;
import javax.vecmath.*;
import titanic.basic.Game;


public class KeyKick extends Thread {

    private Game game;
    private TransformGroup tr;
    private double A;
    private Transform3D trans;
    private double time=0;
    private float Str=0;
     
    public KeyKick(TransformGroup tr, double A,float STR, Game game) {
    this.game = game;
    this.tr = tr;
    this.A = A;
    this.Str = STR;
    trans = new Transform3D();

    }

    @Override
    public void run(){


       while((time-Math.PI)<0){
        try{
        Thread.currentThread().sleep((int)(8*(1.2-Str)));
        time+=0.01;
       
       if(time-Math.PI/2 < 0) trans.setTranslation(new Vector3d(0.0, -0.5*A*Math.sin((time)), 0));
       else trans.setTranslation(new Vector3d(0.0, -0.5*A*Math.sin((time)), 0));


        tr.setTransform(trans);

            }
    catch(Exception ex){
        System.err.print(ex);
    }
        }
       game.getBilliardKey().setPower(Str);
       ((SimpleGame)game).start();
    }

}
