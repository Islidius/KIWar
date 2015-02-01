package pack;

public class Button {
	
	State parent;
	Vector pos;
	String text;
	
	int width,height,id,factor;
	
	public Button(int id,State parent,Vector pos,String text){
		this.parent = parent;this.pos = pos;this.text = text;this.id = id;
		factor = 3;
		
		height = 8*factor+2*4;width = text.length()*8*factor +2*10; // 8 = charsize 3 = factor + = rand
	}
	
	public Button(int id,State parent,Vector pos,String text,int factor){
		this.parent = parent;this.pos = pos;this.text = text;this.id = id;this.factor = factor;
		
		height = 8*factor+2*4;width = text.length()*8*factor +2*10; // 8 = charsize 3 = factor + = rand
	}
	
	public Button(int id,State parent,Vector pos,String text,int width,int height){
		this.parent = parent;this.pos = pos;this.text = text;this.width = width;this.height = height;this.id = id;
		factor = 3;
	}
	
	public Button(int id,State parent,Vector pos,String text,int width,int height,int factor){
		this.parent = parent;this.pos = pos;this.text = text;this.width = width;this.height = height;this.id = id;this.factor = factor;
	}
	
	public void tick(){
		
	}
	
	public void render(){
		Vector v[] = new Vector[4];
		v[0] = new Vector(pos);
		v[1] = new Vector(pos.getX(),pos.getY()+height);
		v[2] = new Vector(pos.getX()+width,pos.getY()+height);
		v[3] = new Vector(pos.getX()+width,pos.getY());
		
		Render.get().drawQuad2D("buttonback", v);
		Render.get().drawString(text, pos.add(new Vector(10,4)),factor);
	}
	
	public void mousepressed(int x,int y,int button){
		if((x-pos.getX()) <= width && (y-pos.getY()) <= height){  // right bot
			if(x >= pos.getX() && y >= pos.getY()){ // left top
				parent.buttonpressed(id);
			}
		}
	}

}
