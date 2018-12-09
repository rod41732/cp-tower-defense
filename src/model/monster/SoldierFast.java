package model.monster;

import constants.Images;
import javafx.scene.image.Image;

public class SoldierFast extends Soldier {

	private static final Image DEFAULT_IMAGE = Images.fastSoldier;
	private static final double DEFAULT_HEALTH = 15;
	private static final double DEFAULT_ARMOR = 0;
	private static final double DEFAULT_MS  = 2;
	private static final int DEFAULT_MONEY = 3;
	
	
	
	public SoldierFast(double x, double y, double health, double armor,
			double moveSpeed, int money) {
		super(DEFAULT_IMAGE, x, y, health, armor, moveSpeed, money);
		level = health/DEFAULT_HEALTH-1;
	}
	
	public SoldierFast(double x, double y, double modifier) {
		this(x, y,DEFAULT_HEALTH*(1+modifier), DEFAULT_ARMOR+(0.5+modifier), 
				DEFAULT_MS*(1+modifier*0.05), (int)(DEFAULT_MONEY*(1+modifier*0.2)));
		level = modifier;
	}
	

}
