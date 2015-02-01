package pack;

public class Editor extends State{
	
	Code code;
	List list;
	Tester tester;
	
	Button back;
	Button newb;
	Button del;
	Button test;
	
	public Editor(Main m) {
		super(m);
		
		code = new Code(this);
		list = new List(this);
		tester = new Tester();
		back = new Button(0,this,new Vector(Stats.get().width-116,100),"Back");
		newb = new Button(1,this,new Vector(Stats.get().width-116,150),"New ");
		del = new Button(2,this,new Vector(Stats.get().width-116,200),"Del ");
		test = new Button(3,this,new Vector(Stats.get().width-116,300),"Test");
	}

	@Override
	public void load() {
		list.load();
	}

	@Override
	public void unload() {
		list.save();
	}

	@Override
	public void tick() {
		code.tick();
		list.tick();
		back.tick();
		newb.tick();
		del.tick();
		test.tick();
	}

	@Override
	public void render() {
		code.render();
		list.render();
		back.render();
		newb.render();
		del.render();
		test.render();
	}

	@Override
	public void keypressed(int key) {
		code.keypressed(key);
		
	}

	@Override
	public void mousepressed(int x, int y, int button) {
		code.mousepressed(x, y, button);
		list.mousepressed(x, y, button);
		back.mousepressed(x, y, button);
		newb.mousepressed(x, y, button);
		del.mousepressed(x, y, button);
		test.mousepressed(x, y, button);
		
	}
	
	public void buttonpressed(int id){
		if(id == 0){
			main.switchstate("menu_main");
		}
		else if(id == 1){
			list.list.add("KI_"+list.list.size()+".txt");
			list.code.add("");
			list.pointer.add(0);
			
		}
		else if(id == 2){
			if(list.id != -1){
				list.list.remove(list.id);  // loeschen
				list.code.remove(list.id);
				list.pointer.remove(list.id);
				if(list.id-1 >= 0){  // hat noch eintrage
					code.code = list.code.get(list.id-1);  // neue laden
					code.pointer = list.pointer.get(list.id-1);
					list.id = list.id-1;
				}
				else{
					code.code = ""; // keine eintrage mehr
					code.pointer = 0;
					list.id = -1;
				}
			}
		}
		else if(id == 3){
			System.out.println(tester.test(code.code));
			code.error = !tester.test(code.code);
			if(code.error)code.errorline = tester.getlastpos();
		}
	}

}
