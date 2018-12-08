package constants;

import java.util.HashMap;

public class TowerStats {
	public static HashMap<String, HashMap<String, Object[]>> data = new HashMap<>();
	static {
		HashMap<String, Object[]> cur;
		cur = new HashMap<>();
		data.put("Air", cur);
		cur.put("Attack", new double[] {15, 25, 35, 55, 75});
		cur.put("Cooldown", new double[] {700, 700, 700, 700, 700});
		cur.put("Range", new double[] {1.5, 1.75, 2, 2.25, 2.25});
		cur.put("Price", new double[] {20, 20, 30, 30, 30});
		cur.put("Description", new String[] {"Damage", "More Damage", 
				"More Damage", "More Damage", "More damage & 3 split shot"});
		cur.put("targetFlag", new int[]{2});
		
		cur = new HashMap<>();
		data.put("ArmorBreaker", cur);
		cur.put("Attack", new double[]  {5, 5, 5, 5, 5});
		cur.put("Cooldown", new double[] {700, 700, 600, 600, 500});
		cur.put("Range", new double[] {2.5, 2.5, 2.5, 3, 3});
		cur.put("DamageMultiplier", new double[] {0.3, 0.35, 0.4, 0.45, 0.5});
		cur.put("Price", new double[] {25, 25, 30, 50, 60});
		cur.put("Description", new String[] {"0.3x", "0.35x", 
				"0.4x", "0.45x", "0.5x"});
		cur.put("targetFlag", new int[]{3});
		
		cur = new HashMap<>();
		data.put("Buff", cur);
		cur.put("Attack", new double[]  {-1, -1, -1, -1, -1});
		cur.put("Cooldown", new double[] {150, 150, 150, 150, 150});
		cur.put("Range", new double[] {1.5, 1.5, 1.5, 1.5, 1.5});
		cur.put("Price", new double[]  {25, 25, 30, 50, 60});
		cur.put("Description", new String[] {"increase ATK speed", "--", 
				"also increase range", "--", "increase damage"});
		cur.put("targetFlag", new int[]{3});
		
		cur = new HashMap<>();
		data.put("Bomb", cur);
		cur.put("Attack", new double[] {15, 20, 25, 35, 50});
		cur.put("Cooldown", new double[] {1250, 1250, 1200, 1200, 1150});
		cur.put("Range", new double[] {3.5, 4, 4, 4.5, 4.5});
		cur.put("Price", new double[] {25, 25, 30, 50, 60});
		cur.put("Description", new String[] {"Damage", "More Damage", 
				"More Damage", "More Damage", "3 split shot"});
		cur.put("targetFlag", new int[]{1});
		
		
		cur = new HashMap<>();
		data.put("Fire", cur);
		cur.put("Attack", new double[]  {15, 20, 25, 35, 50});
		cur.put("Cooldown", new double[] {1250, 1150, 1000, 950, 700});
		cur.put("Range", new double[] {3.5, 4, 4, 4.5, 4.5});
		cur.put("FireRadius", new double[] {0.4, 0.5, 0.6, 0.7, 0.8});
		cur.put("Price", new double[] {25, 25, 30, 50, 60});
		cur.put("Description", new String[] {"1000 sec fire pure damage", "More Damage", 
				"More Damage", "More Damage", "More damage + range"});
		cur.put("targetFlag", new int[]{1});
		
		data.put("Ground", cur);
		cur.put("Attack", new double[] {15, 25, 35, 55, 75});
		cur.put("Cooldown", new double[] {700, 700, 700, 700, 700});
		cur.put("Range", new double[] {1.5, 1.75, 2, 2.25, 2.25});
		cur.put("Price", new double[] {20, 20, 30, 30, 30});
		cur.put("Description", new String[] {"Damage", "More Damage", 
				"More Damage", "More Damage", "More damage & 3 split shot"});
		cur.put("targetFlag", new int[]{1});
		
		data.put("Ice", cur);
		cur.put("Attack", new double[] {5, 5, 5, 5, 5});
		cur.put("Cooldown", new double[] {300, 300, 300, 300, 300});
		cur.put("Range", new double[] {2.5, 2.5, 2.5, 3, 3});
		cur.put("SplashRadius", new double[] {0.35, 0.45, 0.55, 0.65, 0.75});
		cur.put("Slowness", new double[]{0.45, 0.50, 0.55, 0.65, 0.75});
		cur.put("Price", new double[] {25, 25, 30, 50, 60});
		cur.put("Description", new String[] {"Slow 0.45x", "0.50x more splash", 
				"0.55x more splash", "0.65x more splash", "0.75x more splash"});
		cur.put("targetFlag", new int[]{3});
		
		
		cur = new HashMap<>();
		data.put("Missile", cur);
		cur.put("Attack", new double[]  {15, 20, 25, 35, 50});
		cur.put("Cooldown", new double[] {1250, 1250, 1200, 1200, 1150});
		cur.put("Range", new double[] {3.5, 4, 4, 4.5, 4.5});
		cur.put("SplashRadius", new double[] {0.4, 0.45, 0.5, 0.55, 0.6});
		cur.put("Price", new double[] {25, 25, 30, 50, 60});
		cur.put("Description", new String[] {"AoE Damage", "More Damage", 
				"More Damage", "More Damage", "More damage + range"});
		cur.put("targetFlag", new int[]{1});
		
		
		cur = new HashMap<>();
		data.put("Default", cur);
		cur.put("Attack", new double[] {15, 25, 35, 55, 75});
		cur.put("Cooldown", new double[] {700, 700, 700, 700, 700});
		cur.put("Range", new double[] {1.5, 1.75, 2, 2.25, 2.25});
		cur.put("Price", new double[] {20, 20, 30, 30, 30});
		cur.put("Description", new String[] {"Damage", "More Damage", 
				"More Damage", "More Damage", "Less damage but 3 split shot"});	
		cur.put("targetFlag", new int[]{3});
		
	
	}
	
	public static Object getData(String tower, String field, int level) {
		if (data.get(tower) == null) 
			throw new IllegalArgumentException("Invalid Tower type: " + tower);
		if (data.get(tower).get(field) == null) 
			throw new IllegalArgumentException("Invalid Data field: " + field);
		return data.get(tower).get(field);
	}
}
