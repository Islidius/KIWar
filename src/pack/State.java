package pack;

public abstract class State {
	
	Main main;
	
	public State(Main m){
		main = m;
	}
	
	public abstract void load();
	public abstract void unload();
	public abstract void tick();
	public abstract void render();
	public abstract void keypressed(int key);
	public abstract void mousepressed(int x,int y,int button);
	
	public void buttonpressed(int id){} // to overwrite

}
