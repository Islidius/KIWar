package pack;

import org.lwjgl.opengl.Display;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;

public class Main {
	
	State state;
	Window window;
	Input input;
	
	State menu = new MenuMain(this);
	State editor = new Editor(this);
	State game = new Game(this);
	State browser = new Browser(this);
	State lobby = new Lobby(this);
	
	Client client;
	
	boolean rdyswitch = false;
	
	public Main(){
		window = new Window(Stats.get().width,Stats.get().height);
		window.creatwindow();
		
		Render.get().initGl();
		Render.get().init2D();
		
		input = new Input(this);
		
		state = menu;
		state.load();
		
		client = new Client();
		new Thread(client).start();
		
		register();
		
		client.addListener(new NetListener(this));
		
		run();
	}
	
	public void register(){ // register classes
		Kryo kryo = client.getKryo();
		kryo.register(Packet2RequestConnection.class);
		kryo.register(Packet3AnswerConnection.class);
		kryo.register(Packet4SetReady.class);
		kryo.register(Packet5PlayerInfo.class);
		kryo.register(Packet6PlayerCode.class);
	}
	
	public void switchstate(State s){
		state.unload();
		state = s;
		state.load();
	}
	
	public void switchstate(String s){ // states by name
		if(s.equalsIgnoreCase("menu_main"))switchstate(menu);
		else if(s.equalsIgnoreCase("editor"))switchstate(editor);
		else if(s.equalsIgnoreCase("game"))switchstate(game);
		else if(s.equalsIgnoreCase("browser"))switchstate(browser);
		else if(s.equalsIgnoreCase("lobby"))switchstate(lobby);
	}
	
	public void run(){
		while(!Display.isCloseRequested()){
			Time.get().update();
			
			input.tick();
			state.tick();
			
			Render.get().clear();
			
			state.render();
			input.render();  // cursor over state
			
			Display.update();
			Display.sync(60);
		}
		
		window.destroywindow();
		client.close();
	}
	
	
	
	public static void main(String[] args) {
		new Main();

	}

}
