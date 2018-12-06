package model.tower;


import buff.MoveSpeedBuff;
import buff.SlowDebuff;
import constants.Images;
import controller.game.GameManager;
import exceptions.FullyUpgradedException;
import javafx.scene.image.Image;
import model.Monster;
import model.Tower;
import model.projectile.IceCloud;
import model.projectile.IceProjectile;
import util.GameUtil;
import util.cpp;

public class IceTower extends Tower {

	private static final double[] ATTACK_VALUES = {5, 5, 5, 5, 5};
	private static final double[] COOLDOWN_VALUES = {300, 300, 300, 300, 300};
	private static final double[] RANGE_VALUES = {2.5, 2.5, 2.5, 3, 3};
	private static final double[] SPLASH_RADIUS_VALUES = {0.35, 0.45, 0.55, 0.65, 0.75};
	private static final double[] SLOWNESS_VALUES = {0.45, 0.50, 0.55, 0.65, 0.75};
	private static final int[] PRICE_VALUES = {25, 25, 30, 50, 60};
	private static final int SLOW_DURATION = 1000;
	private static final Image DEFAULT_IMAGE = Images.iceTower;
	
	private double slowness;
	private double splashRadius;
		
	public IceTower(double cellX, double cellY) {
		super(DEFAULT_IMAGE, cellX, cellY, ATTACK_VALUES[0], COOLDOWN_VALUES[0], RANGE_VALUES[0]);
		this.price = PRICE_VALUES[0];
		this.slowness = SLOWNESS_VALUES[0];
		this.splashRadius = SPLASH_RADIUS_VALUES[0];
		this.targetFlag = 3;
	}
	
	@Override
	public void tryTarget(Monster m) {
		if (m.hasBuff(SlowDebuff.ID)) return ;
		super.tryTarget(m);
	}
	
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
			this.slowness = SLOWNESS_VALUES[level-1];
		}
	}
	public void fire() {
		if (currentTarget == null) return;
		
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
		
		rotateTo(currentTarget);
		if (level == 5) {
			GameManager.getInstance().addProjectile(new 
					IceCloud(x, y, v.first*15, v.second*15, range, slowness));
		}
		else {
			GameManager.getInstance().addProjectile(new 
					IceProjectile(x, y, v.first*15, v.second*15, range, attack, slowness, splashRadius, SLOW_DURATION));			
		}
		
		currentCooldown = attackCooldown;
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
	
	public String toString() {
		return "Ice Tower";
	}
}
