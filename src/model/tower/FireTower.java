package model.tower;


import constants.Images;
import constants.TowerStats;
import controller.game.GameManager;
import exceptions.FullyUpgradedException;
import javafx.scene.image.Image;
import model.Tower;
import model.projectile.FireProjectile;
import util.GameUtil;
import util.cpp;

public class FireTower extends Tower {

	private static final int FIRE_DURATION = 1000;
	private static final Image DEFAULT_IMAGE = Images.fireTower;
	
	
	private double fireRadius;
	
	public FireTower(double cellX, double cellY) {
		super("Fire", DEFAULT_IMAGE, cellX, cellY);
		this.fireRadius = (double)TowerStats.getData(typeName, "FireRadius", 1);
	}
	
	@Override
	public boolean upgrade() throws FullyUpgradedException {
		if (super.upgrade()) {
			this.fireRadius = (double)TowerStats.getData(typeName, "FireRadius", level);
		}
		return true;
	}

	
	public void fire() {
		if (currentTarget == null) return;
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
		rotateTo(currentTarget);
		GameManager.getInstance().addProjectile(new 
				FireProjectile(x, y, v.first*9, v.second*9, range, attack, fireRadius, attack));
		currentCooldown = attackCooldown;
	}

}
