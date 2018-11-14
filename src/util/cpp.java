package util;

public class cpp {
	
	public static class pii {
		public int first = 0, second = 0;
		
		public pii(int fi, int se) {
			first = fi;
			second = se;
		}
		public boolean equals(Object o) {
			if (!(o instanceof pii)) return false;
			pii other = (pii) o;
			return first == other.first && second == other.second;
		}
		
		public String toString() {
			return String.format("I(%d, %d)", first, second);
		}
		
		public pff toF() {
			return new cpp.pff(first, second);
		}
	}
	
//	public pii pii(int fi, se) {
//		return new cpp.pii(fi, se);
//	}
	
	public static class pff {
		public double first = 0, second = 0;
		
		public pff(double fi, double se) {
			first = fi;
			second = se;
		}
		
		public boolean equals(Object o) {
			if (!(o instanceof pff)) return false;
			pff other = (pff) o;
			return first == other.first && second == other.second;
		}
		public String toString() {
			return String.format("F(%.2f, %.2f)", first, second);
		}
		
		public boolean containedBy(cpp.pii tile) {
			return Double.compare(tile.first, first) < 0 &&
					Double.compare(first, tile.first+1) < 0 &&
					Double.compare(tile.second, second) < 0 &&
					Double.compare(second, tile.second+1) < 0;
		}
		
		public pii toI() {
			return new cpp.pii((int)first, (int)second);
		}
	}
	
	public static class xyt { //
		public int x, y, t;
		
		public xyt(int x, int y, int t) {
			this.x = x;
			this.y = y;
			this.t = t;
		}
		
		public String toString() {
			return String.format("xyt(%s, %s, %s)", x, y, t);
		}
	}
}
