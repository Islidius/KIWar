package pack;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class NetListener extends Listener{   // Client
	
	Main main;
	
	public NetListener(Main m){
		this.main = m;
	}
	
	public void connected(Connection c){
		System.out.println("connected");
	}
	
	public void disconnected(Connection c){
		System.out.println("disconnected");
	}
	
	public void received(Connection c,Object o){
		//System.out.println("Recieved");
		
		/*System.out.println("Client:");
		System.out.println("\tIP:"+c.getRemoteAddressTCP().getAddress());
		System.out.println("\tType:"+o.getClass().getName());*/
		
		if(o instanceof Packet3AnswerConnection){
			if(((Packet3AnswerConnection)o).allowed){
				main.switchstate("lobby");
			}
		}
		
		else if(o instanceof Packet4SetReady){
			if(((Packet4SetReady)o).ip.equals("ALL")){ // code for all player ready
				//main.switchstate("game"); //testing
				main.rdyswitch = true;
				System.out.println("all");
				
				String gesamt = "";
				
				try { // read given file
					BufferedReader in = new BufferedReader(new FileReader("scripts/"+((Lobby)main.lobby).kis.get(((Lobby)main.lobby).idpos)));
					String line = in.readLine();
					while(line != null){
						gesamt += line +"\n";
						line = in.readLine();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Packet6PlayerCode p6 = new Packet6PlayerCode();
				p6.name = "Player:"+c.getID();
				p6.code = gesamt;
				main.client.sendTCP(p6);
			}
			else{
				((Lobby)main.lobby).setready(((Packet4SetReady)o).ip,((Packet4SetReady)o).ready);
			}
		}
		
		else if(o instanceof Packet5PlayerInfo){
			if(((Packet5PlayerInfo)o).mode == 1)((Lobby)main.lobby).addplayer(((Packet5PlayerInfo)o).name);
			else if(((Packet5PlayerInfo)o).mode == 2)((Lobby)main.lobby).removeplayer(((Packet5PlayerInfo)o).name);
		}
		
		else if(o instanceof Packet6PlayerCode){ // game started and code transmission
			String name = ((Packet6PlayerCode)o).name;
			String code = ((Packet6PlayerCode)o).code;
			//System.out.println(code);
			((Game)main.game).addplayer(new Player(new Ki((Game)main.game,code),"",name));
		}
	}
}
