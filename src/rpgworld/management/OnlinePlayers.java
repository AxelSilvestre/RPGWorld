package rpgworld.management;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import rpgworld.game.running.RPGPlayer;

public final class OnlinePlayers {

	private static Map<String, RPGPlayer> players;
	
	private static OnlinePlayers INSTANCE;
	
	private OnlinePlayers(){
		players = new HashMap<String, RPGPlayer>();
	}
	
	public static OnlinePlayers getInstance(){
		if(INSTANCE == null) 
			INSTANCE = new OnlinePlayers();
		return INSTANCE;
	}
	
	public boolean exists(String name){
		return players.containsKey(name);
	}
	
	public boolean addPlayer(RPGPlayer player){
		if(exists(player.getName())) return false;
		players.put(player.getName(), player);
		return true;
	}
	
	public boolean removePlayer(String name){
		return players.remove(name) != null;
	}
	
	public RPGPlayer getPlayer(String name){
		return players.get(name);
	}
	
	public Collection<RPGPlayer> getAllPlayers(){
		return players.values();
	}
	
	
}
