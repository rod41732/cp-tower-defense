package model.projectile;

import buff.SlowDebuff;
import constants.Images;
import model.Monster;

public class IceCloud extends PiercingProjectile {

	private double slowness;
	
	public IceCloud(double x, double y, double vx, double vy, double maxRange, double slowness) {
		super(Images.iceBullet ,x, y, vx, vy, maxRange, 0);
		this.slowness = slowness;
	}
	
	@Override
	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			m.addBuff(new SlowDebuff(500, slowness));
			slowTick = 10;
		}
		return isExpired();
	}

}
