package pack;

import org.lwjgl.Sys;

public class Time {
	
	private static Time time;
    private long lastFrame;
    private int delta;
    private int fpscounter;
    private int fpsconst;
    private long lastFPS;
	
	public static Time get(){
		if(time == null)time = new Time();
		return time;
	}
	
	public Time(){
		lastFPS = getTime();
	}
	

    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }
    
    public void update(){
    	getDelta();
    	updateFPS();
    }

    public void getDelta() {
        long currentTime = getTime();
        delta = (int) (currentTime - lastFrame);
        lastFrame = getTime();
    }
    
    public  void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            fpsconst = fpscounter;
            //System.out.println(fpsconst);
            fpscounter = 0;
            lastFPS += 1000;
        }
        fpscounter++;
    }
    
    public int peek(){
    	return delta;
    }
    
    public int getfps(){
    	return fpsconst;
    }
}
