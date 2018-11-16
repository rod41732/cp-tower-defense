package controller;


import java.util.ArrayList;

import constants.Images;
import constants.Numbers;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.Main;
import model.Monster;
import model.Particle;
import model.Projectile;
import model.Tile;
import model.Tower;
import model.monster.FlyingMonster;
import model.monster.GroundMonster;
import model.projectile.NormalProjectile;
import model.tower.BombTower;
import model.tower.FireTower;
import model.tower.IceTower;
import model.tower.NormalTower;
import ui.TowerMenu;
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
	private int towerChoice = -1;
	private int startRow = 5, startCol = 0, endRow = 5, endCol = 19;
	private int lives = 200;
	
	private ArrayList<Tower> towers = new ArrayList<>();
	private ArrayList<Monster> monsters = new ArrayList<>();
	private ArrayList<Tile> tiles = new ArrayList<>();
	private ArrayList<Projectile> projectiles = new ArrayList<>(); 
	private ArrayList<Particle> particles = new ArrayList<>();
	private Tile[][] placedTiles = new Tile[Numbers.COLUMNS][Numbers.ROWS]; // TODO: Fix yolo allocation
	private cpp.pii[][] path = new cpp.pii[Numbers.COLUMNS][Numbers.ROWS];

	public boolean isPlaceable(int x, int y) {
		return placedTiles[x][y] == null || placedTiles[x][y].isPlaceable();
	}
	
	public boolean isWalkable(int x, int y) {
		return placedTiles[x][y] == null || placedTiles[x][y].isWalkable();
	}
	
	public GameManager() {
		try {
			placedTiles = new Tile[Numbers.COLUMNS][Numbers.ROWS];
			path = Algorithm.BFS(endCol, endRow, startCol, startRow);
		}
		catch (Exception e) {
			e.printStackTrace();
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
		// update entity
		for (Particle p: particles) p.onTick();
		for (Tower t: towers) t.onTick();
		for (Projectile p: projectiles) p.onTick();
		for (Monster m: monsters) {
			m.onTick();
			if (m.getPosition().containedBy(new cpp.pii(endCol, endRow))) {
				m.forceKill();
				lives -= 1;
				message = "a monster reached end";
			}
		}
		
		// entity interaction
		for (int i=projectiles.size()-1; i>=0; i--) {
			Projectile p = projectiles.get(i);
			if (p.isExpired()) projectiles.remove(i);
			else
			for (Monster m: monsters) {
				if (p.collideWith(m)) {
					projectiles.remove(i);
					break;
				}
			}
		}
		
		// cleanUp
		for (int i=monsters.size()-1; i>=0; i--) {
			if (monsters.get(i).isDead()) {
				money += monsters.get(i).getMoney();
				monsters.remove(i);				
			}
		}
		for (int i=particles.size()-1; i>=0; i--) {
			if (particles.get(i).isExpired()) {
				particles.remove(i);
			}
		}
	}
	
	
	public boolean isPaused() {
		return isPaused;
	}

	public void render(GraphicsContext gc) {
		renderTickCount += 1;
		Main.gameScene.next.setDisable(!shouldSpawnNextWave());
	/// rendering
		gc.fillRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		gc.setGlobalAlpha(1);
		for (Tile t: tiles) 
			if (!t.getPosition().containedBy(new cpp.pii(tileX, tileY))) 
				t.render(gc);
		for (Tower t: towers) t.render(gc);
		for (Monster m: monsters) m.render(gc);
		for (Projectile p: projectiles) p.render(gc);
		for (Particle p: particles) p.render(gc);
		
		if (path != null) {
			gc.setFill(new Color(0, 0, 0, 0.5)); // just dim
			cpp.pii pos = new cpp.pii(startCol, startRow);
			while (pos != null && (pos.first != endCol || pos.second != endRow)) {
				gc.fillRect(pos.first*Numbers.TILE_SIZE, pos.second*Numbers.TILE_SIZE,
						Numbers.TILE_SIZE, Numbers.TILE_SIZE);
				pos = path[pos.first][pos.second];
			}
			gc.setFill(Color.BLACK);
		}
		
		gc.setFont(Font.font("Consolas", 12));
		for (int i=0; i<Numbers.COLUMNS; i++)
			for (int j=0; j<Numbers.ROWS; j++)
				gc.fillText(String.format("%s\n%s\n", new cpp.pii(i, j), path[i][j]),
						i*Numbers.TILE_SIZE, j*Numbers.TILE_SIZE+16);
		
		
		if (towerChoice != -1) {
			Tower floatingTower = null;
			int sx = getSelectedPosition().first, sy = getSelectedPosition().second;
			if (towerChoice == 0) {
				floatingTower = new BombTower(Images.bombTower , sx+0.5, sy+0.5, 10, 800, 2.5);				
			}
			else if (towerChoice == 1){
				floatingTower = new NormalTower(Images.normalTower ,sx+0.5, sy+0.5, 3, 100, 4.5);
			}
			else if (towerChoice == 2) {
				floatingTower = new FireTower(Images.fireTower, sx+0.5, sy+0.5, 10, 2000, 5);
			}
			else if (towerChoice == 3) {
				floatingTower = new IceTower(Images.iceTower ,sx+0.5, sy+0.5, 10, 2000, 4);
			}
			if (floatingTower != null) {
				floatingTower.render(gc, true);				
			}
			floatingTower = null;
		}
		
		gc.setFill(Color.MAGENTA);
		gc.setStroke(Color.BLACK);
		gc.setFont(Font.font("Consolas", 20));
		gc.fillText("Selected " + selX + "," + selY, 20, 60);
		gc.fillText("Money = " + money, 20, 100);
		gc.fillText("selcted Tower = " + selectedTile , 20, 120);
		gc.fillText("last msg:" + message, 20, 140);
		gc.fillText("Lives = " + lives , 20, 160);
		TowerMenu.render(gc);
	}


	public void updateMousePos(double x, double y) {
		selX = x/Numbers.TILE_SIZE;
		selY = y/Numbers.TILE_SIZE;
		tileX = (int)selX;
		tileY = (int)selY;
	}
	
	
	private boolean shouldHandle(MouseEvent e) {
		double x = e.getX();
		double y = e.getY();
		return 0 <= x && x <= Numbers.COLUMNS*Numbers.TILE_SIZE &&
				0 <= y && y <= Numbers.ROWS*Numbers.TILE_SIZE ;
	}
	
	public void handleClick(MouseEvent e) {
		if (e.isConsumed()) return;
		if (!shouldHandle(e)) return ;
		if (e.getButton() == MouseButton.PRIMARY) {
			handleTileClick((int)(e.getX()/Numbers.TILE_SIZE), (int)(e.getY()/Numbers.TILE_SIZE));			
		}
		else if (e.getButton() == MouseButton.SECONDARY) {
			System.out.println("Spawn monster at" + new cpp.pff(e.getX()/Numbers.TILE_SIZE, e.getY()/Numbers.TILE_SIZE));
			spawnMonster(e.getX()/Numbers.TILE_SIZE, e.getY()/Numbers.TILE_SIZE);
		}
		else {
			removeTower((int)(e.getX()/Numbers.TILE_SIZE), (int)(e.getY()/Numbers.TILE_SIZE));
		}
		return ;
	}
	
	public void sellTower() {
		if (selectedTile == null) return;
		Tower t = (Tower)selectedTile;
		money += t.getValue();
		cpp.pii pos = t.getPosition().toI();
		removeTower(pos.first, pos.second);
		selectedTile = null;
		towerChoice = -1;
	}
	
	public void removeTower(int x, int y) {
		try {
			pii currentTile = new pii(x, y);
			for (int i=towers.size()-1; i>=0; i--)
				if (towers.get(i).getPosition().containedBy(currentTile)) {
					towers.remove(i);
					message = "removed";
					break;
				}
			placedTiles[x][y] = null;
			path = Algorithm.BFS(endCol, endRow, startCol, startRow);
		}
		catch (Exception e) {
			System.out.println("can't remove tower, this shouldn't happen");
		}
		path[endCol][endRow] = new cpp.pii(endCol+1, endRow+1);
	}
	
	
	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
	}

	public void handleTileClick(int x, int y) {
		selectedTile = placedTiles[x][y];
		if (selectedTile != null)
			return ;
		if (towerChoice < 0) {
			message = "Please select a tower";
			return ;
		}
		System.out.println("try to add tower to" + x +"." + y);
		Tower t;
		switch (towerChoice) {
			case 0:				
				t = new BombTower(Images.bombTower ,x+0.5, y+0.5, 10, 800, 2.5); break;
			case 1:
				t = new NormalTower(Images.normalTower ,x+0.5, y+0.5, 3, 100, 4.5); break;
			case 2:
				t = new FireTower(Images.fireTower, x+0.5, y+0.5, 10, 2000, 5); break;
			case 3:
				t = new IceTower(Images.iceTower, x+0.5, y+0.5, 4, 500, 5); break;
			default:
				return;
		}
		towerChoice = -1;
		try {
			placedTiles[x][y] = t;
			Algorithm.BFS(endCol, endRow, startCol, startRow);
			message = "OK";
			towers.add(t);
		}
		catch (Exception e) {
			placedTiles[x][y] = null;
			System.out.println("You can't build here");
			message = "You can't build there";
		}
		finally {
			try {
				path = Algorithm.BFS(endCol, endRow, startCol, startRow);
				path[endCol][endRow] = new cpp.pii(endCol+1, endRow);
			}
			catch(Exception e) {
				// this shouldn't happen
			}
		}
		return ;
	}
	public void spawnParticle(Particle p) {
		particles.add(p);
	}
	
	public void spawnMonster(double x, double y) {
		monsters.add(new FlyingMonster("Bear", Images.bear, x, y, 0.4, 60, 0, 1.5, 10));
	}
	
	public void spawnMonster(Monster m) {
		monsters.add(m);
	}
	
	public boolean shouldSpawnNextWave() {
		return MonsterSpawner.getInstace().isReady() && monsters.size() == 0;
	}
	
	public void requestNextWave() {
		if (shouldSpawnNextWave())
			MonsterSpawner.getInstace().play();
	}
	
	public void upgradeTower() {
		if (selectedTile != null) {
			((Tower)selectedTile).upgrade();
		}
	}
	
	public cpp.pii getSelectedPosition(){
		return new cpp.pii(tileX, tileY);
	}

	public Tile getSelectedTile() {
		return selectedTile;
	}

	public void setSelectedTile(Tile selectedTile) {
		this.selectedTile = selectedTile;
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

	
	
	
	
	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getStartCol() {
		return startCol;
	}

	public void setStartCol(int startCol) {
		this.startCol = startCol;
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

	public ArrayList<Tower> getTowers() {
		return towers;
	}

	public ArrayList<Monster> getMonsters() {
		return monsters;
	}

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public ArrayList<Projectile> getBullets() {
		return projectiles;
	}
	
	

	public int getTowerChoice() {
		return towerChoice;
	}

	public void setTowerChoice(int towerChoice) {
		selectedTile = null;
		this.towerChoice = towerChoice;
	}

	public int getEndRow() {
		return endRow;
	}

	public int getEndCol() {
		return endCol;
	}

	
	
	
	
	
}
