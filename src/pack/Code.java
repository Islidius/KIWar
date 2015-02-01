package pack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lwjgl.input.Keyboard;

public class Code {
	String code = "";
	String name = "";
	Editor editor;
	int pointer = 0;
	
	int scrollbarposx = 0;
	int scrollbarposy = 0;
	
	Vector pos;
	int width,height;
	
	boolean error = false; // error exsits
	int errorline = 0; // error line
	
	HashMap<String,Vector> colorcode = new HashMap<String,Vector>(); // vector for color
	ArrayList<Vector> color = new ArrayList<Vector>(); // color in pos
	
	public Code(Editor e){
		this.editor = e;
		pos = new Vector(200,100);
		width = 1000;height = 800;
		
		colorcode.put("\\bfunction\\b", new Vector(1,1,0));
		colorcode.put("\\bend\\b", new Vector(1,1,0));
		colorcode.put("\\bif\\b", new Vector(1,1,0));
		colorcode.put("\\bthen\\b", new Vector(1,1,0));
		colorcode.put("\\bfor\\b", new Vector(1,1,0));
		colorcode.put("\\bnot\\b", new Vector(1,1,0));
		
		colorcode.put("\\bmyRobot\\b", new Vector(1,0,0));
		colorcode.put("\\bWorld\\b", new Vector(1,0,0));
		colorcode.put("\\bRobot\\b", new Vector(1,0,0));
		colorcode.put("\\ballRobot\\b", new Vector(1,0,0));
		colorcode.put("\\ballEntity\\b", new Vector(1,0,0));
		
		colorcode.put("\\bRobotManager\\b", new Vector(1,0,0));
		colorcode.put("\\bEntityManager\\b", new Vector(1,0,0));
		
		colorcode.put("\"\\w*\"", new Vector(0,1,0));
	}
	
	public void keypressed(int key){
		if(key == 14){ // backspace
			if(pointer > 0){ // leerstring
				String start = code.substring(0, pointer-1);
				String end = code.substring(pointer);
				code = start+end;
				pointer--;
				if(pointer > 0){ // leestring
					if(code.charAt(code.length()-1) == '\\'){ // controll symbols
						start = code.substring(0, pointer-1);
						end = code.substring(pointer);
						code = start+end;
						pointer--;
					}
				}
			}
		}
		else{
			String start = code.substring(0, pointer);
			String end = code.substring(pointer);
			
			String charkey = getchar(key); // keine dopplung
			
			code = start + charkey + end;
			if(charkey != "")pointer++; // leer string by l-r shift
		}
		
		//System.out.println(pointer);
	}
	
	public void mousepressed(int x,int y,int button){
		
	}
	
	public void tick(){
		color.clear();
		
		for(int i = 0;i<code.length();i++){ // set all to white
			color.add(new Vector(1,1,1));
		}
		
		for(String search:colorcode.keySet()){ // test all search words
			Pattern p = Pattern.compile(search);
			Matcher m = p.matcher(code);
			
			while(m.find()){ // find
				for(int i = m.start();i<m.end();i++){ // word lenght
					color.set(i, colorcode.get(search));  // set
				}
			}
		}
	}
	
	public void render(){
		String[] line = code.split("\n",-1);
		
		for(int i = 0;i<line.length;i++){
			line[i] = line[i].replaceAll("\t", " ");
		}
		
		int lineh  = 10;
		int charwidth = 8;
		
		int chartopoin = 0;
		int linecounter = 0;
		
		int counter = 0; // display char counter
		
		if(error){
			Vector[] v = new Vector[4];
			v[0] = new Vector(pos.getX(),pos.getY()+errorline*lineh);
			v[1] = new Vector(pos.getX(),pos.getY()+(errorline+1)*lineh);
			v[2] = new Vector(pos.getX()+width,pos.getY()+(errorline+1)*lineh);
			v[3] = new Vector(pos.getX()+width,pos.getY()+errorline*lineh);
			Render.get().setColor(0.8, 0, 0);
			Render.get().drawQuad2D(v);
			Render.get().resetColor();
		}
		
		for(int i = scrollbarposx;i<Math.min(height/lineh,line.length);i++){  // height / 10 = eine Line
			for(int j = 0;j<line[i].length();j++){
				Vector col = color.get(counter);
				Render.get().setColor(col.getX(), col.getY(), col.getZ());
				Render.get().drawString(line[i].charAt(j)+"", pos.add(new Vector(j*charwidth,i*lineh))); // render singel char
				Render.get().resetColor();
				counter++; // add
			}
			
			counter++;
		}
		
		int index = code.indexOf("\n");// line counter
		int indexlinecount = 0;
		while(index != -1){
			if(indexlinecount <= pointer && pointer < indexlinecount + line[linecounter].length()+1){ // pointer in line
				index = -1;break;
			}
			indexlinecount += line[linecounter].length()+1;
			linecounter++;
			index = code.indexOf("\n", index+1);
		}
		
		for(int i = 0;i<linecounter;i++){ // char counter
			chartopoin += line[i].length()+1;
		}
		
		Vector[] v = new Vector[2];
		v[0] = new Vector(pos.add(new Vector((pointer-chartopoin)*charwidth,linecounter*lineh-1)));
		v[1] = new Vector(pos.add(new Vector((pointer-chartopoin)*charwidth,linecounter*lineh+9)));
		
		Render.get().drawLine2D(v);  // cursor
		
		//Render.get().drawString("pointer:"+pointer, pos.add(new Vector(-100,0)));
		//Render.get().drawString("lenght:"+code.length(), pos.add(new Vector(-100,20)));
	}
	
	public String getchar(int key){
		switch(key){
		case Keyboard.KEY_A:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "A":"a";
		case Keyboard.KEY_B:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "B":"b";
		case Keyboard.KEY_C:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "C":"c";
		case Keyboard.KEY_D:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "D":"d";
		case Keyboard.KEY_E:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "E":"e";
		case Keyboard.KEY_F:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "F":"f";
		case Keyboard.KEY_G:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "G":"g";
		case Keyboard.KEY_H:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "H":"h";
		case Keyboard.KEY_I:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "I":"i";
		case Keyboard.KEY_J:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "J":"j";
		case Keyboard.KEY_K:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "K":"k";
		case Keyboard.KEY_L:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "L":"l";
		case Keyboard.KEY_M:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "M":"m";
		case Keyboard.KEY_N:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "N":"n";
		case Keyboard.KEY_O:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "O":"o";
		case Keyboard.KEY_P:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "P":"p";
		case Keyboard.KEY_Q:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "Q":"q";
		case Keyboard.KEY_R:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "R":"r";
		case Keyboard.KEY_S:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "S":"s";
		case Keyboard.KEY_T:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "T":"t";
		case Keyboard.KEY_U:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "U":"u";
		case Keyboard.KEY_V:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "V":"v";
		case Keyboard.KEY_W:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "W":"w";
		case Keyboard.KEY_X:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "X":"x";
		case Keyboard.KEY_Y:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "Y":"y";
		case Keyboard.KEY_Z:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "Z":"z";
		
		case Keyboard.KEY_0:if(Keyboard.isKeyDown(29)){return "}";}else{return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))?"=":"0";}//alt gr
		case Keyboard.KEY_1:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "!":"1";
		case Keyboard.KEY_2:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "\"":"2";
		case Keyboard.KEY_3:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "§":"3";
		case Keyboard.KEY_4:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "$":"4";
		case Keyboard.KEY_5:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "%":"5";
		case Keyboard.KEY_6:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "&":"6";
		case Keyboard.KEY_7:if(Keyboard.isKeyDown(29)){return "{";}else{return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))?"/":"7";}
		case Keyboard.KEY_8:if(Keyboard.isKeyDown(29)){return "[";}else{return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))?"(":"8";}
		case Keyboard.KEY_9:if(Keyboard.isKeyDown(29)){return "]";}else{return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))?")":"9";}
		
		case 52:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? ":":".";
		case 12:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "_":"-";
		case 13:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? "*":"+";
		case 0:if(Keyboard.isKeyDown(29)){return "|";}else{return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))?"<":">";}
		
		case Keyboard.KEY_TAB:return "\t";
		
		case 28:return "\n"; // enter
		case 57:return " "; // space
		
		case Keyboard.KEY_LSHIFT:return "";
		case Keyboard.KEY_RSHIFT:return "";
		case 29:return ""; // alt gr
		case 184:return "";
		case Keyboard.KEY_COMMA:return (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)||Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) ? ";":",";
		
		case Keyboard.KEY_LEFT:pointer = pointer > 0 ? pointer-1 : pointer;return "";
		case Keyboard.KEY_RIGHT:pointer = (code.length()-pointer>0) ? pointer+1:pointer;return ""; 
		case Keyboard.KEY_UP:setpointerline(-1);return "";
		case Keyboard.KEY_DOWN:setpointerline(1);return "";
		}
		
		
		return ""+key;
	}
	
	public void setpointerline(int deltaline){ // deltaline -1:to top 1:down
		String[] line = code.split("\n",-1);
		
		int index = code.indexOf("\n");// line counter
		int indexlinecount = 0;
		int linecounter = 0;boolean ch = false;
		int linecountermax = 0;
		
		while(index != -1){
			if(indexlinecount <= pointer && pointer < indexlinecount + line[linecountermax].length()+1){ // pointer in line +1 -> \n to symbols
				linecounter = linecountermax;ch = true;
			}
			indexlinecount += line[linecountermax].length()+1;
			linecountermax++;
			index = code.indexOf("\n", index+1);
		}
		if(!ch){ // letzte Line
			linecounter = linecountermax;
		}
		
		
		int newline = linecounter + deltaline; // new line mit delta
		if(newline >= 0 && newline <= linecountermax){ // in boundaries
			indexlinecount = 0;
			
			for(int i = 0;i<linecounter;i++){ // char counter
				indexlinecount += line[i].length()+1;
			}
			
			int fromindex = indexlinecount;
			indexlinecount = 0;
			
			for(int i = 0;i<newline;i++){ // char counter
				indexlinecount += line[i].length()+1;
			}
			
			pointer = indexlinecount+Math.min(line[newline].length(),pointer-fromindex);  // insert
		}
	}
	
	public void seterror(int line){
		
	}
}
