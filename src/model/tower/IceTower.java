package model.tower;


import buff.MoveSpeedBuff;
import constants.Images;
import controller.game.GameManager;
import exceptions.FullyUpgradedException;
import javafx.scene.image.Image;
import model.Monster;
import model.Tower;
import model.projectile.IceProjectile;
import util.GameUtil;
import util.cpp;

public class IceTower extends Tower {

	private static final double[] ATTACK_VALUES = {5, 5, 5, 5, 5};
	private static final double[] COOLDOWN_VALUES = {700, 700, 600, 600, 500};
	private static final double[] RANGE_VALUES = {2.5, 2.5, 2.5, 3, 3};
	private static final double[] SPLASH_RADIUS_VALUES = {0.2, 0.3, 0.3, 0.4, 0.6};
	private static final double[] SLOWNESS_VALUES = {0.3, 0.35, 0.4, 0.45, 0.5};
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
	}
	
	@Override
	public void tryTarget(Monster m) {
		if (m.hasBuff(new MoveSpeedBuff(1, 1))) return ;
		super.tryTarget(m);
	}
	
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
			this.splashRadius = SPLASH_RADIUS_VALUES[level-1];
			this.slowness = SLOWNESS_VALUES[level-1];
		}
	}
	public void fire() {
		if (currentTarget == null) return;
		
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
		System.out.printf("I'm at %s,%s targeting %s,%s UV = %s\n",
				getX(), getY(), currentTarget.getX(), currentTarget.getY(), v);
		
		rotateTo(currentTarget);
		GameManager.getInstance().updater.addProjectile(GameManager.getInstance(), new 
				IceProjectile(x, y, v.first*15, v.second*15, range, attack, slowness, splashRadius));
		
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
