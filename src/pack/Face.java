package pack;

public class Face {
	
	Vector p1,p2,p3,normal;
	Vector tex1,tex2,tex3;
	boolean textured = false; // is textured
	String texture = "";// texture name
	
	public Face(Vector p1,Vector p2,Vector p3){
		this.p1 = p1;this.p2 = p2;this.p3 = p3;
	}
	
	public Face(Vector p1,Vector p2,Vector p3,Vector normal){
		this.p1 = p1;this.p2 = p2;this.p3 = p3;this.normal = normal;
	}
	
	public Face(Vector p1,Vector tex1,Vector p2,Vector tex2,Vector p3,Vector tex3,String texture){ // with texture
		this.p1 = p1;this.p2 = p2;this.p3 = p3;this.tex1 = tex1;this.tex2 = tex2;this.tex3 = tex3;this.texture = texture;
		textured = true;
	}
	
	public Face(Vector p1,Vector tex1,Vector p2,Vector tex2,Vector p3,Vector tex3,Vector normal,String texture){
		this.p1 = p1;this.p2 = p2;this.p3 = p3;this.tex1 = tex1;this.tex2 = tex2;this.tex3 = tex3;this.normal = normal;this.texture = texture;
		textured = true;
	}
	
	public void render(Vector pos,Matrix mat){
		if(textured){
			Vector[] v = new Vector[3];
			Vector[] texpos = new Vector[3];
			v[0] = p1.multiply(mat).add(pos);
			v[1] = p2.multiply(mat).add(pos);
			v[2] = p3.multiply(mat).add(pos);
			
			texpos[0] = tex1;
			texpos[1] = tex2;
			texpos[2] = tex3;
			
			Render.get().drawTri3D(texture, v, texpos);
		}
		else{
			Vector[] v = new Vector[3];
			v[0] = p1.multiply(mat).add(pos);
			v[1] = p2.multiply(mat).add(pos);
			v[2] = p3.multiply(mat).add(pos);
			
			Render.get().drawTri3D(v);
		}
	}

}
