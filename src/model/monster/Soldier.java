package model.monster;

import controller.game.GameManager;
import javafx.scene.image.Image;
import model.particle.Blood;

public abstract class Soldier extends GroundMonster {

	private static final double DEFAULT_SIZE = 0.2;
	protected double level = 0;
	
	public Soldier(Image image, double x, double y, double health, double armor,
			double moveSpeed, int money) {
		super(image, x, y, DEFAULT_SIZE, health, armor, moveSpeed, money);
	}
	
	@Override
	public void onDeath() {
		super.onDeath();
		GameManager.getInstance().addParticle(new Blood(x, y, 0, 0));
	}

}
