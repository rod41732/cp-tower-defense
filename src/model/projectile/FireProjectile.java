package model.projectile;

import constants.Images;
import controller.game.GameManager;
import javafx.scene.image.Image;
import model.AoE;
import model.Monster;
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
	}

	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			cpp.pff impact = m.getPosition();
			m.takeDamage(damage);
			GameManager.getInstance().spawnParticle(new AoE(Images.flame, impact.first, impact.second, 0, 0, 1000, 0.5, fireDamage));
			System.out.println("Firee!!!");
			forceExpire();
		}
		return isExpired();
	}
	
	
	
	
}
