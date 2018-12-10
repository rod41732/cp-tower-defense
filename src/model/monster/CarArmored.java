package model.monster;

import constants.Images;
import controller.game.GameManager;
import javafx.scene.image.Image;
import util.cpp.pff;

public class CarArmored extends Car {

	private static final Image DEFAULT_IMAGE = Images.armoredCar;
	private static final double DEFAULT_HEALTH = 150;
	private static final double DEFAULT_ARMOR = 5;
	private static final double DEFAULT_MS  = 1.0;
	private static final int DEFAULT_MONEY = 12;

	public CarArmored(double x, double y, double health, double armor, double moveSpeed,
			int money) {
		super(DEFAULT_IMAGE , x, y, health, armor, moveSpeed, money, SoldierFast.class);
		level = health/DEFAULT_HEALTH-1;
	}
	
	public CarArmored(double x, double y, double modifier) {
		this(x, y, DEFAULT_HEALTH*(1+modifier), DEFAULT_ARMOR*(1+modifier*0.2), 
				DEFAULT_MS*(1+modifier*0.05), (int)(DEFAULT_MONEY*(1+modifier*0.2)));
		level = modifier;
	}
	
}
