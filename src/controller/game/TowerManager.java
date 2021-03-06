package controller.game;

import constants.Numbers;
import controller.SuperManager;
import exceptions.FullyUpgradedException;
import exceptions.NotEnoughMoneyException;
import exceptions.PathBlockedException;
import exceptions.UnplaceableException;
import model.Tower;
import model.tower.BombTower;
import model.tower.BuffTower;
import model.tower.DrillTower;
import model.tower.FireTower;
import model.tower.GroundTower;
import model.tower.IceTower;
import model.tower.MissileTower;
import model.tower.NormalTower;
import model.tower.SkyTower;
import sharedobject.SharedObject;
import ui.SnackBar;
import util.BFSAlgo;
import util.cpp;
import util.cpp.pii;

public class TowerManager {

	private GameManager gm;
	private BFSAlgo bfs = new BFSAlgo();
	public TowerManager(GameManager game) {
		this.gm = game;
	}

	public void sellTower() {
		if (gm.selectedTower == null) return;
		Tower t = gm.selectedTower;
		gm.money += t.getPrice()/2;
		pii pos = t.getPosition().toI();
		removeTower(pos.first, pos.second);
		gm.selectedTower = null;
		gm.handler.setTowerChoice(-1);
	}

	public void removeTower(int x, int y) {
		Tower t = (Tower)gm.getPlacedTiles()[x][y].select();
		gm.getPlacedTiles()[x][y].pop();
		gm.towers.remove(t);
		SharedObject.getInstance().removeRenderables(t);
		gm.updatePath();
	
	}


	public boolean canUpgrade() {
		return gm.selectedTower != null && gm.selectedTower.canUpgrade() && gm.selectedTower.getUpgradePrice() <= gm.money;
	}

	public boolean canSell() {
		return gm.selectedTower != null;
	}
	void upgradeTower() {
		if (gm.selectedTower != null && gm.towerManager.canUpgrade()) {
			try {
				Tower twr = (Tower)gm.selectedTower;
				int price = twr.getUpgradePrice();
				twr.upgrade();		
				gm.money -= price;
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
			t = new MissileTower(x+0.5, y+0.5); break;
		case 1:
			t = new NormalTower(x+0.5, y+0.5); break;
		case 2:
			t = new FireTower(x+0.5, y+0.5); break;
		case 3:
			t = new IceTower(x+0.5, y+0.5); break;
		case 4: 
			t = new GroundTower(x+0.5, y+0.5); break;
		case 5:
			t = new SkyTower(x+0.5, y+0.5); break;	
		case 6:
			t = new DrillTower(x+0.5, y+0.5); break;
		case 7:
			t = new BombTower(x+0.5,y+0.5); break;
		case 8:
			t= new BuffTower(x+0.5, y+0.5); break;
		}
		return t;
	}
	
	public void placeAt(int x, int y) {
		Tower t = createTower(gm.getTowerChoice(), x, y);
		try {			
			if (t.getPrice() > gm.money) {
				throw new NotEnoughMoneyException();
			}				
			
			bfs.BFS(gm.endTilePos.first, gm.endTilePos.second, gm.startTilePos.first, gm.startTilePos.second, new cpp.pii(x, y));
			gm.getPlacedTiles()[x][y].push(t);
			gm.towers.add(t);
			SharedObject.getInstance().addRenderables(t);
			gm.money -= t.getPrice();
			gm.selectedTower = t;
		}
		catch (PathBlockedException e) {
			gm.selectedTower = null;
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
			gm.updatePath();
		}
		gm.setSelectedTile(null);
		SuperManager.getInstance().getTowerChoiceProp().set(-1);
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
