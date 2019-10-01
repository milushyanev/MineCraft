
package Main;

import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.Sys;
import java.nio.FloatBuffer;
import java.util.Timer;
import org.lwjgl.BufferUtils;


public class FPCameraController {
    
    private FloatBuffer lightPosition;
    private FloatBuffer whiteLight;
    
    //3d vector to store the camera's position in
    private Vector3f position = null;
    private Vector3f lPosition = null;
    //the rotation around the Y axis of the camera
    private float yaw = 0.0f;
    //the rotation around the X axis of the camera
    private float pitch = 0.0f;
    private Vector3Float me;
    private Chunk chunk = new Chunk(16,16,16);
    private boolean firstRender;
    
    
    public FPCameraController(float x, float y, float z)
    {
        //instantiate position Vector3f to the x y z params.
        position = new Vector3f(x, y, z);
        lPosition = new Vector3f(x,y,z);
        lPosition.x = 0f;
        lPosition.y = 15f;
        lPosition.z = 0f;
        
        yaw = 0.0f;
        pitch = 80.0f;
        firstRender = true;
       
    }
    public void yaw(float amount)
    {
        //increment the yaw by the amount param
        yaw += amount;
    }
    //increment the camera's current yaw rotation
    public void pitch(float amount)
    {
        //increment the pitch by the amount param
        pitch -= amount;
    }
    public void walkForward(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x -= xOffset;
        position.z += zOffset;
      
    }
    public void walkBackwards(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw));
        position.x += xOffset;
        position.z -= zOffset;
    }
    public void strafeLeft(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw+90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw+90));
        position.x += xOffset;
        position.z -= zOffset;
    }
    public void strafeRight(float distance)
    {
        float xOffset = distance * (float)Math.sin(Math.toRadians(yaw-90));
        float zOffset = distance * (float)Math.cos(Math.toRadians(yaw-90));
        position.x += xOffset;
        position.z -= zOffset;
    }
    //moves the camera up relative to its current rotation (yaw)
    public void moveUp(float distance)
    {
        position.y -= distance;
    }
    //moves the camera down
    public void moveDown(float distance)
    {
        position.y += distance;
    }
    //translates and rotate the matrix so that it looks through the camera
    //this does basically what gluLookAt() does
    public void lookThrough()
    {
        //rotate the pitch around the X axis
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        //rotate the yaw around the Y axis
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        //translate to the position vector's location
        glTranslatef(position.x, position.y, position.z);
        
        FloatBuffer lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(lPosition.x).put(
        lPosition.y).put(lPosition.z).put(1.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
    }
    public void gameLoop()
    {
        FPCameraController camera = new FPCameraController(30f, -60f, 30f);
        float gravity = 0.0f;
        float dx = 0.0f;
        float dy = 0.0f;
        float dt = 0.0f; //length of frame
        float lastTime = 0.0f; // when the last frame was
        long time = 0;
        float mouseSensitivity = 0.09f;
        float movementSpeed = .40f; 
        //hide the mouse
        Mouse.setGrabbed(true);
        
        NightCycleManager nightCycleManager = new NightCycleManager();
        while (!Display.isCloseRequested() &&
        !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
        {
            if (Sys.getTime() - lastTime > 2000){
              nightCycleManager.nightLightArrays();
              lastTime = Sys.getTime();
          }
            
  
            
          
            //distance in mouse movement
            //from the last getDX() call.
            dx = Mouse.getDX();
            //distance in mouse movement
            //from the last getDY() call.
            dy = Mouse.getDY();

            camera.yaw(dx * mouseSensitivity);
            camera.pitch(dy * mouseSensitivity);
            
            camera.moveDown(gravity);

            if (Keyboard.isKeyDown(Keyboard.KEY_R))//move forward
            {
                chunk = new Chunk(0,4,0);
            }
            
            if(Keyboard.isKeyDown(Keyboard.KEY_G)){
                if(gravity == 0.0f){
                    gravity = .15f;
                }
                else
                    gravity = 0.0f;
            }
            
            if (Keyboard.isKeyDown(Keyboard.KEY_Q))//move forward
            {
                glClearColor(0.115f, 0.194f, 0.251f,0.0f);
                nightLightArrays();
            }
         
            if (Keyboard.isKeyDown(Keyboard.KEY_E))//move forward
            {
                glClearColor(1.0f, 1.0f, 1.0f,1.0f);
                initLightArrays();
            }
            
            if (Keyboard.isKeyDown(Keyboard.KEY_W))//move forward
            {
                camera.walkForward(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_S))//move backwards
            {
                camera.walkBackwards(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_A))//strafe left 
            {
                camera.strafeLeft(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_D))//strafe right 
            {
                camera.strafeRight(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_UP))//move forward
            {
                camera.walkForward(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))//move backwards
            {
                camera.walkBackwards(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))//strafe left 
            {
                camera.strafeLeft(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))//strafe right 
            {
                camera.strafeRight(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))//move up 
            {
                camera.moveUp(movementSpeed);
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) 
            {
                camera.moveDown(movementSpeed);
            }   
            //set the modelview matrix back to the identity
            glLoadIdentity();
            //look through the camera before you draw anything
            camera.lookThrough();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            if(firstRender) {
                chunk = new Chunk(0,4,0);
                firstRender = false;
            }
            //you would draw your scene here.
            //render();
            chunk.render();
            //draw the buffer to the screen
            Display.update();
            Display.sync(60);
        }
        Display.destroy(); 
    }
 
  
    private void initLightArrays() {
        
        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(0.0f).put(0.0f).put(0.0f).put(1.0f).flip();
        
        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
        glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);
        glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);
        glLight(GL_LIGHT0, GL_AMBIENT, whiteLight);
        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);

    }
    
    public void nightLightArrays() {
        
        lightPosition = BufferUtils.createFloatBuffer(4);
        lightPosition.put(0.0f).put(0.0f).put(0.0f).put(1.0f).flip();
        
        whiteLight = BufferUtils.createFloatBuffer(4);
        whiteLight.put(0.115f).put(0.194f).put(0.251f).put(0.0f).flip();
        glLight(GL_LIGHT0, GL_POSITION, lightPosition);
        glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);
        glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);
        glLight(GL_LIGHT0, GL_AMBIENT, whiteLight);
        glEnable(GL_LIGHTING);
        glEnable(GL_LIGHT0);

    }
    
}

