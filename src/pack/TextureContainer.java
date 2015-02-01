package pack;

import java.util.HashMap;

public class TextureContainer {
	
	HashMap<String,TextureHandler> texs = new HashMap<String,TextureHandler>();
	
	public TextureContainer(){
		
	}
	
	public void addtexture(String name,String filename,String ex){
		texs.put(name, new TextureHandler(filename,ex));
	}
	
	public TextureHandler gettexture(String name){
		return texs.get(name);
	}
	
	public void loadtexture(String name){
		texs.get(name).load();
	}
	
	public void unloadtexture(String name){
		texs.get(name).unload();
	}
	
	public void unloadall(){
		for(String s:texs.keySet()){
			texs.get(s).unload();
		}
	}
	
	public void deletetexture(String name){
		texs.remove(name);
	}
	
	public boolean isloaded(String name){
		return texs.get(name).loaded;
	}

}
