package pack;

public class EffectTele extends Effect{

	public EffectTele(double ttl,Vector move) {
		super(ttl,move);
	}

	@Override
	public void starttick(Player p) {
	}

	@Override
	public void ontick(Player p) { // after ki ticks and before pos calc
		p.pos = move;  // port to move vector
		
		for(Effect ef:p.effects){
			ef.ttl = 1;
		}
	}

	@Override
	public void endtick(Player p) {
	}

	@Override
	public Effect returnnew(double ttl, Vector move) {
		return new EffectTele(ttl,move);
	}

}
