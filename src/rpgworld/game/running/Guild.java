package rpgworld.game.running;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import rpgworld.management.DataBaseManager;

public class Guild {
	
	private String name;
	private String tag;
	private List<String> members;
	private Scoreboard scoreboard;
	private Team team;
	private DataBaseManager data;
	
	public Guild(String name, String tag, boolean registered) {
		this(name, tag, new ArrayList<String>(), registered);
	}
	
	public Guild(String name, String tag, List<String> members, boolean registered) {
		this.name = name;
		this.tag = tag;
		this.members = members;
		scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
		data = DataBaseManager.getInstance();
		if(!registered)
			createTeam(false, true);
		else
			team = scoreboard.getTeam(name);
	}
	
	private boolean createTeam(boolean registered, boolean init){
		if(!init && data.guildExists(name)) return false;
		if(!init && data.guildTagExists(tag)) return false;
//		try{
		team = scoreboard.registerNewTeam(name);
//		}catch(IllegalArgumentException e){return false;}
		team.setPrefix("["+tag+"]");
		team.setAllowFriendlyFire(false);
		team.canSeeFriendlyInvisibles();
		if(!registered) return true;
		DataBaseManager data = DataBaseManager.getInstance();
		members = data.getPlayersInGuild(name);
		return true;
	}
	
	
	@SuppressWarnings("deprecation")
	public boolean addMember(String name){
		RPGPlayer player = data.getPlayer(name);
		if(player == null) return false;
		members.add(player.getName());
		player.setGuild(name);
		team.addPlayer(Bukkit.getOfflinePlayer(name));
		return true;
	}
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
		team.unregister();
		createTeam(true, false);
	}


	public String getTag() {
		return tag;
	}


	public boolean setTag(String tag) {
		if(tag.length() > 4 || tag.length() < 2) return false;
		this.tag = tag;
		if(data.guildTagExists(tag)) return false;
		team.setPrefix("["+tag+"]");
		return true;
	}


	public List<String> getMembers() {
		return members;
	}

	@Override
	public String toString() {
		return "Guild [name=" + name + ", tag=" + tag + ", members=" + members + "]";
	}
	
	


}
