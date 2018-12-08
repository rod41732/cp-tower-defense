package constants;

import java.util.HashMap;

public class TowerStats {
	
	
	public static HashMap<String, HashMap<String, Object[]>> data = new HashMap<>();
	public static void loadTower() {
		data = new HashMap<>();
		HashMap<String, Object[]> cur;
		
		
		cur = new HashMap<>();
		data.put("Air", cur);
		cur.put("Attack", new Object [] {15d, 25d, 35d, 55d, 75d});
		cur.put("Cooldown", new Object [] {700d, 700d, 700d, 700d, 700d});
		cur.put("Range", new Object [] {1.5d, 1.75d, 2d, 2.25d, 2.25d});
		cur.put("Price", new Object [] {20, 20, 30, 30, 30});
		cur.put("Description", new Object [] {"Damage", "More Damage", 
				"More Damage", "More Damage", "More damage & 3 split shot"});
		cur.put("TargetFlag", new Object []{2});

		
		
		cur = new HashMap<>();
		data.put("ArmorBreaker", cur);
		cur.put("Attack", new Object []  {5d, 5d, 5d, 5d, 5d});
		cur.put("Cooldown", new Object [] {700d, 700d, 600d, 600d, 500d});
		cur.put("Range", new Object [] {2.5d, 2.5d, 2.5d, 3d, 3d});
		cur.put("DamageMultiplier", new Object [] {0.3, 0.35, 0.4, 0.45, 0.5});
		cur.put("Price", new Object [] {25, 25, 30, 50, 60});
		cur.put("Description", new Object [] {"0.3x", "0.35x", 
				"0.4x", "0.45x", "0.5x"});
		cur.put("TargetFlag", new Object []{3});
		
		
		
		cur = new HashMap<>();
		data.put("Buff", cur);
		cur.put("Attack", new Object []  {0d, 0d, 0d, 0d, 0d});
		cur.put("Cooldown", new Object [] {150d, 150d, 150d, 150d, 150d});
		cur.put("Range", new Object [] {1.5d, 1.5d, 1.5d, 1.5d, 1.5d});
		cur.put("Price", new Object []  {25, 25, 30, 50, 60});
		cur.put("Description", new Object [] {"increase ATK speed", "--", 
				"also increase range", "--", "increase damage"});
		cur.put("TargetFlag", new Object []{3});
		
		
		
		cur = new HashMap<>();
		data.put("Bomb", cur);
		cur.put("Attack", new Object [] {15d, 20d, 25d, 35d, 50d});
		cur.put("Cooldown", new Object [] {1250d, 1250d, 1200d, 1200d, 1150d});
		cur.put("Range", new Object [] {3.5d, 4d, 4d, 4.5d, 4.5d});
		cur.put("Price", new Object [] {25, 25, 30, 50, 60});
		cur.put("Description", new Object [] {"Damage", "More Damage", 
				"More Damage", "More Damage", "3 split shot"});
		cur.put("TargetFlag", new Object []{1});
		
		
		
		
		cur = new HashMap<>();
		data.put("Fire", cur);
		cur.put("Attack", new Object []  {15d, 20d, 25d, 35d, 50d});
		cur.put("Cooldown", new Object [] {1250d, 1150d, 1000d, 950d, 700d});
		cur.put("Range", new Object [] {3.5d, 4d, 4d, 4.5d, 4.5d});
		cur.put("FireRadius", new Object [] {0.4, 0.5, 0.6, 0.7, 0.8});
		cur.put("Price", new Object [] {25, 25, 30, 50, 60});
		cur.put("Description", new Object [] {"1000 sec fire pure damage", "More Damage", 
				"More Damage", "More Damage", "More damage + range"});
		cur.put("TargetFlag", new Object []{1});
		
		
		
		cur = new HashMap<>();
		data.put("Ground", cur);
		cur.put("Attack", new Object [] {15d, 25d, 35d, 55d, 75d});
		cur.put("Cooldown", new Object [] {700d, 700d, 700d, 700d, 700d});
		cur.put("Range", new Object [] {1.5d, 1.75d, 2d, 2.25d, 2.25d});
		cur.put("Price", new Object [] {20, 20, 30, 30, 30});
		cur.put("Description", new Object [] {"Damage", "More Damage", 
				"More Damage", "More Damage", "More damage & 3 split shot"});
		cur.put("TargetFlag", new Object []{1});
		
		
		
		cur = new HashMap<>();
		data.put("Ice", cur);
		cur.put("Attack", new Object [] {5d, 5d, 5d, 5d, 5d});
		cur.put("Cooldown", new Object [] {300d, 300d, 300d, 300d, 300d});
		cur.put("Range", new Object [] {2.5d, 2.5d, 2.5d, 3d, 3d});
		cur.put("SplashRadius", new Object [] {0.35, 0.45, 0.55, 0.65, 0.75});
		cur.put("Slowness", new Object []{0.45, 0.50, 0.55, 0.65, 0.75});
		cur.put("Price", new Object [] {25, 25, 30, 50, 60});
		cur.put("Description", new Object [] {"Slow 0.45x", "0.50x more splash", 
				"0.55x more splash", "0.65x more splash", "0.75x more splash"});
		cur.put("TargetFlag", new Object []{3});
		
		
		cur = new HashMap<>();
		data.put("Missile", cur);
		cur.put("Attack", new Object []  {15d, 20d, 25d, 35d, 50d});
		cur.put("Cooldown", new Object [] {1250d, 1250d, 1200d, 1200d, 1150d});
		cur.put("Range", new Object [] {3.5d, 4d, 4d, 4.5d, 4.5d});
		cur.put("SplashRadius", new Object [] {0.4, 0.45, 0.5, 0.55, 0.6});
		cur.put("Price", new Object [] {25, 25, 30, 50, 60});
		cur.put("Description", new Object [] {"AoE Damage", "More Damage", 
				"More Damage", "More Damage", "More damage + range"});
		cur.put("TargetFlag", new Object []{1});
		
		
		cur = new HashMap<>();
		data.put("Default", cur);
		cur.put("Attack", new Object [] {7d, 12d, 20d, 35d, 15d});
		cur.put("Cooldown", new Object [] {700d, 700d, 700d, 700d, 700d});
		cur.put("Range", new Object [] {4d, 4.5d, 5d, 5d, 5.5d});
		cur.put("Price", new Object [] {20, 20, 30, 30, 30});
		cur.put("Description", new Object [] {"Damage", "More Damage", 
				"More Damage", "More Damage", "Less damage but 3 split shot"});	
		cur.put("TargetFlag", new Object []{3});
		
	
	}
	
	public static Object getData(String tower, String field, int level) {
		if (data.get(tower) == null) 
			throw new IllegalArgumentException("Invalid Tower type: " + tower);
		if (data.get(tower).get(field) == null) 
			throw new IllegalArgumentException("Invalid Data field: " + field);
		return data.get(tower).get(field)[level-1];
	}
}
