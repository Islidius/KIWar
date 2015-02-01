package pack;

public class Particle {
	
	Vector pos;
	Vector move;
	double r,g,b; // color
	double size;
	Matrix rotx;
	Matrix roty;
	double ttl;
	
	public Particle(Vector pos,Vector move,double size,double ttl){
		this.pos = pos;this.move = move;this.size = size;this.ttl = ttl;
		r = 1;
		g = 137/255.0;
		b = 0;
	}
	
	public Particle(Vector pos,Vector move,double size,double ttl,double r,double g,double b){
		this.pos = pos;this.move = move;this.size = size;this.ttl = ttl;this.r = r;this.g = g;this.b = b;
	}
	
	public void tick(){
		pos = pos.add(move);		
		move = move.add(Stats.get().g);
		ttl -= Time.get().peek();
		
		if(pos.getY() <= 0){ // underground = dead
			ttl = 0;
		}
		
	}
	
	public void render(){
		Render.get().setColor(r, g, b);
		Vector[] v = new Vector[3];
		v[0] = new Vector(pos.getX()-size,pos.getY(),pos.getZ());
		v[1] = new Vector(pos.getX(),pos.getY()-size,pos.getZ());
		v[2] = new Vector(pos.getX(),pos.getY(),pos.getZ()-size);
		Render.get().drawTri3D(v);
		Render.get().resetColor();
	}
	
	

}
