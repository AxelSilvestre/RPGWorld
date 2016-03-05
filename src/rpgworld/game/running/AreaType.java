package rpgworld.game.running;

public enum AreaType {
	
	DUNGEON(false),
	RAID(false),
	NORMAL(true),
	BATTLEGROUND(true);
	
	
	private boolean pvp;
	
	private AreaType(boolean pvp){
		this.pvp = pvp;
	}
	
	public boolean isPvp() {
		return pvp;
	}

}
