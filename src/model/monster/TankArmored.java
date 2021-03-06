package model.monster;

import constants.Images;
import controller.game.GameManager;
import javafx.scene.image.Image;
import model.Particle;
import model.particle.Corpse;

public class TankArmored extends Tank {

	private static final Image DEFAULT_IMAGE = Images.armoredTank;
	private static final double DEFAULT_HEALTH = 300;
	private static final double DEFAULT_ARMOR = 20;
	private static final double DEFAULT_MS  = 0.4;
	private static final int DEFAULT_MONEY = 20;
	
	
	
	public TankArmored(double x, double y, double health, double armor,
			double moveSpeed, int money) {
		super(DEFAULT_IMAGE, x, y, health, armor, moveSpeed, money);
		level = health/DEFAULT_HEALTH-1;
	}
	
	public TankArmored(double x, double y, double modifier) {
		this(x, y, DEFAULT_HEALTH*(1+modifier), DEFAULT_ARMOR*(1+modifier*0.2), 
				DEFAULT_MS*(1+modifier*0.05), (int)(DEFAULT_MONEY*(1+modifier*0.2)));
		level = modifier;
	}
	
	@Override
	public void onDeath() {
		super.onDeath();
		Particle part = new Corpse(Images.armoredTankDead, x, y, 0, 0);
		part.setRotation(rotation);
		GameManager.getInstance().addParticle(part);
	}
	

}
