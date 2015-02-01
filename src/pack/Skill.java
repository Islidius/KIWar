package pack;

import java.util.ArrayList;
import java.util.HashMap;

public class Skill {
	
	HashMap<String,Double> cooldown = new HashMap<String,Double>();
	HashMap<String,Double> velocity = new HashMap<String,Double>();
	HashMap<String,Boolean> state = new HashMap<String,Boolean>(); // false = entity
	HashMap<String,Entity> ref = new HashMap<String,Entity>(); // for creation
	HashMap<String,Effect> eff = new HashMap<String,Effect>(); // for state = true
	ArrayList<String> skills = new ArrayList<String>(); // iterator
	
	public double getcooldown(String skill){
		return cooldown.get(skill);
	}
	
	public double getvelocity(String skill){
		return velocity.get(skill);
	}
	
	public boolean getstate(String skill){
		return state.get(skill);
	}
	
	public Entity getentity(String skill){
		return ref.get(skill);
	}
	
	public Effect geteffect(String skill){
		return eff.get(skill);
	}
	
	public void add(String skill,double cooldown,double velocity,Entity e){
		this.cooldown.put(skill, cooldown);
		this.velocity.put(skill, velocity);
		this.state.put(skill,false);
		ref.put(skill, e);
		skills.add(skill);
	}
	
	public void add(String skill,double cooldown,double velocity,Effect e){
		this.cooldown.put(skill, cooldown);
		this.velocity.put(skill, velocity);
		this.state.put(skill,true);
		eff.put(skill, e);
		skills.add(skill);
	}
	
	public ArrayList<String> getskillnames(){
		return skills;
	}

}
