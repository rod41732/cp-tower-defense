package model.projectile;

import buff.DamageTakenDebuff;
import buff.MoveSpeedBuff;
import constants.Images;
import constants.Other;
import controller.game.GameManager;
import model.Monster;
import util.cpp;

public class ArmorBreakerProjectile extends NormalProjectile {
	
	private double multiplier;;
	private double duration;
		
	public ArmorBreakerProjectile(double x, double y,
			double vx, double vy, double maxRange, double damage, double multiplier, double duration) {
		super(Images.iceBullet ,x, y, vx, vy, maxRange, damage); // default size ?
		this.multiplier = multiplier;
		this.duration = duration;
	}
	@Override
	public boolean shouldCollide(Monster m) {
		return !m.hasBuff(Other.damageTakenDebuffInsance) && super.shouldCollide(m);
	}

	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			m.addBuff(new DamageTakenDebuff(duration, -multiplier));
			forceExpire();
		}
		return isExpired();
	}
		
}
