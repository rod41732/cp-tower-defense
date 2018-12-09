package model.monster;

import constants.Images;
import javafx.scene.image.Image;

public class PlaneHeavy extends Plane {
	private static final Image DEFAULT_IMAGE = Images.heavyPlane;
	private static final double DEFAULT_HEALTH = 250;
	private static final double DEFAULT_ARMOR = 20;
	private static final double DEFAULT_MS  = 0.4;
	private static final int DEFAULT_MONEY = 13;
	
	
	
	public PlaneHeavy(double x, double y, double health, double armor,
			double moveSpeed, int money) {
		super(DEFAULT_IMAGE, x, y, health, armor, moveSpeed, money);
		level = health/DEFAULT_HEALTH-1;
	}
	
	public PlaneHeavy(double x, double y, double modifier) {
		this(x, y, DEFAULT_HEALTH*(1+modifier), DEFAULT_ARMOR*(1+modifier*0.2), 
				DEFAULT_MS*(1+modifier*0.05), (int)(DEFAULT_MONEY*(1+modifier*0.2)));
		
	}
}
