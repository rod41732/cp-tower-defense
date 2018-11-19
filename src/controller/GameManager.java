package controller;


import java.util.ArrayList;

import constants.Images;
import constants.Numbers;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.Main;
import model.Monster;
import model.Particle;
import model.Projectile;
import model.Tile;
import model.TileStack;
import model.Tower;
import model.monster.SplittingMonster;
import model.tile.Barricade;
import model.tower.BombTower;
import model.tower.FireTower;
import model.tower.IceTower;
import model.tower.NormalTower;
import ui.PauseMenu;
import ui.RichTextBox;
import ui.SnackBar;
import ui.TowerMenu;
import util.Algorithm;
import util.cpp;

public class GameManager {
	
	private static GameManager instance = new GameManager();
	
	// TODO: refactor field names
	private boolean isPaused;
	private cpp.pff mousePos = new cpp.pff(0, 0);
	private cpp.pii tilePos = new cpp.pii(0, 0);
	private int money;
	private Tile selectedTile;
	private int towerChoice;
	private cpp.pii startTilePos;
	private cpp.pii endTilePos;
	private int lives;
	private boolean isInitialized;
	
	private ArrayList<Tower> towers = new ArrayList<>();
	private ArrayList<Monster> monsters = new ArrayList<>();
	private ArrayList<Tile> tiles = new ArrayList<>();
	private ArrayList<Projectile> projectiles = new ArrayList<>(); 
	private ArrayList<Particle> particles = new ArrayList<>();
	private TileStack[][] placedTiles = new TileStack[Numbers.COLUMNS][Numbers.ROWS];
	private cpp.pii[][] path = new cpp.pii[Numbers.COLUMNS][Numbers.ROWS];

	public boolean isPlaceable(int x, int y) {
		return placedTiles[x][y] == null || placedTiles[x][y].isPlaceable();
	}
	
	public boolean isWalkable(int x, int y) {
		return placedTiles[x][y] == null || placedTiles[x][y].isWalkable();
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
		MonsterSpawner.getInstace().stop();
		money = 0;
		towerChoice = -1;
		selectedTile = null;
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
		MonsterSpawner.getInstace().stop();
		money = 0;
		towerChoice = -1;
		selectedTile = null;
	}
	
	
	public boolean isInitialized() {
		return isInitialized;
	}

	public void initialize() {
		if (isInitialized) return ;
		try {
			isInitialized = true;
			money = 1000;
			lives = 200;
			startTilePos = new cpp.pii(0, 5);
			endTilePos = new cpp.pii(19, 5);
			for (int i=0;i<Numbers.COLUMNS; i++)
				for (int j=0; j<Numbers.ROWS; j++) {
					Tile t;
					if (Math.random() < 0.05) {
						t = new Barricade(Images.tileUnplaceable, i+0.5, j+0.5);
					}
					else if ((i+j)%2 == 0) {
						t = new Tile(Images.tile1,i+0.5, j+0.5);
					}
					else {
						t = new Tile(Images.tile2, i+0.5, j+0.5);
					}
					System.out.printf("%d,%d -> %s\n", i, j, t);
					placedTiles[i][j] = new TileStack(t);
				}
			path = Algorithm.BFS(endTilePos.first, endTilePos.second,
					startTilePos.first, startTilePos.second);
			money = 100;
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("this shouldn't happen");
		}
	}
	
	public void update() {
		// update entity
		Main.gameScene.getButtonManager().setAllowNextWave(shouldSpawnNextWave());
		for (Particle p: particles) p.onTick();
		for (Tower t: towers) t.onTick();
		for (Projectile p: projectiles) p.onTick();
		for (Monster m: monsters) {
			m.onTick();
			if (m.getPosition().containedBy(endTilePos)) {
				m.forceKill();
				lives -= 1;
				SnackBar.play("monster reached end");
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
				monsters.get(i).onDeath();
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

	private Tower createTower(int towerChoice, int x, int y) {
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
	
	
	
	public void render(GraphicsContext gc) {
		gc.fillRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		gc.setGlobalAlpha(1);
		for (TileStack[] col: placedTiles)
			for (TileStack ts: col)
				ts.render(gc);
		for (Monster m: monsters) m.render(gc);
		for (Projectile p: projectiles) p.render(gc);
		for (Particle p: particles) p.render(gc);
		
		if (path != null) {
			gc.setFill(new Color(0, 0, 0, 0.5)); // just dim
			// need to copy
			cpp.pii pos = new cpp.pii(startTilePos.first, startTilePos.second);
			while (pos != null && !pos.equals(endTilePos)) {
				gc.fillRect(pos.first*Numbers.TILE_SIZE, pos.second*Numbers.TILE_SIZE,
						Numbers.TILE_SIZE, Numbers.TILE_SIZE);
				pos = path[pos.first][pos.second];
			}
			gc.setFill(Color.BLACK);
		}
		
//		gc.setFont(Font.font("Consolas", 12));
//		for (int i=0; i<Numbers.COLUMNS; i++)
//			for (int j=0; j<Numbers.ROWS; j++)
//				gc.fillText(String.format("%s\n%s\n", new cpp.pii(i, j), path[i][j]),
//						i*Numbers.TILE_SIZE, j*Numbers.TILE_SIZE+16);
		
		
		
		Tower floatingTower = createTower(towerChoice, tilePos.first, tilePos.second);
		if (floatingTower != null) floatingTower.render(gc, true);
		
		
		gc.setFill(Color.MAGENTA);
		gc.setStroke(Color.BLACK);
		
		
		ArrayList<Image> imgs= new ArrayList<>();
		ArrayList<String> texts = new ArrayList<>();
		imgs.add(Images.cooldownIcon);
		imgs.add(Images.coinIcon);
		imgs.add(Images.liveIcon);
//		imgs.add(Images.normalTower);
		texts.add("Level 1");
		texts.add("$" + money);
		texts.add("lives" + lives);
//		texts.add(String.format("Pos %.2f, .2f = %.2f, %.2f\n Tower = %s", mousePos.first, mousePos.second, 
//				mousePos.first*Numbers.TILE_SIZE, mousePos.second*Numbers.TILE_SIZE, selectedTile));
		RichTextBox info = new RichTextBox(imgs, texts, 20, 20);
		info.setAlignRight(true);
		info.render(gc);
		
		gc.setFont(Font.font("Consolas", 20));;
		TowerMenu.render(gc);
		PauseMenu.render(gc);
		SnackBar.render(gc);
	}


	public void updateMousePos(double x, double y) {
		mousePos.x = x/Numbers.TILE_SIZE;
		mousePos.second = y/Numbers.TILE_SIZE;
		// don't want to create new object
		tilePos.first = (int)mousePos.x;
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
		if (e.getButton() == MouseButton.PRIMARY) {
			handleTileClick((int)(e.getX()/Numbers.TILE_SIZE), (int)(e.getY()/Numbers.TILE_SIZE));			
		}
		else if (e.getButton() == MouseButton.SECONDARY) {
			System.out.println("Spawn monster at" + new cpp.pff(e.getX()/Numbers.TILE_SIZE, e.getY()/Numbers.TILE_SIZE));
			spawnMonster(e.getX()/Numbers.TILE_SIZE, e.getY()/Numbers.TILE_SIZE);
		}
		else {
			sellTower();
		}
		return ;
	}
	
	public void sellTower() {
		if (selectedTile == null) return;
		// TODO : spaghetti
		if (!(selectedTile instanceof Tower)) return ;
		Tower t = (Tower)selectedTile;
		money += t.getPrice()/2;
		cpp.pii pos = t.getPosition().toI();
		removeTower(pos.first, pos.second);
		selectedTile = null;
		towerChoice = -1;
	}
	
	public void removeTower(int x, int y) {
		try {
			Tile t = placedTiles[x][y].top();
			if (!t.isSelectable()) return ; // don't remove that dansgame
			placedTiles[x][y].pop();
			if (t instanceof Tower) {
				for (int i=0; i<towers.size(); i++)
					if (t == towers.get(i)) {
						towers.remove(i);
						break;
					}
			}
			path = Algorithm.BFS(endTilePos.first, endTilePos.second, startTilePos.first, startTilePos.second);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("can't remove tower, this shouldn't happen");
		}
	}
	
	
	public void pause() {
		this.isPaused = true;
		PauseMenu.show();
		MonsterSpawner.getInstace().pause();
	}
	
	public void resume() {
		this.isPaused = false;
		PauseMenu.hide();
		MonsterSpawner.getInstace().resume();
	}
	
	public void leaveGame() {
		pause();
		PauseMenu.hide();
	}
	
	public void handleTileClick(int x, int y) {

		try {

			Tower t = createTower(towerChoice, x, y);
			towerChoice = -1;
			selectedTile = placedTiles[x][y].top();

			// no select => muse select
			if (t == null) {
				if (!selectedTile.isSelectable()) {
					selectedTile = null;
				}
				return;
			}
			// selected => try build
			if (!selectedTile.isPlaceable()) {
				throw new Exception("already something on tile");
			}
			
			if (t.getPrice() > money) {
				throw new Exception("no money");
			}
			
		

			System.out.println("tried + " + t);
			placedTiles[x][y].push(t);
			Algorithm.BFS(endTilePos.first, endTilePos.second, startTilePos.first, startTilePos.second);
			towers.add(t);
			money -= t.getPrice();
		}
		catch (Exception e) {
			if (e.getMessage().charAt(0) == 'Y') // "Y" ou don't block path 
				placedTiles[x][y].pop();
			SnackBar.play(e.getMessage());
		}
		finally {
			try {
				path = Algorithm.BFS(endTilePos.first, endTilePos.second, startTilePos.first, startTilePos.second);
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
		monsters.add(new SplittingMonster("Eleplant", Images.elephant, x, y, 0.4, 60, 0, 1.5, 10));
	}
	
	public void spawnMonster(Monster m) {
		monsters.add(m);
	}
	
	public boolean shouldSpawnNextWave() {
		return MonsterSpawner.getInstace().isReady() && monsters.size() == 0;
	}
	
	public void requestNextWave() {
		try {
			path = Algorithm.BFS(endTilePos.first, endTilePos.second, startTilePos.first, startTilePos.second);			
		}
		catch (Exception e) {
			System.out.println("nextwave: path not found");
		}
		if (shouldSpawnNextWave()) {
			MonsterSpawner.getInstace().play();			
		}
		else {
			SnackBar.play("Please wait until end of the wave");
		}
	}
	
	
	public void upgradeTower() {
		if (selectedTile != null) {
			((Tower)selectedTile).upgrade();
		}
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

	public ArrayList<Tile> getTiles() {
		return tiles;
	}

	public ArrayList<Projectile> getProjectiles() {
		return projectiles;
	}
	
	

	public cpp.pii getStartTilePos() {
		return startTilePos;
	}

	public cpp.pii getEndTilePos() {
		return endTilePos;
	}

	public int getTowerChoice() {
		return towerChoice;
	}

	public void setTowerChoice(int towerChoice) {
		selectedTile = null;
		this.towerChoice = towerChoice;	
	}
}
