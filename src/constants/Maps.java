package constants;

import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.image.Image;
import model.Map;
import util.cpp;

public class Maps {
	private static ArrayList<Map> maps = new ArrayList<>();
	
	public static void loadMap() {
		int[][] m1 ={{0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0},
				{0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0},
				{0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,2,0,0,0,0},
				{0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0},
				{0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,0,0,0,0,0},
				{0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
				{2,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
				{0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
				{0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
				{0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
				{0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
				{0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3},
				{0,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3}
				};
		HashMap<Integer, Image> tm1 = new HashMap<>();
		tm1.put(0,  null);
		tm1.put(1, Images.tileUnwalkable);
		tm1.put(2, Images.tileUnplaceable);
		tm1.put(3, Images.tileNormal1);
		maps.add(new Map(m1, tm1, new cpp.pii(0, 6), new cpp.pii(16, 2), Images.map0Preview, Images.map0));
		
		int[][] m2 = {	
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
				{0, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 0},
				{0, 5, 10, 10, 10, 10, 5, 5, 5, 10, 10, 10, 10, 10, 10, 10, 10, 10, 5, 5, 0},
				{0, 5, 10, 5, 5, 10, 5, 5, 5, 10, 5, 5, 5, 5, 5, 5, 5, 10, 5, 5, 0},
				{0, 5, 10, 5, 5, 10, 5, 5, 5, 10, 5, 5, 5, 5, 5, 5, 5, 10, 10, 10, 10},
				{0, 5, 10, 5, 5, 10, 5, 5, 5, 10, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 0},
				{0, 5, 10, 5, 5, 10, 5, 5, 5, 10, 10, 10, 10, 10, 5, 5, 5, 5, 5, 5, 0},
				{0, 5, 10, 5, 5, 10, 5, 5, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5, 5, 0},
				{0, 5, 10, 5, 5, 10, 5, 5, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5, 5, 0},
				{0, 5, 10, 5, 5, 10, 5, 5, 5, 5, 5, 5, 5, 10, 5, 5, 5, 5, 5, 5, 0},
				{0, 5, 10, 5, 5, 10, 10, 10, 10, 10, 10, 10, 10, 10, 5, 5, 5, 5, 5, 5, 0},
				{10, 10, 10, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 0},
				{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
			};
		HashMap<Integer, Image> tm2 = new HashMap<>();
		tm2.put(0,  Images.tileBoth);
		tm2.put(1, Images.tileNormal1);
		tm2.put(2, Images.tileUnplaceable);
		maps.add(new Map(m2, tm2, new cpp.pii(0, 11), new cpp.pii(20, 4), Images.map1Preview, Images.map1));
		
	}
	
	public static Map getMap(int i) {
		return maps.get(i);
	}
	
	public static int numOfAvaiableMaps() {
		return maps.size();
	}
}
