package pack;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluOrtho2D;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import org.lwjgl.opengl.Display;


public class Render {
	
	public static Render render;
	
	TextureContainer texcon;
	
	public static Render get(){
		if(render == null)render = new Render();
		return render;
	}
	
	public Render(){
		texcon = new TextureContainer();
		
		texcon.addtexture("font", "tex/font.png", "PNG");
		texcon.loadtexture("font");
		
		texcon.addtexture("buttonback", "tex/buttonback.png", "PNG");
		texcon.loadtexture("buttonback");
		
		texcon.addtexture("cursor", "tex/cursor.png", "PNG");
		texcon.loadtexture("cursor");
		
		texcon.addtexture("lava", "tex/lava.png", "PNG");
		texcon.loadtexture("lava");
		
		texcon.addtexture("terrain", "tex/terrain2.png", "PNG");
		texcon.loadtexture("terrain");
		
		texcon.addtexture("tex_robot", "tex/robottexture2.png", "PNG");
		texcon.loadtexture("tex_robot");
		
		texcon.addtexture("tex_rocket", "tex/rockettexture.png", "PNG");
		texcon.loadtexture("tex_rocket");
		
		texcon.addtexture("skull", "tex/skull.png", "PNG");
		texcon.loadtexture("skull");
	}
	
	public void clear(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}
	
	public TextureContainer gettexcon(){
		return texcon;
	}
	
	//Setting OpenGL -----------------------------------------------------------------------------------------
	public void initGl(){
		 glEnable(GL_TEXTURE_2D);
	     glEnable (GL_BLEND);
	     glDisable(GL_DEPTH_TEST);
	     glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	     glHint(GL_POLYGON_SMOOTH_HINT,GL_NICEST);
	}
	
	public void init2D(){
		glDisable(GL_DEPTH_TEST);
		glViewport(0, 0, 1920, 1080);
		glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        gluOrtho2D(0,Stats.get().width,Stats.get().height,0);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
	}
	
	public void init3D(){
		glEnable(GL_DEPTH_TEST);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(Stats.get().fov,(float) Display.getWidth() / (float) Display.getHeight(),Stats.get().znear, Stats.get().zfar);
		//glOrtho(-(Stats.get().width/2.0), (Stats.get().width/2.0), -(Stats.get().height/2.0), (Stats.get().height/2.0), Stats.get().width, -Stats.get().width);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
	}
	
	//Print OpenGL 2D -----------------------------------------------------------------------------------------
	public void drawQuad2D(Vector[] v){
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2d(v[0].getX(),v[0].getY());
		glVertex2d(v[1].getX(),v[1].getY());
		glVertex2d(v[2].getX(),v[2].getY());
		glVertex2d(v[3].getX(),v[3].getY());
		glEnd();
	}
	
	public void drawQuad2D(String texture,Vector[] v){
		glBindTexture(GL_TEXTURE_2D,texcon.gettexture(texture).tex.getTextureID());
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		double height = 0.99999D;
		double width = 0.999999D;
		
		height = (texcon.gettexture(texture).tex.getImageHeight()+0.0D)/texcon.gettexture(texture).tex.getTextureHeight();
		width = (texcon.gettexture(texture).tex.getImageWidth()+0.0D)/texcon.gettexture(texture).tex.getTextureWidth();
		
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex2d(v[0].getX(),v[0].getY());
		glTexCoord2d(0,height);
		glVertex2d(v[1].getX(),v[1].getY());
		glTexCoord2d(width,height);
		glVertex2d(v[2].getX(),v[2].getY());
		glTexCoord2d(width,0);
		glVertex2d(v[3].getX(),v[3].getY());
		glEnd();
		
		glBindTexture(GL_TEXTURE_2D,0);
	}
	
	public void drawQuad2D(String texture,Vector[] v,Vector[] texpos){
		glBindTexture(GL_TEXTURE_2D,texcon.gettexture(texture).tex.getTextureID());
	
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glBegin(GL_QUADS);
		glTexCoord2f((float)(texpos[0].getX()/(texcon.gettexture(texture).tex.getImageWidth()+0.0D)),(float)(texpos[0].getY()/(texcon.gettexture(texture).tex.getImageHeight()+0.0D)));
		glVertex2d(v[0].getX(),v[0].getY());
		glTexCoord2f((float)(texpos[1].getX()/(texcon.gettexture(texture).tex.getImageWidth()+0.0D)),(float)(texpos[1].getY()/(texcon.gettexture(texture).tex.getImageHeight()+0.0D)));
		glVertex2d(v[1].getX(),v[1].getY());
		glTexCoord2f((float)(texpos[2].getX()/(texcon.gettexture(texture).tex.getImageWidth()+0.0D)),(float)(texpos[2].getY()/(texcon.gettexture(texture).tex.getImageHeight()+0.0D)));
		glVertex2d(v[2].getX(),v[2].getY());
		glTexCoord2f((float)(texpos[3].getX()/(texcon.gettexture(texture).tex.getImageWidth()+0.0D)),(float)(texpos[3].getY()/(texcon.gettexture(texture).tex.getImageHeight()+0.0D)));
		glVertex2d(v[3].getX(),v[3].getY());
		glEnd();
		
		glBindTexture(GL_TEXTURE_2D,0);
	}
	
	public void drawLine2D(Vector[] v){
		glBegin(GL_LINES);
		glVertex2d(v[0].getX(),v[0].getY());
		glVertex2d(v[1].getX(),v[1].getY());
		glEnd();
	}
	
	//Print OpenGL 3D ----------------------------------------------------------------------------------------
	public void drawQuad3D(Vector[] v){
		glBegin(GL_QUADS);
		glVertex3d(v[0].getX(),v[0].getY(),v[0].getZ());
		glVertex3d(v[1].getX(),v[1].getY(),v[1].getZ());
		glVertex3d(v[2].getX(),v[2].getY(),v[2].getZ());
		glVertex3d(v[3].getX(),v[3].getY(),v[3].getZ());
		glEnd();
	}
	
	public void drawQuad3D(String texture,Vector[] v){
		glBindTexture(GL_TEXTURE_2D,texcon.gettexture(texture).tex.getTextureID());
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		double height = (texcon.gettexture(texture).tex.getImageHeight()+0.0D)/texcon.gettexture(texture).tex.getTextureHeight();
		double width = (texcon.gettexture(texture).tex.getImageWidth()+0.0D)/texcon.gettexture(texture).tex.getTextureWidth();
		
		glBegin(GL_QUADS);
		glTexCoord2f(0,0);
		glVertex3d(v[0].getX(),v[0].getY(),v[0].getZ());
		glTexCoord2d(0,height);
		glVertex3d(v[1].getX(),v[1].getY(),v[1].getZ());
		glTexCoord2d(width,height);
		glVertex3d(v[2].getX(),v[2].getY(),v[2].getZ());
		glTexCoord2d(width,0);
		glVertex3d(v[3].getX(),v[3].getY(),v[3].getZ());
		glEnd();
		
		glBindTexture(GL_TEXTURE_2D,0);
	}
	
	public void drawQuad3D(String texture,Vector[] v,Vector[] texpos){
		glBindTexture(GL_TEXTURE_2D,texcon.gettexture(texture).tex.getTextureID());
	
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glBegin(GL_QUADS);
		glTexCoord2f((float)(texpos[0].getX()/(texcon.gettexture(texture).tex.getImageWidth()+0.0D)),(float)(texpos[0].getY()/(texcon.gettexture(texture).tex.getImageHeight()+0.0D)));
		glVertex3d(v[0].getX(),v[0].getY(),v[0].getZ());
		glTexCoord2f((float)(texpos[1].getX()/(texcon.gettexture(texture).tex.getImageWidth()+0.0D)),(float)(texpos[1].getY()/(texcon.gettexture(texture).tex.getImageHeight()+0.0D)));
		glVertex3d(v[1].getX(),v[1].getY(),v[1].getZ());
		glTexCoord2f((float)(texpos[2].getX()/(texcon.gettexture(texture).tex.getImageWidth()+0.0D)),(float)(texpos[2].getY()/(texcon.gettexture(texture).tex.getImageHeight()+0.0D)));
		glVertex3d(v[2].getX(),v[2].getY(),v[2].getZ());
		glTexCoord2f((float)(texpos[3].getX()/(texcon.gettexture(texture).tex.getImageWidth()+0.0D)),(float)(texpos[3].getY()/(texcon.gettexture(texture).tex.getImageHeight()+0.0D)));
		glVertex3d(v[3].getX(),v[3].getY(),v[3].getZ());
		glEnd();
		
		glBindTexture(GL_TEXTURE_2D,0);
	}
	
	
	public void drawTri3D(String texture,Vector[] v,Vector[] texpos){
		glBindTexture(GL_TEXTURE_2D,texcon.gettexture(texture).tex.getTextureID());
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		glBegin(GL_TRIANGLES);
		//glTexCoord2f((float)(texpos[0].getX()/(texcon.gettexture(texture).tex.getImageWidth()+0.0D)),(float)(texpos[0].getY()/(texcon.gettexture(texture).tex.getImageHeight()+0.0D)));
		glTexCoord2d(texpos[0].getX(),1-texpos[0].getY()); // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!dont know why (1-get)(flipped y axis)
		glVertex3d(v[0].getX(),v[0].getY(),v[0].getZ());
		//glTexCoord2f((float)(texpos[1].getX()/(texcon.gettexture(texture).tex.getImageWidth()+0.0D)),(float)(texpos[1].getY()/(texcon.gettexture(texture).tex.getImageHeight()+0.0D)));
		glTexCoord2d(texpos[1].getX(),1-texpos[1].getY());
		glVertex3d(v[1].getX(),v[1].getY(),v[1].getZ());
		//glTexCoord2f((float)(texpos[2].getX()/(texcon.gettexture(texture).tex.getImageWidth()+0.0D)),(float)(texpos[2].getY()/(texcon.gettexture(texture).tex.getImageHeight()+0.0D)));
		glTexCoord2d(texpos[2].getX(),1-texpos[2].getY());
		glVertex3d(v[2].getX(),v[2].getY(),v[2].getZ());
		glEnd();
		
		glBindTexture(GL_TEXTURE_2D,0);
	}
	
	public void drawTri3D(Vector[] v){
		glBegin(GL_TRIANGLES);
		glVertex3d(v[0].getX(),v[0].getY(),v[0].getZ());
		glVertex3d(v[1].getX(),v[1].getY(),v[1].getZ());
		glVertex3d(v[2].getX(),v[2].getY(),v[2].getZ());
		glEnd();
	}
	
	public void drawLine3D(Vector[] v){
		glBegin(GL_LINES);
		glVertex3d(v[0].getX(),v[0].getY(),v[0].getZ());
		glVertex3d(v[1].getX(),v[1].getY(),v[1].getZ());
		glEnd();
	}
	
	//Print OpenGl Special -----------------------------------------------------------------------------------
	
	public void drawString(String s,Vector v,double scale){
		int width = 8;int height = 8;int rowlength = 16;
		
		Vector[] charpos = new Vector[4];
		Vector[] texpos = new Vector[4];
		
		for(int i = 0;i<4;i++){
			charpos[i] = new Vector(0,0,0);
		}
		for(int i = 0;i<4;i++){
			texpos[i] = new Vector(0,0,0);
		}
		
		
		for(int j = 0;j<s.length();j++){	
			int code = (int)s.charAt(j);
			int x = code%rowlength;
			int y = code/rowlength;
			
			texpos[0] = new Vector(x*width,y*height,1);
			texpos[1] = new Vector(x*width,(y+1)*height,1);
			texpos[2] = new Vector((x+1)*width,(y+1)*height,1);
			texpos[3] = new Vector((x+1)*width,y*height,1);
			
			charpos[0] = new Vector(v.getX()+width*j*scale,v.getY(),1);
			charpos[1] = new Vector(v.getX()+width*j*scale,v.getY()+height*scale,1); // + -> coordsystem
			charpos[2] = new Vector(v.getX()+width*(j+1)*scale,v.getY()+height*scale,1); // + -> origin top left
			charpos[3] = new Vector(v.getX()+width*(j+1)*scale,v.getY(),1);
			
			drawQuad2D("font",charpos, texpos);
		}
	}
	
	public void drawString3D(String s,Vector norm,Vector pos,double scale){
		int width = 8;int height = 8;int rowlength = 16;
		
		Vector[] charpos = new Vector[4];
		Vector[] texpos = new Vector[4];
		
		for(int i = 0;i<4;i++){
			charpos[i] = new Vector(0,0,0);
		}
		for(int i = 0;i<4;i++){
			texpos[i] = new Vector(0,0,0);
		}
		
		Matrix roty = new Matrix(Matrix.ROT3D_Y,Math.toRadians(180-norm.getY()+180));
		Matrix rotx = new Matrix(Matrix.ROT3D_X,Math.toRadians(180-norm.getX()+180));
		Matrix trans = new Matrix(Matrix.TRANS,pos.getX(),pos.getY(),pos.getZ());
		
		double maxwidth = s.length()*width*scale;
		
		
		for(int j = 0;j<s.length();j++){	
			int code = (int)s.charAt(j);
			int x = code%rowlength;
			int y = code/rowlength;
			
			texpos[0] = new Vector(x*width,y*height,1);
			texpos[1] = new Vector(x*width,(y+1)*height,1);
			texpos[2] = new Vector((x+1)*width,(y+1)*height,1);
			texpos[3] = new Vector((x+1)*width,y*height,1);
			
			charpos[0] = new Vector(width*j*scale-maxwidth/2,0,0);
			charpos[1] = new Vector(width*j*scale-maxwidth/2,-height*scale,0); // + -> coordsystem
			charpos[2] = new Vector(width*(j+1)*scale-maxwidth/2,-height*scale,0); // + -> origin top left
			charpos[3] = new Vector(width*(j+1)*scale-maxwidth/2,0,0);
			
			for(int i = 0;i<4;i++){
				charpos[i] = charpos[i].multiply(rotx);
				charpos[i] = charpos[i].multiply(roty);
				charpos[i] = charpos[i].multiply(trans);
			}
			
			drawQuad3D("font",charpos, texpos);
		}
	}
	
	public void drawString(String s,Vector v){
		drawString(s,v,1);
	}
	
	public void setColor(double r,double g,double b,double a){
		glColor4d(r,g,b,a);
	}
	
	public void setColor(double r,double g,double b){
		glColor3d(r,g,b);
	}
	
	public void resetColor(){
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}

}
