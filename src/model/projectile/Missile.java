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

	
	@Override
	public boolean shouldCollide(Monster m) {
		// TODO Auto-generated method stub
		return super.shouldCollide(m) && m.isAffectedByGround();
	}

	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			cpp.pff impact = m.getPosition();
			Particle p = new Particle(Images.explosion, impact.first, impact.second, 0, 0, 1000);
			p.setzIndex(3);
			GameManager.getInstance().addParticle(p);
			for (Monster ms: GameManager.getInstance().getMonsters()) {
				if (ms.distanceTo(impact.first, impact.second) < ms.getSize()+radius && ms.isAffectedByGround()) {
					ms.takeDamage(damage);
				}
			}
			forceExpire();
		}
		return isExpired();
	}
	
	
	
	
}
