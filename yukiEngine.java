/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ballsimpact;
import titanic.basic.*;

/**
 *
 * @author AntiZerg
 */

/* ���������� ����������� ������ ������. */
public class yukiEngine implements PhysicalEngine {

    public void compute() {
        testWallCollisions(); //��������� ������������ ����� �� ��������.
        testBallsCollisions(); //��������� ������������ ����� ���� � ������.
        moveBalls(); //������� ����.
        convert2Global();//������������ ���� � ���������� ������.
    }

    /* ����������� ����������� ������. */
    public yukiEngine(Game game){
        /* � �������� ��������� ��������� ��������� ����. */

        dT = (float) 0.25; // ����� �������������.
        balls = game.getGameScene().getBalls(); // �������� ��� ����.
        bQuant = balls.length; // ����� �����.

        impList = new int[bQuant];
        conList = new int[bQuant];
        for(int i=0; i<bQuant; ++i){
            impList[i] = 0;
            conList[i] = 0;
        }

        // �������� ��������� �����.
        bPos = new float [bQuant][3];
        for(int i=0; i<bQuant; ++i){
            Vector2D a = balls[i].getCoordinates();
            bPos[i][0] = a.getX();
            bPos[i][1] = a.getY();
            bPos[i][2] = 0;
        }

        // �������� �������� �����.
        bVel = new float [bQuant][3];
        for(int i=0; i<bQuant; ++i){
            Vector2D a = balls[i].getSpeed();
            bVel[i][0] = a.getX();
            bVel[i][1] = a.getY();
            bVel[i][2] = 0;
        }

        // �������� ������� �����.
        width = game.getGameScene().getBounds().getX();
        height = game.getGameScene().getBounds().getY();

        // �������� ������ �������.
        r = balls[0].getRadius();
    }

    private Ball[] balls; // ������ ����� ��� ���������.
    private int impList[]; // ������ ������������ �����.
    private int conList[]; // ������ ��������� �����.
    private int bQuant; // ����� �����.
    private float dT; // ���������� �����.
    private float bPos[][]; // ������ ��������� �����.
    private float bVel[][]; // ������ ��������� �����.
    /* ������ � ������ �����. */
    private double width;
    private double height;
    private double r; // ������ �����.

    /* ���������, ����������� ������������ ����� ���� � ������. */
    private void testBallsCollisions(){
        for( int i=0; i<bQuant; ++i)
            for( int q=i+1; q<bQuant; ++q){
                //���������� ����� ������.
                if (
                   ((bPos[i][0]-bPos[q][0])*(bPos[i][0]-bPos[q][0])
                   +(bPos[i][1]-bPos[q][1])*(bPos[i][1]-bPos[q][1])
                   +(bPos[i][2]-bPos[q][2])*(bPos[i][2]-bPos[q][2]))
                        < (4*r*r)
                   ){

                   if ( (impList[i]!=q+1) && (impList[q]!=i+1) ){
                       impList[i] = q+1;
                       impList[q] = i+1;
                       impact( i , q );
                   }

                } else
                   if ( (impList[i]==q+1) && (impList[q]==i+1) ){
                       impList[i] = 0;
                       impList[q] = 0;
                   }
            }
    }

    /* ���������, ����������� �� ������������ �� ��������. */
    private void testWallCollisions(){
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
    }

    /* ���������, ������������ ����. */
    private void moveBalls(){
            /* ��������� � ����������� ����� �������� �����, ����������� �� dT. */
            for( int i=0; i<bQuant; ++i )
                for( int q=0; q<3; ++q)
                    bPos[i][q] += bVel[i][q]*dT;
    }

    /* ��������� ����� � ������� �����. */
    private void convert2Global(){
        for(int i=0; i<bQuant; ++i){
            Vector2D a = new Vector2D( bPos[i][0] , bPos[i][1] );
            balls[i].setCoordinates(a);
        }
        for(int i=0; i<bQuant; ++i){
            Vector2D a = new Vector2D( bVel[i][0] , bVel[i][1] );
            balls[i].setSpeed(a);
        }
    }

    /* ���������� ������������. */
    private void impact(int b1, int b2){
        //(x,y,z) - ���������� �������, ������������ �������� �����.
        float x = bPos[b1][0] - bPos[b2][0];
        float y = bPos[b1][1] - bPos[b2][1];
        float z = bPos[b1][2] - bPos[b2][2];

        // ���������� ����� ��������.
        float ro = (float) Math.sqrt(x*x+y*y+z*z);

        // ���������� ������� (x,y,z)
        x /= ro; y /= ro; z /= ro;

        // v1 � v2 �������� ��������� ����� �� ������������� ������.
        float v1 = x*bVel[b1][0] + y*bVel[b1][1] + z*bVel[b1][2];
        float v2 = x*bVel[b2][0] + y*bVel[b2][1] + z*bVel[b2][2];

        //�������� �������� ����� ������������.
        bVel[b1][0] += (v2-v1)*x; bVel[b2][0] += (v1-v2)*x;
        bVel[b1][1] += (v2-v1)*y; bVel[b2][1] += (v1-v2)*y;
        bVel[b1][2] += (v2-v1)*z; bVel[b2][2] += (v1-v2)*z;
    }
}
