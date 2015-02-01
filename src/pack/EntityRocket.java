package pack;

public class EntityRocket extends Entity{

	public EntityRocket(Vector pos, Vector move) {
		super(pos, move);
		model = new Model("models/rocket.obj","tex_rocket");
		hitboxr = 4; // max vector lenght (3.8938)
	}

	@Override
	public void doeffect(Player p) {
		p.addeffect(new EffectRocket(1000,move));
		dead = true;
	}

	@Override
	public Entity returnnew(Vector pos, Vector move) {
		return new EntityRocket(pos,move);
	}

}
