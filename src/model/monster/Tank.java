package model.monster;

import constants.Images;
import controller.game.GameManager;
import javafx.scene.image.Image;
import model.particle.Explosion;

public abstract class Tank extends GroundMonster {

	private static final double DEFAULT_SIZE = 0.7;
	protected double level = 0;
	
	public Tank(Image image, double x, double y, double health, double armor,
			double moveSpeed, int money) {
		super("Tank", image, x, y, DEFAULT_SIZE, health, armor, moveSpeed, money);
	}
	
	@Override
	public void onDeath() {
		GameManager.getInstance().addParticle(new Explosion(Images.deathExplosion, x, y, 0, 0)); // common death anim for cars		
		super.onDeath();
	}
}
