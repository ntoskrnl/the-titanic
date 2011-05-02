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


public class UpDownBottom extends Thread {
    private Game game;
    static int COUNT=0;
    private TransformGroup tr;
    private TransformGroup key;
    private double A;
    private Transform3D trans;
    private double time=0;
    private float Str=0;

    public UpDownBottom(TransformGroup tr, double A, TransformGroup key, Game game) {
    this.game = game;
    this.tr = tr;
    this.A = A;
    trans = new Transform3D();
    this.key = key;
    }

    @Override
    public void run(){
        COUNT++;
        Transform3D rot = new Transform3D();
        for(;;){
        if(COUNT!=1){
            COUNT=0;
            //while(time>2*Math.PI){
            //    time-=Math.PI*2;
            //}
            Str = (1+(float)(Math.sin(time)))/2;
            if(Str!=0.5) {
                KeyKick kick = new KeyKick(key, 1, Str, game);
                kick.start();
            }
            break;
        }

    try{
        Thread.currentThread().sleep(10);
        time+=0.01;
        //Transform3D ad = new Transform3D();
        //ad.setTranslation(new Vector3d(Math.sin(time),Math.cos(time),0));
        trans.setTranslation(new Vector3d(0.8855, 0.635+0.4*A*Math.sin(time), 1));
        rot.rotX(Math.PI/3);
        rot.mul(trans);
        
        tr.setTransform(rot);

            }
    catch(Exception ex){
        System.err.print(ex);
    }
        }
    }

}
