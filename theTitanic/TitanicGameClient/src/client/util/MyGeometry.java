/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client.util;

import javax.media.j3d.Geometry;
import javax.media.j3d.LineStripArray;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

/**
 *
 * @author 7
 */
public class MyGeometry {

    public Geometry mySphere(float rad, int N){
    double w,dw,t,dt;
    w = 0;
    t = - Math.PI/2;
    int i,j;
    dw = 2*Math.PI/N;
    dt = 2*Math.PI/N;
    int ints[] = {N*N/2};
    LineStripArray sphere = new LineStripArray(N*(N+1)/2, LineStripArray.COORDINATES | LineStripArray.NORMALS, ints);
    sphere.setCoordinate(0, new Point3f(0.0f, 0.0f, 0.0f-rad));
    Point3f vec = new Point3f();
    Vector3f norm = new Vector3f();
    for(i=1; i <= N/2; i++){
        t = t + dt;
        for(j = 1; j<=N; j++){
           if((w = w + dw) > 2*Math.PI) w = 0;
           vec.setX(rad * (float)Math.cos(w)*(float)Math.cos(t));
           vec.setY(rad * (float)Math.sin(w)*(float)Math.cos(t));
           vec.setZ(rad * (float)Math.sin(t));

           norm.setX(rad * (float)Math.cos(w)*(float)Math.cos(t));
           norm.setY(rad * (float)Math.sin(w)*(float)Math.cos(t));
           norm.setZ(rad * (float)Math.sin(t));
           norm.normalize();
           sphere.setCoordinate(j + (i-1)*N, vec);
           sphere.setNormal(j + (i-1)*N, norm);


        }
    }

    return sphere;
}

}
