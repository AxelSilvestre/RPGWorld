package rpgworld.management;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import rpgworld.game.running.RPGPlayer;

public class OnlinePlayers {

	private static Map<String, RPGPlayer> players;
	
	private static final OnlinePlayers INSTANCE = new OnlinePlayers();
	
	private OnlinePlayers(){
		players = new HashMap<String, RPGPlayer>();
	}
	
	public static OnlinePlayers getInstance(){
		return INSTANCE;
	}
	
	public void addPlayer(RPGPlayer player){
		players.put(player.getName(), player);
	}
	
	public RPGPlayer getPlayer(String name){
		return players.get(name);
	}
	
	public Collection<RPGPlayer> getAllPlayers(){
		return players.values();
	}
	
	
}
