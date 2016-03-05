package rpgworld.management;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import rpgworld.game.running.RPGArea;

public final class ExistingAreas {

	private static Map<String, RPGArea> areas;
	
	private static ExistingAreas INSTANCE;
	
	private ExistingAreas(){
		areas = new HashMap<String, RPGArea>();
	}
	
	public static ExistingAreas getInstance(){
		if(INSTANCE == null)
			INSTANCE = new ExistingAreas();
		return INSTANCE;
	}
	
	public boolean exists(String label){
		return areas.containsKey(label);
	}
	
	public boolean addArea(RPGArea area){
		if(exists(area.getLabel())) return false;
		areas.put(area.getLabel(), area);
		return true;
	}
	
	public boolean removeArea(String label){
		return areas.remove(label) != null;
	}
	
	public RPGArea getArea(String label){
		return areas.get(label);
	}
	
	public Collection<RPGArea> getAllAreas(){
		return areas.values();
	}
	
}
