package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class NetListener extends Listener{ // server
	ServerMain main;
	
	public NetListener(ServerMain main){
		this.main = main;
	}
	
	public void connected(Connection c){
		
	}
	
	public void disconnected(Connection c){
		if(main.players.containsKey(c.getID())){
			String host = main.players.get(c.getID()).name;
			main.players.remove(c.getID());
			
			for(Integer i:main.players.keySet()){ // Relay Information for disconnect
				Packet5PlayerInfo p5 = new Packet5PlayerInfo();
				p5.name = host;
				p5.mode = 2; // 2 = remove
				main.players.get(i).con.sendTCP(p5);
			}
		}
		
		if(main.players.size() == 0){
			main.server.stop();
			//System.exit(0);
		}
	}
	
	public void received(Connection c,Object o){
		/*System.out.println("Server:");
		System.out.println("\tID:"+c.getID());
		System.out.println("\tIP:"+c.getRemoteAddressTCP().getAddress());
		System.out.println("\tType:"+o.getClass().getName());*/
		
		if(o instanceof Packet2RequestConnection){  // Request Connection
			Packet3AnswerConnection p3 = new Packet3AnswerConnection();
			p3.allowed = true;
			c.sendTCP(p3);
			
			for(Integer i:main.players.keySet()){ // //send all players in the lobby
				Packet5PlayerInfo p5 = new Packet5PlayerInfo();
				p5.name = main.players.get(i).name; //con.getRemoteAddressTCP().getAddress().getHostAddress();
				p5.mode = 1;
				c.sendTCP(p5);
			}
			
			main.players.put(c.getID(),new Player(c));
			
			for(Integer i:main.players.keySet()){ // Relay Information
				Packet5PlayerInfo p5 = new Packet5PlayerInfo();
				p5.name = c.getRemoteAddressTCP().getAddress().getHostAddress();
				p5.mode = 1;
				main.players.get(i).con.sendTCP(p5);
			}
		}
		
		else if(o instanceof Packet4SetReady){ // Set Ready 
			Player akp = main.players.get(c.getID());
			akp.ready = ((Packet4SetReady) o).ready;
			
			boolean allready = true;
			
			for(Integer i:main.players.keySet()){ //Relay Ready
				Packet4SetReady p4 = new Packet4SetReady();
				allready = allready ? main.players.get(i).ready:false;
				p4.ready = akp.ready;p4.ip = c.getRemoteAddressTCP().getAddress().getHostAddress();
				main.players.get(i).con.sendTCP(p4);
			}
			
			if(allready){ // alle sind ready
				Packet4SetReady p4 = new Packet4SetReady();
				p4.ready = true;p4.ip = "ALL"; // code for all player
				for(Integer i:main.players.keySet()){
					main.players.get(i).con.sendTCP(p4); // an alle
				}
				
				// code for bots
				
				String gesamt = "";
				
				try { // read given file
					BufferedReader in = new BufferedReader(new FileReader("scripts/bot.txt"));
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
				
				Packet6PlayerCode p6 = new Packet6PlayerCode();
				for(int j = 0;j<main.botcount;j++){
					p6.name = "BOT"+j;
					p6.code = gesamt;
					for(Integer i:main.players.keySet()){
						main.players.get(i).con.sendTCP(p6); // an alle
					}
				}
			}
		}
		
		else if(o instanceof Packet5PlayerInfo){ // name = bot mode = botcount
			if(((Packet5PlayerInfo)o).name.equals("BOT")){
				main.botcount = ((Packet5PlayerInfo)o).mode;
			}
		}
		
		else if(o instanceof Packet6PlayerCode){
			for(Integer i:main.players.keySet()){ //Relay Ready
				main.players.get(i).con.sendTCP(o);
			}
		}
		
		//print();
	}
	
	public void print(){
		System.out.println("\n\nPlayer\t\t\tReady");
		for(Integer i:main.players.keySet()){
			System.out.println(main.players.get(i).con.getRemoteAddressTCP().getAddress().getHostAddress()+"\t\t"+main.players.get(i).ready);
		}
	}

}
