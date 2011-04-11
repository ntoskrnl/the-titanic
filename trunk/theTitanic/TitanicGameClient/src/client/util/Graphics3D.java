
package client.util;


import java.awt.*;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.universe.*;
import java.awt.event.MouseWheelEvent;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import titanic.basic.*;


import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.Text2D;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;
import java.io.*;
/**
 *
 * @author 7
 */
public class Graphics3D implements GraphicalEngine {

    
    Ball[] BallsArray;
    private BranchGroup scene;
    private TransformGroup Gamescenegroup;
    private float phi = 0, psi = 0;
    private Vector3d scale = new Vector3d(1, 1, 1);
    private int dragX, dragY;
    boolean drag;
    
    private int N;
    private TransformGroup[] objTrans;
    private Vector3f[] mass;
    public Vector3f[] V;
    public Transform3D[] speedch;
    private Game game;
    private TransformGroup Keytrans;
    private Transform3D Keyposition;

    Appearance startballapp = new Appearance();
    Appearance ballapp = new Appearance();
    final float width = 0.59f;  // ширина стола
    final float high  = 0.86f;  // длина
    private float r; //радиус
    private float maxwidth;
    private float maxhight;
    private SimpleUniverse u;


    Color3f ambient = new Color3f(0.5f, 0.5f, 0.5f);
    Color3f emissive = new Color3f(0.0f, 0.0f, 0.0f);
    Color3f diffuse = new Color3f(0.0f, .0f, 1.0f);
    Color3f speculas = new Color3f(1.0f, 1.0f, 1.0f);

    public Graphics3D(Game g) {
        game = g;
        scene = new BranchGroup();
        N = g.getGameScene().getBalls().length;
        mass = new Vector3f[N];
        V = new Vector3f[N];
        speedch = new Transform3D[N];
        objTrans = new TransformGroup[N+1];

        maxhight = g.getGameScene().getBounds().getY();
        maxwidth = g.getGameScene().getBounds().getX();
        r = game.getGameScene().getBalls()[0].getRadius()/maxhight*high;
      //  BallsArray = game.getGameScene().getBalls();


    }

    /** Adds some mouse and key listeners to the component */
    private void setEventListeners(Component c){
        /**
         * Behavour on key pressing
         */
        c.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent evt){
                int code = evt.getKeyCode();
                int curBall = game.getBilliardKey().getBall().getId();
                
                if(code==KeyEvent.VK_COMMA || code == KeyEvent.VK_PERIOD || code ==KeyEvent.VK_A ||  code ==KeyEvent.VK_D) {
                    if(code == KeyEvent.VK_COMMA)  {
                        curBall+=game.getGameScene().getBalls().length-1;
                        game.getBilliardKey().setAngle(0);
                      }
                    if(code == KeyEvent.VK_PERIOD) {
                        curBall+=game.getGameScene().getBalls().length+1;
                        game.getBilliardKey().setAngle(0);
                    }
                    if(code == KeyEvent.VK_A) game.getBilliardKey().setAngle(game.getBilliardKey().getAngle() - (float)Math.PI/100);
                    if(code == KeyEvent.VK_D) game.getBilliardKey().setAngle(game.getBilliardKey().getAngle() + (float)Math.PI/100);
                    
                    curBall%=game.getGameScene().getBalls().length;
                    game.getBilliardKey().changeBall(game.getGameScene().getBalls()[curBall]);

                     // if(Keyposition == null) {
                       Keyposition = new Transform3D();
                     
                       Keyposition.rotZ(game.getBilliardKey().getAngle());
                       Transform3D rotk = new Transform3D();
                       rotk.rotX(-Math.PI/24);
                       Keyposition.mul(rotk);

                    // }

                    Vector3f pos = new Vector3f();
                    pos.setX(game.getBilliardKey().getBall().getCoordinates().getX()/maxwidth*2*width*0.8f);
                    pos.setY(game.getBilliardKey().getBall().getCoordinates().getY()/maxhight*2*high*0.87f);
                    pos.setZ(game.getBilliardKey().getBall().getCoordinates().getZ());
                      Keyposition.setTranslation(pos);
                     
                      Keytrans.setTransform(Keyposition);
                }

                if(code == KeyEvent.VK_END){
                      Transform3D key = new Transform3D();
                      key.setTranslation(new Vector3f(-.7f, 0.0f,0.0f));
                      Keytrans.setTransform(key);
                      
                }

                if(code == KeyEvent.VK_LEFT){
                    phi = phi + 0.01f;
                    Transform3D t = new Transform3D();
                    t.rotZ(phi);
                    Transform3D setscenerot = new Transform3D();
                    setscenerot.rotX(psi);
                    setscenerot.mul(t);
                    setscenerot.setScale(scale);
                    Gamescenegroup.setTransform(setscenerot);
                }

                 if(code == KeyEvent.VK_RIGHT){
                    phi = phi - 0.01f;
                    Transform3D t = new Transform3D();
                    t.rotZ(phi);
                    Transform3D setscenerot = new Transform3D();
                    setscenerot.rotX(psi);
                    setscenerot.mul(t);
                    setscenerot.setScale(scale);
                    Gamescenegroup.setTransform(setscenerot);
                }

                 if(code == KeyEvent.VK_UP){
                    Transform3D t = new Transform3D();
                    t.rotZ(phi);
                    psi = psi - 0.01f;
                    Transform3D setscenerot = new Transform3D();
                    setscenerot.rotX(psi);
                    setscenerot.mul(t);
                    setscenerot.setScale(scale);
                    Gamescenegroup.setTransform(setscenerot);
                }

                if(code == KeyEvent.VK_DOWN){
                    Transform3D t = new Transform3D();
                    t.rotZ(phi);
                    psi = psi + 0.01f;
                    Transform3D setscenerot = new Transform3D();
                    setscenerot.rotX(psi);
                    setscenerot.mul(t);
                    setscenerot.setScale(scale);
                    Gamescenegroup.setTransform(setscenerot);
                }
                
                if(code == KeyEvent.VK_ESCAPE){
                    phi = 0; psi = 0; 
                    scale.x = 1;
                    scale.y = 1;
                    scale.z = 1;
                    Transform3D t = new Transform3D();
                    t.rotZ(phi);
                    Transform3D setscenerot = new Transform3D();
                    setscenerot.rotX(psi);
                    setscenerot.mul(t);
                    setscenerot.setScale(scale);
                    Gamescenegroup.setTransform(setscenerot);
                }
                
                 if(code == KeyEvent.VK_0){
                     scale.setX(scale.getX() + scale.getX()/50);
                     scale.setY(scale.getY() + scale.getY()/50);
                     scale.setZ(scale.getZ() + scale.getZ()/50);
                                      
                     Transform3D t = new Transform3D();
                     t.rotZ(phi);
                     Transform3D setscenerot = new Transform3D();
                     setscenerot.rotX(psi);
                     setscenerot.mul(t);
                     setscenerot.setScale(scale);
                     Gamescenegroup.setTransform(setscenerot);
                                        
                     
                 }
                 if(code == KeyEvent.VK_9){
                     scale.setX(scale.getX() - scale.getX()/50);
                     scale.setY(scale.getY() - scale.getY()/50);
                     scale.setZ(scale.getZ() - scale.getZ()/50);
                                      
                     Transform3D t = new Transform3D();
                     t.rotZ(phi);
                     Transform3D setscenerot = new Transform3D();
                     setscenerot.rotX(psi);
                     setscenerot.mul(t);
                     setscenerot.setScale(scale);
                     Gamescenegroup.setTransform(setscenerot);
                                        
                     
                 }
            }
        });

        /**
         * Bahavour at mouse (wheel) click or move
         */
        c.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent evt){
                    dragX = evt.getX();
                    dragY = evt.getY();
                    drag = true;
            }

            @Override
            public void mouseMoved(MouseEvent evt){
                int dx = evt.getX()-dragX;
                int dy = evt.getY()-dragY;
                if(drag)
                System.out.println(dx+" "+dy);
            }

            @Override
            public void mouseReleased(MouseEvent evt){
                drag=false;
            }

            @Override
            public void mouseExited(MouseEvent evt){
                drag=false;
            }
        });

        c.addMouseWheelListener(new MouseWheelListener() {

            public void mouseWheelMoved(MouseWheelEvent e) {
                
            }
        });

    }

    public void setRenderingArea(Container area) {

        area.setLayout(new BorderLayout());
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        Canvas3D c = new Canvas3D(config);
        u = new SimpleUniverse(c);
        area.add(BorderLayout.CENTER, c);

        // Let's add some event listeners to the rendering area!
        // For example, we can magnify the scene using a mouse wheel
        // or select ball using '<' and '>'
        // or just rotate the universe with arrow keys.
        setEventListeners(c);
        BranchGroup Gamescene;

        Gamescene = createSceneGraph();

       if(Gamescenegroup==null) Gamescenegroup = new TransformGroup();
        Gamescenegroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        Gamescenegroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        MouseRotate rotatescene = new MouseRotate(Gamescenegroup);
        rotatescene.setSchedulingBounds(new BoundingSphere());


       Gamescenegroup.addChild(Gamescene);

        //scene = createSceneGraph();
        scene.addChild(Gamescenegroup);
        scene.addChild(rotatescene);
        Text2D text = new Text2D("Game in process", diffuse, "Times new Roman", 25, Font.PLAIN);
        TransformGroup texttr = new TransformGroup();
        texttr.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        texttr.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        Transform3D textposition = new Transform3D();
        textposition.rotX(Math.PI / 3.0d);
        textposition.setTranslation(new Vector3f(0.2f,0f,0.9f));
        texttr.setTransform(textposition);
        texttr.addChild(text);
       

        scene.addChild(texttr);
      
                

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

    private void SetDrawBalls(){
    int i;

    for(i = 0;i < N;i++){
     if(speedch[i] == null) speedch[i] = new Transform3D();
    speedch[i].setTranslation(mass[i]);
    objTrans[i].setTransform(speedch[i]);
    }
}

    private void SetCoadinates(){
    int i;
    for(i=0;i<N;i++){

        mass[i] = new Vector3f();

        mass[i].setX(BallsArray[i].getCoordinates().getX()/maxwidth*2*width*0.8f);
        mass[i].setY(BallsArray[i].getCoordinates().getY()/maxhight*2*high*0.87f);
        mass[i].setZ(BallsArray[i].getCoordinates().getZ()-0.005f);


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

     
public Vector3f[] startmass(){
   /* mass[0] = start;

    
    int k = 5,i,x=5;
    for(i=1; i<15; i++){
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
    * */


    int i;

    for(i=0;i<N;i++){
        mass[i] = new Vector3f();
        mass[i].setX(game.getGameScene().getBalls()[i].getCoordinates().getX()/maxwidth*2*width*0.8f);
        mass[i].setY(game.getGameScene().getBalls()[i].getCoordinates().getY()/maxhight*2*high*0.87f);
        mass[i].setZ(game.getGameScene().getBalls()[i].getCoordinates().getZ()-0.005f);

    }
   
    return mass;
}


// не делать
private void SetStartTransform(Vector3f[] mass, BranchGroup bran){

    TransformGroup[] tr = new TransformGroup[N];
    int i=0;

   // Shape3D[] ball = new Shape3D[15];
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
   
   mass = startmass();
   SetStartTransform(mass, objRoot);
   //---------------------------------------------------------------------------
   

   objTrans[N-1] = new TransformGroup();
   objTrans[N-1].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
   objRoot.addChild(objTrans[N-1]);

   // Create a simple shape leaf node, add it to the scene graph.
   startballapp.setMaterial(new Material(ambient, emissive, diffuse, speculas, 12000f));
   ballapp.setMaterial(new Material(ambient, emissive, new Color3f(0.3f, 0.3f, 0.3f), speculas, 12000f));

   //MyGeometry geom = new MyGeometry();
  // Shape3D sphere = new Shape3D(geom.mySphere(r, 1000),startballapp);
   
   Sphere sphere = new Sphere(r, startballapp);

   objTrans[N-1] = new TransformGroup();
   objTrans[N-1].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
   objTrans[N-1].setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
   Transform3D pos = new Transform3D();
   pos.setTranslation(mass[N-1]);

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
   TextureLoader loader = new TextureLoader(getClass().getResource("/client/res/table.jpg"),  new Container());
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
   //objRoot.addChild(objTrans[N]);

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

   Point3f two = new Point3f(0.0f,0.8f,-0.8f);
   PointLight light3 = new PointLight(ambient, two, two);
   light3.setInfluencingBounds(bounds);

   Point3f three = new Point3f(0.0f,-0.8f,0.8f);
   PointLight light4 = new PointLight(ambient, three, three);
   light4.setInfluencingBounds(bounds);
   
   Point3f four = new Point3f(0.0f,0.8f,0.8f);
   PointLight light5 = new PointLight(ambient, four, four);
   light5.setInfluencingBounds(bounds);
   
   light1.setInfluencingBounds(bounds);
   //objRoot.addChild(light1);
   objRoot.addChild(light2);
   objRoot.addChild(light3);
   objRoot.addChild(light4);
   objRoot.addChild(light5);

   Background back = new Background(1,1,1);
   back.setApplicationBounds(new BoundingSphere());

   objRoot.addChild(back);

   // Set up the ambient light

   Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
   AmbientLight ambientLightNode = new AmbientLight(ambientColor);
   ambientLightNode.setInfluencingBounds(bounds);
   objRoot.addChild(ambientLightNode);

   //НАконецто кий из 3Ds MAX!!!!
   Transform3D let = new Transform3D();
   Transform3D key = new Transform3D();
   key.setTranslation(new Vector3f(-.7f,0.0f,0.0f));
   let.rotX(Math.PI/2);
   Keytrans = new TransformGroup();
   Keytrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
   Keytrans.setTransform(let);
   Keytrans.setTransform(key);
   



   ObjectFile gamekey = new ObjectFile();
   Scene skey = null;
   ObjectFile MAXtable = new ObjectFile();
   Scene stable = null;

   try {

       skey = gamekey.load(getClass().getResource("/client/res/Key.obj"));
       stable = MAXtable.load(getClass().getResource("/client/res/tablep.obj"));
         }
   catch (FileNotFoundException e){
       System.err.println(e);
       System.exit(1);
   }

   catch (IncorrectFormatException e){
       System.err.println(e);
       System.exit(1);
   }
    catch (ParsingErrorException e){
       System.err.println(e);
       System.exit(1);
   }

   Keytrans.addChild(skey.getSceneGroup());
   objRoot.addChild(Keytrans);

   TransformGroup tabletransform = new TransformGroup();
   Transform3D settable = new Transform3D();
   settable.setTranslation(new Vector3d(-0.177,0.045,-0.14));
   settable.setScale(new Vector3d(1.5,1.15,1.0));
   
  
   tabletransform.addChild(stable.getSceneGroup());
    tabletransform.setTransform(settable);

 

   objRoot.addChild(tabletransform);
   
   return objRoot;

}

   

}
