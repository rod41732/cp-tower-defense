package controller;


import java.awt.Window;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import constants.Images;
import constants.Numbers;
import constants.Other;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;
import model.StationaryObject;
import model.tower.Tower;
import util.Algorithm;
import util.cpp;

public class GameManager {
	
	private static GameManager instance = new GameManager();
	private boolean isRunning = false;
	private boolean isPaused = false;
	private int selX = 0;
	private int selY = 0;
	private ArrayList<Tower> towers = new ArrayList<>();
	private int[][] tileState = new int[100][100]; // TODO: Fix yolo allocation
	private int money = 1000;
	private String message = "";
	private ArrayList<cpp.pii> path;
	
	public GameManager() {
		try {
			path = Algorithm.BFS(tileState);
		}
		catch (Exception e) {
			System.out.println("this shouldn't happen");
		}
		// init some
	}
	
//	public void tick() {
//
//		if (isRunning && !isPaused) {
//			
//			// breadth-first search
//		}
//		
//	}
	
	public void render(GraphicsContext gc) {
//		System.out.printf("selected %d, %d\n", selX, selY)
//		gc.clip();
		gc.fillRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		gc.setGlobalAlpha(1);
//		System.out.println("Rendering");
		for (int i=0; i<Numbers.WIN_WIDTH; i+=Numbers.TILE_SIZE) {
			for (int j=0 ; j<Numbers.WIN_WIDTH; j+=Numbers.TILE_SIZE) {
				int ix = i/Numbers.TILE_SIZE, iy = j/Numbers.TILE_SIZE;
				if (ix == selX && iy == selY) continue;
				else if ((ix+iy)%2 == 0) {
					gc.drawImage(Images.tile2, i, j);					
				}
				else {
					gc.drawImage(Images.tile1, i, j);
				}
			
			}
		}
		
		for (Tower t: towers) {
			t.render(gc, t.getCellX()*Numbers.TILE_SIZE, t.getCellY()*Numbers.TILE_SIZE);	
		}
		
		if (path != null) {
			gc.setFill(new Color(0, 0, 0, 0.4)); // just dim
			for (cpp.pii c: path) {
				gc.fillRect(c.first*Numbers.TILE_SIZE, c.second*Numbers.TILE_SIZE, Numbers.TILE_SIZE, Numbers.TILE_SIZE);
			}			
			gc.setFill(Color.BLACK);
		}
		
		gc.setFill(Color.MAGENTA);
		gc.setFont(Font.font("Consolas", 20));
		gc.fillText("last msg:" + message, 20, 140);
		gc.fillText("Selected " + selX + "," + selY, 20, 60);
		gc.fillText("Money = " + money, 20, 100);
	}

	public void buildTower(int x, int y) {

		try {
			if (tileState[x][y] > 0) {
				message = "Tower already there";
				return ;
			}
			if (money < 1500) {
				message = "Not enough money (need 1500)";
				return ;
			}
			money -= 1500;
			System.out.println("try to add tower to" + x +"." + y);
			tileState[x][y] = 1;
			path = Algorithm.BFS(tileState);
			towers.add(new Tower(Images.tower1, x, y));
			message = "OK";
		}
		catch (Exception e) {
			tileState[x][y] = 0;
			money += 1500;
			System.out.println("You can't build here");
			message = "You can't build there";
		}
		
	}
	
	public void addMoney(int amount) {
		money += amount;
	}
	
	public static GameManager getInstance() {
		return instance;
	}
	
	public int getSelX() {
		return selX;
	}

	public void setSelX(int selX) {
		this.selX = selX;
	}

	public int getSelY() {
		return selY;
	}

	public void setSelY(int selY) {
		this.selY = selY;
	}

	public void updateSelection(double x, double y) {
		selX = (int)(x)/Numbers.TILE_SIZE;
		selY = (int)(y)/Numbers.TILE_SIZE;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		System.out.println("GameManager: Running state changed: " + isRunning);
		this.isRunning = isRunning;
	}	
	
	
	
}
