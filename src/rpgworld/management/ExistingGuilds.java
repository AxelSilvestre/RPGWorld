package rpgworld.management;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import rpgworld.game.running.Guild;

public class ExistingGuilds {

	private static Map<String, Guild> guilds;
	
	private static ExistingGuilds INSTANCE;
	
	private ExistingGuilds(){
		guilds = new HashMap<String, Guild>();
	}
	
	public static ExistingGuilds getInstance(){
		if(INSTANCE == null)
			INSTANCE = new ExistingGuilds();
		return INSTANCE;
	}
	
	public boolean exists(String name){
		return guilds.containsKey(name);
	}
	
	public boolean addGuild(Guild guild){
		if(exists(guild.getName())) return false;
		guilds.put(guild.getName(), guild);
		return true;
	}
	
	public boolean removeGuild(String name){
		return guilds.remove(name) != null;
	}
	
	public Guild getGuild(String name){
		return guilds.get(name);
	}
	
	public Collection<Guild> getAllAreas(){
		return guilds.values();
	}
	
	
}
