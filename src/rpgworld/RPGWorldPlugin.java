package rpgworld;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import rpgworld.management.CommandManager;
import rpgworld.management.DataBaseManager;
import rpgworld.management.EventManager;

public class RPGWorldPlugin extends JavaPlugin{
	
	private static EventManager eventManager;
	private static CommandManager commandManager;
	private static DataBaseManager data;
	
	@Override
	public void onEnable() {
		data = DataBaseManager.getInstance();		
		eventManager = EventManager.getInstance();
		eventManager.startListening(this);
		commandManager = CommandManager.getInstance();
		data.loadAreas();
		super.onEnable();		
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		return commandManager.proceedCommand(sender, command, label, args);
	}
	
	public static void broadcastMessageOnWorld(String message, String worldName){
		for (Player player : Bukkit.getWorld(worldName).getPlayers())
			player.sendMessage(message);
	}
	
	public static void broadcastMessageOnServer(String message){
		Bukkit.getServer().broadcastMessage(message);
	}	
	
}
