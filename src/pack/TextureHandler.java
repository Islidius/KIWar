package pack;

import java.io.File;
import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class TextureHandler {
	
	int width,height;
	String filename,ex;
	File name;
	Texture tex;
	boolean loaded;
	
	public TextureHandler(String filename,String ex){
		this.filename = filename;
		this.ex = ex;
		loaded = false;
	}
	
	public void load(){
		if(!loaded){
			try {
				tex = TextureLoader.getTexture(ex, ResourceLoader.getResourceAsStream(filename));
			} catch (IOException e) {
				e.printStackTrace();
			}
			width = tex.getImageWidth();height = tex.getImageHeight();
			loaded = true;
		}
	}
	
	public void unload(){
		tex = null;
		loaded = false;
	}
	
	public void bind(){
		tex.bind();
	}

}
