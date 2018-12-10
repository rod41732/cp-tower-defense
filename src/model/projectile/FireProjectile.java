package model.projectile;

import constants.Images;
import controller.game.GameManager;
import model.Monster;
import model.particle.FireAoE;
import util.cpp;

public class FireProjectile extends NormalProjectile {
	
	protected double fireRadius;
	protected double fireDamage;
	
	public FireProjectile(double x, double y,
			double vx, double vy, double maxRange, double damage, double fireRadius, double fireDamage) {
		super(Images.fireBullet, x, y, vx, vy, maxRange, damage); // default size ?
		this.fireRadius = fireRadius;
		this.damage = damage;
		this.fireDamage = fireDamage;
		this.targetFlag = 1;
	}
	
	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			cpp.pff impact = m.getPosition();
			m.takeDamage(damage);
			GameManager.getInstance().addParticle(new FireAoE(Images.flame, impact.first, impact.second, 0, 0, 1000, 0.5, fireDamage));
			forceExpire();
		}
		return isExpired();
	}
	

	
	
	
}
