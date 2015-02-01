package pack;

public class MenuMain extends State{

	Button editor;
	Button game;
	Button exit;
	
	
	public MenuMain(Main m) {
		super(m);
		
		editor = new Button(0,this,new Vector(0,100),"Editor");
		game = new Button(1,this,new Vector(0,150),"Game",164,32);
		exit = new Button(2,this,new Vector(0,200),"Exit",164,32);
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unload() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick() {
		editor.tick();
		game.tick();
		exit.tick();
		
	}

	@Override
	public void render() {
		editor.render();
		game.render();
		exit.render();
	}

	@Override
	public void keypressed(int key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousepressed(int x, int y, int button) {
		editor.mousepressed(x, y, button);
		game.mousepressed(x, y, button);
		exit.mousepressed(x, y, button);
	}
	
	public void buttonpressed(int id){
		if(id == 0){
			main.switchstate("editor");
		}
		else if(id == 1){
			main.switchstate("browser");
		}
		else if(id == 2){
			main.window.destroywindow();
		}
	}

}
