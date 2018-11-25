package controller.game;

import constants.Numbers;
import controller.SuperManager;
import exceptions.PathBlockedException;
import javafx.beans.property.IntegerProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.Tower;
import ui.SnackBar;
import util.Algorithm;
import util.cpp.pff;

public class Handler {
	private GameManager gm;
	public Handler(GameManager gm) {
		this.gm = gm;
	}
	public void updateMousePos(GameManager gm, double x, double y) {
		x -= Numbers.LEFT_OFFSET;
		y -= Numbers.TOP_OFFSET;
		gm.mousePos.first = x/Numbers.TILE_SIZE;
		gm.mousePos.second = y/Numbers.TILE_SIZE;
		// don't want to create new object
		gm.tilePos.first = (int)gm.mousePos.first;
		gm.tilePos.second = (int)gm.mousePos.second;
	}
	public boolean shouldHandle(MouseEvent e) {
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
			handleTileClick((int)x/Numbers.TILE_SIZE, (int)y/Numbers.TILE_SIZE);			
		}
		else if (e.getButton() == MouseButton.SECONDARY) {
			System.out.println("Spawn monster at" + new pff(x/Numbers.TILE_SIZE, x/Numbers.TILE_SIZE));
			gm.updater.spawnMonster(x/Numbers.TILE_SIZE, y/Numbers.TILE_SIZE);
		}
		else {
			gm.towerManager.sellTower();
		}
		return ;
	}
	
	public void handleTileClick(int x, int y) {
		
		try {
			Tower t = gm.towerManager.createTower(gm.getTowerChoice(), x, y);
			gm.handler.setTowerChoice(-1);
			gm.selectedTile = gm.placedTiles[x][y].top();
			// no tower => muse select
			if (t == null) {
				gm.selectedTile = gm.placedTiles[x][y].select();
				return;
			}
	
				// selected => try build
			if (!gm.selectedTile.isPlaceable()) {
				throw new Exception("already something on tile");
			}
			
			if (t.getPrice() > gm.money) {
				throw new Exception("no money");
			}				
			
		
			gm.placedTiles[x][y].push(t);
			Algorithm.BFS(gm.endTilePos.first, gm.endTilePos.second, gm.startTilePos.first, gm.startTilePos.second);
			gm.towers.add(t);
			gm.money -= t.getPrice();
			gm.selectedTile = t;
		}
		catch (PathBlockedException e) {
//			e.printStackTrace();
			gm.selectedTile = null;
			gm.placedTiles[x][y].pop();
			SnackBar.play("path" + e.getMessage());
		}
		catch (NullPointerException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			SnackBar.play("path" +  e.getMessage());
		}
		finally {
			try {
				gm.path = Algorithm.BFS(gm.endTilePos.first, gm.endTilePos.second, gm.startTilePos.first, gm.startTilePos.second);
			}
			catch(Exception e) {
				// this shouldn't happen
			}
		}
		return ;
	}
	public void handleKeyPress(KeyEvent e) {
		
		if (e.getCode() == KeyCode.G) {
			GameManager.getInstance().requestNextWave();			
		} else if (e.getCode() == KeyCode.DIGIT1) {
			GameManager.getInstance().setSelectedTile(null);
			IntegerProperty prop = SuperManager.getInstance().getTowerChoiceProp();
			prop.set(prop.get() == 0 ? -1 : 0);
		} else if (e.getCode() == KeyCode.DIGIT2) {
			GameManager.getInstance().setSelectedTile(null);
			IntegerProperty prop = SuperManager.getInstance().getTowerChoiceProp();
			prop.set(prop.get() == 1 ? -1 : 1);
		} else if (e.getCode() == KeyCode.DIGIT3) {
			GameManager.getInstance().setSelectedTile(null);
			IntegerProperty prop = SuperManager.getInstance().getTowerChoiceProp();
			prop.set(prop.get() == 2 ? -1 : 2);
		} else if (e.getCode() == KeyCode.DIGIT4) {
			GameManager.getInstance().setSelectedTile(null);
			IntegerProperty prop = SuperManager.getInstance().getTowerChoiceProp();
			prop.set(prop.get() == 3 ? -1 : 3);
		} else if (e.getCode() == KeyCode.S) {
			GameManager.getInstance().sellTower();
		} else if (e.getCode() == KeyCode.D) {
			GameManager.getInstance().upgradeTower();
		} else if (e.getCode() == KeyCode.Z) {
			GameManager.getInstance().addMoney(1000);
		}
//		e.consume(); // prevent 'ding' sound 
	}
	
	public void setTowerChoice(int towerChoice) {
		SuperManager.getInstance().getTowerChoiceProp().set(towerChoice);
		return;
	}
}
