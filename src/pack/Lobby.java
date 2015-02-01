package pack;

import java.io.File;
import java.util.ArrayList;

public class Lobby extends State{
	
	Button ready;
	Button back;
	Button add;
	Button rem;
	
	Tester test = new Tester(); // for testing selected KI
	boolean error = false;
	
	ArrayList<String> kis = new ArrayList<String>(); // KIs to choose from
	int idpos = -1;int width = 200;int height = 1000;
	Vector pos;
	
	ArrayList<String> players = new ArrayList<String>(); // players in the lobby
	ArrayList<Boolean> rdy = new ArrayList<Boolean>(); // status of the player
	Vector posplayers;
	boolean selfready = false; // status of the client
	
	int botcount = 0;
	

	public Lobby(Main m) {
		super(m);
		back = new Button(0,this,new Vector(Stats.get().width-116,100),"Back");
		ready = new Button(1,this,new Vector(Stats.get().width-116,150),"Rdy ");
		add = new Button(2,this,new Vector(Stats.get().width-116,250),"+KI ");
		rem = new Button(3,this,new Vector(Stats.get().width-116,300),"-KI ");
		
		pos = new Vector(20,100);
		posplayers = new Vector(250,100);
	}

	@Override
	public void load() { // clear all Infos
		players.clear();rdy.clear();
		selfready = false;idpos = -1;botcount = 0;
		kis.clear();
		loadkis();
		
	}
	
	public void loadkis(){ // load kis from disk
		
		File dir = new File("scripts");
		File[] lists = dir.listFiles();
		
		for(int i = 0;i<lists.length;i++){
			if(lists[i].isFile()){
				kis.add(lists[i].getName());
			}
		}
		
	}

	@Override
	public void unload() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick() {
		back.tick();
		ready.tick();
		add.tick();
		rem.tick();
	}

	@Override
	public void render() {
		back.render();
		ready.render();
		add.render();
		rem.render();
		
		if(idpos != -1){
			Vector[] v = new Vector[4];
			v[0] = new Vector(pos.getX(),pos.getY()+idpos*20);
			v[1] = new Vector(pos.getX(),pos.getY()+(idpos+1)*20);
			v[2] = new Vector(pos.getX()+width,pos.getY()+(idpos+1)*20);
			v[3] = new Vector(pos.getX()+width,pos.getY()+idpos*20);
			Render.get().drawQuad2D("buttonback", v);
		}
		
		for(int i = 0;i<kis.size();i++){
			Render.get().drawString(kis.get(i), new Vector(pos.getX()+5,pos.getY()+i*20),2);
		}
		
		for(int i = 0;i<players.size();i++){
			Render.get().drawString((rdy.get(i) ? "RDY ":"--- ")+players.get(i), new Vector(posplayers.getX()+5,posplayers.getY()+i*20),2);
		}
		
		Render.get().drawString("BOT COUNT: "+botcount, new Vector(posplayers.getX()+5,posplayers.getY()+players.size()*20),2);
		
		if(main.rdyswitch){
			main.switchstate("game");
		}
	}

	@Override
	public void keypressed(int key) {
		
	}

	@Override
	public void mousepressed(int x, int y, int button) {
		back.mousepressed(x, y, button);
		ready.mousepressed(x, y, button);
		add.mousepressed(x, y, button);
		rem.mousepressed(x, y, button);
		
		if(x >= pos.getX() && x <= pos.getX()+width){
			int dy = y - (int)pos.getY();
			int nid = dy/20;
			if(nid >= 0 && nid < kis.size()){
				idpos = nid;
			}
		}
	}
	
	public void buttonpressed(int id){
		if(id == 0){ // back
			main.client.close();
			main.switchstate("browser");
		}
		else if(id == 1){  // ready
			selfready = !selfready && idpos != -1;
			ready.text = selfready ? "NOT " : "Rdy ";
			Packet4SetReady p4 = new Packet4SetReady();
			p4.ready = selfready;
			main.client.sendTCP(p4);
		}
		else if(id == 2){ // bot +
			botcount += 1;
			Packet5PlayerInfo p5 = new Packet5PlayerInfo(); // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!test solution
			p5.name = "BOT";
			p5.mode = botcount;
			main.client.sendTCP(p5);
		}
		else if(id == 3){// bot -
			if(botcount > 0)botcount -= 1;
			Packet5PlayerInfo p5 = new Packet5PlayerInfo(); // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!test solution
			p5.name = "BOT";
			p5.mode = botcount;
			main.client.sendTCP(p5);
		}
	}
	
	public void setready(String name,boolean rdy){ // called from server
		int posid = players.indexOf(name);
		this.rdy.set(posid, rdy);
	}
	
	public void addplayer(String name){ // called from server
		players.add(name);
		rdy.add(false);
	}
	
	public void removeplayer(String name){ // called from server
		int posid = players.indexOf(name);
		players.remove(posid);
		rdy.remove(posid);
	}

}
