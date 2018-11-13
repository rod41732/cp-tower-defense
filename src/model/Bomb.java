package model;

import controller.GameManager;
import javafx.scene.image.Image;
import util.cpp;

public class Bomb extends NormalProjectile {
	
	protected double radius;
	
	public Bomb(Image image, double x, double y,
			double vx, double vy, double maxRange, double damage, double radius) {
		super(image, x, y, vx, vy, maxRange, damage); // default size ?
		this.radius = radius;
	}


	public boolean collideWith(Monster m) {
		if (shouldCollide(m)) {
			cpp.pff impact = m.getPosition();
			System.out.println("Boom");
			for (Monster ms: GameManager.getInstance().getMonsters()) {
				if (Double.compare(ms.distanceTo(impact.first, impact.second), 3) < 0) {
					ms.takeDamage(damage);
					System.out.println("boom =>" + ms);
				}
			}
			return true;
		}
		return false;
	}
	
	
	
	
}
