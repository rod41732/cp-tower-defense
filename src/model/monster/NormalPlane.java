package model.monster;

import constants.Images;
import javafx.scene.image.Image;

public class NormalPlane extends FlyingMonster {
	private static final Image DEFAULT_IMAGE = Images.normalPlane;
	private static final double DEFAULT_HEALTH =100;
	private static final double DEFAULT_ARMOR = 3;
	private static final double DEFAULT_MS  = 1.2;
	private static final int DEFAULT_MONEY = 5;
	private static final double DEFAULT_SIZE = 0.5;
	
	
	
	public NormalPlane(double x, double y, double health, double armor,
			double moveSpeed, int money) {
		super("Normal Plane", DEFAULT_IMAGE, x, y, DEFAULT_SIZE, health, armor, moveSpeed, money);
	}
	
	public NormalPlane(double x, double y, double modifier) {
		super("Normal Plane", DEFAULT_IMAGE, x, y, DEFAULT_SIZE,
				DEFAULT_HEALTH*(1+modifier), DEFAULT_ARMOR+(0.5*modifier), 
				DEFAULT_MS*(1+modifier*0.05), (int)(DEFAULT_MONEY*(1+modifier*0.2)));
		
	}
}
