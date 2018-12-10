package model.projectile;

import constants.Images;

public class GroundProjectile extends NormalProjectile{
	
	public GroundProjectile(double x, double y,
			double vx, double vy, double maxRange, double damage) {
		super(Images.groundBullet, x, y, vx, vy, maxRange, damage);
		this.targetFlag = 1;
	}
	
}
