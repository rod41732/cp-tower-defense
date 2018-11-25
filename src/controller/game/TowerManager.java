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

	
	private GameManager gm;
	
	public TowerManager(GameManager game) {
		this.gm = game;
	}

	public void sellTower() {
		if (gm.selectedTile == null) return;
		// TODO : spaghetti
		if (!(gm.selectedTile instanceof Tower)) return ;
		Tower t = (Tower)gm.selectedTile;
		gm.money += t.getPrice()/2;
		pii pos = t.getPosition().toI();
		removeTower(pos.first, pos.second);
		gm.selectedTile = null;
		gm.handler.setTowerChoice(-1);
	}

	public void removeTower(int x, int y) {
		try {
			Tile t = gm.placedTiles[x][y].top();
			if (!t.isSelectable()) return ; // don't remove that dansgame
			gm.placedTiles[x][y].pop();
			if (t instanceof Tower) {
				for (int i=0; i<gm.towers.size(); i++)
					if (t == gm.towers.get(i)) {
						gm.towers.remove(i);
						break;
					}
			}
			gm.path = Algorithm.BFS(gm.endTilePos.first, gm.endTilePos.second, gm.startTilePos.first, gm.startTilePos.second);
		}
		catch (Exception e) {
	//			e.printStackTrace();
			System.out.println("can't remove tower, this shouldn't happen");
		}
	}

	public void handleTileClick(int x, int y) {
	
		try {
			Tower t = createTower(gm.getTowerChoice(), x, y);
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
			gm.selectedTile = null;
			gm.placedTiles[x][y].pop();
			SnackBar.play(e.getMessage());
		}
		catch (Exception e) {
			SnackBar.play(e.getMessage());
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

	public boolean canUpgrade() {
		return gm.selectedTile != null && ((Tower)gm.selectedTile).getPrice() <= gm.money;
	}

	public boolean canSell() {
		return gm.selectedTile != null;
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

	public boolean isPlaceable(int x, int y) {
		return  boundCheck(x, y) && gm.placedTiles[x][y].isPlaceable();
	}

	public boolean isWalkable(int x, int y) {
		return boundCheck(x, y) && gm.placedTiles[x][y].isWalkable();
	}

}
