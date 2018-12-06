package model.projectile;

import constants.Images;
import controller.game.GameManager;
import javafx.scene.image.Image;
import model.Monster;
import model.particle.FireAoE;
import util.cpp;

public class FireProjectile extends NormalProjectile {
	
	private static final Image DEFAULT_IMAGE = Images.fireBullet; 
	protected double radius;
	protected double fireDamage;
	
	public FireProjectile(double x, double y,
			double vx, double vy, double maxRange, double damage, double radius, double fireDamage) {
		super(DEFAULT_IMAGE, x, y, vx, vy, maxRange, damage); // default size ?
		this.radius = radius;
		this.damage = damage;
		this.fireDamage = fireDamage;
		this.targetFlag = 1;
	}
	
	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			cpp.pff impact = m.getPosition();
			m.takeDamage(damage);
			GameManager.getInstance().addParticle(new FireAoE(Images.flame, impact.first, impact.second, 0, 0, 1000, 0.5, fireDamage));
			forceExpire();
		}
		return isExpired();
	}
	

	
	
	
}
