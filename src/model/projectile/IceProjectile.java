package model.projectile;

import buff.MoveSpeedBuff;
import constants.Images;
import javafx.scene.image.Image;
import model.Monster;
import model.Projectile;

public class IceProjectile extends NormalProjectile {
	
	protected double vx, vy;
	protected double damage;
	
	protected boolean isExpired = false;
	
	public IceProjectile(double x, double y,
			double vx, double vy, double maxRange, double damage) {
		super(Images.iceBullet ,x, y, vx, vy, maxRange, damage); // default size ?
	}
	

	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			m.takeDamage(damage);
			m.addBuff(new MoveSpeedBuff(2000, -0.7));
			setExpired(true);		
		}
		return isExpired();
	}
		
}
