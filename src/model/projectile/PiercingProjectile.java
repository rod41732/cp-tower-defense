package model.projectile;

import javafx.scene.image.Image;
import model.Monster;

public class PiercingProjectile extends NormalProjectile {

	
	protected int slowTick = 0;
	
	
	public PiercingProjectile(Image image, double x, double y,
			double vx, double vy, double maxRange, double damage) {
		super(image, x, y, vx, vy, maxRange, damage); // default size ?
		this.damage = damage;
	}
	
	@Override
	public void move() {
		if (slowTick > 0) { 
			vx /= 5;
			vy /= 5;
		}
		super.move();
		if (slowTick > 0) {
			vx *= 5;
			vy *= 5;
		}
	}
	
	@Override
	public void preUpdate() {
		if (slowTick > 0) slowTick--;
	}
	
	
	
	@Override
	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			m.takePureDamage(3);
			System.out.println("monster take damage from" + this);
			slowTick = 35;
		}
		return isExpired();
	}
}
