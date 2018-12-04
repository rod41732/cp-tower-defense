package model.projectile;

import javafx.scene.image.Image;
import model.Monster;
import model.Projectile;

public class AirProjectile extends Projectile {
	
	
	protected double damage;
	
	protected boolean isExpired = false;
	
	public AirProjectile(Image image, double x, double y,
			double vx, double vy, double maxRange, double damage) {
		super(image, x, y, vx, vy, maxRange); // default size ?
		this.damage = damage;
	}
	
	@Override
	public boolean shouldCollide(Monster m) {
		return super.shouldCollide(m) && m.isAffectedByAir();
	}
	

	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			m.takeDamage(damage);
			forceExpire();	
		}
		return isExpired();
	}
		
}
