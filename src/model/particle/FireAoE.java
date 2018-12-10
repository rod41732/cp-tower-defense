package model.particle;

import controller.game.GameManager;
import javafx.scene.image.Image;
import model.Monster;
import model.Particle;

public class FireAoE extends Particle {

	protected double damage;
	
	public FireAoE(Image[] images, double x, double y, double vx, double vy, double maxAge, double radius, double damage) {
		super(images, x, y, vx, vy, maxAge);
		this.size = radius; // size of hitbox = radius
		this.damage = damage;
	}

	public void onTick() {
		super.onTick();
		for (Monster m: GameManager.getInstance().getMonsters()) {
			if (this.isCollideWith(m) && (m.getTargetFlag() & 1) != 0) {
				m.takePureDamage(damage/60);
			}
		}
	}
	
	
}
