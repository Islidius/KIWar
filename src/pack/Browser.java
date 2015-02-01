package pack;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import server.ServerMain;

public class Browser extends State{

	Button back;
	Button news;
	Button rel;
	Button join;
	
	List<InetAddress> address = new ArrayList<InetAddress>();
	Discover discover;
	int idpos = -1;int width  = 1000;int heigth = 800;
	
	boolean canopenserver = true; // no serverrunning
	
	Vector pos;
	
	public Browser(Main m) {
		super(m);
		
		pos = new Vector(200,100);
		
		back = new Button(0,this,new Vector(Stats.get().width-116,100),"Back");
		news = new Button(1,this,new Vector(Stats.get().width-116,150),"New ");
		rel = new Button(2,this,new Vector(Stats.get().width-116,200),"Rel ");
		join = new Button(3,this,new Vector(Stats.get().width-116,250),"Join");
		
		discover = new Discover(this);
	}

	@Override
	public void load() {
		canopenserver = true; // only goes into broweser if server is ende
		news.text = "New "; // update text on button
		idpos = -1;
		discover.start();
		discover = new Discover(this);
	}

	@Override
	public void unload() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick() {
		back.tick();
		news.tick();
		rel.tick();
		join.tick();
	}

	@Override
	public void render() {
		back.render();
		news.render();
		rel.render();
		join.render();
		
		if(idpos != -1){
			Vector[] v = new Vector[4];
			v[0] = new Vector(pos.getX(),pos.getY()+idpos*20);
			v[1] = new Vector(pos.getX(),pos.getY()+(idpos+1)*20);
			v[2] = new Vector(pos.getX()+width,pos.getY()+(idpos+1)*20);
			v[3] = new Vector(pos.getX()+width,pos.getY()+idpos*20);
			Render.get().drawQuad2D("buttonback", v);
		}
		
		for(int i = 0;i<address.size();i++){
			Render.get().drawString(address.get(i).getHostAddress(), new Vector(pos.getX()+5,pos.getY()+i*20),2);
		}
	}

	@Override
	public void keypressed(int key) {
		if(key == Keyboard.KEY_G){
			main.switchstate("game");
		}
		
	}

	@Override
	public void mousepressed(int x, int y, int button) {
		back.mousepressed(x, y, button);
		news.mousepressed(x, y, button);
		rel.mousepressed(x, y, button);
		join.mousepressed(x, y, button);
		
		if(x >= pos.getX() && x <= pos.getX()+width){
			int dy = y - (int)pos.getY();
			int nid = dy/20;
			if(nid >= 0 && nid < address.size()){
				idpos = nid;
			}
		}
		
	}
	
	public void buttonpressed(int id){
		if(id == 0){
			main.switchstate("menu_main");
		}
		else if(id == 1){
			if(canopenserver){
				new ServerMain();
				discover.start();
				discover = new Discover(this);
				canopenserver = false;
				news.text = "--- ";
			}
		}
		else if(id == 2){
			discover.start();
			discover = new Discover(this);
		}
		else if(id == 3 && idpos != -1){
			try {
				main.client.connect(5000, address.get(idpos), 25556,25555);
				Packet2RequestConnection p2 = new Packet2RequestConnection();
				main.client.sendTCP(p2);
				System.out.println("SEND");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	class Discover extends Thread{
		Browser b;
		List<InetAddress> address;
		
		public Discover(Browser b){
			this.b = b;
		}
		
		public void run(){
			address = main.client.discoverHosts(25555, 1000);
			List<InetAddress> todel = new ArrayList<InetAddress>();
			
			for(InetAddress ia:address){ // remove 127.0.0.1
				if(ia.getAddress()[0] == 127){
					todel.add(ia);
				}
			}
			
			for(InetAddress ia:todel){ // remove entries
				address.remove(ia);
			}
			
			todel.clear();
			
			b.address = address;
			b.idpos = -1;
		}
		
	}

}
