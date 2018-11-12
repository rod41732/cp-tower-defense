package util;

import model.Monster;
import model.Tower;

public class GameUtil {

	
	public static double distance(Tower t, Monster m) {
		return Math.sqrt(Math.pow(t.getX()-m.getX(), 2) + Math.pow(t.getY()-m.getY(), 2));
	}
	
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
	}
	
	public static cpp.pff unitVector(double x1, double y1, double x2, double y2) {
		double dist = distance(x1, y1, x2, y2);
		if (dist < 1e-5) return new cpp.pff(0, 0); 
		return new cpp.pff((x2-x1)/dist, (y2-y1)/dist);
	}
}
