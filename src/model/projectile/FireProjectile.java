package model.projectile;

import constants.Images;
import controller.GameManager;
import javafx.scene.image.Image;
import model.AoE;
import model.Monster;
import model.Particle;
import util.cpp;

public class FireProjectile extends NormalProjectile {
	
	protected double radius;
	
	public FireProjectile(Image image, double x, double y,
			double vx, double vy, double maxRange, double damage, double radius) {
		super(image, x, y, vx, vy, maxRange, damage); // default size ?
		this.radius = radius;
		this.damage = damage;
	}


	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			cpp.pff impact = getPosition();
			GameManager.getInstance().spawnParticle(new AoE(Images.flame, impact.first, impact.second, 0, 0, 1000, 2, damage));
			System.out.println("Firee!!!");
			setExpired(true);
		}
		return isExpired();
	}
	
	
	
	
}
