package pack;

import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTranslated;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Game extends State{
	
	World world;
	ParticleSystem parsys;
	
	ArrayList<Player> players = new ArrayList<Player>();
	ArrayList<Entity> entitys = new ArrayList<Entity>();
	
	final Object lock = new Object();
	
	Vector poscam = new Vector(0,0,0);
	Vector dircam = new Vector(0,0,0);
	
	double tostart = 5000;
	boolean spawned = false; // compute spawn only once

	public Game(Main m) {
		super(m);
		world = new World();
		parsys = new ParticleSystem();
	}

	@Override
	public void load() {
		Render.get().init3D();
		players.clear();entitys.clear();tostart = 5000;spawned = false;
		world.reset();
		System.out.println("load");
		/*String s = "function tick()\n";
		s += "myRobot:moveto(Vector:new(100,100))\n";
		s += "local target = allRobot[1]:getPos():copy()\n";
		s += "local move = myRobot:getPos():copy()\n";
		s += "move:multiply(-1)\n";
		s += "target:add(move)\n";
		s += "myRobot:useskill(\"rocket\",target)\n";
		s += "end\n";
		
		players.add(new Player(new Ki(this,s),"","BOT1"));
		
		s = "function tick()\n";
		s += "myRobot:moveto(Vector:new(100,40))\n";
		s += "myRobot:useskill(\"rocket\",Vector:new(0.1,0))\n";
		s += "if myRobot.pos:getY() < -100 then\n";
		s += "myRobot:useskill(\"teleport\",Vector:new(100,200))\n";
		s += "end\n";
		s += "end\n";
		
		players.add(new Player(new Ki(this,s),"","BOT2"));*/
		
		//spawnentity("rocket",new Vector(0,10,0),new Vector(0.001,0,0));
		
	}

	@Override
	public void unload() {
		Render.get().init2D();
	}
	
	//spawn entity
	public void spawnentity(String skill,Vector pos,Vector move){ // spawn new Enity with factory entity and name
		Entity e = Stats.get().skill.getentity(skill).returnnew(pos, move);
		entitys.add(e);
	}

	@Override
	public void tick() {
		//gerneral
		tostart -= Time.get().peek();
		
		if(tostart <= 0 && !spawned){
			computespawn(); // sets the spawns
			spawned = true;
		}
		
		synchronized (lock){
			for(Player p:players){
				if(p.dead == false && tostart <= 0){p.tick();}
			}
		}
		
		ArrayList<Entity> todel = new ArrayList<Entity>();
		for(Entity e:entitys){
			e.tick();
			if(e.dead == true){
				todel.add(e); // append to buffer list
			}
		}
		
		for(Entity e:todel){ // to del
			entitys.remove(e); // del all in list
		}
		todel.clear(); // clear buffer
		world.tick();
		parsys.tick();
		
		//colision
		
		synchronized (lock){
			for(Player p:players){
				for(Entity e:entitys){
					if(e.collidenow(p)){  // colide with entity
						e.doeffect(p); // do the effect
						parsys.addeffect(new ParticleEffectHit(e.pos));
					}
				}
			}
		}
		
		
		//movement
		
		double dx = Mouse.getDX()*0.2; // rotiert um y
		double dy = Mouse.getDY()*0.2; // rotiert um x
		
		double newdx = (dx+dircam.getY())%360;
		if(newdx < 0){ newdx = 360+newdx;}
		
		double newdy = (-dy+dircam.getX())%360;
		if(newdy < 0){ newdy = 360+newdy;}
		
		dircam = new Vector(newdy,newdx,0);
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)){
			poscam = poscam.add(new Vector(-10*Math.sin(Math.toRadians(dircam.getY())),0,10*Math.cos(Math.toRadians(dircam.getY()))));
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_S)){
			poscam = poscam.add(new Vector(10*Math.sin(Math.toRadians(dircam.getY())),0,-10*Math.cos(Math.toRadians(dircam.getY())))); 
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_A)){
			poscam = poscam.add(new Vector(10*Math.sin(Math.toRadians(dircam.getY()+90)),0,-10*Math.cos(Math.toRadians(dircam.getY()+90))));// 90 + -= Left
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_D)){
			poscam = poscam.add(new Vector(-10*Math.sin(Math.toRadians(dircam.getY()+90)),0,10*Math.cos(Math.toRadians(dircam.getY()+90)))); // 90 = Right
		}
		
		else if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
			poscam = poscam.add(new Vector(0,10,0));
		}
		else if(Keyboard.isKeyDown(Keyboard.KEY_E)){
			poscam = poscam.add(new Vector(0,-10,0));
		}
		
	}
	
	public void computespawn(){ // sets the spawns short befor start
		double deltaan = (2*Math.PI) / players.size();
		double radius = 100;
		
		int counter = 0;
		for(Player p:players){
			Vector pos = new Vector(radius,0,0);
			pos = pos.multiply(new Matrix(Matrix.ROT3D_Y,deltaan*counter));
			p.pos = pos;
			counter += 1;
		}
	}

	@Override
	public void render() {
		world.render();
		
		synchronized (lock){
			for(Player p:players){
				p.render();
			}
		}
		for(Entity e:entitys){
			e.render();
			//System.out.println("RENDER");
		}
		
		parsys.render();
		
		synchronized (lock){
			rendernames();
		}
		
		
		glLoadIdentity();
		
		glRotated(dircam.getX(),1,0,0);
		glRotated(dircam.getY(),0,1,0);
		glRotated(dircam.getZ(),0,0,1);
		
		glTranslated(poscam.getX(),poscam.getY(),poscam.getZ());
	}
	
	public void rendernames(){
		for(Player p:players){ // names
			Render.get().drawString3D(p.name, dircam, p.pos.add(new Vector(0,10,0)), 0.5);
			
			if(p.inlava){ // lava action bar
				if(p.dead == false){ // not dead
					
					double perlava = p.inlavatime/Stats.get().maxinlava; // perzent of time in lava
					
					double barwidth = 48*0.5*perlava;
					double barheight = 10*0.4;
					
					Vector[] v = new Vector[4];
					
					v[0] = new Vector(-barwidth/2,barheight,0);
					v[1] = new Vector(-barwidth/2,0,0);
					v[2] = new Vector(barwidth/2,0,0);
					v[3] = new Vector(barwidth/2,barheight,0);
					
					Matrix roty = new Matrix(Matrix.ROT3D_Y,Math.toRadians(180-dircam.getY()+180));
					Matrix rotx = new Matrix(Matrix.ROT3D_X,Math.toRadians(180-dircam.getX()+180));
					Matrix trans = new Matrix(Matrix.TRANS,p.pos.getX(),p.pos.getY()+12,p.pos.getZ());
					
					for(int i = 0;i<4;i++){
						v[i] = v[i].multiply(rotx);
						v[i] = v[i].multiply(roty);
						v[i] = v[i].multiply(trans);
					}
					
					Render.get().drawQuad3D("buttonback", v);
				}
				
				else{ // dead -> skull
					double skullwidth = 20;
					double skullheight = 20;
					Vector[] v = new Vector[4];
					
					v[0] = new Vector(-skullwidth/2,skullheight,0);
					v[1] = new Vector(-skullwidth/2,0,0);
					v[2] = new Vector(skullwidth/2,0,0);
					v[3] = new Vector(skullwidth/2,skullheight,0);
					
					Matrix roty = new Matrix(Matrix.ROT3D_Y,Math.toRadians(180-dircam.getY()+180));
					Matrix rotx = new Matrix(Matrix.ROT3D_X,Math.toRadians(180-dircam.getX()+180));
					Matrix trans = new Matrix(Matrix.TRANS,p.pos.getX(),p.pos.getY()+12,p.pos.getZ());
					
					for(int i = 0;i<4;i++){
						v[i] = v[i].multiply(rotx);
						v[i] = v[i].multiply(roty);
						v[i] = v[i].multiply(trans);
					}
					
					Render.get().drawQuad3D("skull", v);
				}
			}
		}
	}

	@Override
	public void keypressed(int key) {
		if(key == Keyboard.KEY_ESCAPE){
			main.switchstate("browser");  // go back
			main.client.close();
		}
		if(key == Keyboard.KEY_F){
			System.out.println("POSITION:");
			poscam.print();
			System.out.println("ROTATION:");
			dircam.print();
		}
	}
	
	public void addplayer(Player p){
		synchronized (lock){
			players.add(p);
		}
	}
	

	@Override
	public void mousepressed(int x, int y, int button) {
		// TODO Auto-generated method stub
		
	}

}
