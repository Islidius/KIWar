package pack;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Input {
	
	int x,y;
	
	Main main;
	
	int keycount = 220;
	
	boolean[] button = new boolean[Mouse.getButtonCount()];
	boolean[] keys = new boolean[keycount];
	
	
	public Input(Main m){
		
		//System.out.println(Keyboard.getKeyCount());
		
		this.main = m;
		for (int i = 0; i < keycount; i++) {
			keys[i] = Keyboard.isKeyDown(i);
			//System.out.println(i+":"+Keyboard.getKeyName(i));
		}
		for (int i = 0; i < Mouse.getButtonCount(); i++) {
			button[i] = Mouse.isButtonDown(i);
		}
		
		x = Mouse.getX();y = Stats.get().height - Mouse.getY();
	}

	public void tick() {
		x = Mouse.getX();y = Stats.get().height - Mouse.getY();
		
		for (int i = 0; i < keycount; i++) {
			if(keys[i] == false && Keyboard.isKeyDown(i) == true){keypressed(i);keys[i] = true;}
			if(keys[i] == true && Keyboard.isKeyDown(i) == false){keyreleassed(i);keys[i] = false;}
		}
		for (int i = 0; i < Mouse.getButtonCount(); i++) {
			if(button[i] == false && Mouse.isButtonDown(i) == true){mousepressed(i);button[i] = true;}
			if(button[i] == true && Mouse.isButtonDown(i) == false){mousereleassed(i);button[i] = false;}
		}
	}
	
	public void render(){    //mouse cursor
		Vector[] v = new Vector[4];
		v[0] = new Vector(x,y);
		v[1] = new Vector(x,y+20);
		v[2] = new Vector(x+20,y+20);
		v[3] = new Vector(x+20,y);
		
		Render.get().drawQuad2D("cursor", v);
	}

	public void keypressed(int key) {
		main.state.keypressed(key);
	}
	
	public void keyreleassed(int key){
	}
	
	public void mousepressed(int mouse){
		main.state.mousepressed(x,y,mouse);
		
	}
	public void mousereleassed(int mouse){
		
	}
}
