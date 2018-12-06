package model.tower;


import constants.Images;
import controller.game.GameManager;
import exceptions.FullyUpgradedException;
import javafx.scene.image.Image;
import model.Monster;
import model.Tower;
import model.projectile.Missile;
import util.GameUtil;
import util.cpp;

public class MissileTower extends Tower {
	
	public static String TOWER_NAME = "Bomb Tower";

	private static final double[] ATTACK_VALUES = {15, 20, 25, 35, 50};
	private static final double[] COOLDOWN_VALUES = {1250, 1250, 1200, 1200, 1150};
	private static final double[] RANGE_VALUES = {3.5, 4, 4, 4.5, 4.5};
	private static final double[] SPLASH_RADIUS_VALUES = {0.4, 0.45, 0.5, 0.55, 0.6};
	private static final Image DEFAULT_IMAGE = Images.bombTower;
	private static final int[] PRICE_VALUES = {25, 25, 30, 50, 60};
	
	private double splashRadius;
	
	
	public MissileTower(double cellX, double cellY) {
		super(DEFAULT_IMAGE, cellX, cellY, ATTACK_VALUES[0], COOLDOWN_VALUES[0], RANGE_VALUES[0]);
		this.price = PRICE_VALUES[0];
		this.level = 1;
		this.splashRadius = SPLASH_RADIUS_VALUES[0];
	}

	
	
	
	@Override
	public void upgrade() throws FullyUpgradedException {
		if (level == 5) {
			throw new FullyUpgradedException();
		}
		else {
			level += 1;
			this.baseAttack = ATTACK_VALUES[level-1];
			this.attackCooldown = COOLDOWN_VALUES[level-1];
			this.baseRange = RANGE_VALUES[level-1];
			this.price += PRICE_VALUES[level-1];
			this.splashRadius = SPLASH_RADIUS_VALUES[level-1];
		}
	}
	
	public void fire() {
		if (currentTarget == null) return;
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
		rotateTo(currentTarget);
		GameManager.getInstance().addProjectile(new 
				Missile(Images.bomb, x, y, v.first*9, v.second*9, range, attack, splashRadius));
		
		currentCooldown = attackCooldown;
	}
	
	
	@Override
	public boolean isInRange(Monster m) {
		return super.isInRange(m) && m.isAffectedByGround();
	}
	
	@Override
	public String description() {
		return super.description() + String.format("Explosion radius: %.2f\n", splashRadius);
	}

	@Override
	public int getUpgradePrice() {
		return level == 5 ? -1 : PRICE_VALUES[level];
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
}
