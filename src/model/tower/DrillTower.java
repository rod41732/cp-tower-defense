package model.tower;


import buff.DamageTakenDebuff;
import constants.Images;
import constants.Sounds;
import constants.TowerStats;
import controller.game.GameManager;
import exceptions.FullyUpgradedException;
import javafx.scene.image.Image;
import model.Monster;
import model.Tower;
import model.projectile.DrillProjectile;
import util.GameUtil;
import util.cpp;

public class DrillTower extends Tower {

	private static final int DEBUFF_DURATION = 1250;
	
	private double amplification;
		
	public DrillTower(double cellX, double cellY) {
		super("ArmorBreaker", cellX, cellY);
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
		Sounds.genericShoot.play();
		
		cpp.pff v = GameUtil.unitVector(this, currentTarget);
		
		rotateTo(currentTarget);
		GameManager.getInstance().addProjectile(new 
				DrillProjectile(x, y, v.first*15, v.second*15, range, attack, amplification, DEBUFF_DURATION));
		
		currentCooldown = attackCooldown;
	}
}