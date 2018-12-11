package model.projectile;

import constants.Images;
import constants.Sounds;
import controller.game.GameManager;
import model.Monster;
import model.particle.Crater;
import model.particle.Explosion;
import util.GameUtil;
import util.cpp;

public class HomingMissile extends NormalProjectile {
	
	private double explosionRadius;
	private Monster target;
	
	public HomingMissile(double x, double y,
			double vx, double vy, double maxRange, double damage, double radius, Monster target) {
		super(Images.missileBullet, x, y, vx, vy, maxRange, damage); // default size ?
		this.explosionRadius = radius;
		this.damage = damage;
		this.target = target;
		this.targetFlag = 3;
	}

	@Override 
	public void preUpdate() {
		double angle = angleTo(target);
		cpp.pff newV = GameUtil.rotateVector(vx, vy, angle-rotation);
		vx = newV.first;
		vy = newV.second;
	}

	public boolean collideWith(Monster m) {
		cpp.pff impact;
		if (m == target && shouldCollide(m)){
			impact = target.getPosition();
		}
		else if (target.isDead() && distanceTo(target) <= 0.2) {
			impact = this.getPosition();
		}
		else {
			return isExpired();
		}	
		Sounds.hitExplosion.play();
		GameManager.getInstance().addParticle(new Explosion(Images.explosion, impact.first, impact.second, 0, 0));
		GameManager.getInstance().addParticle(new Crater(impact.first, impact.second, 3000));
		for (Monster ms: GameManager.getInstance().getMonsters()) {
			if (ms.distanceTo(impact.first, impact.second) < explosionRadius+ms.getSize()) {
				ms.takeDamage(damage);	
			}
		}
		forceExpire();
		
		return isExpired();
	}
	
	
	
	
}
