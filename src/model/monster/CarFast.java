package model.monster;

import constants.Images;
import javafx.scene.image.Image;

public class CarFast extends Car {

	private static final Image DEFAULT_IMAGE = Images.fastCar;
	private static final double DEFAULT_HEALTH = 80;
	private static final double DEFAULT_ARMOR = 0;
	private static final double DEFAULT_MS  = 1.7;
	private static final int DEFAULT_MONEY = 10;
	
	
	public CarFast(double x, double y, double health, double armor, double moveSpeed,
			int money) {
		super(DEFAULT_IMAGE , x, y, health, armor, moveSpeed, money, SoldierFast.class);
		level = health/DEFAULT_HEALTH-1;
	}
	
	public CarFast(double x, double y, double modifier) {
		this(x, y,DEFAULT_HEALTH*(1+modifier), DEFAULT_ARMOR+(0.5+modifier), 
				DEFAULT_MS*(1+modifier*0.05), (int)(DEFAULT_MONEY*(1+modifier*0.2)));
		level = modifier;
	}

}
