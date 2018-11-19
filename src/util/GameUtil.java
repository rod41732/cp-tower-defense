package util;

import model.Entity;

public class GameUtil {

	
	public static double distance(Entity e1, Entity e2) {
		return Math.sqrt(Math.pow(e1.getX()-e2.getX(), 2) + Math.pow(e1.getY()-e2.getY(), 2));
	}
	
	public static double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2));
	}
	
	public static cpp.pff unitVector(double x1, double y1, double x2, double y2) {
		double dist = distance(x1, y1, x2, y2);
		if (dist < 1e-5) return new cpp.pff(0, 0); 
		return new cpp.pff((x2-x1)/dist, (y2-y1)/dist);
	}
	
	public static cpp.pff unitVector(Entity e1, Entity e2) {
		double dist = distance(e1.getX(), e1.getY(), e2.getX(), e2.getY());
		if (dist < 1e-5) return new cpp.pff(0, 0); 
		return new cpp.pff((e2.getX()-e1.getX())/dist, (e2.getY()-e1.getY())/dist);
	}
}

