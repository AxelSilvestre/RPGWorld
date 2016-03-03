package rpgworld.game.running;

import rpgworld.game.classes.RPGClass;

public class RPGPlayer {
	
	private final String name;
	private int xp;
	private RPGClass rpgClass;
	private Skin skin;
	private int level;
	private String guild;
	
	
	public RPGPlayer(String name, int level,  int xp, RPGClass rpgClass, Skin skin, String guild) {
		this.name = name;
		this.xp = xp;
		this.rpgClass = rpgClass;
		this.skin = skin;
		this.level = level;
		this.guild = guild;
	}
	
	public RPGPlayer(String name) {
		this(name, 1, 0, null, null, "");
	}

	public String getGuild() {
		return guild;
	}
	
	public void setGuild(String guild) {
		this.guild = guild;
	}
	
	public String getName() {
		return name;
	}

	public void addXP(int amount){
		xp += amount;
	}
	
	public void removeXP(int amount){
		xp -= amount;
	}
	
	public int getXP() {
		return xp;
	}
	
	public RPGClass getRpgClass() {
		return rpgClass;
	}

	public void setRpgClass(RPGClass rpgClass) {
		this.rpgClass = rpgClass;
	}

	public Skin getSkin() {
		return skin;
	}

	public void setSkin(Skin skin) {
		this.skin = skin;
	}
	
	public void addLevel(int amount){
		level += amount;
	}
	
	public void removeLevel(int amount){
		level -= amount;
	}
	
	public int getLevel() {
		return level;
	}
	
	
	
	
	
	
}
