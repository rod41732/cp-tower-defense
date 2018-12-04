package model.projectile;

import javafx.scene.image.Image;
import model.Monster;
import model.Projectile;

public class GroundProjectile extends NormalProjectile{
	
	
	protected double damage;
	
	protected boolean isExpired = false;
	
	public GroundProjectile(Image image, double x, double y,
			double vx, double vy, double maxRange, double damage) {
		super(image, x, y, vx, vy, maxRange, damage); // default size ?
	}
	
	@Override
	public boolean shouldCollide(Monster m) {
		return super.shouldCollide(m) && m.isAffectedByGround();
	}
	
	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			m.takeDamage(damage);
			forceExpire();	
		}
		return isExpired();
	}
		
}
