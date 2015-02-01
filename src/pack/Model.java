package pack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Model {
	
	double size;
	ArrayList<Face> face = new ArrayList<Face>(); // all faces(tris) 
	
	String path; // Model path
	String texture; // texture name
	
	public Model(String path,String texture){
		this.path = path;this.texture = texture;size = 1;
		load();
	}
	
	public Model(String path,String texture,double size){
		this.path = path;this.texture = texture;this.size = size;
		load();
	}
	
	public void load(){
		ArrayList<Vector> vertex = new ArrayList<Vector>();
		ArrayList<Vector> normalv = new ArrayList<Vector>();
		ArrayList<Vector> texturev = new ArrayList<Vector>();
		
		double maxlenght = 0;
		
		try {

			BufferedReader in = new BufferedReader(new FileReader(path));

			String line = in.readLine();
			while (line != null) {
				//System.out.println(line);
				
				String[] space = line.split(" ");
				
				if(space[0].equals("v")){
					vertex.add(new Vector(Double.parseDouble(space[1]),Double.parseDouble(space[2]),Double.parseDouble(space[3])));
					maxlenght = Math.max(vertex.get(vertex.size()-1).length(),maxlenght);
				}
				
				else if(space[0].equals("vt")){
					texturev.add(new Vector(Double.parseDouble(space[1]),Double.parseDouble(space[2])));
				}
				
				else if(space[0].equals("f")){
					Vector v1 = vertex.get(Integer.parseInt(space[1].split("/")[0])-1); // Vectex
					Vector v2 = vertex.get(Integer.parseInt(space[2].split("/")[0])-1);
					Vector v3 = vertex.get(Integer.parseInt(space[3].split("/")[0])-1);
					
					Vector t1 = texturev.get(Integer.parseInt(space[1].split("/")[1])-1); // tex pos
					Vector t2 = texturev.get(Integer.parseInt(space[2].split("/")[1])-1);
					Vector t3 = texturev.get(Integer.parseInt(space[3].split("/")[1])-1);
					
					Vector n = normalv.get(Integer.parseInt(space[1].split("/")[2])-1); // normal
					
					face.add(new Face(v1,t1,v2,t2,v3,t3,n,texture));
				}
				
				else if(space[0].equals("vn")){
					normalv.add(new Vector(Double.parseDouble(space[1]),Double.parseDouble(space[2]),Double.parseDouble(space[3])));
				}
				
				
				line = in.readLine();
			}
			in.close();
		} catch (IOException e) {
		}
		
		//System.out.println(path+":"+maxlenght);
	}
	
	public void tick(){
		
	}
	
	public void render(Vector pos,Matrix mat){ // mat = rot matrix / pos = render position
		for(Face f:face){
			f.render(pos, mat);
		}
		//System.out.println("path:"+path);
		//System.out.println("faces:"+face.size());
	}

}
