package model.projectile;

import constants.Images;
import constants.Sounds;
import controller.game.GameManager;
import javafx.scene.image.Image;
import model.Monster;
import model.Projectile;
import model.particle.Explosion;

public class AirProjectile extends Projectile {
	
	
	protected double damage;
	
	protected boolean isExpired = false;
	
	public AirProjectile(double x, double y,
			double vx, double vy, double maxRange, double damage) {
		super(Images.airBullet, x, y, vx, vy, maxRange); // default size ?
		this.damage = damage;
		this.targetFlag = 2;
	}
		
	public AirProjectile(Image image, double x, double y, double vx, double vy, double maxRange, double damage) {
		this(x, y, vx, vy, maxRange, damage);
		this.image = image;
	}

	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			Sounds.hitExplosion.play();
			GameManager.getInstance().addParticle(new Explosion(Images.explosion, m.getX(), m.getY(), 0, 0));
			m.takeDamage(damage);
			forceExpire();	
		}
		return isExpired();
	}
		
}
