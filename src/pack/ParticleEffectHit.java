package pack;

import java.util.Random;

public class ParticleEffectHit extends ParticleEffect{
	Vector pos;
	Random r = new Random();
	
	public ParticleEffectHit(Vector pos){
		this.pos = pos;
	}
	
	@Override
	public void init(ParticleSystem ps) {
		for(int i = 0;i<70;i++){
			Vector move = new Vector(r.nextDouble(),r.nextDouble(),r.nextDouble());
			move = move.multiply(new Matrix(Matrix.ROT3D_X,r.nextDouble()*2*Math.PI));
			move = move.multiply(new Matrix(Matrix.ROT3D_Y,r.nextDouble()*2*Math.PI));
			ps.particles.add(new Particle(pos,move,1,2000,1,0.5+r.nextDouble()/2,0));
		}
	}

	@Override
	public boolean tick() {
		return true;
	}

}
