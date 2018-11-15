package model;

import controller.GameManager;
import javafx.scene.image.Image;

public class AoE extends Particle implements IExpirable {

	
//	protected double radius;
	protected double damage;
	
	public AoE(Image[] images, double x, double y, double vx, double vy, double maxAge, double radius, double damage) {
		super(images, x, y, vx, vy, maxAge);
		this.size = radius; // size of hitbox = radius
		this.damage = damage;
	}

	public void update() {
		super.update();
		for (Monster m: GameManager.getInstance().getMonsters()) {
			if (m.isCollideWith(m)) {
				System.out.println("fooo" + m);
				m.takePureDamage(damage/60);
			}
		}
	}
	
	
}
