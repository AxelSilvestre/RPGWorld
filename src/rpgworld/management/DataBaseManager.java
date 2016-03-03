package rpgworld.management;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import rpgworld.Main;
import rpgworld.game.classes.RPGClass;
import rpgworld.game.running.RPGPlayer;
import rpgworld.game.running.Skin;

public class DataBaseManager {
	
	private static final DataBaseManager INSTANCE = new DataBaseManager();
	
	private FileConfiguration dataBase;
	private File dataFile;
	
	@SuppressWarnings("deprecation")
	private DataBaseManager() {
		dataBase = YamlConfiguration.loadConfiguration(Main.getPlugin(Main.class).getResource("dataBase.yml"));
		saveDefault();
	}
	
	public static DataBaseManager getInstance(){
		return INSTANCE;
	}
	
	public RPGPlayer registerPlayer(String name){
		String path = "Players."+name+".";
		dataBase.set(path+"guild", "");
		dataBase.set(path+"class", "");
		dataBase.set(path+"skin", "");
		dataBase.set(path+"level", 50);
		dataBase.set(path+"xp", 0);
		save();
		return new RPGPlayer(name);
	}
	
	public boolean playerExists(String name){
		return dataBase.contains(name);
	}
	
	public RPGPlayer loadPlayer(String name){
		reload();
		String path = "Players."+name+".";
		// TODO Replace nulls
		RPGClass rpgClass = null;
		Skin skin = null;
		
		int level = dataBase.getInt(path+"level");
		int xp = dataBase.getInt(path+"xp");
		String guild = dataBase.getString(path+"guild");
		
		return new RPGPlayer(name, level, xp, rpgClass, skin, guild);		
	}
	
	public void updatePlayer(RPGPlayer player){
		String path = "Players."+player.getName()+".";
		dataBase.set(path+"guild", player.getGuild() == null ? "" : player.getGuild());
		dataBase.set(path+"class", player.getRpgClass() == null ? "" : player.getRpgClass());
		dataBase.set(path+"skin", player.getSkin() == null ? "" : player.getSkin());
		dataBase.set(path+"level", player.getLevel());
		dataBase.set(path+"xp", player.getXP());
		save();
	}
	
	private void reload() {
	    if (dataFile == null) {
	    	dataFile = new File(Main.getPlugin(Main.class).getDataFolder(), "dataBase.yml");
	    }
	    dataBase = YamlConfiguration.loadConfiguration(dataFile);

	    Reader defConfigStream = null;
		try {
			defConfigStream = new InputStreamReader(Main.getPlugin(Main.class).getResource("dataBase.yml"), "UTF8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	    if (defConfigStream != null) {
	        YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
	        dataBase.setDefaults(defConfig);
	    }
	}
	
	
	private void saveDefault(){
	    if (dataFile == null) {
	        dataFile = new File(Main.getPlugin(Main.class).getDataFolder(), "dataBase.yml");
	    }
	    if (!dataFile.exists()) {            
	         Main.getPlugin(Main.class).saveResource("dataBase.yml", false);
	     }
	}
	
	private void save(){
	    if (dataBase == null || dataFile == null) {
	        return;
	    }
	    try {
			dataBase.save(dataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
