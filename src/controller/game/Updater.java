package controller.game;

import controller.SuperManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import main.Main;
import model.Monster;
import model.Particle;
import model.Projectile;
import model.Tower;
import ui.SnackBar;

public class Updater {
	private GameManager gm;
	private Timeline updateLoop;
	
	public Updater(GameManager gm) {
		this.gm= gm;

		SuperManager.getInstance().getIsInGameProp().addListener((obs, old, nw) -> {
			if (nw.booleanValue()) {
				updateLoop.play();
			}
			else {
				updateLoop.pause();
			}
		});
		updateLoop = new Timeline(new KeyFrame(Duration.seconds(1./60), e -> {
			update();
		}));
		updateLoop.setCycleCount(Timeline.INDEFINITE);
		
		
		SuperManager.getInstance().getIsGamePausedProp().addListener((obs, old, nw) -> {
			if (!nw.booleanValue()) {
				updateLoop.play();
			}
			else {
				updateLoop.pause();
			}
		});
	}
	public void update() {
		SuperManager.getInstance().getCanUpgradeProp().set(gm.selectedTower != null 
				&& gm.selectedTower.canUpgrade() && gm.selectedTower.getUpgradePrice() <= gm.money);
		SuperManager.getInstance().getCanSellProp().set(gm.selectedTower != null);
		SuperManager.getInstance().getnextWaveAvailableProp().set(shouldSpawnNextWave());
		if (shouldSpawnNextWave() && SuperManager.getInstance().getGameStateProp().get() == 2) {
			SuperManager.getInstance().getIsGamePausedProp().set(true);
		}
		if (gm.selectedTower == null) {
			Main.getGameScene().getButtonManager().setUpgradeText("Upgrade");			
		}
		else {
			if (!gm.selectedTower.canUpgrade()) 
				Main.getGameScene().getButtonManager().setUpgradeText("Fully Upgraded");
			else 
				Main.getGameScene().getButtonManager().setUpgradeText("$ " + gm.selectedTower.getUpgradePrice());
		}
		
		
		
		
		for (int i=gm.particles.size()-1; i>=0; i--) {
			gm.particles.get(i).onTick();
		}
		for (Tower t: gm.towers) t.onTick();
		
		
		for (int i=gm.projectiles.size()-1; i>=0; i--) {
			gm.projectiles.get(i).onTick();
		}
		for (int i=gm.monsters.size()-1; i>=0; i--) {
			Monster m = gm.monsters.get(i);
			m.onTick();
			if (m.getPosition().containedBy(gm.endTilePos)) {
				m.forceKill();
				gm.lives -= 1;
				SnackBar.play("monster reached end");
			}
		}
		
		// entity interaction
		for (int i=gm.projectiles.size()-1; i>=0; i--) {
			Projectile proj = gm.projectiles.get(i);
			if (proj.isExpired()) {
				gm.removeProjectile(proj);
				continue;
			}
			for (Monster m: gm.monsters) {
				if (proj.collideWith(m)) {
					gm.removeProjectile(proj);
					break;
				}
			}				
		}
		// cleanUp
		for (int i=gm.monsters.size()-1; i>=0; i--) {
			Monster mon = gm.monsters.get(i); 
			if (mon.isDead()) {
				gm.money += mon.getMoney();
				mon.onDeath();
				gm.removeMonster(mon);
			}
		}
		for (int i=gm.particles.size()-1; i>=0; i--) {
			Particle part = gm.particles.get(i);
			if (part.isExpired()) {
				gm.removeParticle(part);
			}
		}
		
		if (gm.lives <= 0) {
			SuperManager.getInstance().getGameStateProp().set(1); // VI LOST ZULUL
			SuperManager.getInstance().getIsGamePausedProp().set(true);
		}
	}
	
	public void requestNextWave() {
		try {
			gm.updatePath();			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (shouldSpawnNextWave()) {
			MonsterSpawner.getInstace().nextWave();			
		}
		else {
			SnackBar.play("Please wait until end of the wave");
		}
	}
	
	
	public boolean shouldSpawnNextWave() {
		return MonsterSpawner.getInstace().isReady() && gm.monsters.size() == 0;
	}

	
	
}
