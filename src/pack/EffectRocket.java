package pack;

public class EffectRocket extends Effect{

	public EffectRocket(double ttl,Vector move) {
		super(ttl,move);
	}
	
	@Override
	public void starttick(Player p) {
	}

	@Override
	public void ontick(Player p) {
		p.move = p.move.add(move); // add knockback
	}

	@Override
	public void endtick(Player p) {
		p.move = p.move.add(move.multiply(-1)); // subract knockback
	}

	@Override
	public Effect returnnew(double ttl, Vector move) {
		return new EffectRocket(ttl,move);
	}

	
}
