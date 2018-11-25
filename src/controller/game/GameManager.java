package controller.game;


import java.util.ArrayList;
import java.util.HashMap;

import constants.Images;
import constants.Maps;
import constants.Numbers;
import controller.SuperManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import main.Main;
import model.Map;
import model.Monster;
import model.Particle;
import model.Projectile;
import model.Tile;
import model.TileStack;
import model.Tower;
import model.monster.SplittingMonster;
import model.tower.BombTower;
import model.tower.FireTower;
import model.tower.IceTower;
import model.tower.NormalTower;
import util.Algorithm;
import util.cpp;

public class GameManager {
	
	private static GameManager instance = new GameManager();
	
	// Shared in game
	ArrayList<Tower> towers = new ArrayList<>();
	ArrayList<Monster> monsters = new ArrayList<>();
	ArrayList<Tile> tiles = new ArrayList<>();
	ArrayList<Projectile> projectiles = new ArrayList<>(); 
	ArrayList<Particle> particles = new ArrayList<>();
	TileStack[][] placedTiles = new TileStack[Numbers.COLUMNS][Numbers.ROWS];
	cpp.pii[][] path = new cpp.pii[Numbers.COLUMNS][Numbers.ROWS];
	
	cpp.pff mousePos = new cpp.pff(0, 0);
	cpp.pii tilePos = new cpp.pii(0, 0);
	int money;
	Tile selectedTile;
	cpp.pii startTilePos;
	cpp.pii endTilePos;
	int lives;

	private boolean isInitialized;
	private Renderer renderer;
	public Updater updater;
	public TowerManager towerManager;
	
	public boolean boundCheck(int x, int y) {
		return 0 <= x && x < Numbers.COLUMNS && 0 <= y && y < Numbers.ROWS;
	}
	public boolean isPlaceable(int x, int y) {
		return  boundCheck(x, y) && placedTiles[x][y].isPlaceable();
	}
	
	public boolean isWalkable(int x, int y) {
		return boundCheck(x, y) && placedTiles[x][y].isWalkable();
	}
	
	public GameManager() {
		isInitialized = false;
		towers.clear();
		monsters.clear();
		tiles.clear();
		projectiles.clear();
		particles.clear();
		for (int i=0;i < Numbers.COLUMNS; i++)
			for (int j=0; j<Numbers.ROWS; j++)
				placedTiles[i][j] = new TileStack();
		MonsterSpawner.getInstace().cancelWave();
		money = 0;
		selectedTile = null;
		renderer = new Renderer(this);
		updater = new Updater(this);
	}
	
	public void setGC(GraphicsContext otherGC, GraphicsContext tileGC, GraphicsContext overlayGC) {
		this.renderer.setGC(otherGC, tileGC, overlayGC);
	}
	
	public void newGame() { // reset all
		isInitialized = false;
		towers.clear();
		monsters.clear();
		tiles.clear();
		projectiles.clear();
		particles.clear();
		for (int i=0;i < Numbers.COLUMNS; i++)
			for (int j=0; j<Numbers.ROWS; j++)
				placedTiles[i][j] = new TileStack();
		MonsterSpawner.getInstace().cancelWave();
		money = 0;
		setTowerChoice(-1);
		selectedTile = null;
	}
	
	
	public boolean isInitialized() {
		return isInitialized;
	}

	public void loadMap(int mapId) {
		if (isInitialized) return ;
		Map m = Maps.getMap(mapId);
		HashMap<Integer, Image> tileMap = m.getTileMap();
		int[][] tiles = m.getTiles();
		try {
			isInitialized = true;
			lives = 200;
			startTilePos = m.getStart();
			endTilePos = m.getEnd();
			for (int i=0; i<Numbers.COLUMNS; i++)
				for (int j=0; j<Numbers.ROWS; j++) {
					int t = tiles[j][i];
					System.out.printf("%d,%d -> %d%d\n", i, j, t%4&2, t%4%2);
					placedTiles[i][j].push(new Tile(tileMap.get(t/4), i+0.5, j+0.5, ((t%4)&2) > 0, ((t%4)%2) > 0));
				}
			path = Algorithm.BFS(endTilePos.first, endTilePos.second,
					startTilePos.first, startTilePos.second);
			money = 100;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("can't initialize. this shouldn't happen");
		}
	}

	public Tower createTower(int towerChoice, int x, int y) {
		Tower t = null;
		switch (towerChoice) {
		case 0:				
			t = new BombTower(x+0.5, y+0.5); break;
		case 1:
			t = new NormalTower(x+0.5, y+0.5); break;
		case 2:
			t = new FireTower(x+0.5, y+0.5); break;
		case 3:
			t = new IceTower(x+0.5, y+0.5); break;
		}
		return t;
	}
	
	
	
	public void render() {	
		renderer.render();
	}


	public void updateMousePos(double x, double y) {
		x -= Numbers.LEFT_OFFSET;
		y -= Numbers.TOP_OFFSET;
		mousePos.first = x/Numbers.TILE_SIZE;
		mousePos.second = y/Numbers.TILE_SIZE;
		// don't want to create new object
		tilePos.first = (int)mousePos.first;
		tilePos.second = (int)mousePos.second;
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
		double x = e.getX()-Numbers.LEFT_OFFSET;
		double y = e.getY()-Numbers.TOP_OFFSET;
		if (e.getButton() == MouseButton.PRIMARY) {
			towerManager.handleTileClick(this, (int)x/Numbers.TILE_SIZE, (int)y/Numbers.TILE_SIZE);			
		}
		else if (e.getButton() == MouseButton.SECONDARY) {
			System.out.println("Spawn monster at" + new cpp.pff(x/Numbers.TILE_SIZE, x/Numbers.TILE_SIZE));
			spawnMonster(x/Numbers.TILE_SIZE, y/Numbers.TILE_SIZE);
		}
		else {
			towerManager.sellTower(this);
		}
		return ;
	}
	public boolean canUpgrade() {
		return selectedTile != null && ((Tower)selectedTile).getPrice() <= money;
	}
	public boolean canSell() {
		return selectedTile != null;
	}
	
	
	public void spawnParticle(Particle p) {
		particles.add(p);
	}
	
	public void spawnMonster(double x, double y) {
		monsters.add(new SplittingMonster("Eleplant", Images.elephant, x, y, 0.4, 60, 0, 1.5, 10));
	}
	
	public void spawnMonster(Monster m) {
		monsters.add(m);
	}
	
	public boolean shouldSpawnNextWave() {
		return MonsterSpawner.getInstace().isReady() && monsters.size() == 0;
	}
	
	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}
	
	
	public cpp.pii getSelectedPosition(){
		return tilePos;
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

	public cpp.pff getMousePosition() {
		return mousePos;
	}
	

	public cpp.pii[][] getPath() {
		return path;
	}
	
	public ArrayList<Tower> getTowers() {
		return towers;
	}

	public ArrayList<Monster> getMonsters() {
		return monsters;
	}

	

	public TileStack[][] getPlacedTiles() {
		return placedTiles;
	}

	public cpp.pii getStartTilePos() {
		return startTilePos;
	}

	public cpp.pii getEndTilePos() {
		return endTilePos;
	}

	public int getTowerChoice() {
		return SuperManager.getInstance().getTowerChoiceProp().get();
	}

	public void setTowerChoice(int towerChoice) {
		SuperManager.getInstance().getTowerChoiceProp().set(towerChoice);
		return;
	}

	public void addMoney(int i) {
		money += i;
	}
}
