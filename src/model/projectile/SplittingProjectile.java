package model.projectile;

import constants.Images;
import controller.GameManager;
import javafx.scene.image.Image;
import model.Monster;
import model.Particle;
import util.GameUtil;
import util.cpp;

public class SplittingProjectile extends NormalProjectile {
	
	private double splitDistance;
	
	
	public SplittingProjectile(Image image, double x, double y,
			double vx, double vy, double maxRange, double damage, double splitDistance) {
		super(image, x, y, vx, vy, maxRange, damage); // default size ?
		this.splitDistance = splitDistance;
		this.damage = damage;
	}

	@Override
	public void preUpdate() {
		
		if (distance >= splitDistance) {
			cpp.pff v1 = GameUtil.rotateVector(vx, vy, 20);
			cpp.pff v2 = GameUtil.rotateVector(vx, vy, -20);
			forceExpire();
			GameManager.getInstance().addProjectile(new NormalProjectile(Images.normalBullet,
					x, y, vx, vy,maxDistance-splitDistance, damage));
			GameManager.getInstance().addProjectile(new NormalProjectile(Images.normalBullet,
					x, y, v1.first, v1.second, maxDistance-splitDistance, damage));
			GameManager.getInstance().addProjectile(new NormalProjectile(Images.normalBullet,
					x, y, v2.first, v2.second, maxDistance-splitDistance, damage));
		}
	}
	

	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
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
