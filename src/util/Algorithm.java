package util;

import java.util.LinkedList;
import java.util.Queue;

import constants.Numbers;
import constants.Other;
import controller.GameManager;
import exceptions.PathBlockedException;

public class Algorithm {
	
	private static int TABLE_R = Numbers.ROWS, TABLE_C = Numbers.COLUMNS;
	private static int[][] dist = new int[TABLE_C][TABLE_R]; 
	private static cpp.pii[][] pred = new cpp.pii[TABLE_C][TABLE_R];
	private static Queue<cpp.xyt> q = new LinkedList<cpp.xyt>();
	public static void initialize() {
		for (int i=0; i<TABLE_C; i++) {
			for (int j=0; j<TABLE_R; j++)  {
				dist[i][j] = 10000;				
				pred[i][j] = null;
			}
		}
		q.clear();
	}
	
	
	public static cpp.pii[][] BFS(int fromCol, int fromRow, int toCol, int toRow) throws PathBlockedException {
		// TODO: fix bfs null pointer on init
		initialize();
		GameManager gi = GameManager.getInstance();
		if (gi == null) return pred;
		if (gi.isWalkable(fromCol, fromRow)) {
			q.add(new cpp.xyt(fromCol, fromRow, 0));
			dist[fromCol][fromRow] = 0;		
			pred[fromCol][fromRow] = new cpp.pii(fromCol, fromRow);	
		}
		
//		System.out.println("------------------------------------------------------");
		while (!q.isEmpty()) {
			cpp.xyt top = q.remove();
			int x = top.x, y = top.y, t = top.t;
//			System.out.println("current is " + top);
			for (int[] rc: Other.dir) {
				int nx = x+rc[0], ny = y+rc[1], nt = t+1;
				if (nx < 0 || nx >= TABLE_C || ny < 0 || ny >= TABLE_R) { 
					continue;
				} else if (nt >= dist[nx][ny] || !gi.isWalkable(nx, ny)) { // in bound but not shorted/						
					if (nt <= dist[nx][ny]  && !gi.isWalkable(nx, ny) && nx != toCol && ny != toRow) { // shortest path but on blocked tile 
						// set pred to available (current) tile to fix stuck
						dist[nx][ny] = nt;
						pred[nx][ny] = new cpp.pii(x, y);						
					}
					continue;
				}
				else {
					dist[nx][ny] = nt;
					pred[nx][ny] = new cpp.pii(x, y);
					q.add(new cpp.xyt(nx, ny, nt));					
				}
				
			}
		}
		
		if (pred[toCol][toRow] == null) {
			System.out.printf("pred is %s\n", pred[toCol][toRow]);
			throw new PathBlockedException(); // TODO: Change to my except;
		}
		return pred;
	}
}
