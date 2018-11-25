package controller.game;

import controller.SuperManager;
import exceptions.FullyUpgradedException;
import main.Main;
import model.Monster;
import model.Particle;
import model.Projectile;
import model.Tower;
import ui.SnackBar;
import util.Algorithm;

public class Updater {
	private GameManager game;
	
	public Updater(GameManager game) {
		this.game = game;
	}
	public void update(GameManager gm) {
		SuperManager.getInstance().getCanUpgradeProp().set(gm.selectedTile != null 
				&& ((Tower)gm.selectedTile).getUpgradePrice() <= gm.money && ((Tower)gm.selectedTile).getUpgradePrice() >= 0);
		SuperManager.getInstance().getCanSellProp().set(gm.selectedTile != null);
		SuperManager.getInstance().getnextWaveAvailableProp().set(gm.shouldSpawnNextWave());
		if (gm.selectedTile == null) {
			Main.getGameScene().getButtonManager().setUpgradeText("Upgrade");			
		}
		else {
			int price = ((Tower)gm.selectedTile).getUpgradePrice();
			if (price <= 0) 
				Main.getGameScene().getButtonManager().setUpgradeText("Fully Upgraded");
			else 
				Main.getGameScene().getButtonManager().setUpgradeText("$ " + price);
		}
		for (Particle p: gm.particles) p.onTick();
		for (Tower t: gm.towers) t.onTick();
		int n = gm.projectiles.size();
		for (int i=n-1; i>=0; i--) {
			Projectile p = gm.projectiles.get(i);
			p.onTick();
		}
		for (Monster m: gm.monsters) {
			m.onTick();
			if (m.getPosition().containedBy(gm.endTilePos)) {
				m.forceKill();
				gm.lives -= 1;
				SnackBar.play("monster reached end");
			}
		}
		
		// entity interaction
		for (int i=gm.projectiles.size()-1; i>=0; i--) {
			Projectile p = gm.projectiles.get(i);
			if (p.isExpired()) gm.projectiles.remove(i);
			else
			for (Monster m: gm.monsters) {
				if (p.collideWith(m)) {
					gm.projectiles.remove(i);
					break;
				}
			}
		}
		// cleanUp
		for (int i=gm.monsters.size()-1; i>=0; i--) {
			if (gm.monsters.get(i).isDead()) {
				gm.money += gm.monsters.get(i).getMoney();
				gm.monsters.get(i).onDeath();
				gm.monsters.remove(i);			
			}
		}
		for (int i=gm.particles.size()-1; i>=0; i--) {
			if (gm.particles.get(i).isExpired()) {
				gm.particles.remove(i);
			}
		}
	}
	public void upgradeTower(GameManager gm) {
		if (gm.selectedTile != null && gm.canUpgrade()) {
			try {
				Tower twr = (Tower)gm.selectedTile;
				int price = twr.getUpgradePrice();
				twr.upgrade();		
				gm.money -= price;
			}
			catch (FullyUpgradedException e) {
				SnackBar.play("Already Fully upgraded");
			}
		}
	}
	public void requestNextWave(GameManager gameManager) {
		try {
			gameManager.path = Algorithm.BFS(gameManager.endTilePos.first, gameManager.endTilePos.second, gameManager.startTilePos.first, gameManager.startTilePos.second);			
		}
		catch (Exception e) {
			System.out.println("nextwave: path not found");
		}
		if (gameManager.shouldSpawnNextWave()) {
			MonsterSpawner.getInstace().nextWave();			
		}
		else {
			SnackBar.play("Please wait until end of the wave");
		}
	}
}
