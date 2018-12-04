package model.tower;


import constants.Images;
import controller.game.GameManager;
import exceptions.FullyUpgradedException;
import javafx.scene.image.Image;
import model.FadingParticle;
import model.Particle;
import model.Tower;
import model.projectile.NormalProjectile;
import model.projectile.SplittingProjectile;
import util.GameUtil;
import util.cpp;

public class NormalTower extends Tower {

	// TODO : more fields
	private static final double[] ATTACK_VALUES = {7, 12, 15, 20, 15};
	private static final double[] COOLDOWN_VALUES = {700, 650, 650, 650, 650};
	private static final double[] RANGE_VALUES = {2.5, 2.5, 3, 3, 4};
	private static final int[] PRICE_VALUES = {7, 10, 15, 15, 30};
	private static final Image DEFAULT_IMAGE = Images.normalTower;
		
	public NormalTower(double cellX, double cellY) {
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
					SplittingProjectile(Images.normalBullet, x, y, v.first*15, v.second*15, range, attack, 
							Math.min(distanceTo(currentTarget)*0.6, distanceTo(currentTarget)-currentTarget.getSize()-size)));			
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
		return "Normal Tower";
	}

}

