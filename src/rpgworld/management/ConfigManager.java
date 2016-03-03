package rpgworld.management;

import org.bukkit.configuration.file.FileConfiguration;

import rpgworld.Main;

public class ConfigManager {
	
	private static FileConfiguration config;
	
	private static final ConfigManager INSTANCE = new ConfigManager();
	
	private ConfigManager(){
		config = Main.getPlugin(Main.class).getConfig();
		// TODO do methods here
		Main.getPlugin(Main.class).saveDefaultConfig();
	}
	
	public static ConfigManager getInstance(){
		return INSTANCE;
	}
	
	

}
