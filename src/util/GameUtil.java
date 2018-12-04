package util;

import java.util.Comparator;

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
	
	
	
	public static cpp.pff rotateVector(double x, double y, double degree) {
		cpp.pff v = new cpp.pff(x*Math.cos(Math.toRadians(degree)) - y*Math.sin(Math.toRadians(degree)),
				x*Math.sin(Math.toRadians(degree)) + y*Math.cos(Math.toRadians(degree)));
		return v;
	}
	
	public static class ZIndexComparator implements Comparator<Entity> {

		@Override
		public int compare(Entity o1, Entity o2) {
			try {
				return Double.compare(((Entity) o1).getzIndex(), ((Entity) o2).getzIndex());				
			}
			catch (NullPointerException e) { // most likely happen when concurrent modification
				return -1;
			}
		}
		
	}
	
	public static int directionIndex(cpp.pii p1, cpp.pii p2) {
		if (p1 == null || p2 == null) return -1;
		if (p1.first > p2.first) return 0;
		if (p1.first < p2.first) return 2;
		if (p1.second < p2.second) return 1;
		if (p1.second > p2.second) return 3;
		return -1;
	}
	
}

