package model;

import controller.game.GameManager;
import javafx.scene.image.Image;

public class AoE extends Particle {

	
	// TODO: finish more AoE
	protected double damage;
	
	public AoE(Image[] images, double x, double y, double vx, double vy, double maxAge, double radius, double damage) {
		super(images, x, y, vx, vy, maxAge);
		this.size = radius; // size of hitbox = radius
		this.damage = damage;
	}

	public void onTick() {
		super.onTick();
		for (Monster m: GameManager.getInstance().getMonsters()) {
			if (this.isCollideWith(m) && m.isAffectedByGround()) {
				m.takePureDamage(damage/60);
			}
		}
	}
	
	
}
