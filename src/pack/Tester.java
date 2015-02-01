package pack;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import org.luaj.vm2.LuaError;

public class Tester {
	
	String addcode = "";
	String message;
	
	public Tester(){
		addcode = addcode("");
	}
	
	public boolean test(String code){
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine e = mgr.getEngineByName("luaj");
		Bindings sb = new SimpleBindings();
		
		try {
			e.eval(addcode+"\n"+code,sb);
			return true;
		} catch (ScriptException|LuaError e1) {
			//e1.printStackTrace();
			System.out.println(e1.getMessage());
			message = e1.getMessage();
			
			System.out.println(getlastpos());
			
			return false;
		}
	}
	
	public int getlastpos(){
		Pattern p = Pattern.compile("\\b\\d+\\b");
		Matcher m = p.matcher(message);
		
		while(m.find()){ // find
			return Integer.valueOf(message.substring(m.start(), m.end()))-195; // 195 = lenght addcode
		}
		return 0;
	}
	
	public String getlastmessage(){
		return "";
	}
	
	public String addcode(String script){
		String addcode = "";
		
		//Vector
		
		addcode += "Vector = {x = 0,y = 0}\n"; // new Vector
		addcode += "function Vector:new (posx,posy)\n";
		addcode += "local o = setmetatable({},self)\n";
		addcode += "self.__index = self\n";
		addcode += "o.x = posx\n";
		addcode += "o.y = posy\n";
		addcode += "return o\n";
		addcode += "end\n";
		
		addcode += "function Vector:add (v)\n"; // add Vector
		addcode += "self.x = self.x + v.x\n";
		addcode += "self.y = self.y + v.y\n";
		addcode += "end\n";
		
		addcode += "function Vector:multiply (f)\n"; // multiply Vector
		addcode += "self.x = self.x * f\n";
		addcode += "self.y = self.y * f\n";
		addcode += "end\n";
		
		addcode += "function Vector:getX()\n"; // getter methods for x
		addcode += "return self.x\n";
		addcode += "end\n";
		
		addcode += "function Vector:getY()\n"; // getter methods for y
		addcode += "return self.y\n";
		addcode += "end\n";
		
		addcode += "function Vector:lenght()\n"; // lenght of the Vector
		addcode += "return math.sqrt(self.x*self.x+self.y*self.y)\n";
		addcode += "end\n";
		
		addcode += "function Vector:copy()\n"; // return new object with same value
		addcode += "return Vector:new(self.x,self.y)\n";
		addcode += "end\n";
		
		// Robot(27)
		
		addcode += "Robot = {pos,move,dead}\n"; // new Vector
		addcode += "function Robot:new (pos,move)\n";
		addcode += "local o = setmetatable({},self)\n";
		addcode += "self.__index = self\n";
		addcode += "o.pos = pos\n";
		addcode += "o.move = move\n";
		addcode += "o.dead = false\n";
		addcode += "return o\n";
		addcode += "end\n";
		
		addcode += "function Robot:setmove (move)\n"; // setter method
		addcode += "self.move = move\n";
		addcode += "end\n";
		
		addcode += "function Robot:getPos()\n"; // getter for pos
		addcode += "return self.pos\n";
		addcode += "end\n";
		
		addcode += "function Robot:getMove()\n"; // getter for move
		addcode += "return self.move\n";
		addcode += "end\n";
		
		addcode += "function Robot:getDead()\n";
		addcode += "return self.dead\n";
		addcode += "end\n";
		
		addcode += "function Robot:inLava()\n";
		addcode += "return World:getRadius() < self.pos:lenght()\n";
		addcode += "end\n";
		
		// Entity
		
		addcode += "Entity = {type,move,pos}\n";
		addcode += "function Robot:new (type,pos,move)\n";
		addcode += "local o = setmetatable({},self)\n";
		addcode += "self.__index = self\n";
		addcode += "o.pos = pos\n";
		addcode += "o.move = move\n";
		addcode += "o.type = type\n";
		addcode += "return o\n";
		addcode += "end\n";
		
		addcode += "function Entity:getPos()\n";
		addcode += "return self.pos\n";
		addcode += "end\n";
		
		addcode += "function Entity:getMove()\n";
		addcode += "return self.move\n";
		addcode += "end\n";
		
		addcode += "function Entity:getType()\n";
		addcode += "return self.type\n";
		addcode += "end\n";
		
		// World (no class)
		
		addcode += "World = {radius = 300}\n";
		addcode += "function World:setradius(r)\n";
		addcode += "self.radius = r\n";
		addcode += "end\n";
		
		addcode += "function World:getRadius()\n";
		addcode += "return self.radius\n";
		addcode += "end\n";
		
		addcode += "function World:inLava(vec)\n";
		addcode += "return World:getRadius() < vec:lenght()\n";
		addcode += "end\n";
		
		//for outside communication
		
		addcode += "function getmoverobotX()\n"; // for communication robot x
		addcode += "return myRobot:getMove():getX()\n";
		addcode += "end\n";
		
		addcode += "function getmoverobotY()\n"; // for communication robot y
		addcode += "return myRobot:getMove():getY()\n";
		addcode += "end\n";
		
		addcode += "function updatemyrobotpos(x,y)\n"; 
		addcode += "pos = Vector:new(x,y)\n";
		addcode += "myRobot.pos = pos\n";
		addcode += "end\n";
		
		addcode += "function updatemyrobotmove(movx,movy)\n";
		addcode += "move = Vector:new(movx,movy)\n";
		addcode += "myRobot.move = move\n";
		addcode += "end\n";
		
		addcode += "function updatemyrobotdead(dead)\n";
		addcode += "myRobot.dead = dead\n";
		addcode += "end\n";
		
		addcode += "function updateworldradius(radius)\n";
		addcode += "World.radius = radius\n";
		addcode += "end\n";
		
		// Skills class
		
		addcode += "skilllist = {}"; // liste mit Vectoren for skills
		addcode += "function resetskills()\n"; // reset
		addcode += "skilllist = {}\n";
		addcode += "end\n";
		
		addcode += "function getskillx(skill)\n";// return value of x coord for skill
		addcode += "if skilllist[skill] ~= nil then\n";
		addcode += "return skilllist[skill]:getX()\n";
		addcode += "end\n";
		addcode += "return nill\n";
		addcode += "end\n";
		
		addcode += "function getskilly(skill)\n";   // return value of y coord for skill
		addcode += "if skilllist[skill] ~= nil then\n";
		addcode += "return skilllist[skill]:getY()\n";
		addcode += "end\n";
		addcode += "return nill\n";
		addcode += "end\n";
		
		// all Robots
		
		addcode += "allRobot = {}\n"; // liste for all robots except myself
		addcode += "function addrobot()\n";
		addcode += "table.insert(allRobot,Robot:new())\n"; // add to list starts with 1(not 0)
		addcode += "end\n";
		
		addcode += "function resetrobots()\n";
		addcode += "allRobot = {}\n";
		addcode += "end\n";
		
		addcode += "function updaterobopos(id,posx,posy)\n";
		addcode += "allRobot[id].pos = Vector:new(posx,posy)\n";
		addcode += "end\n";
		
		addcode += "function updaterobomove(id,movex,movey)\n";
		addcode += "allRobot[id].move = Vector:new(movex,movey)\n";
		addcode += "end\n";
		
		addcode += "function updaterobodead(id,dead)\n";
		addcode += "allRobot[id].dead = dead\n";
		addcode += "end\n";
		
		// Robot Manager Class
		
		addcode += "RobotManager = {}\n";
		addcode += "function RobotManager:getRobot(id)\n";
		addcode += "return allRobot[id]\n";
		addcode += "end\n";
		
		addcode += "function RobotManager:getSize()\n";
		addcode += "return #allRobot\n";
		addcode += "end\n";
		
		addcode += "function RobotManager:getRandom()\n";
		addcode += "return allRobot[math.random(RobotManager:getSize())]\n";
		addcode += "end\n";
		
		// all Entity

		addcode += "allEntity = {}\n"; // liste for all robots except myself
		addcode += "function addentity()\n";
		addcode += "table.insert(allEntity,Entity:new(\"\",Vector:new(0,0),Vector:new(0,0)))\n"; // add to list starts with 1(not 0)
		addcode += "end\n";
		
		addcode += "function resetentitys()\n";
		addcode += "allEntity = {}\n";
		addcode += "end\n";
		
		addcode += "function updateentpos(id,posx,posy)\n";
		addcode += "allEntity[id].pos = Vector:new(posx,posy)\n";
		addcode += "end\n";
		
		addcode += "function updateentmove(id,movex,movey)\n";
		addcode += "allEntity[id].move = Vector:new(movex,movey)\n";
		addcode += "end\n";
		
		addcode += "function updateenttype(id,type)\n";
		addcode += "allEntity[id].type = type\n";
		addcode += "end\n";
		
		// Entity Manager Class
		
		addcode += "EntityManager = {}\n";
		addcode += "function EntityManager:getEntity(id)\n";
		addcode += "return allEntity[id]\n";
		addcode += "end\n";
		
		addcode += "function EntityManager:getSize()\n";
		addcode += "return #allEntity\n";
		addcode += "end\n";
		
		// init myRobot
		
		addcode += "myRobot = Robot:new(Vector:new(0,0),Vector:new(0,0))\n";
		
		// only my robot function
		
		addcode += "function setskill(skill,cooldown)\n"; // set cooldown
		addcode += "myRobot[skill] = cooldown\n";
		addcode += "end\n";
		
		addcode += "function myRobot:oncooldown(skill)\n"; // skill on cooldown
		addcode += "return myRobot[skill] <= 0\n";
		addcode += "end\n";
		
		addcode += "function myRobot:useskill(skill,vec)\n"; // use skill
		addcode += "if myRobot:oncooldown(skill) then\n"; // skill is ready
		addcode += "skilllist[skill] = vec\n";
		addcode += "end\n";
		addcode += "end\n";
		
		addcode += "function myRobot:useskillat(skill,robot)\n";
		addcode += "local target = robot:getPos():copy()\n";
		addcode += "local move = myRobot:getPos():copy()\n";
		addcode += "taget:multiply(-1)\n";
		addcode += "taget:add(move)\n";
		addcode += "myRobot:useskill(skill,target)\n";
		addcode += "end\n";
		
		addcode += "function myRobot:moveto(vec)\n";
		addcode += "local pos = myRobot:getPos():copy()\n";
		addcode += "pos:multiply(-1)\n";
		addcode += "vec:add(pos)\n";
		addcode += "if vec:lenght() > 0.01 then\n";
		addcode += "myRobot:setmove(vec)\n";
		addcode += "else\n";
		addcode += "myRobot:setmove(Vector:new(0,0))\n";
		addcode += "end\n";
		addcode += "end\n";
		
		// add infront of actual script
		
		return addcode+script;
		
		
		// avilable methos:
		// Vector:
		//	add(Vector)
		//	multiply(factor)
		//	getX()
		//	getY()
		//	copy()
		//Robot:
		//	setmove(Vector)
		//	getMove()
		//	getPos()
		//	useskill(skill,Vector)
		//	oncooldown(skill)
		// 	getDead()
		//World:
		//	getradius()
		//list ; allRobot
		
	}
	
	

}
