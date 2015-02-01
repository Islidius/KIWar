package pack;

import java.awt.Dimension;
import java.awt.Toolkit;

public class Stats {
	
	private static Stats stats;
	public int width,height;
	public float znear,zfar,fov;
	public Skill skill;
	public double maxinlava;
	public Vector g; // gravite
	
	
	public static Stats get(){
		if(stats == null)stats = new Stats();
		return stats;
	}
	
	public Stats(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int)screenSize.getWidth();
		height = (int)screenSize.getHeight();
		
		znear = 0.1F;zfar = 3000;fov = 68;
		
		maxinlava = 5000;
		
		g = new Vector(0,-0.05,0);
		
		skill = new Skill();
		
		skill.add("rocket", 5000, 0.1, new EntityRocket(null,null));
		skill.add("teleport", 10000, 1, new EffectTele(0,null));
	}

}
