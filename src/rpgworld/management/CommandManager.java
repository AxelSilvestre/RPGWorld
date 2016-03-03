package rpgworld.management;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandManager {
	
	private OnlinePlayers players;
	private DataBaseManager data;
	private ConfigManager config;
	
	private static CommandManager INSTANCE;
	
	private CommandManager(){
		players = OnlinePlayers.getInstance();
		data = DataBaseManager.getInstance();
		config = ConfigManager.getInstance();
	}
	
	public static CommandManager getInstance(){
		if(INSTANCE == null)
			INSTANCE = new CommandManager();
		return INSTANCE;
	}
	
	public boolean proceedCommand(CommandSender sender, Command command, String label, String[] args){
		return false;
	}

}
