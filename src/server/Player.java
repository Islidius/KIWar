package server;

import com.esotericsoftware.kryonet.Connection;

public class Player {
	
	Connection con;
	String name;
	boolean host = false;
	boolean ready = false;
	
	public Player(Connection con){
		this.con = con;
		name = con.getRemoteAddressTCP().getAddress().getHostAddress();
	}
	

}
