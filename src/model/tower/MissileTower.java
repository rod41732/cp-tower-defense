package model.tower;


import constants.Images;
import constants.TowerStats;
import controller.game.GameManager;
import exceptions.FullyUpgradedException;
import javafx.scene.image.Image;
import model.Tower;
import model.projectile.HomingMissle;
import model.projectile.Missile;
import util.GameUtil;
import util.cpp;

public class MissileTower extends Tower {

	private static final Image DEFAULT_IMAGE = Images.bombTower;	
	private double splashRadius;
	
	
	public MissileTower(double cellX, double cellY) {
		super("Missile", DEFAULT_IMAGE, cellX, cellY);
		this.splashRadius = (double) TowerStats.getData(typeName, "SplashRadius", level-1);
	}

	
	
	
	@Override
	public boolean upgrade() throws FullyUpgradedException {
		if (super.upgrade()) {
			this.splashRadius = (double) TowerStats.getData(typeName, "SplashRadius", level-1);
		}
		return true;
	}
	
	public void fire() {
		if (currentTarget == null) return;
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
		rotateTo(currentTarget);
		if (level == 5) {
			GameManager.getInstance().addProjectile(new 
					HomingMissle(Images.bomb, x, y, v.first*9, v.second*9, range, attack, splashRadius, currentTarget));
		}
		else {			
			GameManager.getInstance().addProjectile(new 
					Missile(Images.bomb, x, y, v.first*9, v.second*9, range, attack, splashRadius));
		}
		currentCooldown = attackCooldown;
	}
}
