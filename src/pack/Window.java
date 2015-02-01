package pack;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GLContext;

import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.LWJGLException;

public class Window {
	
	int width,height;
	boolean resizable = false;
	boolean fullscreen = true;
	boolean vsync = true;
	
	public Window(int width,int height){
		this.width = width;this.height = height;
		
	}
	
	public void creatwindow(){
		try {
            if (fullscreen) {
                //Display.setDisplayModeAndFullscreen(Display.getDesktopDisplayMode());
            	Display.setResizable(resizable);
            	Display.setDisplayMode(Display.getDesktopDisplayMode());
            	Display.setFullscreen(true);
            } else {
                Display.setResizable(resizable);
                Display.setDisplayMode(new DisplayMode(width, height));
            }
            Display.setTitle("test(v0.001)");
            //Mouse.setGrabbed(false);
           // Display.setVSyncEnabled(vsync);
            Display.create();
            
            System.out.println(Display.getWidth()+ ": "+Display.getHeight()+ ":"+Display.isFullscreen());
            
            System.out.println("create");
        } catch (LWJGLException ex) {
            System.err.println("Display initialization failed.");
            System.out.println("err1");
            System.exit(1);
        }

        if (fullscreen) {
            Mouse.setGrabbed(true);
        } else {
            Mouse.setGrabbed(false);
        }

        if (!GLContext.getCapabilities().OpenGL11) {
            System.err.println("Your OpenGL version doesn't support the required functionality.");
            System.out.println("err2");
            Display.destroy();
            System.exit(1);
        }
		
	}
	
	public void destroywindow(){
		System.out.println("destroy");
		Mouse.setGrabbed(false);
		Display.destroy();
        System.exit(1);
	}

}
