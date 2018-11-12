package controller;


import java.util.ArrayList;

import constants.Images;
import constants.Numbers;
import javafx.scene.canvas.GraphicsContext;

public class GameManager {
	
	private static GameManager instance = new GameManager();
	private boolean isRunning = false;
	private int selX = 0;
	private int selY = 0;
	private ArrayList<Integer> towerX = new ArrayList<Integer>();
	private ArrayList<Integer> towerY = new ArrayList<Integer>();
	private int money = 1000;
	
	public GameManager() {
		// init some
	}
	
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
		for (int i=0; i<towerX.size(); i++) {
			gc.drawImage(Images.tower1, towerX.get(i)*Numbers.TILE_SIZE,
					towerY.get(i)*Numbers.TILE_SIZE);
		}
		gc.fillText("Selected " + selX + "," + selY, 20, 20);
		gc.fillText("Money = " + money, 20, 100);
	}

	public void buildTower(int x, int y) {
		for (int i=0; i<towerX.size(); i++) {
			if (x == towerX.get(i) && y == towerY.get(i)) {
				System.out.printf("Already Built at %d %d\n", x, y);
				return ;
			}
		}
		if (money < 1500) return ;
		money -= 1500;
		towerX.add(x);
		towerY.add(y);
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
