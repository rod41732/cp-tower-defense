package model.monster;

import controller.game.GameManager;
import javafx.scene.image.Image;
import model.particle.Explosion;

public abstract class Plane extends FlyingMonster {
	private static final double DEFAULT_SIZE = 0.5;
	protected double level = 0.5;
	
	public Plane(Image image, double x, double y, double health, double armor,
			double moveSpeed, int money) {
		super("Plane", image, x, y, DEFAULT_SIZE, health, armor, moveSpeed, money);
	}
	
	@Override
	public void onDeath() {
		GameManager.getInstance().addParticle(new Explosion(x, y, 0, 0));
		// TODO: add common death
	}
	
}
