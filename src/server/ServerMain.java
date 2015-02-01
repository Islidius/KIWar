package server;

import java.io.IOException;
import java.util.HashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;

public class ServerMain {
	
	Server server;
	String name;
	int botcount = 0; // count of bots
	HashMap<Integer,Player> players = new HashMap<Integer,Player>();
	
	public ServerMain(){
		server = new Server();
		
		register();
		
		server.addListener(new NetListener(this));
		
		try {
			server.bind(25556,25555);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		server.start();
	}
	
	public void register(){
		Kryo kryo = server.getKryo();
		kryo.register(Packet2RequestConnection.class);
		kryo.register(Packet3AnswerConnection.class);
		kryo.register(Packet4SetReady.class);
		kryo.register(Packet5PlayerInfo.class);
		kryo.register(Packet6PlayerCode.class);
	}

}
