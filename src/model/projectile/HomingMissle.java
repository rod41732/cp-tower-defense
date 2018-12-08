package model.projectile;

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
	
	public HomingMissle(Image image, double x, double y,
			double vx, double vy, double maxRange, double damage, double radius, Monster target) {
		super(image, x, y, vx, vy, maxRange, damage); // default size ?
		this.radius = radius;
		this.damage = damage;
		this.target = target;
		this.targetFlag = 1;
	}

	@Override 
	public void preUpdate() {
		double angle = angleTo(target);
		cpp.pff newV = GameUtil.rotateVector(vx, vy, angle-rotation);
		vx = newV.first;
		vy = newV.second;
	}

	public boolean collideWith(Monster m) {
		if (shouldCollide(m) || distanceTo(target) < 0.2) { // collide with dead monster
			cpp.pff impact = m.getPosition();
			GameManager.getInstance().addParticle(new Explosion(impact.first, impact.second, 0, 0));
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
