package pack;

import java.util.ArrayList;

public class ParticleSystem {
	
	ArrayList<Particle> particles = new ArrayList<Particle>();
	ArrayList<ParticleEffect> effects = new ArrayList<ParticleEffect>();
	
	public void tick(){
		
		ArrayList<Particle> todel = new ArrayList<Particle>(); // particles to delete
		
		for(Particle p:particles){
			p.tick();
			if(p.ttl <= 0){ // no more time to live
				todel.add(p);
			}
		}
		
		for(Particle p:todel){
			particles.remove(p); // remove particles from list
		}
		
		todel.clear();
		
		ArrayList<ParticleEffect> todeleffect = new ArrayList<ParticleEffect>(); // particleseffects to delete
		
		for(ParticleEffect pe:effects){
			if(pe.tick()){
				todeleffect.add(pe);
			}
		}
		
		for(ParticleEffect pe:todeleffect){
			effects.remove(pe); // remove particleseffects from list
		}
		
		todeleffect.clear();
		
	}
	
	
	public void render(){
		for(Particle p:particles){
			p.render();
		}
	}
	
	
	public void addeffect(ParticleEffect pe){
		pe.init(this);
	}

}
