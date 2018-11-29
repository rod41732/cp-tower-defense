package model.projectile;

import controller.game.GameManager;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.Blood;
import model.Monster;
import model.Projectile;

public class NormalProjectile extends Projectile {
	
	
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
			forceExpire();	
		}
		return isExpired();
	}
		
}
