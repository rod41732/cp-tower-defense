package model.projectile;

import buff.DamageTakenDebuff;
import constants.Images;
import controller.game.GameManager;
import model.Monster;
import util.cpp;

public class ArmorBreakerProjectile extends NormalProjectile {
	
	private double multiplier;;
	private double duration;
		
	public ArmorBreakerProjectile(double x, double y,
			double vx, double vy, double maxRange, double damage, double multiplier, double duration) {
		super(Images.armorBreakerBullet ,x, y, vx, vy, maxRange, damage); // default size ?
		this.multiplier = multiplier;
		this.duration = duration;
		this.targetFlag = 3;
	}
	
	@Override
	public boolean shouldCollide(Monster m) {
		return !m.hasBuff(DamageTakenDebuff.ID) && super.shouldCollide(m);
	}

	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			cpp.pff impact = m.getPosition();
			for (Monster ms: GameManager.getInstance().getMonsters()) {				
				if (ms.distanceTo(impact.first, impact.second) <= 0.5+ms.getSize()) {
					ms.addBuff(new DamageTakenDebuff(duration, multiplier));
				}
			}
			forceExpire();
		}
		return isExpired();
	}
		
}
