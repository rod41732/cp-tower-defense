package model.tower;


import buff.MoveSpeedBuff;
import constants.Images;
import controller.game.GameManager;
import exceptions.FullyUpgradedException;
import javafx.scene.image.Image;
import model.Monster;
import model.Tower;
import model.projectile.ArmorBreakerProjectile;
import model.projectile.IceProjectile;
import util.GameUtil;
import util.cpp;

public class ArmorBreakerTower extends Tower {

	private static final double[] ATTACK_VALUES = {5, 5, 5, 5, 5};
	private static final double[] COOLDOWN_VALUES = {700, 700, 600, 600, 500};
	private static final double[] RANGE_VALUES = {2.5, 2.5, 2.5, 3, 3};
	private static final double[] DAMAGE_MULTIPLIER_VALUES = {0.3, 0.35, 0.4, 0.45, 0.5};
	private static final int[] PRICE_VALUES = {25, 25, 30, 50, 60};
	private static final int DEBUFF_DURATION = 1250;
	private static final Image DEFAULT_IMAGE = Images.iceTower;
	
	private double slowness;
		
	public ArmorBreakerTower(double cellX, double cellY) {
		super(DEFAULT_IMAGE, cellX, cellY, ATTACK_VALUES[0], COOLDOWN_VALUES[0], RANGE_VALUES[0]);
		this.price = PRICE_VALUES[0];
		this.slowness = DAMAGE_MULTIPLIER_VALUES[0];
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
			this.slowness = DAMAGE_MULTIPLIER_VALUES[level-1];
		}
	}
	public void fire() {
		if (currentTarget == null) return;
		
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
		
		rotateTo(currentTarget);
		GameManager.getInstance().addProjectile(new 
				ArmorBreakerProjectile(x, y, v.first*15, v.second*15, range, attack, slowness, DEBUFF_DURATION));
		
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
		return "Armor Breaker Tower";
	}
}
