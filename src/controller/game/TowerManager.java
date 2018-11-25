package controller.game;

import constants.Numbers;
import exceptions.PathBlockedException;
import model.Tile;
import model.Tower;
import model.tower.BombTower;
import model.tower.FireTower;
import model.tower.IceTower;
import model.tower.NormalTower;
import ui.SnackBar;
import util.Algorithm;
import util.cpp;
import util.cpp.pii;

public class TowerManager {

	
	private GameManager game;
	
	public TowerManager(GameManager game) {
		this.game = game;
	}

	public void sellTower(GameManager gameManager) {
		if (gameManager.selectedTile == null) return;
		// TODO : spaghetti
		if (!(gameManager.selectedTile instanceof Tower)) return ;
		Tower t = (Tower)gameManager.selectedTile;
		gameManager.money += t.getPrice()/2;
		pii pos = t.getPosition().toI();
		gameManager.towerManager.removeTower(gameManager, pos.first, pos.second);
		gameManager.selectedTile = null;
		gameManager.handler.setTowerChoice(-1);
	}

	public void removeTower(GameManager gameManager, int x, int y) {
		try {
			Tile t = gameManager.placedTiles[x][y].top();
			if (!t.isSelectable()) return ; // don't remove that dansgame
			gameManager.placedTiles[x][y].pop();
			if (t instanceof Tower) {
				for (int i=0; i<gameManager.towers.size(); i++)
					if (t == gameManager.towers.get(i)) {
						gameManager.towers.remove(i);
						break;
					}
			}
			gameManager.path = Algorithm.BFS(gameManager.endTilePos.first, gameManager.endTilePos.second, gameManager.startTilePos.first, gameManager.startTilePos.second);
		}
		catch (Exception e) {
	//			e.printStackTrace();
			System.out.println("can't remove tower, this shouldn't happen");
		}
	}

	public void handleTileClick(GameManager gameManager, int x, int y) {
	
		try {
			Tower t = gameManager.towerManager.createTower(gameManager.getTowerChoice(), x, y);
			gameManager.handler.setTowerChoice(-1);
			gameManager.selectedTile = gameManager.placedTiles[x][y].top();
	
			// no tower => muse select
			if (t == null) {
				gameManager.selectedTile = gameManager.placedTiles[x][y].select();
				return;
			}
	
				// selected => try build
			if (!gameManager.selectedTile.isPlaceable()) {
				throw new Exception("already something on tile");
			}
			
			if (t.getPrice() > gameManager.money) {
				throw new Exception("no money");
			}				
			
		
			gameManager.placedTiles[x][y].push(t);
			Algorithm.BFS(gameManager.endTilePos.first, gameManager.endTilePos.second, gameManager.startTilePos.first, gameManager.startTilePos.second);
			gameManager.towers.add(t);
			gameManager.money -= t.getPrice();
			gameManager.selectedTile = t;
		}
		catch (PathBlockedException e) {
			gameManager.selectedTile = null;
			gameManager.placedTiles[x][y].pop();
			SnackBar.play(e.getMessage());
		}
		catch (Exception e) {
			SnackBar.play(e.getMessage());
		}
		finally {
			try {
				gameManager.path = Algorithm.BFS(gameManager.endTilePos.first, gameManager.endTilePos.second, gameManager.startTilePos.first, gameManager.startTilePos.second);
			}
			catch(Exception e) {
				// this shouldn't happen
			}
		}
		return ;
	}

	public boolean canUpgrade(GameManager gameManager) {
		return gameManager.selectedTile != null && ((Tower)gameManager.selectedTile).getPrice() <= gameManager.money;
	}

	public boolean canSell(GameManager gameManager) {
		return gameManager.selectedTile != null;
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

	public boolean boundCheck(int x, int y) {
		return 0 <= x && x < Numbers.COLUMNS && 0 <= y && y < Numbers.ROWS;
	}

	public boolean isPlaceable(GameManager gameManager, int x, int y) {
		return  boundCheck(x, y) && gameManager.placedTiles[x][y].isPlaceable();
	}

	public boolean isWalkable(GameManager gameManager, int x, int y) {
		return boundCheck(x, y) && gameManager.placedTiles[x][y].isWalkable();
	}

}
