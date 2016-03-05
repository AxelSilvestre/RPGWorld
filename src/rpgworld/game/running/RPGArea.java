package rpgworld.game.running;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class RPGArea {
	
	private int minLevel;
	private int maxLevel;
	
	private String label;
	private AreaType type;
	
	private Map<String, Location> bounds;

	// TODO Adapt RPGArea to multiple bounds areas
	public RPGArea(int minLevel, int maxLevel, String label, AreaType type, Location[] bounds) {
		if(bounds.length > 4 ) throw new IllegalArgumentException();
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.label = label;
		this.type = type;
		createMap(bounds);
	}
	
	public RPGArea(int minLevel, int maxLevel, String label, AreaType type, Map<String, Location> bounds) {
		this.minLevel = minLevel;
		this.maxLevel = maxLevel;
		this.label = label;
		this.type = type;
		this.bounds = bounds;
	}	
	
	private void createMap(Location[] bounds){
		this.bounds = new HashMap<String, Location>();
		Location ne = null, nw = null, se = null, sw = null;		
		
		int searchedX = bounds[0].getX();
		int searchedZ = bounds[0].getZ();
		for(Location location : bounds){
			int x = location.getX();
			int z = location.getZ();
			
			if (x >= searchedX && z >= searchedZ)
				ne = location;
			
		}
		
		searchedX = bounds[0].getX();
		searchedZ = bounds[0].getZ();
		for(Location location : bounds){
			int x = location.getX();
			int z = location.getZ();
			
			if (x <= searchedX && z >= searchedZ)
				nw = location;
			
		}
		
		searchedX = bounds[0].getX();
		searchedZ = bounds[0].getZ();
		for(Location location : bounds){
			int x = location.getX();
			int z = location.getZ();
			
			if (x >= searchedX && z <= searchedZ)
				se = location;
			
		}
		
		searchedX = bounds[0].getX();
		searchedZ = bounds[0].getZ();
		for(Location location : bounds){
			int x = location.getX();
			int z = location.getZ();
			
			if (x <= searchedX && z <= searchedZ)
				sw = location;
			
		}
		
		this.bounds.put("NW", nw);
		this.bounds.put("NE", ne);
		this.bounds.put("SW", sw);
		this.bounds.put("SE", se);
		
	}
	
	public boolean isInside(int x, int z){		
		Location ne = bounds.get("NE");
		Location nw = bounds.get("NW");
		Location sw = bounds.get("SW");
		Location se = bounds.get("SE");
		
		if(x > ne.getX() && z > ne.getZ())
			return false;
		if(x < nw.getX() && z > nw.getZ())
			return false;
		if(x > se.getX() && z < se.getZ())
			return false;
		if(x < sw.getX() && z < sw.getZ())
			return false;
		
		return true;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
		
	public Collection<Location> getBoundsLocations() {
		return bounds.values();
	}
	
	public Map<String, Location> getBoundsMap() {
		return bounds;
	}
	
	public int getMaxLevel() {
		return maxLevel;
	}
	
	public int getMinLevel() {
		return minLevel;
	}
	
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}
	
	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}
	
	public AreaType getType() {
		return type;
	}
	
	public void setType(AreaType type) {
		this.type = type;
	}
	
	
	public void setBounds(Location[] bounds) {
		createMap(bounds);
	}

	@Override
	public String toString() {
		return "RPGArea [minLevel=" + minLevel + ", maxLevel=" + maxLevel + ", label=" + label + ", type=" + type
				+ ", bounds=" + bounds + "]";
	}
	

}
