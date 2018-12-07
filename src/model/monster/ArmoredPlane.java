package model.monster;

import constants.Images;
import javafx.scene.image.Image;

public class ArmoredPlane extends Plane {
	private static final Image DEFAULT_IMAGE = Images.armoredPlane;
	private static final double DEFAULT_HEALTH = 170;
	private static final double DEFAULT_ARMOR = 10;
	private static final double DEFAULT_MS  = 0.8;
	private static final int DEFAULT_MONEY = 8;
	
	
	public ArmoredPlane(double x, double y, double health, double armor,
			double moveSpeed, int money) {
		super(DEFAULT_IMAGE, x, y, health, armor, moveSpeed, money);
		level = health/DEFAULT_HEALTH-1;
	}
	
	public ArmoredPlane(double x, double y, double modifier) {
		this(x, y, DEFAULT_HEALTH*(1+modifier), DEFAULT_ARMOR*(1+modifier*0.2), 
				DEFAULT_MS*(1+modifier*0.05), (int)(DEFAULT_MONEY*(1+modifier*0.2)));
		level = modifier;
	}
	
}
