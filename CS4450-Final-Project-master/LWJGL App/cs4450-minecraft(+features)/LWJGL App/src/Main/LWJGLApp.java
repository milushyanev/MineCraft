package Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;

public class LWJGLApp {
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    public static void main(String[] args) throws FileNotFoundException
    {
        Basic3D basic = new Basic3D();
        basic.start();
    }
}
