package pack;

public class World {
	
	Model model;
	double radianmax = 500;
	double radianstartlava = 300;
	double radianmin = 30;
	double radianaklava;
	double rate = 0.02;
	
	public World(){
		radianaklava = radianstartlava;
	}
	
	public void tick(){
		if(radianaklava > radianmin){radianaklava -= rate;}
	}
	
	public void reset(){
		radianaklava = radianstartlava;
	}
	
	public void render(){
		/*glLineWidth(3);
		
		Render.get().setColor(255, 0, 0);
		Vector[] v = new Vector[2];
		v[0] = new Vector(-1000,0,0);
		v[1] = new Vector(1000,0,0);
		Render.get().drawLine3D(v);
		
		Render.get().setColor(0, 255, 0);
		v[0] = new Vector(0,-1000,0);
		v[1] = new Vector(0,1000,0);
		Render.get().drawLine3D(v);
		
		Render.get().setColor(0, 0, 255);
		v[0] = new Vector(0,0,-1000);
		v[1] = new Vector(0,0,1000);
		Render.get().drawLine3D(v);*/
		
		Render.get().resetColor();
		
		Vector[] v = new Vector[4];
		
		v[0] = new Vector(-radianmax,-10,-radianmax);
		v[1] = new Vector(radianmax,-10,-radianmax);
		v[2] = new Vector(radianmax,-10,radianmax);
		v[3] = new Vector(-radianmax,-10,radianmax);
		Render.get().drawQuad3D("lava",v);
		
		v[0] = new Vector(-radianaklava,0,-radianaklava);
		v[1] = new Vector(radianaklava,0,-radianaklava);
		v[2] = new Vector(radianaklava,0,radianaklava);
		v[3] = new Vector(-radianaklava,0,radianaklava);
		Render.get().drawQuad3D("terrain",v);
		
		
	}

}
