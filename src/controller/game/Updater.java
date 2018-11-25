package controller.game;

import constants.Images;
import controller.SuperManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import main.Main;
import model.Monster;
import model.Particle;
import model.Projectile;
import model.Tower;
import model.monster.SplittingMonster;
import ui.SnackBar;
import util.Algorithm;

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
		if (!(gm.selectedTile instanceof Tower)) {
			gm.selectedTile = null;
		}
		SuperManager.getInstance().getCanUpgradeProp().set(gm.selectedTile != null 
				&& ((Tower)gm.selectedTile).getUpgradePrice() <= gm.money && ((Tower)gm.selectedTile).getUpgradePrice() >= 0);
		SuperManager.getInstance().getCanSellProp().set(gm.selectedTile != null);
		SuperManager.getInstance().getnextWaveAvailableProp().set(shouldSpawnNextWave());
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
	
	public void requestNextWave() {
		try {
			gm.path = Algorithm.BFS(gm.endTilePos.first, gm.endTilePos.second, gm.startTilePos.first, gm.startTilePos.second);			
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
	public void spawnParticle(Particle p) {
		gm.particles.add(p);
	}
	public void spawnMonster(double x, double y) {
		gm.monsters.add(new SplittingMonster("Eleplant", Images.elephant, x, y, 0.4, 60, 0, 1.5, 10));
	}
	public void spawnMonster(Monster m) {
		gm.monsters.add(m);
	}
	
	public boolean shouldSpawnNextWave() {
		return MonsterSpawner.getInstace().isReady() && gm.monsters.size() == 0;
	}
	public void addProjectile(Projectile p) {
		gm.projectiles.add(p);
	}
}
