package rpgworld.management;

import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import rpgworld.game.running.RPGPlayer;

public class EventManager implements Listener {

	private DataBaseManager data;
	private ConfigManager config;
	private OnlinePlayers players;
	private ReportingFileManager report;
	
	private static EventManager INSTANCE;
	
	private EventManager() {
		data = DataBaseManager.getInstance();
		players = OnlinePlayers.getInstance();
		config = ConfigManager.getInstance();
		report = ReportingFileManager.getInstance();
	}
	
	public static EventManager getInstance(){
		if(INSTANCE == null)
			INSTANCE = new EventManager();
		return INSTANCE;
	}
	
	public void startListening(Plugin plugin){
		Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public void stopListening(Plugin plugin){
		HandlerList.unregisterAll(plugin);
	}
	
	@EventHandler
	private void onJoin(PlayerJoinEvent e){
		String name = e.getPlayer().getName();
		Player player = e.getPlayer();
		if(data.playerExists(name)){
			data.loadPlayers(name);
		}else{
			data.registerPlayers(name);
			player.setExp(0);
			player.setLevel(1);
		}
		// TODO Test
		players.getPlayer(name).joinGuild("Staff");
	}
	
	@EventHandler
	private void onLeave(PlayerQuitEvent e){
		String name = e.getPlayer().getName();
		RPGPlayer player = players.getPlayer(name);
		data.updatePlayers(player);
		players.removePlayer(name);
	}
	
	@EventHandler
	private void onKillEntity(EntityDeathEvent e){
		LivingEntity entity = e.getEntity();
		LivingEntity killer = entity.getKiller();
		
		if(killer instanceof Player && !(entity instanceof Player)){
			float mobXp = config.getMobXP(entity.getName()) * config.getXPRate();
			
			RPGPlayer player = players.getPlayer(killer.getName());
			float playerXp = player.getXP();
			int level = player.getLevel();
			if(level == config.getMaxLevel())
				return;
			player.addXP(mobXp);
			
			int totalMinecraftXp[] = {7,9,11,13,15,17,19,21,23,25,27,29,31,33,35,37,42,47,52,57};
						
			float neededXp = config.getXpNeededForLevel(level);
			
			if((int) playerXp >= neededXp){
				player.setLevel(level+1);
				player.setXp(0);
			}
			
			float xpToSet = playerXp*totalMinecraftXp[level]/neededXp;

			((Player) killer).setExp(0);
			((Player) killer).giveExp((int) xpToSet);
			
		}
	}
	
	@EventHandler
	private void onLevelUp(PlayerLevelChangeEvent e){
		if(e.getNewLevel() - e.getOldLevel() > 1){
			report.report("Weird level up", "Player: "+ e.getPlayer().getName() ,"Level won: "+ (e.getNewLevel() - e.getOldLevel()));
		}
	}
	
	@EventHandler
	private void onSpeak(AsyncPlayerChatEvent e){
		System.out.println(e.getPlayer().getName() + " HAS SPOKE !");
	}
	
	
	
}
