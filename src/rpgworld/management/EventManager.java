package rpgworld.management;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import rpgworld.game.running.RPGPlayer;

public class EventManager implements Listener {

	private DataBaseManager data;
	private OnlinePlayers players;
	
	private static EventManager INSTANCE;
	
	private EventManager() {
		data = DataBaseManager.getInstance();
		players = OnlinePlayers.getInstance();
	}
	
	public static EventManager getInstance(){
		if(INSTANCE == null)
			INSTANCE = new EventManager();
		return INSTANCE;
	}
	
	@EventHandler
	private void onJoin(PlayerJoinEvent e){
		String name = e.getPlayer().getName();
		RPGPlayer player = null;
		if(data.playerExists(name)){
			player = data.loadPlayer(name);
		}else{
			player = data.registerPlayer(name);
		}
		players.addPlayer(player);
	}
	
	@EventHandler
	private void onLeave(PlayerQuitEvent e){
		String name = e.getPlayer().getName();
		RPGPlayer player = players.getPlayer(name);
		data.updatePlayer(player);
	}
	
	
	
}
