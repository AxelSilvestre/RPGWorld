package rpgworld.game.running;


import rpgworld.game.classes.RPGClass;

public class RPGPlayer {
	
	private final String name;
	private float xp;
	private RPGClass rpgClass;
	private Skin skin;
	private int level;
	private String guild;
	
	
	public RPGPlayer(String name, int level,  float xp, RPGClass rpgClass, Skin skin, String guild) {
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

	public void addXP(float amount){
		xp += amount;
	}
	
	public void removeXP(float amount){
		xp -= amount;
	}
	
	public void setXp(float xp) {
		this.xp = xp;
	}
	
	public float getXP() {
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
	
	public void setLevel(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}

	@Override
	public String toString() {
		return "RPGPlayer [name=" + name + ", xp=" + xp + ", rpgClass=" + rpgClass + ", skin=" + skin + ", level="
				+ level + ", guild=" + guild + "]";
	}
	
	
	
	
	
	
}
