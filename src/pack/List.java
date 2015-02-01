package pack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class List {
	
	ArrayList<String> list = new ArrayList<String>();
	ArrayList<String> code = new ArrayList<String>();
	ArrayList<Integer> pointer = new ArrayList<Integer>();
	
	Editor editor;
	Vector pos;
	int width,heigth;
	int id = -1; // marked
	
	public List(Editor e){
		this.editor = e;
		pos = new Vector(20,100);
		width = 150;heigth = 1000;

		id = 0;
		load();
	}

	public void load(){
		list.clear();
		code.clear();
		pointer.clear();
		id = 0;
		
		File dir = new File("scripts");
		File[] lists = dir.listFiles();
		
		for(int i = 0;i<lists.length;i++){
			if(lists[i].isFile()){
				list.add(lists[i].getName());
				
				String gesamt = "";
				
				try { // read given file
					BufferedReader in = new BufferedReader(new FileReader(lists[i]));
					String line = in.readLine();
					while(line != null){
						gesamt += line+"\n";
						line = in.readLine();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				code.add(gesamt);
				pointer.add(0);
			}
		}
		
		editor.code.code = code.get(id);     // load
		editor.code.pointer = pointer.get(id);
		
		if(list.size() == 0){ // no empty list
			list.add("Ki0.txt");
			code.add("");
			pointer.add(0);
		}
	}
	
	public void save(){
		code.set(id, editor.code.code);   // save
		pointer.set(id, editor.code.pointer);
		
		File dir = new File("scripts");
		for(int i = 0;i<list.size();i++){
			File fi = new File(dir,list.get(i));
			
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(fi));
				String[] codelist = code.get(i).split("\n");
				for(int j = 0;j<codelist.length;j++){
					out.write(codelist[j]);
					out.newLine();
				}
				out.close();
			} catch (IOException e) {
			}
		}
	}
	
	public void mousepressed(int x,int y,int button){
		if(x >= pos.getX() && x <= pos.getX()+width){
			int dy = y - (int)pos.getY();
			int nid = dy/20;
			if(nid >= 0 && nid < list.size()){
				if(id != -1){
				code.set(id, editor.code.code);   // save
				pointer.set(id, editor.code.pointer);}
				
				id = nid;
				
				editor.code.code = code.get(id);     // load
				editor.code.pointer = pointer.get(id);
			}
		}
	}
	
	public void tick(){
		
	}
	
	public void render(){
		if(id != -1){
			Vector[] v = new Vector[4];
			v[0] = new Vector(pos.getX(),pos.getY()+id*20);
			v[1] = new Vector(pos.getX(),pos.getY()+(id+1)*20);
			v[2] = new Vector(pos.getX()+width,pos.getY()+(id+1)*20);
			v[3] = new Vector(pos.getX()+width,pos.getY()+id*20);
			Render.get().drawQuad2D("buttonback", v);
		}
		
		for(int i = 0;i<list.size();i++){
			Render.get().drawString(list.get(i), new Vector(pos.getX()+5,pos.getY()+i*20),2);
		}
	}

}
