package Main;

import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.GL_AMBIENT;
import static org.lwjgl.opengl.GL11.GL_DIFFUSE;
import static org.lwjgl.opengl.GL11.GL_LIGHT0;
import static org.lwjgl.opengl.GL11.GL_LIGHTING;
import static org.lwjgl.opengl.GL11.GL_POSITION;
import static org.lwjgl.opengl.GL11.GL_SPECULAR;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glLight;

public class NightCycleManager{
    private FloatBuffer lightPosition;
    private FloatBuffer whiteLight;
    private float x, y, z;
    private boolean darkenCycle;
        public NightCycleManager(){
            x = 1.0f;
            y = 1.0f;
            z = 1.0f;
            darkenCycle = true;
        }
        public void nightLightArrays() {
        
            lightPosition = BufferUtils.createFloatBuffer(4);
            lightPosition.put(0.0f).put(0.0f).put(0.0f).put(1.0f).flip();

            whiteLight = BufferUtils.createFloatBuffer(4);
           
            
            if(darkenCycle){
                x -= 0.1f;
                y -= 0.1f;
                z -= 0.1f;
                if(x < 0.1f)
                    darkenCycle = false;
            }else{
                x += 0.1f;
                y += 0.1f;
                z += 0.1f;
                if(x > 1.0f)
                    darkenCycle = true;
            }
            
            whiteLight.put(x).put(y).put(z).put(0.0f).flip();
            glLight(GL_LIGHT0, GL_POSITION, lightPosition);
            glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);
            glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);
            glLight(GL_LIGHT0, GL_AMBIENT, whiteLight);
            glEnable(GL_LIGHTING);
            glEnable(GL_LIGHT0);

        }
}