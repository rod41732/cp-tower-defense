package model.tower;


import buff.AttackSpeedBuff;
import buff.DamageBuff;
import buff.RangeBuff;
import constants.Images;
import controller.game.GameManager;
import exceptions.FullyUpgradedException;
import javafx.scene.image.Image;
import model.Monster;
import model.Particle;
import model.Tower;
import model.projectile.Missile;
import util.GameUtil;
import util.cpp;

public class BuffTower extends Tower {
	
	public static String TOWER_NAME = "Buff Tower";

	private static final Image DEFAULT_IMAGE = Images.bombTower;
	private static final int[] PRICE_VALUES = {25, 25, 30, 50, 60};
	
	
	public BuffTower(double cellX, double cellY) {
		super(DEFAULT_IMAGE, cellX, cellY, 0, 16, 1.5);
		this.price = PRICE_VALUES[0];
		this.level = 1;
	}

	
	
	
	@Override
	public void upgrade() throws FullyUpgradedException {
		if (level == 5) {
			throw new FullyUpgradedException();
		}
		else {
			level += 1;
			this.price += PRICE_VALUES[level-1];
		}
	}
	
	public void fire() {
		for (Tower twr: GameManager.getInstance().getTowers()) {
			if (distanceTo(twr) < range && twr != this) {
				twr.addBuff(new AttackSpeedBuff(50, 2));
				if (level >= 3) {
					twr.addBuff(new RangeBuff(50, 0.5));
				}
				if (level >= 5) {
					twr.addBuff(new DamageBuff(50, 2));
				}
			}
		}
		currentCooldown = attackCooldown;
	}
	
	
	@Override
	public boolean isInRange(Monster m) {
		return super.isInRange(m) && m.isAffectedByGround();
	}
	
	
	@Override
	public int getUpgradePrice() {
		return level == 5 ? -1 : PRICE_VALUES[level];
	}
	
	@Override
	public double getUpgradedAttackCooldown() {
		return 0;
	}

	@Override
	public double getUpgradedAttack() {
		return 0;
	}

	@Override
	public double getUpgradedRange() {
		return 1.5;
	}
}
