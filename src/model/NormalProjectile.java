package model;

import javafx.scene.image.Image;

public class NormalProjectile extends Projectile {
	
	protected double vx, vy;
	protected double damage;
	
	protected boolean isExpired = false;
	
	public NormalProjectile(Image image, double x, double y,
			double vx, double vy, double maxRange, double damage) {
		super(image, x, y, vx, vy, maxRange); // default size ?
		this.damage = damage;
	}
	
	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			m.takeDamage(damage);
			isExpired = true;
			return true;			
		}
		return false;
	}
		
}
