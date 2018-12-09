package model.monster;

import constants.Images;
import controller.game.GameManager;
import javafx.scene.image.Image;
import model.Particle;

public class HeavyTank extends Tank {

	private static final Image DEFAULT_IMAGE = Images.heavyTank;
	private static final double DEFAULT_HEALTH = 500;
	private static final double DEFAULT_ARMOR = 30;
	private static final double DEFAULT_MS  = 0.6;
	private static final int DEFAULT_MONEY = 5;
	
	public HeavyTank(double x, double y, double health, double armor,
			double moveSpeed, int money) {
		super(DEFAULT_IMAGE, x, y, health, armor, moveSpeed, money);
		level = health/DEFAULT_HEALTH-1;
	}
	
	public HeavyTank(double x, double y, double modifier) {
		this(x, y, DEFAULT_HEALTH*(1+modifier), DEFAULT_ARMOR*(1+modifier*0.2), 
				DEFAULT_MS*(1+modifier*0.05), (int)(DEFAULT_MONEY*(1+modifier*0.2)));
		level = modifier;
	}
	
	@Override
	public void onDeath() {
		super.onDeath();
		Particle part = new Particle(Images.heavyTankDead, x, y, 0, 0, 2000);
		part.setRotation(rotation);
		GameManager.getInstance().addParticle(part);
	}
}
