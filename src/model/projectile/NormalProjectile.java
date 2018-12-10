package model.projectile;

import javafx.scene.image.Image;
import model.Monster;
import model.Projectile;

public class NormalProjectile extends Projectile {
	

	
	public NormalProjectile(Image image, double x, double y,
			double vx, double vy, double maxRange, double damage) {
		super(image, x, y, vx, vy, maxRange, damage); // default size ?
		this.targetFlag = 3;
	}
	
	

	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			m.takeDamage(damage);
			forceExpire();	
		}
		return isExpired();
	}
		
}
