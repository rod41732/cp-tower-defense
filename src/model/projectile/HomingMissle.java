package model.projectile;

import constants.Images;
import controller.GameManager;
import javafx.scene.image.Image;
import model.Monster;
import model.Particle;
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
	}

	@Override 
	public void preUpdate() {
		double angle = angleTo(target);
		cpp.pff newV = GameUtil.rotateVector(vx, vy, angle-rotation-90);
		vx = newV.first;
		vy = newV.second;
		rotation = angle-90;
	}

	public boolean collideWith(Monster m) {
		if (shouldCollide(m) || distanceTo(target) < 0.2) { // collide with dead monster
			cpp.pff impact = getPosition();
			GameManager.getInstance().spawnParticle(new Particle(Images.explosion, impact.first, impact.second, 0, 0, 1000));
			System.out.println("Boom");
			for (Monster ms: GameManager.getInstance().getMonsters()) {
				if (Double.compare(ms.distanceTo(impact.first, impact.second), 3) < 0) {
					ms.takeDamage(damage);
//					ms.addBuff(new DamageTakenDebuff(3000, 5));
					System.out.println("boom =>" + ms);
				}
			}
			forceExpire();
		}
		return isExpired();
	}
	
	
	
	
}
