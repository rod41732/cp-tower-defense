package model.tower;


import buff.DamageTakenDebuff;
import constants.Images;
import constants.TowerStats;
import controller.game.GameManager;
import exceptions.FullyUpgradedException;
import javafx.scene.image.Image;
import model.Monster;
import model.Tower;
import model.projectile.ArmorBreakerProjectile;
import util.GameUtil;
import util.cpp;

public class ArmorBreakerTower extends Tower {

	private static final int DEBUFF_DURATION = 1250;
	private static final Image DEFAULT_IMAGE = Images.iceTower;
	
	private double amplification;
		
	public ArmorBreakerTower(double cellX, double cellY) {
		super("ArmorBreaker", DEFAULT_IMAGE, cellX, cellY);
		this.amplification = (double) TowerStats.getData(typeName, "DamageMultiplier", 1);
	}
	
	@Override
	public void tryTarget(Monster m) {
		if (m.hasBuff(DamageTakenDebuff.ID)) return ;
		super.tryTarget(m);
	}
	
	public boolean upgrade() throws FullyUpgradedException {
		if (super.upgrade()) {
			this.amplification = (double) TowerStats.getData(typeName, "DamageMultiplier", 1);
		}
		return true;
	}
	public void fire() {
		if (currentTarget == null) return;
		
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
		
		rotateTo(currentTarget);
		GameManager.getInstance().addProjectile(new 
				ArmorBreakerProjectile(x, y, v.first*15, v.second*15, range, attack, amplification, DEBUFF_DURATION));
		
		currentCooldown = attackCooldown;
	}
}
