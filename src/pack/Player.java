package pack;

import java.util.ArrayList;
import java.util.HashMap;

public class Player {
	
	Ki ki;
	Model model;
	Model modeldead;
	
	Vector pos; // position od robot
	Vector move; // movevector
	double yrot; // rotation on world(x,z)
	double hitboxr;
	
	double maxmove = 0.01;
	
	String name = "Player";
	
	double inlavatime = Stats.get().maxinlava;
	boolean dead = false;
	boolean inlava = false;
	
	HashMap<String,Double> cooldowns = new HashMap<String,Double>();
	ArrayList<Effect> effects = new ArrayList<Effect>(); // aktive effect
	
	public Player(Ki ki,String texture,String name){
		this.ki = ki;yrot = 0;this.name = name;
		ki.setcontroller(this);
		hitboxr = 7;// in real (6.717)
		model = new Model("models/robot.obj","tex_robot");
		modeldead = new Model("models/robotdead.obj","tex_robot");
		pos = new Vector(0,0,0);
		move = new Vector(0,0,0);
		
		for(String s:Stats.get().skill.getskillnames()){ // set all to 0
			cooldowns.put(s, 0.0D);
		}
	}
	
	public boolean collidenow(Player p){
		return pos.distance(p.pos) < hitboxr+p.hitboxr;
	}
	
	public boolean collidenow(Entity e){
		return pos.distance(e.pos) < hitboxr+e.hitboxr;
	}
	
	public void useskill(String skill){
		cooldowns.put(skill, Stats.get().skill.getcooldown(skill)); // use skill
	}
	
	public boolean getcool(String skill){
		return cooldowns.get(skill) <= 0; // cooldowns goes negativ(for testing)
	}
	
	public void tick(){
		for(Effect ef:effects){ // tick all active effects
			ef.starttick(this);
		}
		
		tickskill();
		//System.out.println("tickplayer:"+name);
		ki.tick();
		
		for(Effect ef:effects){ // tick all active effects
			ef.ontick(this);
		}
		
		//applyslow();
		getrot(); // set the rotation
		pos = pos.add(move.multiply(Time.get().peek()));
		
		
		ArrayList<Effect> todel = new ArrayList<Effect>(); // delete after next tick
		for(Effect ef:effects){ // end tick all effects
			ef.endtick(this);
			ef.ttl -= Time.get().peek(); // reduce counter
			if(ef.ttl <= 0){ // needs to del
				todel.add(ef); // add to del list
			}
		}
		
		for(Effect ef:todel){ // remove all effects
			effects.remove(ef); 
		}
		todel.clear(); // clear list
		
		// in lava 
		
		inlava = pos.length() > ki.game.world.radianaklava;
		
		if(inlava){
			inlavatime -= Time.get().peek();
		}
		else{
			inlavatime = Stats.get().maxinlava;
		}
		
		if(inlavatime <= 0){
			dead = true;
			ki.game.parsys.addeffect(new ParticleEffectDeath(pos));
		}
		
	}
	
	public void tickskill(){ // reduce cooldown
		for(String s:cooldowns.keySet()){
			double akcool = cooldowns.get(s); // get ak time
			akcool -= Time.get().peek(); // reduce by tick time -> cooldown in milliesec
			cooldowns.put(s, akcool);  //store
		}
	}
	
	public void getrot(){
		if(move.length() != 0){
			yrot = move.getyrot();
		}
	}
	
	public void applyslow(){
		double slowfactor = 0;
		
		if(move.length() < 0.1){
			slowfactor = move.length(); // drift protection
		}
		else{
			slowfactor = move.length()/3; // normal slow
		}
		
		move = move.add(new Vector(slowfactor,0,0).multiply(new Matrix(Matrix.ROT3D_Y,Math.PI))); // slowfactor gedrecht 180 -> slow
	}
	
	public void render(){
		if(dead == false){model.render(pos, new Matrix(Matrix.ROT3D_Y,yrot-Math.PI/2));}
		else{modeldead.render(pos, new Matrix(Matrix.ROT3D_Y,yrot-Math.PI/2));}
	}
	
	// function for the Ki
	
	public Vector getPos(){
		return pos;
	}
	
	public Vector getMove(){
		return move;
	}
	
	public double getRot(){
		return yrot;
	}
	
	public void setMove(Vector move){
		this.move = move;
	}
	
	public void addeffect(Effect ef){
		effects.add(ef);
	}
	
	public void removealleffects(){
		effects.clear();
	}

}
