package model.tower;


import constants.Images;
import controller.game.GameManager;
import exceptions.FullyUpgradedException;
import javafx.scene.image.Image;
import model.Monster;
import model.Tower;
import model.projectile.FireProjectile;
import util.GameUtil;
import util.cpp;

public class FireTower extends Tower {

	// TODO : more fields like fire duration etc
	private static final double[] ATTACK_VALUES = {15, 20, 25, 35, 50};
	private static final double[] COOLDOWN_VALUES = {1250, 1150, 1000, 950, 700};
	private static final double[] RANGE_VALUES = {3.5, 4, 4, 4.5, 4.5};
	private static final double[] FIRE_RADIUS_VALUES = {0.4, 0.5, 0.6, 0.7, 0.8};
	private static final int[] PRICE_VALUES = {25, 25, 30, 50, 60};
	private static final int FIRE_DURATION = 1000;
	private static final Image DEFAULT_IMAGE = Images.fireTower;
	
	
	private double fireDamage;
	
	public FireTower(double cellX, double cellY) {
		super(DEFAULT_IMAGE, cellX, cellY, ATTACK_VALUES[0], COOLDOWN_VALUES[0], RANGE_VALUES[0]);
		this.fireDamage = ATTACK_VALUES[0];
		this.price = PRICE_VALUES[0];
	}
	
	@Override
	public void upgrade() throws FullyUpgradedException {
		if (level == 5) {
			throw new FullyUpgradedException();
		}
		else {
			level += 1;
			this.attack = ATTACK_VALUES[level-1];
			this.attackCooldown = COOLDOWN_VALUES[level-1];
			this.range = RANGE_VALUES[level-1];
			this.price += PRICE_VALUES[level-1];
		}
	}
	
	@Override
	public double getUpgradedAttackCooldown() {
		return COOLDOWN_VALUES[level];
	}
	
	@Override
	public double getUpgradedAttack() {
		return ATTACK_VALUES[level];
	}

	@Override
	public double getUpgradedRange() {
		return RANGE_VALUES[level];
	}
	
	@Override
	public int getUpgradePrice() {
		return level == 5 ? -1 :PRICE_VALUES[level];
	}
	
	@Override
	public boolean isInRange(Monster m) {
		return super.isInRange(m) && m.isAffectedByGround();
	}
	
	public void fire() {
		if (currentTarget == null) return;
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
		rotateTo(currentTarget);
		GameManager.getInstance().addProjectile(new 
				FireProjectile(x, y, v.first*9, v.second*9, range, attack, FIRE_RADIUS_VALUES[level-1], fireDamage));
		currentCooldown = attackCooldown;
	}
	@Override 
	public String toString() {
		return "FireTower";
	}
	
	@Override 
	public String description() {
		return super.description() + String.format("Fire radius: %.2f\n", FIRE_RADIUS_VALUES[level-1]);
	}
	
}
