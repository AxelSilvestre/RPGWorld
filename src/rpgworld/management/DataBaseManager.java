package rpgworld.management;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import rpgworld.RPGWorldPlugin;
import rpgworld.game.classes.RPGClass;
import rpgworld.game.running.AreaType;
import rpgworld.game.running.Guild;
import rpgworld.game.running.Location;
import rpgworld.game.running.RPGArea;
import rpgworld.game.running.RPGPlayer;
import rpgworld.game.running.Skin;

public class DataBaseManager {
	
	private static final DataBaseManager INSTANCE = new DataBaseManager();
	
	private FileConfiguration dataBase;
	private File dataFile;
	
	private ExistingAreas areas;
	private OnlinePlayers players;
	private ExistingGuilds guilds;
	
	@SuppressWarnings("deprecation")
	private DataBaseManager() {
		dataBase = YamlConfiguration.loadConfiguration(RPGWorldPlugin.getPlugin(RPGWorldPlugin.class).getResource("dataBase.yml"));
		areas = ExistingAreas.getInstance();
		players = OnlinePlayers.getInstance();
		guilds = ExistingGuilds.getInstance();
		saveDefault();
	}
	
	public static DataBaseManager getInstance(){
		return INSTANCE;
	}
	
	public void registerGuild(Guild guild){
		String path = "Guilds."+guild.getName()+".";
		dataBase.set(path+"tag", guild.getTag());
		for(String player : guild.getMembers()){
			dataBase.set(path+"members", player);
		}
		guilds.addGuild(guild);
		save();

	}
	
	public boolean guildExists(String name){
		return dataBase.contains("Guilds." + name);
	}
	
	public boolean guildTagExists(String tag){
		return dataBase.contains("Guilds.tag" + tag);
	}
	
	public void loadGuilds(){
		reload();
		String name = null, tag = null;
		List<String> members = null;
		
		int i = 0;
		for(String sub : dataBase.getConfigurationSection("Guilds").getKeys(true)){
			String path = "Guilds."+sub;
			switch(i%3){
			case 0 : name = sub; break;
			case 1 : tag = dataBase.getString(path); break;
			case 2 : members = dataBase.getStringList(path);
					 guilds.addGuild(new Guild(name, tag, members, true));
					 break;
			default :
			}
			i++;
			
		}
	}
	
	public void updateGuilds(Guild... guilds){
		for(Guild guild : guilds){
			String path = "Guilds."+guild.getName()+".";
			dataBase.set(path+"tag", guild.getTag());
			dataBase.set(path+"members", guild.getMembers());
		}
		save();
	}
	
	public void registerPlayers(String... names){
		for(String name : names){
			String path = "Players."+name+".";
			dataBase.set(path+"guild", "");
			dataBase.set(path+"class", "");
			dataBase.set(path+"skin", "");
			dataBase.set(path+"level", 1);
			dataBase.set(path+"xp", 0);
			players.addPlayer(new RPGPlayer(name));
		}
		save();

	}
	
	public boolean playerExists(String name){
		return dataBase.contains("Players." + name);
	}
	
	public void loadPlayers(String... names){
		reload();
		for(String name : names){
			String path = "Players."+name+".";
			// TODO Replace nulls
			RPGClass rpgClass = null;
			Skin skin = null;

			int level = dataBase.getInt(path+"level");
			int xp = dataBase.getInt(path+"xp");
			String guild = dataBase.getString(path+"guild");

			players.addPlayer(new RPGPlayer(name, level, xp, rpgClass, skin, guild));
		}
	}
	
	public void updatePlayers(RPGPlayer... players){
		for(RPGPlayer player : players){
			String path = "Players."+player.getName()+".";
			// TODO Remove nulls
			dataBase.set(path+"guild", player.getGuild() == null ? "" : player.getGuild());
			dataBase.set(path+"class", player.getRpgClass() == null ? "" : player.getRpgClass());
			dataBase.set(path+"skin", player.getSkin() == null ? "" : player.getSkin());
			dataBase.set(path+"level", player.getLevel());
			dataBase.set(path+"xp", player.getXP());
		}
		save();
	}
		
	public void saveAllPlayers(){
		for (RPGPlayer p : players.getAllPlayers())
			updatePlayers(p);					
	}
	
	public List<String> getPlayersInGuild(String guildName){
		List<String> list = dataBase.getStringList("Guilds."+guildName+".members");		
		return list;		
	}
	
	public RPGPlayer getPlayer(String name){
		if(!playerExists(name)) return null;
		if(players.exists(name)) return players.getPlayer(name);
		
		String path = "Players."+name+".";
		// TODO Replace nulls
		RPGClass rpgClass = null;
		Skin skin = null;

		int level = dataBase.getInt(path+"level");
		int xp = dataBase.getInt(path+"xp");
		String guild = dataBase.getString(path+"guild");

		return new RPGPlayer(name, level, xp, rpgClass, skin, guild);
	}
	
	public boolean areaExists(String label){
		return dataBase.contains("Areas."+label);
	}
	
	public void saveAreas(RPGArea... areas){
		for(RPGArea area : areas){
			String path = "Areas."+area.getLabel()+".";
			dataBase.set(path+"minLevel", area.getMinLevel());
			dataBase.set(path+"maxLevel", area.getMaxLevel());
			dataBase.set(path+"type", area.getType().toString());
			Map<String, Location> map = area.getBoundsMap();
			dataBase.set(path+"NE.x", map.get("NE").getX());
			dataBase.set(path+"NE.z", map.get("NE").getZ());
			dataBase.set(path+"NW.x", map.get("NW").getX());
			dataBase.set(path+"NW.z", map.get("NW").getZ());
			dataBase.set(path+"SE.x", map.get("SE").getX());
			dataBase.set(path+"SE.z", map.get("SE").getZ());
			dataBase.set(path+"SW.x", map.get("SW").getX());
			dataBase.set(path+"SW.z", map.get("SW").getZ());			
		}
		save();
	}
	
	public void loadAreas(){
		reload();
		String label = null;
		int minLevel = 0, maxLevel = 0;
		AreaType type = null;
		Location location = new Location(0, 0, 0);
		Map<String, Location> map = new HashMap<String, Location>();

		int i = 0;
		try{
			for(String sub : dataBase.getConfigurationSection("Areas").getKeys(false)){
				String path = "Areas."+sub;
				switch(i%16){
				case 0 : label = sub; break;
				case 1 : minLevel = dataBase.getInt(path); break;
				case 2 : maxLevel = dataBase.getInt(path); break;
				case 3 : type = AreaType.valueOf(dataBase.getString(path)); break;
				case 5 : location.setX(dataBase.getInt(path)); break;
				case 6 : location.setZ(dataBase.getInt(path));
				map.put("NE", location.copy());
				break;
				case 8 : location.setX(dataBase.getInt(path)); break;
				case 9 : location.setZ(dataBase.getInt(path));
				map.put("NW", location.copy());
				break;
				case 11 : location.setX(dataBase.getInt(path)); break;
				case 12 : location.setZ(dataBase.getInt(path));
				map.put("SE", location.copy());
				break;
				case 14 : location.setX(dataBase.getInt(path)); break;
				case 15 : location.setZ(dataBase.getInt(path));
				map.put("SW", location.copy());

				RPGArea area = new RPGArea(minLevel, maxLevel, label, type, map);
				areas.addArea(area);
				map = new HashMap<String, Location>();
				break;
				default :
				}
				i++;
			}
		}catch(NullPointerException e){return;}


	}
	
	private void reload() {
	    if (dataFile == null) {
	    	dataFile = new File(RPGWorldPlugin.getPlugin(RPGWorldPlugin.class).getDataFolder(), "dataBase.yml");
	    }
	    dataBase = YamlConfiguration.loadConfiguration(dataFile);

	    Reader defConfigStream = null;
		try {
			defConfigStream = new InputStreamReader(RPGWorldPlugin.getPlugin(RPGWorldPlugin.class).getResource("dataBase.yml"), "UTF8");
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
	        dataFile = new File(RPGWorldPlugin.getPlugin(RPGWorldPlugin.class).getDataFolder(), "dataBase.yml");
	    }
	    if (!dataFile.exists()) {            
	         RPGWorldPlugin.getPlugin(RPGWorldPlugin.class).saveResource("dataBase.yml", false);
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
