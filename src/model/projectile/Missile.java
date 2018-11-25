package model.projectile;

import constants.Images;
import controller.game.GameManager;
import javafx.scene.image.Image;
import model.Monster;
import model.Particle;
import util.cpp;

public class Missile extends NormalProjectile {
	
	protected double radius;
	
	public Missile(Image image, double x, double y,
			double vx, double vy, double maxRange, double damage, double radius) {
		super(image, x, y, vx, vy, maxRange, damage); // default size ?
		this.radius = radius;
		this.damage = damage;
	}


	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			cpp.pff impact = m.getPosition();
			GameManager.getInstance().spawnParticle(new Particle(Images.explosion, impact.first, impact.second, 0, 0, 1000));
			for (Monster ms: GameManager.getInstance().getMonsters()) {
				if (distanceTo(impact.first, impact.second) < ms.getSize()+radius) {
					ms.takeDamage(damage);
				}
			}
			forceExpire();
		}
		return isExpired();
	}
	
	
	
	
}
