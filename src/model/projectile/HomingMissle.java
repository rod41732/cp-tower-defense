package model.projectile;

import constants.Images;
import constants.Sounds;
import controller.game.GameManager;
import javafx.scene.image.Image;
import model.Monster;
import model.particle.Crater;
import model.particle.Explosion;
import util.GameUtil;
import util.cpp;

public class HomingMissle extends NormalProjectile {
	
	protected double radius;
	protected Monster target;
	
	public HomingMissle(double x, double y,
			double vx, double vy, double maxRange, double damage, double radius, Monster target) {
		super(Images.missileBullet, x, y, vx, vy, maxRange, damage); // default size ?
		this.radius = radius;
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
		if ((shouldCollide(m)) || (target.isDead() && distanceTo(target) < 0.2)) { // collide with dead monster
			Sounds.hitExplosion.play();
			cpp.pff impact = m.getPosition();
			GameManager.getInstance().addParticle(new Explosion(Images.explosion, impact.first, impact.second, 0, 0));
			GameManager.getInstance().addParticle(new Crater(impact.first, impact.second, 3000));
			for (Monster ms: GameManager.getInstance().getMonsters()) {
				if (ms.distanceTo(impact.first, impact.second) < radius+ms.getSize()) {
					ms.takeDamage(damage);	
				}
			}
			forceExpire();
		}
		return isExpired();
	}
	
	
	
	
}
