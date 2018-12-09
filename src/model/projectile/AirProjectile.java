package model.projectile;

import constants.Images;
import javafx.scene.image.Image;
import model.Monster;
import model.Projectile;

public class AirProjectile extends Projectile {
	
	
	protected double damage;
	
	protected boolean isExpired = false;
	
	public AirProjectile(double x, double y,
			double vx, double vy, double maxRange, double damage) {
		super(Images.airBullet, x, y, vx, vy, maxRange); // default size ?
		this.damage = damage;
		this.targetFlag = 2;
	}
		

	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			m.takeDamage(damage);
			forceExpire();	
		}
		return isExpired();
	}
		
}
