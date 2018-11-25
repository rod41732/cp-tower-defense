package controller.game;


import java.util.ArrayList;
import java.util.HashMap;

import constants.Maps;
import constants.Numbers;
import controller.SuperManager;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import model.Map;
import model.Monster;
import model.Particle;
import model.Projectile;
import model.Tile;
import model.TileStack;
import model.Tower;
import model.monster.GroundMonster;
import util.Algorithm;
import util.cpp;
import util.cpp.pii;

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
	Renderer renderer;
	Updater updater;
	TowerManager towerManager;
	Handler handler;
	
	public GameManager() {
		renderer = new Renderer(this);
		updater = new Updater(this);
		towerManager = new TowerManager(this);
		handler = new Handler(this);
		MonsterSpawner.getInstace().bindTo(this);
		reset();
	}
	
	public void setGC(GraphicsContext otherGC, GraphicsContext tileGC, GraphicsContext overlayGC) {
		this.renderer.setGC(otherGC, tileGC, overlayGC);
	}
	
	public void reset() { // reset all
		isInitialized = false;
		towers.clear();
		monsters.clear();
		tiles.clear();
		projectiles.clear();
		particles.clear();
		selectedTile = null;
		for (int i=0;i < Numbers.COLUMNS; i++)
			for (int j=0; j<Numbers.ROWS; j++)
				placedTiles[i][j] = new TileStack();
		MonsterSpawner.getInstace().cancelWave();
		money = 0;
		handler.setTowerChoice(-1);
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
			System.err.println("can't initialize. this shouldn't happen");
		}
	}
	
	// --------------------------------------- Delegate stuffs 
	public Tower createTower(int towerChoice, int x, int y) {
		return towerManager.createTower(towerChoice, x, y);
	}

	public void updateMousePos(GameManager gm, double x, double y) {
		handler.updateMousePos(gm, x, y);
	}

	public void update() {
		updater.update();
	}

	public void requestNextWave() {
		updater.requestNextWave();
	}

	public void sellTower() {
		towerManager.sellTower();
	}

	public void upgradeTower() {
		towerManager.upgradeTower();
	}

	public void handleClick(MouseEvent e) {
		handler.handleClick(e);
	}

	public void handleTileClick(int x, int y) {
		handler.handleTileClick(x, y);
	}

	public void spawnParticle(Particle p) {
		updater.spawnParticle(p);
	}

	public void addProjectile(Projectile p) {
		updater.addProjectile(p);
	}

	public boolean isPlaceable(int x, int y) {
		return towerManager.isPlaceable(x, y);
	}

	public boolean isWalkable(int x, int y) {
		return towerManager.isWalkable(x, y);
	}

	public static GameManager getInstance() {
		return instance;
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
	
	public void handleKeyPress(KeyEvent e) {
		handler.handleKeyPress(e);
	}
	
	public int getTowerChoice() {
		return SuperManager.getInstance().getTowerChoiceProp().get();
	}
	
	public void render() {
		renderer.render();
	}
	
	public void spawnMonster(Monster m) {
		updater.spawnMonster(m);
	}
	
	// getters 
	public cpp.pii getSelectedPosition() {
		return tilePos;
	}

	public Tile getSelectedTile() {
		return selectedTile;
	}

	public void addMoney(int i) {
		money += i;
	}

	public cpp.pii getStartTilePos() {
		return startTilePos;
	}

	public cpp.pii getEndTilePos() {
		return endTilePos;
	}

	
}

