package util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import constants.Other;

public class Algorithm {
	
	private static int TABLE_R = 20, TABLE_C = 20;
	private static int[][] dist = new int[TABLE_R][TABLE_C]; 
	private static cpp.pii[][] pred = new cpp.pii[TABLE_R][TABLE_C]; 
	private static ArrayList<cpp.pii> path = new ArrayList<>();
	private static Queue<cpp.xyt> q = new LinkedList<cpp.xyt>();
	public static void initialize() {
		for (int i=0; i<TABLE_R; i++) {
			for (int j=0; j<TABLE_C; j++)  {
				dist[i][j] = 10000;				
				pred[i][j] = null;
			}
		}
		path.clear();
		q.clear();
	}
	
	public static ArrayList<cpp.pii> BFS(int[][] tileState) throws Exception {
		initialize();
		q.add(new cpp.xyt(0, 0, 0));
		dist[0][0] = 0;
		
		
		while (!q.isEmpty()) {
			System.out.println("in q, xyt");
			cpp.xyt top = q.remove();
			int x = top.x, y = top.y, t = top.t;
			for (int[] rc: Other.dir) {
				int nx = x+rc[0], ny = y+rc[1], nt = t+1;
				if (nx < 0 || nx >= 20 || ny < 0 || ny >= 20 || dist[nx][ny] <= nt
						|| tileState[nx][ny] > 0) continue;
				dist[nx][ny] = nt;
				pred[nx][ny] = new cpp.pii(x, y);
				q.add(new cpp.xyt(nx, ny, nt));
			}
		}
		ArrayList<cpp.pii> path = new ArrayList<>();
		if (pred[10][10] == null) {
			throw new Exception("You don't block path DansGame"); // TODO: Change to my except;
		}
		
		cpp.pii pos = new cpp.pii(10,  10);
		while (pos.first > 0 || pos.second > 0) {
			path.add(pos);
			pos = pred[pos.first][pos.second];
		}
		path.add(new cpp.pii(0, 0));
		return path;
	}
}
