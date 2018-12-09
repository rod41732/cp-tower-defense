package model.monster;

import constants.Images;
import javafx.scene.image.Image;

public class CarNormal extends Car {

	private static final Image DEFAULT_IMAGE = Images.normalCar;
	private static final double DEFAULT_HEALTH = 100;
	private static final double DEFAULT_ARMOR = 0;
	private static final double DEFAULT_MS  = 1.3;
	private static final int DEFAULT_MONEY = 10;
	
	
	public CarNormal(double x, double y, double health, double armor, double moveSpeed,
			int money) {
		super(DEFAULT_IMAGE, x, y, health, armor, moveSpeed, money, SoldierNormal.class);
		level = health/DEFAULT_HEALTH-1;
	}
	
	public CarNormal(double x, double y, double modifier) {
		this(x, y, DEFAULT_HEALTH*(1+modifier), DEFAULT_ARMOR+(0.5*modifier), 
				DEFAULT_MS*(1+modifier*0.05), (int)(DEFAULT_MONEY*(1+modifier*0.2)));
		level = modifier;
	}


}
