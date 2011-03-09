//915 Gilmullin A
//�������� ������ ��� ������� the Titanic (aka theLoop).
// � �� ������ ��� ������������ �����������.

#include <iostream>

/* ��� ����� � ��������� ����. */
/*��������� ��� ������������� ��������.*/
typedef struct {
        float x;
        float y;
} vector; 
/*------------------------------------------------------------------------*/
/* �������� ��������. */
vector add ( vector a, vector b ) {
       vector c;
       c.x = a.x + b.x;
       c.y = a.y + b.y;
       return c;
}
/*------------------------------------------------------------------------*/
/* ��������� ��������� ��������. */
float scMul ( vector a, vector b ){
      return a.x * b.x + a.y * b.y;
}
/*------------------------------------------------------------------------*/
/* ��������� ������� �� �����. */
vector operator * ( float a, vector b ){
      vector c;
             c.x = b.x * a;
             c.y = b.y * a;
      return c;
}      
/*------------------------------------------------------------------------*/
/* ����� �������� �������, ����, �������, � ��������� ������ ���
���� ����� ��������������. */
void printVector( vector a ){
     using namespace std;
     cout << "vector: (" << a.x << "," << a.y << ")" << endl;
}
/*------------------------------------------------------------------------*/
/*------------------------------------------------------------------------*/
/*------------------------------------------------------------------------*/



/*���� � ��������� ����*/
/* ������ ����.  */
const float BallRadius = 2.5;
/*------------------------------------------------------------------------*/
/* ����� ��� ������������� �����. */
typedef class Ball Ball;
class Ball{
      public:
             //N - ����� ����.
             Ball(int N = 0, float X = 0, float Y = 0){
                        n = N;
                        
                        x = X;
                        y = Y;
                        
                        v.x = 0;
                        v.y = 0;
             }
             int n;
             //���������� ����.
             float x;
             float y;
             //�������� ����.
             vector v;
      void to_string(){
           //�� ��� � �����.
           using namespace std;
           cout << "ball " << n << endl;
           cout << "(" << x << "," << y << ")" << endl;
           cout << "speed: " << "(" << v.x << "," << v.y << ")" << endl;
      }
};
/*------------------------------------------------------------------------*/
/* ������������ �������. */
void impact( Ball &a, Ball &b ){
     /*
     ����� �� ������: ����� ���������� �������� � ������:-)
     */
     
     // ������ ����������� �������� �����.
     vector c;
            c.x = a.x - b.x;
            c.y = a.y - b.y;
     // ������ ��� �������������.
     vector d;
            d.x = -c.y;
            d.y =  c.x;
            
     // ������ ������: ��������� ���� �����������, ���������� ����� ����
     // ����� 2*BallRadius!
     
     vector v1;
            v1 = (1/(4*BallRadius*BallRadius)) 
               * add( scMul(b.v , c)*c , scMul(a.v , d)*d );
     vector v2;
            v2 = (1/(4*BallRadius*BallRadius)) 
               * add( scMul(a.v , c)*c , scMul(b.v , d)*d );
               
     a.v = v1;
     b.v = v2;
}
/*------------------------------------------------------------------------*/

int main(){
    using namespace std;
    //�������� �������.
    vector a;
           a.x = 0;
           a.y = 1;
    vector b;
           b.x = 0;
           b.y = -1;
    printVector(a);
    printVector(b);
    printVector(add(a,b));
    cout << scMul( a,b ) <<endl;

    //�������� ������.
    Ball f1(1,0,0);
    f1.v = a;
    Ball f2(2,0,5);
    f2.v = b;
    //�� ������������
    f1 . to_string();
    f2 . to_string();
    //����� ������������
    impact(f1,f2);
    f1 . to_string();
    f2 . to_string();
        

    return 0;
}