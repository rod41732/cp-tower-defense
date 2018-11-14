package controller;


import java.util.ArrayList;

import constants.Images;
import constants.Numbers;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import model.Tile;
import model.BombTower;
import model.monster.GroundMonster;
import model.projectile.NormalProjectile;
import util.Algorithm;
import util.GameUtil;
import util.cpp;
import util.cpp.pii;

public class GameManager {
	
	private static GameManager instance = new GameManager();
	private boolean isRunning = false;
	private boolean isPaused = false;
	private double selX, selY = 0;
	private int tileX = 0, tileY = 0;
	private int renderTickCount = 0;
	private int money = 1000;
	private String message = "";
	private Tile selectedTile;
	private int startRow = 5, startCol = 0, endRow = 5, endCol = 19;
	
	
	private ArrayList<BombTower> towers = new ArrayList<>();
	private ArrayList<GroundMonster> monsters = new ArrayList<>();
	private ArrayList<Tile> tiles = new ArrayList<>();
	private ArrayList<NormalProjectile> projectiles = new ArrayList<>(); 
	private int[][] tileState = new int[100][100]; // TODO: Fix yolo allocation
	private cpp.pii[][] path;

	
	
	public GameManager() {
		try {
			path = Algorithm.BFS(tileState, endCol, endRow, startCol, startRow);
		}
		catch (Exception e) {
			System.out.println("this shouldn't happen");
		}
		for (int i=0; i<Numbers.WIN_WIDTH; i+=Numbers.TILE_SIZE) {
			for (int j=0; j<Numbers.WIN_HEIGHT; j+=Numbers.TILE_SIZE) {
				int ix = (int)((i)/Numbers.TILE_SIZE), iy = (int)((j)/Numbers.TILE_SIZE);
				if ((ix+iy)%2 == 0) tiles.add(new Tile(Images.tile1, ix+0.5, iy+0.5));
				else if ((ix+iy)%2 != 0) tiles.add(new Tile(Images.tile2, ix+0.5, iy+0.5));
			}
		}
	}
	
	public void update() {
		if (!isPaused) {
			for (BombTower t: towers) {
				for (GroundMonster m: monsters) {
					t.tryTarget(m);
				}
				t.fire();
			}
			for (int i=monsters.size()-1; i>=0; i--) {
				if (monsters.get(i).isDead())
					monsters.remove(i);
			}
			for (GroundMonster m: monsters) {
				m.move();
				if (Double.compare(m.distanceTo(endCol, endRow), 0.1) < 0) {
					m.forceKill();
					message = "a monster reached end";
				}
			}
			for (int i=projectiles.size()-1; i>=0; i--) {
				NormalProjectile p = projectiles.get(i);
				p.move();
				if (p.isExpired()) projectiles.remove(i);
				for (GroundMonster m: monsters) {
					if (p.collideWith(m)) {
						projectiles.remove(i);
						break;
					}
				}
			}
		}
	}
	
	
	public void render(GraphicsContext gc) {
		renderTickCount += 1;
	/// rendering
		gc.fillRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		gc.setGlobalAlpha(1);
		for (Tile t: tiles) 
			if (!t.getPosition().containedBy(new cpp.pii(tileX, tileY)))t.render(gc);
		for (BombTower t: towers) t.render(gc);
		for (GroundMonster m: monsters) m.render(gc);
		for (NormalProjectile p: projectiles) p.render(gc);
		
		
		if (path != null) {
			gc.setFill(new Color(0, 0, 0, 0.5)); // just dim
			cpp.pii pos = new cpp.pii(startCol, startRow);
			while (pos.first != endCol || pos.second!= endRow) {
				gc.fillRect(pos.first*Numbers.TILE_SIZE, pos.second*Numbers.TILE_SIZE,
						Numbers.TILE_SIZE, Numbers.TILE_SIZE);
				pos = path[pos.first][pos.second];
			}
			gc.setFill(Color.BLACK);
		}
		
		gc.setFill(Color.MAGENTA);
		gc.setStroke(Color.BLACK);
		gc.setFont(Font.font("Consolas", 20));
		gc.fillText("last msg:" + message, 20, 140);
		gc.fillText("Selected " + selX + "," + selY, 20, 60);
		gc.fillText("Money = " + money, 20, 100);
		 
	}


	public void updateSelection(double x, double y) {
		selX = x/Numbers.TILE_SIZE;
		selY = y/Numbers.TILE_SIZE;
		tileX = (int)selX;
		tileY = (int)selY;
	}
	
	
	
	public void buildTower(int x, int y) {
		try {
			pii currentTile = new pii(x, y);
			if (tileState[x][y] > 0) {
				for (BombTower t: towers) {
					if (t.getPosition().containedBy(currentTile)) {
						selectedTile = t;
						break;
					}
				}
				((BombTower)selectedTile).upgrade();
				return ;
			}
			System.out.println("try to add tower to" + x +"." + y);
			tileState[x][y] = 1;
			Algorithm.BFS(tileState.clone(), endCol, endRow, startCol, startRow);
			towers.add(new BombTower(Images.tower1, x+0.5, y+0.5, 0, 100, 2.5));
			message = "OK";
		}
		catch (Exception e) {
			tileState[x][y] = 0;
			money += 1500;
			System.out.println("You can't build here");
			message = "You can't build there";
		}
		finally {
			try {
				path = Algorithm.BFS(tileState, endCol, endRow, startCol, startRow);
			}
			catch(Exception e) {
				// this shouldn't happen
			}
		}
	}

	public void spawnMonster(double x, double y) {
		monsters.add(new GroundMonster("Bear", Images.bear, x, y, 0.4, 100, 0, 1.5));
	}
	
	
	public void addMoney(int amount) {
		money += amount;
	}
	

	
	public static GameManager getInstance() {
		return instance;
	}
	
	public double getSelX() {
		return selX;
	}

	public void setSelX(int selX) {
		this.selX = selX;
	}

	public double getSelY() {
		return selY;
	}

	public void setSelY(int selY) {
		this.selY = selY;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public cpp.pii getSelectedTile(){
		return new cpp.pii(tileX, tileY);
	}
	
	
	public int getRenderTickCount() {
		return renderTickCount;
	}

	public cpp.pii[][] getPath() {
		return path;
	}

	public void setRunning(boolean isRunning) {
		System.out.println("GameManager: Running state changed: " + isRunning);
		this.isRunning = isRunning;
	}

	public ArrayList<BombTower> getTowers() {
		return towers;
	}

	public ArrayList<GroundMonster> getMonsters() {
		return monsters;
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public ArrayList<NormalProjectile> getBullets() {
		return projectiles;
	}

	public int getEndRow() {
		return endRow;
	}

	public int getEndCol() {
		return endCol;
	}

	
	
	
	
	
}
