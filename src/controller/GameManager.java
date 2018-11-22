package controller;


import java.util.ArrayList;

import constants.Images;
import constants.Numbers;
import exceptions.FullyUpgradedException;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Toggle;
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

	private cpp.pff mousePos = new cpp.pff(0, 0);
	private cpp.pii tilePos = new cpp.pii(0, 0);
	private int money;
	private Tile selectedTile;
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
	private RichTextBox infoBox;
	private GraphicsContext tileGC, otherGC;
	
	public void setGC(GraphicsContext tileGC, GraphicsContext otherGC) {
		this.tileGC = tileGC;
		this.otherGC = otherGC;
	}
	
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
		MonsterSpawner.getInstace().cancelWave();
		money = 0;
		selectedTile = null;
		ArrayList<Image> imgs= new ArrayList<>();
		ArrayList<String> texts = new ArrayList<>();
		imgs.add(Images.cooldownIcon);
		imgs.add(Images.coinIcon);
		imgs.add(Images.liveIcon);
		texts.add("Level 99999");
		texts.add("$" + 999999);
		texts.add("lives" + 999999);
		infoBox = new RichTextBox(imgs, texts, 20, 20);
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
					placedTiles[i][j] = new TileStack(t);
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
	
	public void update() {
		SuperManager.getInstance().getCanUpgradeProp().set(selectedTile != null 
				&& ((Tower)selectedTile).getUpgradePrice() <= money && ((Tower)selectedTile).getUpgradePrice() >= 0);
		SuperManager.getInstance().getCanSellProp().set(selectedTile != null);
		SuperManager.getInstance().getnextWaveAvailableProp().set(shouldSpawnNextWave());
		
		for (Particle p: particles) p.onTick();
		for (Tower t: towers) t.onTick();
		int n = projectiles.size();
		for (int i=n-1; i>=0; i--) {
			Projectile p = projectiles.get(i);
			p.onTick();
		}
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
	
	
	
	public void render(GraphicsContext otherGC, GraphicsContext tileGC, GraphicsContext overlayGC) {

		otherGC.clearRect(0, 0, Numbers.WIN_WIDTH, Numbers.WIN_HEIGHT);
		otherGC.setGlobalAlpha(1);
		for (TileStack[] col: placedTiles)
			for (TileStack ts: col)
				ts.render(otherGC, tileGC);
		for (Monster m: monsters) m.render(otherGC);
		for (Projectile p: projectiles) p.render(otherGC);
		for (Particle p: particles) p.render(otherGC);
		
		if (SuperManager.getInstance().getTowerChoiceProp().get() == -1) {
			if (path != null) {
				tileGC.setFill(new Color(0, 0, 0, 0.5)); // just dim
				// need to copy
				cpp.pii pos = new cpp.pii(startTilePos.first, startTilePos.second);
				while (pos != null && !pos.equals(endTilePos)) {
					tileGC.fillRect(pos.first*Numbers.TILE_SIZE, pos.second*Numbers.TILE_SIZE,
							Numbers.TILE_SIZE, Numbers.TILE_SIZE);
					pos = path[pos.first][pos.second];
				}
				tileGC.fillRect(pos.first*Numbers.TILE_SIZE, pos.second*Numbers.TILE_SIZE,
						Numbers.TILE_SIZE, Numbers.TILE_SIZE);
			}			
		}
		else {
			int choice = SuperManager.getInstance().getTowerChoiceProp().get();
			Tower floatingTower = createTower(choice, tilePos.first, tilePos.second);
			if (floatingTower.getX() < Numbers.COLUMNS && floatingTower.getY() < Numbers.ROWS) {
				floatingTower.render(otherGC, true);							
			}
		}
		
		
		
		otherGC.setFill(Color.MAGENTA);
		otherGC.setStroke(Color.BLACK);
		infoBox.getTexts().set(0, "level" + (int)Math.random()*4);
		infoBox.getTexts().set(1, "$ " + money);
		infoBox.getTexts().set(2, lives + " lives");
		infoBox.render(otherGC);
		
		otherGC.setFont(Font.font("Consolas", 20));;
		TowerMenu.render(otherGC);
		PauseMenu.render(overlayGC);
		SnackBar.render(otherGC);
	}


	public void updateMousePos(double x, double y) {
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
	public boolean canUpgrade() {
		return selectedTile != null && ((Tower)selectedTile).getPrice() <= money;
	}
	public boolean canSell() {
		return selectedTile != null;
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
		setTowerChoice(-1);
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
//			e.printStackTrace();
			System.out.println("can't remove tower, this shouldn't happen");
		}
	}
	

	public void handleTileClick(int x, int y) {

		try {
			Tower t = createTower(getTowerChoice(), x, y);
			setTowerChoice(-1);
			selectedTile = placedTiles[x][y].top();

			// no tower => muse select
			if (t == null) {
				selectedTile = placedTiles[x][y].select();
				return;
			}

				// selected => try build
			if (!selectedTile.isPlaceable()) {
				throw new Exception("already something on tile");
			}
			
			if (t.getPrice() > money) {
				throw new Exception("no money");
			}				
			
		
			placedTiles[x][y].push(t);
			Algorithm.BFS(endTilePos.first, endTilePos.second, startTilePos.first, startTilePos.second);
			towers.add(t);
			money -= t.getPrice();
			selectedTile = t;
		}
		catch (Exception e) {
			selectedTile = null;
//			e.printStackTrace();
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
			MonsterSpawner.getInstace().nextWave();			
		}
		else {
			SnackBar.play("Please wait until end of the wave");
		}
	}
	
	public void addProjectile(Projectile p) {
		projectiles.add(p);
	}
	
	
	public void upgradeTower() {
		if (selectedTile != null && canUpgrade()) {
			try {
				Tower twr = (Tower)selectedTile;
				int price = twr.getUpgradePrice();
				twr.upgrade();		
				money -= price;
			}
			catch (FullyUpgradedException e) {
				SnackBar.play("Already Fully upgraded");
			}
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
