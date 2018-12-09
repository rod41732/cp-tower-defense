package model.projectile;

import constants.Images;
import constants.Sounds;
import controller.game.GameManager;
import model.Monster;
import model.Particle;
import model.particle.Crater;
import model.particle.Explosion;
import util.cpp;

public class Missile extends NormalProjectile {
	
	protected double radius;
	
	public Missile(double x, double y,
			double vx, double vy, double maxRange, double damage, double radius) {
		super(Images.missileBullet, x, y, vx, vy, maxRange, damage); // default size ?
		this.radius = radius;
		this.damage = damage;
		this.targetFlag = 3;
	}
	
	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			Sounds.hitExplosion.play();
			cpp.pff impact = m.getPosition();
			Particle p = new Explosion(Images.explosion, impact.first, impact.second, 0, 0),
					p2 = new Crater(impact.first, impact.second, 5000.);
			p.setzIndex(3);
			GameManager.getInstance().addParticle(p);
			GameManager.getInstance().addParticle(p2);
			for (Monster ms: GameManager.getInstance().getMonsters()) {
				if (ms.distanceTo(impact.first, impact.second) < ms.getSize()+radius && (ms.getTargetFlag() & targetFlag) != 0) {
					ms.takeDamage(damage);
				}
			}
			forceExpire();
		}
		return isExpired();
	}
	
	
	
	
}
