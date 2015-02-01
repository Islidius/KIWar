package pack;

public abstract class Entity {
	
	Vector pos;
	Vector move;
	double yrot = 0; // rot
	
	double hitboxr;
	double timelived = 0;
	
	boolean dead = false;
	
	Model model;
	
	public Entity(Vector pos,Vector move){ // model needs to set
		this.pos = pos;this.move = move;
		if(move != null)yrot = move.getyrot();
	}
	
	public void tick(){
		pos = pos.add(move.multiply(Time.get().peek())); // delta fur ausgleich ( peek = look up dor delta)
		if(pos.length() >= 1000){ // delete after out of areana
			dead = true;
		}
	}
	
	public void render(){
		//System.out.println("RENDER ENTITY");
		model.render(pos, new Matrix(Matrix.ROT3D_Y,yrot-Math.PI/2));
	}
	
	public abstract void doeffect(Player p);  // action performend to player
	
	public boolean collidenow(Player p){
		return pos.distance(p.pos) < hitboxr+p.hitboxr;
	}
	
	public abstract Entity returnnew(Vector pos,Vector move); // needs to return object of itsself (factory)

}
