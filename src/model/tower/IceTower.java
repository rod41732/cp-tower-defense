package model.tower;


import buff.SlowDebuff;
import constants.Images;
import constants.Sounds;
import constants.TowerStats;
import controller.game.GameManager;
import exceptions.FullyUpgradedException;
import javafx.scene.image.Image;
import model.Monster;
import model.Tower;
import model.projectile.IcePiercingProjectile;
import model.projectile.IceProjectile;
import util.GameUtil;
import util.cpp;

public class IceTower extends Tower {

	private static final double SLOW_DURATION = 1000;
	
	private double slowness;
	private double splashRadius;
		
	public IceTower(double cellX, double cellY) {
		super("Ice", cellX, cellY);
		this.slowness = (double) TowerStats.getData(typeName, "Slowness", 1);
		this.splashRadius = (double) TowerStats.getData(typeName, "SplashRadius", 1);
	}
	
	@Override
	public void tryTarget(Monster m) {
		if (m.hasBuff(SlowDebuff.ID)) return;
		super.tryTarget(m);
	}
	
	@Override
	public boolean upgrade() throws FullyUpgradedException {
		if (super.upgrade()) {
			this.slowness = (double) TowerStats.getData(typeName, "Slowness", level);
			this.splashRadius = (double) TowerStats.getData(typeName, "SplashRadius", level);
		}
		return true;
	}
	public void fire() {
		if (currentTarget == null) return;
		Sounds.genericShoot.play();
		
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
		rotateTo(currentTarget);
		if (level == 5) {
			GameManager.getInstance().addProjectile(new 
					IcePiercingProjectile(x, y, v.first*15, v.second*15, range, slowness));
		}
		else {
			GameManager.getInstance().addProjectile(new 
					IceProjectile(x, y, v.first*15, v.second*15, range, attack, slowness, splashRadius, SLOW_DURATION));			
		}	
		currentCooldown = attackCooldown;
	}
	
	public String toString() {
		return "Freeze Tower";
	}
	
}
