package controller.game;

import constants.Images;
import constants.Numbers;
import exceptions.FullyUpgradedException;
import exceptions.NotEnoughMoneyException;
import exceptions.PathBlockedException;
import exceptions.UnplaceableException;
import model.Particle;
import model.Tile;
import model.Tower;
import model.tower.AirAttackTower;
import model.tower.BombTower;
import model.tower.FireTower;
import model.tower.GroundAttackTower;
import model.tower.IceTower;
import model.tower.NormalTower;
import sharedobject.SharedObject;
import ui.SnackBar;
import util.Algorithm;
import util.cpp.pii;

public class TowerManager {

	private static TowerManager instance;
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
			Tile t = gm.getPlacedTiles()[x][y].top();
			if (!t.isSelectable()) return ; // don't remove that dansgame
			gm.getPlacedTiles()[x][y].pop();
			if (t instanceof Tower) {
				gm.towers.remove(t);
				SharedObject.getInstance().removeRenderables(t);
			}
			gm.path = Algorithm.BFS(gm.endTilePos.first, gm.endTilePos.second, gm.startTilePos.first, gm.startTilePos.second);
		}
		catch (Exception e) {
			System.err.println("can't remove tower, this shouldn't happen");
				e.printStackTrace();
		}
	}


	public boolean canUpgrade() {
		return gm.selectedTile != null && ((Tower)gm.selectedTile).getUpgradePrice() <= gm.money && ((Tower)gm.selectedTile).getUpgradePrice() >= 0;
	}

	public boolean canSell() {
		return gm.selectedTile != null;
	}
	void upgradeTower() {
		if (gm.selectedTile != null && gm.towerManager.canUpgrade()) {
			try {
				Tower twr = (Tower)gm.selectedTile;
				int price = twr.getUpgradePrice();
				twr.upgrade();		
				gm.money -= price;
				gm.addParticle(new Particle(Images.smoke, twr.getX(), twr.getY(), 0, 0, 500));
			}
			catch (FullyUpgradedException e) {
				SnackBar.play("Already Fully upgraded");
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
		case 4: 
			t = new GroundAttackTower(x+0.5, y+0.5); break;
		case 5:
			t = new AirAttackTower(x+0.5, y+0.5); break;	
		}
		return t;
	}
	
	public void placeAt(int x, int y) {
		Tower t = createTower(gm.getTowerChoice(), x, y);
		try {			
			if (!gm.getPlacedTiles()[x][y].isPlaceable()) {
				throw new UnplaceableException();
			}
			
			if (t.getPrice() > gm.money) {
				throw new NotEnoughMoneyException();
			}				
			
			gm.getPlacedTiles()[x][y].push(t);
			Algorithm.BFS(gm.endTilePos.first, gm.endTilePos.second, gm.startTilePos.first, gm.startTilePos.second);
			gm.towers.add(t);
			SharedObject.getInstance().addRenderables(t);
			gm.money -= t.getPrice();
			gm.selectedTile = t;
		}
		catch (PathBlockedException e) {
			gm.selectedTile = null;
			gm.getPlacedTiles()[x][y].pop();
			SnackBar.play("path blocked");
		}
		catch (NotEnoughMoneyException e) {
			SnackBar.play("not enough money");
		}
		catch (UnplaceableException e) {
			SnackBar.play("can't place there");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				gm.path = Algorithm.BFS(gm.endTilePos.first, gm.endTilePos.second, gm.startTilePos.first, gm.startTilePos.second);
			}
			catch(PathBlockedException e) {
			}
		}
		gm.setSelectedTile(null);
	}

	public boolean boundCheck(int x, int y) {
		return 0 <= x && x < Numbers.COLUMNS && 0 <= y && y < Numbers.ROWS;
	}

	public boolean isPlaceable(int x, int y) {
		return  boundCheck(x, y) && gm.getPlacedTiles()[x][y].isPlaceable();
	}

	public boolean isWalkable(int x, int y) {
		return boundCheck(x, y) && gm.getPlacedTiles()[x][y].isWalkable();
	}

}
