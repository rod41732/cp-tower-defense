package model.projectile;

import buff.MoveSpeedBuff;
import constants.Images;
import model.Monster;

public class IceProjectile extends NormalProjectile {
	
	protected double vx, vy;
	protected double damage;
	
	protected boolean isExpired = false;
	
	public IceProjectile(double x, double y,
			double vx, double vy, double maxRange, double damage) {
		super(Images.iceBullet ,x, y, vx, vy, maxRange, damage); // default size ?
	}
	@Override
	public boolean shouldCollide(Monster m) {
		return !m.hasBuff(new MoveSpeedBuff(1, 1)) && super.shouldCollide(m);
	}

	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			m.takeDamage(damage);
			m.addBuff(new MoveSpeedBuff(2000, -0.7));
			forceExpire();	
		}
		return isExpired();
	}
		
}
