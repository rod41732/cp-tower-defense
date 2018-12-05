package model.monster;

import constants.Images;
import javafx.scene.image.Image;

public class HeavyPlane extends FlyingMonster {
	private static final Image DEFAULT_IMAGE = Images.heavyPlane;
	private static final double DEFAULT_HEALTH = 250;
	private static final double DEFAULT_ARMOR = 20;
	private static final double DEFAULT_MS  = 0.4;
	private static final int DEFAULT_MONEY = 13;
	private static final double DEFAULT_SIZE = 0.5;
	
	
	
	public HeavyPlane(double x, double y, double health, double armor,
			double moveSpeed, int money) {
		super("Heavy Plane", DEFAULT_IMAGE, x, y, DEFAULT_SIZE, health, armor, moveSpeed, money);
	}
	
	public HeavyPlane(double x, double y, double modifier) {
		super("Heavy Plane", DEFAULT_IMAGE, x, y, DEFAULT_SIZE,
				DEFAULT_HEALTH*(1+modifier), DEFAULT_ARMOR*(1+modifier*0.2), 
				DEFAULT_MS*(1+modifier*0.05), (int)(DEFAULT_MONEY*(1+modifier*0.2)));
		
	}
}
