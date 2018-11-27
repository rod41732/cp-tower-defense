package util;

import java.util.LinkedList;
import java.util.Queue;

import constants.Numbers;
import constants.Other;
import controller.game.GameManager;
import exceptions.PathBlockedException;

public class Algorithm2 {
	
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
	
	
	public static cpp.pii[][] BFS(int fromCol, int fromRow, int toCol, int toRow, cpp.pii block) throws PathBlockedException {
		initialize();
		GameManager gm = GameManager.getInstance();
		if (gm == null) return pred;
		if (gm.isWalkable(fromCol, fromRow) && (block.first != fromCol || block.second != fromRow)) {
			q.add(new cpp.xyt(fromCol, fromRow, 0));
			dist[fromCol][fromRow] = 0;	
			pred[fromCol][fromRow] = new cpp.pii(fromCol, fromRow);
		}
		else {
			throw new PathBlockedException();
		}
		while (!q.isEmpty()) {
			cpp.xyt top = q.remove();
			int x = top.x, y = top.y, t = top.t;
			for (int[] rc: Other.dir) {
				int nx = x+rc[0], ny = y+rc[1], nt = t+1;
				if (nx < 0 || nx >= TABLE_C || ny < 0 || ny >= TABLE_R) { 
					continue;
				} else if (nt >= dist[nx][ny] || !gm.isWalkable(nx, ny) || (nx == block.first && ny == block.second)) { // in bound but not shorted/						
					if (nt <= dist[nx][ny]  && (!gm.isWalkable(nx, ny) || (nx == block.first && ny==block.second )) 
							&& nx != toCol && ny != toRow) { 
						dist[nx][ny] = nt;
						pred[nx][ny] = new cpp.pii(x, y);						
					}
					continue;
				}
				else  {
					dist[nx][ny] = nt;
					pred[nx][ny] = new cpp.pii(x, y);
					q.add(new cpp.xyt(nx, ny, nt));					
				}
				
			}
		}
		
		if (pred[toCol][toRow] == null) {
			throw new PathBlockedException(); 
		}
		return pred;
	}
}
