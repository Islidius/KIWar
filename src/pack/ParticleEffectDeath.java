package pack;

import java.util.Random;

public class ParticleEffectDeath extends ParticleEffect{
	Vector pos;
	Random r = new Random();
	double timerunning = 2000;
	ParticleSystem ps;
	
	public ParticleEffectDeath(Vector pos){
		this.pos = pos;
	}
	
	@Override
	public void init(ParticleSystem ps) {
		this.ps = ps;
		for(int i = 0;i<200;i++){
			Vector move = new Vector(0,3*r.nextDouble(),0);
			move = move.multiply(new Matrix(Matrix.ROT3D_X,r.nextDouble()));
			move = move.multiply(new Matrix(Matrix.ROT3D_Y,r.nextDouble()*2*Math.PI));
			ps.particles.add(new Particle(pos,move,1,2000,0.5+r.nextDouble()/2,0,0));
		}
	}

	@Override
	public boolean tick() {
		timerunning -= Time.get().peek();
		if(timerunning<= 0)return true;
		
		for(int i = 0;i<5;i++){
			Vector move = new Vector(0,3*r.nextDouble(),0);
			move = move.multiply(new Matrix(Matrix.ROT3D_X,r.nextDouble()));
			move = move.multiply(new Matrix(Matrix.ROT3D_Y,r.nextDouble()*2*Math.PI));
			ps.particles.add(new Particle(pos,move,1,2000,0.5+r.nextDouble()/2,0,0));
		}
		
		return false;
	}

}
