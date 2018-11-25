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
	public Handler handler;
	
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
		towerManager = new TowerManager(this);
		handler = new Handler(this);
		MonsterSpawner.getInstace().bindTo(this);
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
		handler.setTowerChoice(-1);
		selectedTile = null;
	}
	
	
	public boolean isInitialized() {
		return isInitialized;
	}
	
	
	

	public Tower createTower(int towerChoice, int x, int y) {
		return towerManager.createTower(towerChoice, x, y);
	}

	public void updateMousePos(GameManager gm, double x, double y) {
		handler.updateMousePos(gm, x, y);
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
	
	public void handleKeyPress(KeyEvent e) {
		handler.handleKeyPress(e);
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

	public void addMoney(int i) {
		money += i;
	}

	public void render() {
		renderer.render();
	}
}
