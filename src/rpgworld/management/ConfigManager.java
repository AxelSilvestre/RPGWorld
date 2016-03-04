package rpgworld.management;

import org.bukkit.configuration.file.FileConfiguration;

import rpgworld.RPGWorldPlugin;

public class ConfigManager {
	
	private static FileConfiguration config;
	
	private static final ConfigManager INSTANCE = new ConfigManager();
	
	private ConfigManager(){
		config = RPGWorldPlugin.getPlugin(RPGWorldPlugin.class).getConfig();
		saveDefault();
	}
	
	public int getMobXP(String name){
		return config.getInt("mobXP."+name);
	}
	
	public int getXPRate(){
		return config.getInt("xpRate");
	}
	
	public int getMaxLevel(){
		return config.getInt("maxLevel");
	}
	
	public int getXpNeededForLevel(int level){
		return config.getInt("xpForLevel."+level);
	}
	
	public static ConfigManager getInstance(){
		return INSTANCE;
	}
	
//	private void reload(){
//		Main.getPlugin(Main.class).reloadConfig();
//	}
//	
	private void saveDefault(){
		RPGWorldPlugin.getPlugin(RPGWorldPlugin.class).saveDefaultConfig();
	}
	
//	private void save(){
//		Main.getPlugin(Main.class).saveConfig();
//	}
//	
	

}
