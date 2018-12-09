package model.projectile;

import constants.Images;
import javafx.scene.image.Image;
import model.Monster;

public class GroundProjectile extends NormalProjectile{
	
	
	protected double damage;
	
	protected boolean isExpired = false;
	
	public GroundProjectile(double x, double y,
			double vx, double vy, double maxRange, double damage) {
		super(Images.groundBullet, x, y, vx, vy, maxRange, damage); // default size ?
		this.targetFlag = 1;
	}
	
	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			m.takeDamage(damage);
			forceExpire();	
		}
		return isExpired();
	}
		
}
