
package ballsimpact;


import java.awt.*;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import titanic.basic.*;

/**
 *
 * @author 7
 */
public class Graphics3D implements GraphicalEngine {

    
    Ball[] BallsArray;
    private BranchGroup scene;

    private int N;
    private TransformGroup[] objTrans;
    private Vector3f[] mass;
    public Vector3f[] V;
    public Transform3D[] speedch;

    Appearance ap = new Appearance();
    
    final float width = 0.6f;  // ширина стола
    final float high  = 0.85f;  // длина
    private float r; //радиус
    private float maxwidth;
    private float maxhight;
    private SimpleUniverse u;

Color3f ambient = new Color3f(0.5f, 0.5f, 0.5f);
Color3f emissive = new Color3f(0.0f, 0.0f, 0.0f);
Color3f diffuse = new Color3f(0.0f, .0f, 1.0f);
Color3f speculas = new Color3f(1.0f, 1.0f, 1.0f);

    public Graphics3D(Game g) {
        scene = new BranchGroup();
        N = g.getGameScene().getBalls().length;
        mass = new Vector3f[N];
        V = new Vector3f[N];
        speedch = new Transform3D[N];
        objTrans = new TransformGroup[N+1];

        maxhight = g.getGameScene().getBounds().getY();
        maxwidth = g.getGameScene().getBounds().getX();
        r = g.getGameScene().getBalls()[0].getRadius()/maxhight*high;
    }



    public void setRenderingArea(Container area) {

       area.setLayout(new BorderLayout());
    GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
    Canvas3D c = new Canvas3D(config);
    u = new SimpleUniverse(c);
    area.add(BorderLayout.CENTER, c);
 

    scene = createSceneGraph();
    scene.compile();

      u.getViewingPlatform().setNominalViewingTransform();

      //                        --------------Вращение вселенной------------
        Vector3f viewTranslation = new Vector3f();
        Transform3D viewTransform = new Transform3D();

                viewTranslation.z = 3f;          //установление начальной точки наблюдения
		viewTranslation.x = 0f;
		viewTranslation.y = 0f;

              	viewTransform.setTranslation(viewTranslation);
		Transform3D rotation = new Transform3D();
                //---------------------------------------------------Поворот
		rotation.rotX(Math.PI / 3.0d);
		rotation.mul(viewTransform);

		u.getViewingPlatform().getViewPlatformTransform().setTransform(
				rotation);
		u.getViewingPlatform().getViewPlatformTransform().getTransform(	viewTransform);
// --------------------------------------------------------------------
            
                u.addBranchGraph(scene);

      
    }

    public void SetDrawBalls(){
    int i;

    for(i = 0;i < N;i++){
     if(speedch[i] == null) speedch[i] = new Transform3D();
    speedch[i].setTranslation(mass[i]);
    objTrans[i].setTransform(speedch[i]);
    }
}

    public void SetCoadinates(){
    int i;
    for(i=0;i<N;i++){

        mass[i] = new Vector3f();

        mass[i].setX(BallsArray[i].getCoordinates().getX()/maxwidth*2*width*0.8f);
        mass[i].setY(BallsArray[i].getCoordinates().getY()/maxhight*2*high*0.87f);
        mass[i].setZ(-0.01f);


            }
}

   //---------------------------------------------------------------------------
   //****************************************************************
   //*               Метод перерисовки группы обьектов              *
   //*               public void render(Game game)                  *
   //****************************************************************
   //---------------------------------------------------------------------------

     public void render(Game game) {
        
     BallsArray = game.getGameScene().getBalls();
     SetCoadinates();
     SetDrawBalls();


    }

   //---------------------------------------------------------------------------
   //****************************************************************
   //*           Метод размещения начальныой пирамиды шаров         *
   //*           public Vector3f[] startmass(Vector3f start)        *
   //****************************************************************
   //---------------------------------------------------------------------------

     
public Vector3f[] startmass(Vector3f start){
    mass[0] = start;

    int k = 5,i,x=5;
    for(i=1; i<=14; i++){
      Vector3f a = new Vector3f();
      mass[i] = a;

        if(i == x) {

        mass[i].setX(mass[i - k].getX() - r);
        mass[i].setY((float) (mass[i - k].getY() + 2 * r * Math.cos((Math.PI) / 6)));
        k--;
        x = x + k;
        }
     else{
            mass[i].setX(mass[i-1].getX() + r);
            mass[i].setY((float)(mass[i-1].getY() +2 * r * Math.cos((Math.PI) / 6)));

 }
       // System.out.println("i: "+i+" x: "+mass[i].x+" y: "+mass[i].y);

    }

    return mass;
}

public void SetStartTransform(Vector3f[] mass, BranchGroup bran){

    TransformGroup[] tr = new TransformGroup[N];
    int i=0;

    Sphere[] ball = new Sphere[N];
    Transform3D[] pos = new Transform3D[N];

    for(i=0;i<N-1;i++){

        
        tr[i] = new TransformGroup();
        tr[i].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        bran.addChild(tr[i]);

        
        tr[i] =  new TransformGroup();
        
        tr[i].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        ball[i] = new Sphere(r);
        pos[i] = new Transform3D();

        pos[i].setTranslation(mass[i]);
        tr[i].setTransform(pos[i]);
        tr[i].addChild(ball[i]);
        objTrans[i] = tr[i];
        
        bran.addChild(tr[i]);
    }

}


   //---------------------------------------------------------------------------
   //****************************************************************
   //*               Метод создания группы объектов                 *
   //*              public BranchGroup createSceneGraph()           *
   //****************************************************************
   //---------------------------------------------------------------------------

    public BranchGroup createSceneGraph() {

   // Create the root of the branch graph

   BranchGroup objRoot = new BranchGroup();

   //---------------------------------------------------------------------------
   //****************************************************************
   //*                Начальное положение обьектов                  *
   //****************************************************************
   //---------------------------------------------------------------------------

   //--------------Устанавливаем шары-------------------------------------------
   mass = startmass(new Vector3f(0.0f,0.0f,0.0f));
   SetStartTransform(mass, objRoot);
   //---------------------------------------------------------------------------

   mass[N-1] = new Vector3f(0.0f,0.0f,0.0f);
   objTrans[N-1] = new TransformGroup();
   objTrans[N-1].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
   objRoot.addChild(objTrans[N-1]);

   // Create a simple shape leaf node, add it to the scene graph.
   ap.setMaterial(new Material(ambient, emissive, diffuse, speculas, 12000f));

   Sphere sphere = new Sphere(r, ap);

   objTrans[N-1] = new TransformGroup();
   objTrans[N-1].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
   objTrans[N-1].setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
   Transform3D pos = new Transform3D();
   pos.setTranslation(new Vector3f(0.0f,-0.4f,0.0f));

   //1 шар, которым начинается игра---------------------------------------------
   objTrans[N-1].setTransform(pos);
   objTrans[N-1].addChild(sphere);
   objRoot.addChild(objTrans[N-1]);
   
   //---------------------------------------------------------------------------
   //-------------------------------Table---------------------------------------
   //---------------------------------------------------------------------------
   Appearance table = new Appearance();
  // table.setMaterial(new Material(outer, tabem, tabdif, tabsp, 1200f));

   // Now the texture file is in special package ballsimpact.res,
   // so we need to use getClass.getResource(String resname) method to access it.
   // For more information see http://netbeans.org/kb/docs/java/gui-image-display.html
   TextureLoader loader = new TextureLoader(getClass().getResource("/ballsimpact/res/table.jpg"),  new Container());
   Texture texture = loader.getTexture();
   texture.setBoundaryModeS(Texture.WRAP);
   texture.setBoundaryModeT(Texture.WRAP);
   texture.setBoundaryColor( new Color4f( 2.0f, 1.0f, 1.0f, 1.0f ) );
    TextureAttributes texAttr = new TextureAttributes();

   texAttr.setTextureMode(TextureAttributes.MODULATE);
   table.setTexture(texture);
   table.setTextureAttributes(texAttr);
   int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
//Создание стола
   com.sun.j3d.utils.geometry.Box box = new Box(width, high, .1f, primflags, table);

   objTrans[N] = new TransformGroup();
   objTrans[N].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
   objRoot.addChild(objTrans[16]);
//НАчальное положение стола
   Transform3D vec = new Transform3D();
   vec.setTranslation(new Vector3f(0.0f,0.0f,(-0.1f - r)));
   objTrans[N] = new TransformGroup();
   objTrans[N].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

   objTrans[N].setTransform(vec);
   objTrans[N].addChild(box);
   objRoot.addChild(objTrans[N]);

   //---------------------------------------------------------------------------
   //****************************************************************
   //*                  Освещение                                   *
   //****************************************************************
   //---------------------------------------------------------------------------

   BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 1000.0);

   Color3f light1Color = new Color3f(.9f, .9f, 1.1f);
   Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
   DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);

   Point3f one = new Point3f(0.0f,0.0f,0.8f);
   PointLight light2 = new PointLight(ambient, one, one);
   light2.setInfluencingBounds(bounds);

   Point3f two = new Point3f(0.0f,0.8f,0.8f);
   PointLight light3 = new PointLight(ambient, two, two);
   light3.setInfluencingBounds(bounds);

   Point3f three = new Point3f(0.0f,-0.8f,0.8f);
   PointLight light4 = new PointLight(ambient, three, three);
   light4.setInfluencingBounds(bounds);

   light1.setInfluencingBounds(bounds);
   //objRoot.addChild(light1);
   objRoot.addChild(light2);
   objRoot.addChild(light3);
   objRoot.addChild(light4);
   // Set up the ambient light

   Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
   AmbientLight ambientLightNode = new AmbientLight(ambientColor);
   ambientLightNode.setInfluencingBounds(bounds);
   objRoot.addChild(ambientLightNode);

   return objRoot;

}

   

}
