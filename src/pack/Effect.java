package pack;

public abstract class Effect {
	
	double ttl = 0; // ttl = time to live
	Vector move;
	
	public Effect(double ttl,Vector move){
		this.ttl = ttl;this.move = move;
	}
	
	public abstract void starttick(Player p); // start in tick methode
	public abstract void ontick(Player p); // after ki tick
	public abstract void endtick(Player p);  // at the end in tick methode
	
	public abstract Effect returnnew(double ttl,Vector move);

}
