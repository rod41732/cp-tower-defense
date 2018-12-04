package model.tower;


import constants.Images;
import controller.game.GameManager;
import exceptions.FullyUpgradedException;
import javafx.scene.image.Image;
import model.FadingParticle;
import model.Monster;
import model.Particle;
import model.Tower;
import model.projectile.NormalProjectile;
import model.projectile.SplittingProjectile;
import util.GameUtil;
import util.cpp;

public class GroundAttackTower extends Tower {

	// TODO : more fields
	private static final double[] ATTACK_VALUES = {15, 25, 35, 55, 75};
	private static final double[] COOLDOWN_VALUES = {700, 700, 700, 700, 700};
	private static final double[] RANGE_VALUES = {1.5, 1.75, 2, 2.25, 2.25};
	private static final int[] PRICE_VALUES = {20, 20, 30, 30, 30};
	private static final Image DEFAULT_IMAGE = Images.groundAttackTower;
		
	public GroundAttackTower(double cellX, double cellY) {
		super(DEFAULT_IMAGE, cellX, cellY, ATTACK_VALUES[0], COOLDOWN_VALUES[0], RANGE_VALUES[0]);
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
	
	
	public void fire() {
		if (currentTarget == null) return;
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
		rotateTo(currentTarget);
		if (level < 5) {
		GameManager.getInstance().addProjectile(new 
				NormalProjectile(Images.normalBullet, x, y, v.first*15, v.second*15, range, attack));
				Particle p = new FadingParticle(Images.normalTowerFlash, x+v.first*0.6 , y+v.second*0.6, 0, 0, 300);
				p.rotateTo(currentTarget);
				GameManager.getInstance().addParticle(p);
		}
		else {
			GameManager.getInstance().addProjectile(new 
					SplittingProjectile(Images.normalBullet, x, y, v.first*15, v.second*15, range, 10, 
							Math.min(distanceTo(currentTarget)*0.6, distanceTo(currentTarget)-currentTarget.getSize()-size)));			
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
		return "Normal Tower";
	}

}

