package model.projectile;

import buff.DamageTakenDebuff;
import buff.MoveSpeedBuff;
import constants.Images;
import controller.GameManager;
import javafx.scene.image.Image;
import model.Monster;
import model.Particle;
import util.cpp;

public class Bomb extends NormalProjectile {
	
	protected double radius;
	
	public Bomb(Image image, double x, double y,
			double vx, double vy, double maxRange, double damage, double radius) {
		super(image, x, y, vx, vy, maxRange, damage); // default size ?
		this.radius = radius;
		this.damage = damage;
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
