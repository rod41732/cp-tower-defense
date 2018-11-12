package util;

import model.Monster;
import model.Tower;

public class GameUtil {

	
	public static double distance(Tower t, Monster m) {
		return Math.sqrt(Math.pow(t.getCellX()-m.getCellX(), 2) + Math.pow(t.getCellY()-m.getCellY(), 2));
	}
}
