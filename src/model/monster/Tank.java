package model.monster;

import javafx.scene.image.Image;

public abstract class Tank extends GroundMonster {

	private static final double DEFAULT_SIZE = 0.7;
	protected double level = 0;
	
	public Tank(Image image, double x, double y, double health, double armor,
			double moveSpeed, int money) {
		super("Tank", image, x, y, DEFAULT_SIZE, health, armor, moveSpeed, money);
	}
	

}
